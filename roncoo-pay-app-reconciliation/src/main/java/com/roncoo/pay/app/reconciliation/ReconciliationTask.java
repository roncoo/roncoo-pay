/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roncoo.pay.app.reconciliation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.roncoo.pay.app.reconciliation.biz.ReconciliationCheckBiz;
import com.roncoo.pay.app.reconciliation.biz.ReconciliationFileDownBiz;
import com.roncoo.pay.app.reconciliation.biz.ReconciliationFileParserBiz;
import com.roncoo.pay.app.reconciliation.biz.ReconciliationValidateBiz;
import com.roncoo.pay.app.reconciliation.utils.DateUtil;
import com.roncoo.pay.app.reconciliation.utils.SpringContextUtil;
import com.roncoo.pay.app.reconciliation.vo.ReconciliationInterface;
import com.roncoo.pay.reconciliation.entity.RpAccountCheckBatch;
import com.roncoo.pay.reconciliation.enums.BatchStatusEnum;
import com.roncoo.pay.reconciliation.service.RpAccountCheckBatchService;
import com.roncoo.pay.reconciliation.vo.ReconciliationEntityVo;
import com.roncoo.pay.user.service.BuildNoService;

/**
 * 对账处理(包括下载对账文件、转换对账文件、对账) .
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class ReconciliationTask {

	private static final Log LOG = LogFactory.getLog(ReconciliationTask.class);

	public static void main(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		try {
			// 加载Spring配置文件
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring-context.xml" });
			// 初始化SpringContextUtil
			final SpringContextUtil ctxUtil = new SpringContextUtil();
			ctxUtil.setApplicationContext(context);

			@SuppressWarnings("rawtypes")
			// 获取全部有效的对账接口(目前是写死了，可以做持久化到数据库，再查出来)
			List reconciliationInterList = ReconciliationInterface.getInterface();

			// 获取业务biz实体
			ReconciliationFileDownBiz fileDownBiz = (ReconciliationFileDownBiz) SpringContextUtil.getBean("reconciliationFileDownBiz");
			ReconciliationFileParserBiz parserBiz = (ReconciliationFileParserBiz) SpringContextUtil.getBean("reconciliationFileParserBiz");
			ReconciliationCheckBiz checkBiz = (ReconciliationCheckBiz) SpringContextUtil.getBean("reconciliationCheckBiz");
			ReconciliationValidateBiz validateBiz = (ReconciliationValidateBiz) SpringContextUtil.getBean("reconciliationValidateBiz");
			RpAccountCheckBatchService batchService = (RpAccountCheckBatchService) SpringContextUtil.getBean("rpAccountCheckBatchService");
			BuildNoService buildNoService = (BuildNoService) SpringContextUtil.getBean("buildNoService");

			// 根据不同的渠道发起对账
			for (int num = 0; num < reconciliationInterList.size(); num++) {
				// 判断接口是否正确
				ReconciliationInterface reconciliationInter = (ReconciliationInterface) reconciliationInterList.get(num);
				if (reconciliationInter == null) {
					LOG.info("对账接口信息" + reconciliationInter + "为空");
					continue;
				}
				// 获取需要对账的对账单时间
				Date billDate = DateUtil.addDay(new Date(), -reconciliationInter.getBillDay());
				// 获取对账渠道
				String interfaceCode = reconciliationInter.getInterfaceCode();

				/** step1:判断是否对过账 **/
				RpAccountCheckBatch batch = new RpAccountCheckBatch();
				Boolean checked = validateBiz.isChecked(interfaceCode, billDate);
				if (checked) {
					LOG.info("账单日[" + sdf.format(billDate) + "],支付方式[" + interfaceCode + "],已经对过账，不能再次发起自动对账。");
					continue;
				}
				// 创建对账批次
				batch.setCreater("reconciliationSystem");
				batch.setCreateTime(new Date());
				batch.setBillDate(billDate);
				batch.setBatchNo(buildNoService.buildReconciliationNo());
				batch.setBankType(interfaceCode);

				/** step2:对账文件下载 **/
				File file = null;
				try {
					LOG.info("ReconciliationFileDownBiz,对账文件下载开始");
					file = fileDownBiz.downReconciliationFile(interfaceCode, billDate);
					if (file == null) {
						continue;
					}
					LOG.info("对账文件下载结束");
				} catch (Exception e) {
					LOG.error("对账文件下载异常:", e);
					batch.setStatus(BatchStatusEnum.FAIL.name());
					batch.setRemark("对账文件下载异常");
					batchService.saveData(batch);
					continue;
				}

				/** step3:解析对账文件 **/
				List<ReconciliationEntityVo> bankList = null;
				try {
					LOG.info("=ReconciliationFileParserBiz=>对账文件解析开始>>>");

					// 解析文件
					bankList = parserBiz.parser(batch, file, billDate, interfaceCode);
					// 如果下载文件异常，退出
					if (BatchStatusEnum.ERROR.name().equals(batch.getStatus())) {
						continue;
					}
					LOG.info("对账文件解析结束");
				} catch (Exception e) {
					LOG.error("对账文件解析异常:", e);
					batch.setStatus(BatchStatusEnum.FAIL.name());
					batch.setRemark("对账文件解析异常");
					batchService.saveData(batch);
					continue;
				}

				/** step4:对账流程 **/
				try {
					checkBiz.check(bankList, interfaceCode, batch);
				} catch (Exception e) {
					LOG.error("对账异常:", e);
					batch.setStatus(BatchStatusEnum.FAIL.name());
					batch.setRemark("对账异常");
					batchService.saveData(batch);
					continue;
				}

			}

			/** step5:清理缓冲池 **/
			// 如果缓冲池中有三天前的数据就清理掉并记录差错
			validateBiz.validateScratchPool();
		} catch (Exception e) {
			LOG.error("roncoo-app-reconciliation error:", e);
		}

	}
}
