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
 * 对账暂存池.
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class RpAccountCheckMistakeScratchPool extends BaseEntity {

	/** 商品名称 **/
	private String productName;

	/** 商户订单号 **/
	private String merchantOrderNo;

	/** 流水号 **/
	private String trxNo;

	/** 银行流水号 **/
	private String bankOrderNo;

	private String bankTrxNo;

	private BigDecimal orderAmount;

	private BigDecimal platIncome;

	private BigDecimal feeRate;

	private BigDecimal platCost;

	private BigDecimal platProfit;

	private String payWayCode;

	private String payWayName;

	private Date paySuccessTime;

	private Date completeTime;

	private String isRefund;

	private Short refundTimes;

	private BigDecimal successRefundAmount;

	private String batchNo;

	private Date billDate;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
	}

	public String getTrxNo() {
		return trxNo;
	}

	public void setTrxNo(String trxNo) {
		this.trxNo = trxNo == null ? null : trxNo.trim();
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

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getPlatIncome() {
		return platIncome;
	}

	public void setPlatIncome(BigDecimal platIncome) {
		this.platIncome = platIncome;
	}

	public BigDecimal getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}

	public BigDecimal getPlatCost() {
		return platCost;
	}

	public void setPlatCost(BigDecimal platCost) {
		this.platCost = platCost;
	}

	public BigDecimal getPlatProfit() {
		return platProfit;
	}

	public void setPlatProfit(BigDecimal platProfit) {
		this.platProfit = platProfit;
	}

	public String getPayWayCode() {
		return payWayCode;
	}

	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode == null ? null : payWayCode.trim();
	}

	public String getPayWayName() {
		return payWayName;
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName == null ? null : payWayName.trim();
	}

	public Date getPaySuccessTime() {
		return paySuccessTime;
	}

	public void setPaySuccessTime(Date paySuccessTime) {
		this.paySuccessTime = paySuccessTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public String getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(String isRefund) {
		this.isRefund = isRefund == null ? null : isRefund.trim();
	}

	public Short getRefundTimes() {
		return refundTimes;
	}

	public void setRefundTimes(Short refundTimes) {
		this.refundTimes = refundTimes;
	}

	public BigDecimal getSuccessRefundAmount() {
		return successRefundAmount;
	}

	public void setSuccessRefundAmount(BigDecimal successRefundAmount) {
		this.successRefundAmount = successRefundAmount;
	}

}