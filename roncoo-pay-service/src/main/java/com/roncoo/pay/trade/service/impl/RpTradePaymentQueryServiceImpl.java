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
package com.roncoo.pay.trade.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.trade.utils.MerchantApiUtil;
import com.roncoo.pay.trade.vo.PaymentOrderQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.pay.common.core.enums.PublicEnum;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.trade.dao.RpTradePaymentOrderDao;
import com.roncoo.pay.trade.dao.RpTradePaymentRecordDao;
import com.roncoo.pay.trade.entity.RpTradePaymentOrder;
import com.roncoo.pay.trade.entity.RpTradePaymentRecord;
import com.roncoo.pay.trade.enums.TradeStatusEnum;
import com.roncoo.pay.trade.service.RpTradePaymentQueryService;
import com.roncoo.pay.trade.vo.OrderPayResultVo;
import com.roncoo.pay.user.entity.RpUserPayConfig;
import com.roncoo.pay.user.exception.UserBizException;
import com.roncoo.pay.user.service.RpUserPayConfigService;

/**
 * <b>功能说明:交易模块查询类实现</b>
 * @author  Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */

@Service("rpTradePaymentQueryService")
public class RpTradePaymentQueryServiceImpl implements RpTradePaymentQueryService {
	@Autowired
	private RpTradePaymentRecordDao rpTradePaymentRecordDao;

	@Autowired
	private RpTradePaymentOrderDao rpTradePaymentOrderDao;

	@Autowired
	private RpUserPayConfigService rpUserPayConfigService;

	/**
	 * 根据参数查询交易记录List
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<RpTradePaymentRecord> listPaymentRecord(Map<String, Object> paramMap) {
		return rpTradePaymentRecordDao.listByColumn(paramMap);
	}

	/**
	 * 根据商户支付KEY 及商户订单号 查询支付结果
	 *
	 * @param payKey
	 *            商户支付KEY
	 * @param orderNo
	 *            商户订单号
	 * @return
	 */
	@Override
	public OrderPayResultVo getPayResult(String payKey, String orderNo) {

		RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByPayKey(payKey);
		if (rpUserPayConfig == null) {
			throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
		}

		String merchantNo = rpUserPayConfig.getUserNo();// 商户编号
		RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);

		RpTradePaymentRecord rpTradePaymentRecord = rpTradePaymentRecordDao.getSuccessRecordByMerchantNoAndMerchantOrderNo(rpTradePaymentOrder.getMerchantNo(), rpTradePaymentOrder.getMerchantOrderNo());

		OrderPayResultVo orderPayResultVo = new OrderPayResultVo();// 返回结果
		if (rpTradePaymentOrder != null && TradeStatusEnum.SUCCESS.name().equals(rpTradePaymentOrder.getStatus())) {// 支付记录为空,或者支付状态非成功
			orderPayResultVo.setStatus(PublicEnum.YES.name());// 设置支付状态
			orderPayResultVo.setOrderPrice(rpTradePaymentOrder.getOrderAmount());// 设置支付订单
			orderPayResultVo.setProductName(rpTradePaymentOrder.getProductName());// 设置产品名称
			String url = getMerchantNotifyUrl(rpTradePaymentRecord, rpTradePaymentOrder, rpTradePaymentRecord.getReturnUrl(), TradeStatusEnum.SUCCESS);
			orderPayResultVo.setReturnUrl(url);
		}

		return orderPayResultVo;
	}


	private String getMerchantNotifyUrl(RpTradePaymentRecord rpTradePaymentRecord ,RpTradePaymentOrder rpTradePaymentOrder ,String sourceUrl , TradeStatusEnum tradeStatusEnum){

		RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByUserNo(rpTradePaymentRecord.getMerchantNo());
		if (rpUserPayConfig == null){
			throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
		}

		Map<String , Object> paramMap = new HashMap<>();

		String payKey = rpUserPayConfig.getPayKey();// 企业支付KEY
		paramMap.put("payKey",payKey);
		String productName = rpTradePaymentRecord.getProductName(); // 商品名称
		paramMap.put("productName",productName);
		String orderNo = rpTradePaymentRecord.getMerchantOrderNo(); // 订单编号
		paramMap.put("orderNo",orderNo);
		BigDecimal orderPrice = rpTradePaymentRecord.getOrderAmount(); // 订单金额 , 单位:元
		paramMap.put("orderPrice",orderPrice);
		String payWayCode = rpTradePaymentRecord.getPayWayCode(); // 支付方式编码 支付宝: ALIPAY  微信:WEIXIN
		paramMap.put("payWayCode",payWayCode);
		paramMap.put("tradeStatus",tradeStatusEnum);//交易状态
		String orderDateStr = DateUtils.formatDate(rpTradePaymentOrder.getOrderDate(), "yyyyMMdd"); // 订单日期
		paramMap.put("orderDate",orderDateStr);
		String orderTimeStr = DateUtils.formatDate(rpTradePaymentOrder.getOrderTime(), "yyyyMMddHHmmss"); // 订单时间
		paramMap.put("orderTime",orderTimeStr);
		String remark = rpTradePaymentRecord.getRemark(); // 支付备注
		paramMap.put("remark",remark);
		String trxNo = rpTradePaymentRecord.getTrxNo();//支付流水号
		paramMap.put("trxNo",trxNo);

		String field1 = rpTradePaymentOrder.getField1(); // 扩展字段1
		paramMap.put("field1",field1);
		String field2 = rpTradePaymentOrder.getField2(); // 扩展字段2
		paramMap.put("field2",field2);
		String field3 = rpTradePaymentOrder.getField3(); // 扩展字段3
		paramMap.put("field3",field3);
		String field4 = rpTradePaymentOrder.getField4(); // 扩展字段4
		paramMap.put("field4",field4);
		String field5 = rpTradePaymentOrder.getField5(); // 扩展字段5
		paramMap.put("field5",field5);

		String paramStr = MerchantApiUtil.getParamStr(paramMap);
		String sign = MerchantApiUtil.getSign(paramMap, rpUserPayConfig.getPaySecret());
		String notifyUrl = sourceUrl + "?" + paramStr + "&sign=" + sign;

		return notifyUrl;
	}

	/**
	 * 根据银行订单号查询支付记录
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	public RpTradePaymentRecord getRecordByBankOrderNo(String bankOrderNo) {
		return rpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
	}
	
	/**
	 * 根据支付流水号查询支付记录
	 * 
	 * @param trxNo
	 * @return
	 */
	public RpTradePaymentRecord getRecordByTrxNo(String trxNo){
		return rpTradePaymentRecordDao.getByTrxNo(trxNo);
	}

	/**
	 * 分页查询支付订单
	 *
	 * @param pageParam
	 * @param paymentOrderQueryParam
	 * @return
	 */
	@Override
	public PageBean<RpTradePaymentOrder> listPaymentOrderPage(PageParam pageParam, PaymentOrderQueryParam paymentOrderQueryParam) {

		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("merchantNo", paymentOrderQueryParam.getMerchantNo());//商户编号
		paramMap.put("merchantName", paymentOrderQueryParam.getMerchantName());//商户名称
		paramMap.put("merchantOrderNo", paymentOrderQueryParam.getMerchantOrderNo());//商户订单号
		paramMap.put("fundIntoType", paymentOrderQueryParam.getFundIntoType());//资金流入类型
		paramMap.put("merchantOrderNo", paymentOrderQueryParam.getOrderDateBegin());//订单开始时间
		paramMap.put("merchantOrderNo", paymentOrderQueryParam.getOrderDateEnd());//订单结束时间
		paramMap.put("payTypeName", paymentOrderQueryParam.getPayTypeName());//支付类型
		paramMap.put("payWayName", paymentOrderQueryParam.getPayWayName());//支付类型
		paramMap.put("status", paymentOrderQueryParam.getStatus());//支付状态

		if (paymentOrderQueryParam.getOrderDateBegin() != null){
			paramMap.put("orderDateBegin", paymentOrderQueryParam.getOrderDateBegin());//支付状态
		}

		if (paymentOrderQueryParam.getOrderDateEnd() != null){
			paramMap.put("orderDateEnd", paymentOrderQueryParam.getOrderDateEnd());//支付状态
		}

		return rpTradePaymentOrderDao.listPage(pageParam,paramMap);
	}

	/**
	 * 分页查询支付记录
	 *
	 * @param pageParam
	 * @param paymentOrderQueryParam
	 * @return
	 */
	@Override
	public PageBean<RpTradePaymentRecord> listPaymentRecordPage(PageParam pageParam, PaymentOrderQueryParam paymentOrderQueryParam) {
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("merchantNo", paymentOrderQueryParam.getMerchantNo());//商户编号
		paramMap.put("merchantName", paymentOrderQueryParam.getMerchantName());//商户名称
		paramMap.put("merchantOrderNo", paymentOrderQueryParam.getMerchantOrderNo());//商户订单号
		paramMap.put("fundIntoType", paymentOrderQueryParam.getFundIntoType());//资金流入类型
		paramMap.put("merchantOrderNo", paymentOrderQueryParam.getOrderDateBegin());//订单开始时间
		paramMap.put("merchantOrderNo", paymentOrderQueryParam.getOrderDateEnd());//订单结束时间
		paramMap.put("payTypeName", paymentOrderQueryParam.getPayTypeName());//支付类型
		paramMap.put("payWayName", paymentOrderQueryParam.getPayWayName());//支付类型
		paramMap.put("status", paymentOrderQueryParam.getStatus());//支付状态

		if (paymentOrderQueryParam.getOrderDateBegin() != null){
			paramMap.put("orderDateBegin", paymentOrderQueryParam.getOrderDateBegin());//支付状态
		}

		if (paymentOrderQueryParam.getOrderDateEnd() != null){
			paramMap.put("orderDateEnd", paymentOrderQueryParam.getOrderDateEnd());//支付状态
		}

		return rpTradePaymentRecordDao.listPage(pageParam,paramMap);
	}
	
	/**
	 * 获取交易流水报表
	 * 
	 * @param merchantNo
	 * @return
	 */
	public List<Map<String, String>> getPaymentReport(String merchantNo){
		return rpTradePaymentRecordDao.getPaymentReport(merchantNo);
	}
	
	/**
	 * 获取交易方式报表
	 * 
	 * @param merchantNo
	 * @return
	 */
	public List<Map<String, String>> getPayWayReport(String merchantNo){
		return rpTradePaymentRecordDao.getPayWayReport(merchantNo);
	}
}
