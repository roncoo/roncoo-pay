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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.roncoo.pay.reconciliation.entity.RpAccountCheckBatch;
import com.roncoo.pay.reconciliation.enums.BatchStatusEnum;
import com.roncoo.pay.reconciliation.service.RpAccountCheckBatchService;
import com.roncoo.pay.reconciliation.utils.XmlUtils;
import com.roncoo.pay.reconciliation.vo.ReconciliationEntityVo;

/**
 * 微信对账单解析器 .
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
@Component("WEIXINParser")
public class WEIXINParser implements ParserInterface {

	private static final Log LOG = LogFactory.getLog(WEIXINParser.class);

	private static final String DATE_FORMAT_STYLE = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	private RpAccountCheckBatchService rpAccountCheckBatchService;

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
	public List<ReconciliationEntityVo> parser(File file, Date billDate, RpAccountCheckBatch batch) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String billDateStr = sdf.format(billDate);

		// 判断返回的file文件是否正确
		this.isError(file, batch);
		if (batch.getStatus() != null) {
			if (batch.getStatus().equals(BatchStatusEnum.ERROR.name()) || batch.getStatus().equals(BatchStatusEnum.NOBILL.name())) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("对账失败, 对账日期: " + billDateStr + ", batchStatus: " + BatchStatusEnum.ERROR + ", bankMsg: [" + batch.getBankErrMsg() + "], checkFailMsg: [" + batch.getCheckFailMsg() + "]");
				}
				return null;
			}
		}
		// file中有数据，解析
		try {
			List<String> list = FileUtils.readLines(file, "UTF-8");
			// 对账文件过滤空值
			for (Iterator<String> it = list.iterator(); it.hasNext();) {
				if (StringUtils.isBlank(it.next())) {
					it.remove();
				}
			}

			List<ReconciliationEntityVo> sheetList = null;
			sheetList = parseSuccess(list, billDateStr, batch);
			return sheetList;
		} catch (IOException e) {
			LOG.error("解析微信账单文件失败", e);
			return null;
		}

	}

	/**
	 * 解析文件
	 * 
	 * @param list
	 * @param billDate
	 *            账单日
	 * @param batch
	 *            对账批次记录
	 * @return
	 */
	private List<ReconciliationEntityVo> parseSuccess(List<String> list, String billDate, RpAccountCheckBatch batch) {
		// 交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,企业红包金额,商品名称,商户数据包,手续费,费率
		// `2016-02-16
		// 20:30:28,`wx3798432a27e0c92a,`1263453701,`1308363301,`,`1000010956201602163321502558,`PAY2016021610017753,`ozSK7wgGhLpgZ09x_OjkKgz0Zeis,`MICROPAY,`SUCCESS,`CFT,`CNY,`32.50,`0.00,`erp
		// product,`,`0.20000,`0.60%
		// `2016-02-16
		// 18:42:38,`wx3798432a27e0c92a,`1263453701,`1308363301,`,`1000020956201602163316153533,`PAY2016021610017685,`ozSK7wswIt3nBfUxsJDp5hWZSm_8,`MICROPAY,`SUCCESS,`CFT,`CNY,`95.00,`0.00,`erp
		// product,`,`0.57000,`0.60%

		// 总交易单数,总交易额,总退款金额,总企业红包退款金额,手续费总金额
		// `383,`32903.45,`0.00,`0.00,`197.45000

		String title = "交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,企业红包金额,商品名称,商户数据包,手续费,费率";
		String totalTitle = "总交易单数,总交易额,总退款金额,总企业红包退款金额,手续费总金额";

		Pattern titlePattern = Pattern.compile("(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?)$");
		Pattern pattern = Pattern.compile("^`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?)$");

		Pattern totalTitlePattern = Pattern.compile("(.*?),(.*?),(.*?),(.*?),(.*?)$");
		Pattern totalPattern = Pattern.compile("^`(.*?),`(.*?),`(.*?),`(.*?),`(.*?)$");

		// 校验标题行
		String titleRawData = list.remove(0);
		if (!titlePattern.matcher(titleRawData).find()) {
			batch.setStatus(BatchStatusEnum.FAIL.name());
			batch.setCheckFailMsg("校验标题行不通过, rawdata[" + titleRawData + "], 期望值[" + title + "]");
			return null;
		}

		// 解析统计数据
		String totalRawData = list.remove(list.size() - 1); // 这两行的顺序不能变
		String totalTitleRawData = list.remove(list.size() - 1);
		if (!totalTitlePattern.matcher(totalTitleRawData).find()) {
			batch.setStatus(BatchStatusEnum.FAIL.name());
			batch.setCheckFailMsg("校验统计标题行不通过, rawdata[" + totalTitleRawData + "], 期望值[" + totalTitle + "]");
			return new ArrayList<ReconciliationEntityVo>();
		}
		Matcher totalMatcher = totalPattern.matcher(totalRawData);
		if (totalMatcher.find()) {
			// 总交易单数
			String totalCount = totalMatcher.group(1);
			// 总交易额
			String totalAmountStr = totalMatcher.group(2);
			// 总退款金额
			String refundAmountStr = totalMatcher.group(3);
			// 手续费总金额
			String feeAmountStr = totalMatcher.group(5);

			try {
				batch.setBankTradeCount(Integer.parseInt(totalCount));
				// 微信账单金额单位是元
				batch.setBankTradeAmount(new BigDecimal(totalAmountStr));
				batch.setBankRefundAmount(new BigDecimal(refundAmountStr));
				batch.setBankFee(new BigDecimal(feeAmountStr));
			} catch (NumberFormatException e) {
				LOG.warn("解析统计行失败, billDate[" + billDate + "], billType[SUCCESS], rawdata[" + totalRawData + "]", e);
				batch.setStatus(BatchStatusEnum.FAIL.name());
				batch.setCheckFailMsg("解析统计行失败, rawdata[" + totalRawData + "]");
				// 恢复空值
				batch.setBankTradeCount(null);
				batch.setBankTradeAmount(null);
				batch.setBankRefundAmount(null);
				batch.setBankFee(null);
				return null;
			}
		} else {
			batch.setStatus(BatchStatusEnum.FAIL.name());
			batch.setCheckFailMsg("匹配统计行失败, rawdata[" + totalRawData + "]");
			return new ArrayList<ReconciliationEntityVo>();
		}

		// 解析出来的数据保存在list中
		List<ReconciliationEntityVo> entityVoList = new ArrayList<ReconciliationEntityVo>();
		for (String rawData : list) {
			ReconciliationEntityVo entityVo = new ReconciliationEntityVo();
			entityVoList.add(entityVo);

			Matcher matcher = pattern.matcher(rawData);
			if (matcher.find()) {

				// 交易时间
				String tradeTimeStr = matcher.group(1);
				// 微信订单号(微信流水号)
				String bankTrxNo = matcher.group(6);
				// 商户订单号(平台传递给微信的银行订单号)
				String bankOrderNo = matcher.group(7);
				// 交易状态
				String bankTradeStatus = matcher.group(10);
				// 总金额
				String orderAmount = matcher.group(13);
				// 企业红包金额
				// String discountAmount = matcher.group(14);
				// 手续费
				String bankFee = matcher.group(17);

				try {
					// 设置支付时间
					SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STYLE);
					entityVo.setOrderTime(sdf.parse(tradeTimeStr));
				} catch (ParseException e) {
					LOG.warn("解析交易时间出错, billDate[" + billDate + "], billType[SUCCESS], tradeTime[" + tradeTimeStr + "], rawdata[" + rawData + "]", e);
					batch.setStatus(BatchStatusEnum.FAIL.name());
					batch.setCheckFailMsg("解析交易时间出错, tradeTime[" + tradeTimeStr + "], rawdata[" + rawData + "]");
					return null;
				}
				// 设置微信流水号
				entityVo.setBankTrxNo(bankTrxNo);
				// 设置平台银行订单号
				entityVo.setBankOrderNo(bankOrderNo);
				// 设置微信订单状态（默认全部是success）
				entityVo.setBankTradeStatus(bankTradeStatus);
				// 设置微信账单金额:(单位是元)
				entityVo.setBankAmount(new BigDecimal(orderAmount));
				// 设置银行
				entityVo.setBankFee(new BigDecimal(bankFee));
			} else {
				batch.setStatus(BatchStatusEnum.FAIL.name());
				batch.setCheckFailMsg("匹配账单明细失败, rawdata[" + rawData + "]");
				return null;
			}
		}

		return entityVoList;
	}

	/**
	 * 微信接口返回fail时
	 * 
	 * @param file
	 *            对账文件
	 * @param batch
	 *            对账批次记录
	 */
	public void isError(File file, RpAccountCheckBatch batch) {
		// <xml><return_code><![CDATA[FAIL]]></return_code>
		// <return_msg><![CDATA[No Bill Exist]]></return_msg>
		// </xml>
		try {
			String content = FileUtils.readFileToString(file, "UTF-8");
			if (content.contains("<return_code>")) {

				Map<String, Object> map = XmlUtils.xmlToMap(content);
				String returnMsg = map.get("return_msg") != null ? map.get("return_msg").toString() : "";
				batch.setBankErrMsg(returnMsg);
				// 判断是没有数据还是下载失败
				// 注意：如果是没有数据，还是需要继续对账处理，为了把平台数据放入缓冲池，如果是下载失败，直接不进行下一步，保存batch
				if (returnMsg.contains("No Bill Exist")) {
					batch.setStatus(BatchStatusEnum.NOBILL.name());
				} else {
					batch.setStatus(BatchStatusEnum.ERROR.name());
					rpAccountCheckBatchService.saveData(batch);
				}
			}
		} catch (JDOMException e) {
			LOG.error("解析微信账单(判断返回是否正确)失败", e);
		} catch (IOException e) {
			LOG.error("解析微信账单(判断返回是否正确)失败", e);
		}
	}

}
