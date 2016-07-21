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
package com.roncoo.pay.reconciliation.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roncoo.pay.reconciliation.entity.RpAccountCheckBatch;
import com.roncoo.pay.reconciliation.entity.RpAccountCheckMistake;
import com.roncoo.pay.reconciliation.entity.RpAccountCheckMistakeScratchPool;
import com.roncoo.pay.reconciliation.enums.BatchStatusEnum;
import com.roncoo.pay.reconciliation.enums.MistakeHandleStatusEnum;
import com.roncoo.pay.reconciliation.service.RpAccountCheckBatchService;
import com.roncoo.pay.reconciliation.service.RpAccountCheckMistakeScratchPoolService;
import com.roncoo.pay.reconciliation.service.RpAccountCheckMistakeService;
import com.roncoo.pay.reconciliation.service.RpAccountCheckTransactionService;
import com.roncoo.pay.trade.service.RpTradeReconciliationService;

/**
 * 对账数据事务一致性service.
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
@Service("rpAccountCheckTransactionService")
public class RpAccountCheckTransactionServiceImpl implements RpAccountCheckTransactionService {

	private static final Log LOG = LogFactory.getLog(RpAccountCheckTransactionServiceImpl.class);

	@Autowired
	private RpAccountCheckBatchService rpAccountCheckBatchService;
	@Autowired
	private RpAccountCheckMistakeService rpAccountCheckMistakeService;
	@Autowired
	private RpAccountCheckMistakeScratchPoolService rpAccountCheckMistakeScratchPoolService;
	@Autowired
	private RpTradeReconciliationService rpTradeReconciliationService;

	/**
	 * 为了保证事务一致性，把对数据库的操作放入一个事务中
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveDatasaveDate(RpAccountCheckBatch batch, List<RpAccountCheckMistake> mistakeList, List<RpAccountCheckMistakeScratchPool> insertScreatchRecordList, List<RpAccountCheckMistakeScratchPool> removeScreatchRecordList) {
		LOG.info("========>  对完数据后业务数据处理开始=========>");

		LOG.info("===> step1:保存批次记录====");
		if (batch.getStatus() == null) {
			batch.setStatus(BatchStatusEnum.SUCCESS.name());
		}
		rpAccountCheckBatchService.saveData(batch);

		LOG.info("===> step2:保存差错记录====总共[" + mistakeList.size() + "]条");
		rpAccountCheckMistakeService.saveListDate(mistakeList);

		LOG.info("===> step3:保存记录到缓存池中====总共[" + insertScreatchRecordList.size() + "]条");
		rpAccountCheckMistakeScratchPoolService.savaListDate(insertScreatchRecordList);

		LOG.info("===> step4:从缓存池中删除已匹配记录====总共[" + removeScreatchRecordList.size() + "]条");
		rpAccountCheckMistakeScratchPoolService.deleteFromPool(removeScreatchRecordList);

		LOG.info("<========  对完数据后业务数据处理结束<=========");
	}

	/**
	 * 
	 * @param list
	 * @param mistakeList
	 */
	@Transactional(rollbackFor = Exception.class)
	public void removeDateFromPool(List<RpAccountCheckMistakeScratchPool> list, List<RpAccountCheckMistake> mistakeList) {
		LOG.info("========>  清理缓冲池中没有对账的数据，记录差错=========>");

		LOG.info("===> step1:保存差错记录====总共[" + mistakeList.size() + "]条");
		rpAccountCheckMistakeService.saveListDate(mistakeList);

		LOG.info("===> step2:从缓存池中删除已匹配记录====总共[" + list.size() + "]条");
		rpAccountCheckMistakeScratchPoolService.deleteFromPool(list);

		LOG.info("<========  清理缓冲池中没有对账的数据，记录差错结束<=========");
	}

	/**
	 * 差错处理
	 * 
	 * @param mistake
	 *            差错信息
	 */

	@Transactional(rollbackFor = Exception.class)
	public void handle(String id, String handleType, String handleRemark) {
		// 根据id查询
		RpAccountCheckMistake mistake = rpAccountCheckMistakeService.getDataById(id);
		mistake.setHandleStatus(MistakeHandleStatusEnum.HANDLED.name());
		mistake.setHandleRemark(handleRemark);
		// 修改差错记录
		rpAccountCheckMistakeService.updateData(mistake);

		Boolean bank = false;
		if ("bank".equals(handleType.trim())) {
			mistake.setHandleValue("以银行为准");
			bank = true;
		}
		// 以平台数据为准：只需修改差错记录
		if (!bank) {
			return;
		}

		switch (mistake.getErrType()) {

		case "BANK_MISS":// 银行不存在该订单
			// 以银行为准
			if (bank) {
				// 把订单改为失败，减款
				String trxNo = mistake.getTrxNo();
				rpTradeReconciliationService.bankMissOrBankFailBaseBank(trxNo);
			}

			break;

		case "PLATFORM_SHORT_STATUS_MISMATCH":// 银行支付成功，平台支付不成功,默认以银行为准
			// 以银行为准
			if (bank) {
				String trxNo = mistake.getTrxNo();
				String bankTrxNo = mistake.getBankTrxNo();
				rpTradeReconciliationService.platFailBankSuccess(trxNo, bankTrxNo);
			}
			break;

		case "PLATFORM_SHORT_CASH_MISMATCH":// 平台需支付金额比银行实际支付金额少
			// 以银行为准
			if (bank) {
				// 累加金额
				rpTradeReconciliationService.handleAmountMistake(mistake, true);
			}
			break;

		case "PLATFORM_OVER_CASH_MISMATCH":// 银行实际支付金额比平台需支付金额少
			// 以银行为准
			if (bank) {
				// 支付记录减款
				rpTradeReconciliationService.handleAmountMistake(mistake, false);
			}
			break;

		case "PLATFORM_OVER_STATUS_MISMATCH":// 平台支付成功，银行支付不成功(和银行漏单一致)
			// 以银行为准
			if (bank) {
				// 把订单改为失败，减款
				String trxNo = mistake.getTrxNo();
				rpTradeReconciliationService.bankMissOrBankFailBaseBank(trxNo);
			}
			break;

		case "FEE_MISMATCH":// 手续费不匹配
			// 以银行为准
			if (bank) {
				rpTradeReconciliationService.handleFeeMistake(mistake);
			}
			break;

		case "PLATFORM_MISS":// 平台不存在该订单(暂时不考虑这种情况)
			break;

		default:
			break;
		}

	}
}