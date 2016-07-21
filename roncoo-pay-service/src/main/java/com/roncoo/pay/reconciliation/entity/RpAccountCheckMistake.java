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
package com.roncoo.pay.reconciliation.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.roncoo.pay.common.core.entity.BaseEntity;

/**
 * 对账差错表.
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class RpAccountCheckMistake extends BaseEntity {

	/** 对账批次号 **/
	private String accountCheckBatchNo;

	/** 账单日期 **/
	private Date billDate;

	/** 银行类型 WEIXIN ALIPAY **/
	private String bankType;

	/** 下单时间 **/
	private Date orderTime;

	/** 商家名称 **/
	private String merchantName;

	/** 商家编号 **/
	private String merchantNo;

	/** 商家订单号 **/
	private String orderNo;

	/** 平台交易时间 **/
	private Date tradeTime;

	/** 平台流水号 **/
	private String trxNo;

	/** 平台交易金额 **/
	private BigDecimal orderAmount;

	/** 平台退款金额 **/
	private BigDecimal refundAmount;

	/** 平台交易状态 **/
	private String tradeStatus;

	/** 平台手续费 **/
	private BigDecimal fee;

	/** 银行交易时间 **/
	private Date bankTradeTime;

	/** 银行订单号 **/
	private String bankOrderNo;

	/** 银行流水号 **/
	private String bankTrxNo;

	/** 银行交易状态 **/
	private String bankTradeStatus;

	/** 银行交易金额 **/
	private BigDecimal bankAmount;

	/** 银行退款金额 **/
	private BigDecimal bankRefundAmount;

	/** 银行手续费 **/
	private BigDecimal bankFee;

	/** 差错类型 **/
	private String errType;

	/** 处理状态, 已处理, 未处理 **/
	private String handleStatus;

	/** 处理结果 **/
	private String handleValue;

	private String handleRemark;

	private String operatorName;

	private String operatorAccountNo;

	public String getAccountCheckBatchNo() {
		return accountCheckBatchNo;
	}

	public void setAccountCheckBatchNo(String accountCheckBatchNo) {
		this.accountCheckBatchNo = accountCheckBatchNo == null ? null : accountCheckBatchNo.trim();
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType == null ? null : bankType.trim();
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName == null ? null : merchantName.trim();
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo == null ? null : merchantNo.trim();
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getTrxNo() {
		return trxNo;
	}

	public void setTrxNo(String trxNo) {
		this.trxNo = trxNo == null ? null : trxNo.trim();
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus == null ? null : tradeStatus.trim();
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Date getBankTradeTime() {
		return bankTradeTime;
	}

	public void setBankTradeTime(Date bankTradeTime) {
		this.bankTradeTime = bankTradeTime;
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo == null ? null : bankOrderNo.trim();
	}

	public String getBankTrxNo() {
		return bankTrxNo;
	}

	public void setBankTrxNo(String bankTrxNo) {
		this.bankTrxNo = bankTrxNo == null ? null : bankTrxNo.trim();
	}

	public String getBankTradeStatus() {
		return bankTradeStatus;
	}

	public void setBankTradeStatus(String bankTradeStatus) {
		this.bankTradeStatus = bankTradeStatus == null ? null : bankTradeStatus.trim();
	}

	public BigDecimal getBankAmount() {
		return bankAmount;
	}

	public void setBankAmount(BigDecimal bankAmount) {
		this.bankAmount = bankAmount;
	}

	public BigDecimal getBankRefundAmount() {
		return bankRefundAmount;
	}

	public void setBankRefundAmount(BigDecimal bankRefundAmount) {
		this.bankRefundAmount = bankRefundAmount;
	}

	public BigDecimal getBankFee() {
		return bankFee;
	}

	public void setBankFee(BigDecimal bankFee) {
		this.bankFee = bankFee;
	}

	public String getErrType() {
		return errType;
	}

	public void setErrType(String errType) {
		this.errType = errType == null ? null : errType.trim();
	}

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus == null ? null : handleStatus.trim();
	}

	public String getHandleValue() {
		return handleValue;
	}

	public void setHandleValue(String handleValue) {
		this.handleValue = handleValue == null ? null : handleValue.trim();
	}

	public String getHandleRemark() {
		return handleRemark;
	}

	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark == null ? null : handleRemark.trim();
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName == null ? null : operatorName.trim();
	}

	public String getOperatorAccountNo() {
		return operatorAccountNo;
	}

	public void setOperatorAccountNo(String operatorAccountNo) {
		this.operatorAccountNo = operatorAccountNo == null ? null : operatorAccountNo.trim();
	}

}