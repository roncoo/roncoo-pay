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
 * 对账批次实体 .
 * 
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class RpAccountCheckBatch extends BaseEntity {

	// 对账批次号
	private String batchNo;

	// 账单时间(账单交易发生时间)
	private Date billDate;

	// 账单类型(默认全部是交易成功)
	private String billType;

	// 批次处理状态, 已处理, 未处理
	private String handleStatus;

	// 银行类型 WEIXIN ALIPAY
	private String bankType;

	// 所有差错总单数
	private Integer mistakeCount;

	// 待处理的差错总单数
	private Integer unhandleMistakeCount;

	// 平台总交易单数
	private Integer tradeCount;

	// 银行总交易单数
	private Integer bankTradeCount;

	// 平台交易总金额
	private BigDecimal tradeAmount;

	// 银行交易总金额
	private BigDecimal bankTradeAmount;

	// 平台退款总金额
	private BigDecimal refundAmount;

	// 银行退款总金额
	private BigDecimal bankRefundAmount;

	// 平台总手续费, 单位元
	private BigDecimal fee;

	// 银行总手续费, 单位元
	private BigDecimal bankFee;

	// 原始对账文件存放地址
	private String orgCheckFilePath;

	// 解析后文件存放地址
	private String releaseCheckFilePath;

	// 解析状态
	private String releaseStatus;

	/** 解析检查失败的描述信息 */
	private String checkFailMsg;

	/** 银行返回的错误信息 */
	private String bankErrMsg;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo == null ? null : batchNo.trim();
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType == null ? null : billType.trim();
	}

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus == null ? null : handleStatus.trim();
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType == null ? null : bankType.trim();
	}

	public Integer getMistakeCount() {
		return mistakeCount;
	}

	public void setMistakeCount(Integer mistakeCount) {
		this.mistakeCount = mistakeCount;
	}

	public Integer getUnhandleMistakeCount() {
		return unhandleMistakeCount;
	}

	public void setUnhandleMistakeCount(Integer unhandleMistakeCount) {
		this.unhandleMistakeCount = unhandleMistakeCount;
	}

	public Integer getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(Integer tradeCount) {
		this.tradeCount = tradeCount;
	}

	public Integer getBankTradeCount() {
		return bankTradeCount;
	}

	public void setBankTradeCount(Integer bankTradeCount) {
		this.bankTradeCount = bankTradeCount;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public BigDecimal getBankTradeAmount() {
		return bankTradeAmount;
	}

	public void setBankTradeAmount(BigDecimal bankTradeAmount) {
		this.bankTradeAmount = bankTradeAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
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

	public String getOrgCheckFilePath() {
		return orgCheckFilePath;
	}

	public void setOrgCheckFilePath(String orgCheckFilePath) {
		this.orgCheckFilePath = orgCheckFilePath == null ? null : orgCheckFilePath.trim();
	}

	public String getReleaseCheckFilePath() {
		return releaseCheckFilePath;
	}

	public void setReleaseCheckFilePath(String releaseCheckFilePath) {
		this.releaseCheckFilePath = releaseCheckFilePath == null ? null : releaseCheckFilePath.trim();
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus == null ? null : releaseStatus.trim();
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getCheckFailMsg() {
		return checkFailMsg;
	}

	public void setCheckFailMsg(String checkFailMsg) {
		this.checkFailMsg = checkFailMsg;
	}

	public String getBankErrMsg() {
		return bankErrMsg;
	}

	public void setBankErrMsg(String bankErrMsg) {
		this.bankErrMsg = bankErrMsg;
	}

}