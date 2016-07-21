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
package com.roncoo.pay.app.reconciliation.parser;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import com.roncoo.pay.app.reconciliation.vo.AlipayAccountLogVO;
import com.roncoo.pay.common.core.enums.PayWayEnum;
import com.roncoo.pay.reconciliation.entity.RpAccountCheckBatch;
import com.roncoo.pay.reconciliation.enums.BatchStatusEnum;
import com.roncoo.pay.reconciliation.vo.ReconciliationEntityVo;

/**
 * 支付宝对账单解析器 .
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
@Component("ALIPAYParser")
public class ALIPAYParser implements ParserInterface {

	private static final Log LOG = LogFactory.getLog(ALIPAYParser.class);

	private static final String DATE_FORMAT_STYLE = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 解析器的入口方法，每个解析器都必须有这个方法
	 * 
	 * @param file
	 *            需要解析的文件
	 * @param billDate
	 *            账单日
	 * @param batch
	 *            对账批次记录
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ReconciliationEntityVo> parser(File file, Date billDate, RpAccountCheckBatch batch) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STYLE);

		// xml解析file文件
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(file);
			// 使用dom4j的Xpath找到AccountQueryAccountLogVO节点
			List projects = document.selectNodes("alipay/response/account_page_query_result/account_log_list/AccountQueryAccountLogVO");

			Iterator it = projects.iterator();
			// 业务类型为在线支付的账户历史
			List<AlipayAccountLogVO> tradeList = new ArrayList<AlipayAccountLogVO>();
			// 除在线支付，其他业务类型的账户历史
			List<AlipayAccountLogVO> otherAll = new ArrayList<AlipayAccountLogVO>();
			while (it.hasNext()) {
				AlipayAccountLogVO vo = new AlipayAccountLogVO();
				Element elm = (Element) it.next();
				List<Element> childElements = elm.elements();
				for (Element child : childElements) {
					String name = child.getName();
					// 赋值
					switch (name) {
					case "balance":
						vo.setBalance(new BigDecimal(child.getText()));
						break;
					case "rate":
						vo.setBankRate(new BigDecimal(("").equals(child.getText()) ? "0" : child.getText()));
						break;
					case "buyer_account":
						vo.setBuyerAccount(child.getText());
						break;
					case "goods_title":
						vo.setGoodsTitle(child.getText());
						break;
					case "income":
						vo.setIncome(new BigDecimal(("").equals(child.getText()) ? "0" : child.getText()));
						break;
					case "outcome":
						vo.setOutcome(new BigDecimal(("").equals(child.getText()) ? "0" : child.getText()));
						break;
					case "merchant_out_order_no":
						vo.setMerchantOrderNo(child.getText());
						break;
					case "total_fee":
						vo.setTotalFee(new BigDecimal(("").equals(child.getText()) ? "0" : child.getText()));
						break;
					case "trade_no":// 银行流水
						vo.setTradeNo(child.getText());
						break;
					case "trans_code_msg":// 交易类型
						vo.setTransType(child.getText());
						break;
					case "trans_date":
						String dateStr = child.getText();
						Date date;
						try {
							date = sdf.parse(dateStr);
						} catch (ParseException e) {
							date = billDate;
						}
						vo.setTransDate(date);
						break;

					default:
						break;
					}
				}

				// 过滤出即时支付的订单
				if ("在线支付".equals(vo.getTransType())) {
					tradeList.add(vo);
				} else {
					otherAll.add(vo);
				}
			}
			// 因为支付宝只提供账户变动历史的接口，在线支付和支付宝收取手续费是两笔记录，
			// 所以该接口提供的数据需要做整理，把在线支付订单的手续费找出来
			for (AlipayAccountLogVO trade : tradeList) {
				String tradeNo = trade.getTradeNo();
				for (AlipayAccountLogVO other : otherAll) {
					String otherTradeNo = other.getTradeNo();
					if (tradeNo.equals(otherTradeNo)) {
						trade.setBankFee(other.getOutcome());
					}
				}
			}
			// 把AlipayAccountLogVOvo对象转为ReconciliationEntityVo，统计后，返回list
			List<ReconciliationEntityVo> list = new ArrayList<ReconciliationEntityVo>();

			// 交易笔数、金额、手续费累计
			int totalCount = 0;
			BigDecimal totalAmount = BigDecimal.ZERO;
			BigDecimal totalFee = BigDecimal.ZERO;

			for (AlipayAccountLogVO trade : tradeList) {
				// 统计
				totalCount++;
				totalAmount = totalAmount.add(trade.getTotalFee());
				totalFee = totalFee.add(trade.getBankFee());

				// 把AlipayAccountLogVO转为ReconciliationEntityVo
				ReconciliationEntityVo vo = new ReconciliationEntityVo();
				vo.setAccountCheckBatchNo(batch.getBatchNo());
				vo.setBankAmount(trade.getTotalFee());
				vo.setBankFee(trade.getBankFee());
				vo.setBankOrderNo(trade.getMerchantOrderNo());
				vo.setBankRefundAmount(BigDecimal.ZERO);
				vo.setBankTradeStatus("SUCCESS");
				vo.setBankTradeTime(trade.getTransDate());
				vo.setBankTrxNo(trade.getTradeNo());
				vo.setBankType(PayWayEnum.ALIPAY.name());
				vo.setOrderTime(trade.getTransDate());
				list.add(vo);
			}
			batch.setBankTradeCount(totalCount);
			batch.setBankTradeAmount(totalAmount);
			batch.setBankRefundAmount(BigDecimal.ZERO);
			batch.setBankFee(totalFee);

			return list;

		} catch (DocumentException e) {
			LOG.warn("解析对账文件异常", e);
			batch.setStatus(BatchStatusEnum.FAIL.name());
			batch.setCheckFailMsg("解析对账文件异常, payway[" + PayWayEnum.ALIPAY.name() + "], billdata[" + sdf.format(billDate) + "]");
			return null;
		}

	}
}
