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
package com.roncoo.pay.app.reconciliation.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付宝对账文件vo转换实体.
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class AlipayAccountLogVO {

	/** 支付宝账户余额 **/
	private BigDecimal balance = BigDecimal.ZERO;

	/** 购买人帐号 **/
	private String buyerAccount;

	/** 商品名称 **/
	private String goodsTitle;

	/** 平台账户入账金额 **/
	private BigDecimal income = BigDecimal.ZERO;

	/** 平台账户出款金额 **/
	private BigDecimal outcome = BigDecimal.ZERO;

	/** 商户订单号 **/
	private String merchantOrderNo;

	/** 银行费率 **/
	private BigDecimal bankRate;

	/** 订单金额 **/
	private BigDecimal totalFee = BigDecimal.ZERO;

	/** 银行流水 **/
	private String tradeNo;

	/** 交易类型 **/
	private String transType;

	/** 交易时间 **/
	private Date transDate;

	/** 银行(支付宝)该笔订单收取的手续费 **/
	private BigDecimal bankFee = BigDecimal.ZERO;

	/** 支付宝账户余额 **/
	public BigDecimal getBalance() {
		return balance;
	}

	/** 支付宝账户余额 **/
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/** 购买人帐号 **/
	public String getBuyerAccount() {
		return buyerAccount;
	}

	/** 购买人帐号 **/
	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}

	/** 商品名称 **/
	public String getGoodsTitle() {
		return goodsTitle;
	}

	/** 商品名称 **/
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	/** 平台账户入账金额 **/
	public BigDecimal getIncome() {
		return income;
	}

	/** 平台账户入账金额 **/
	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	/** 平台账户出款金额 **/
	public BigDecimal getOutcome() {
		return outcome;
	}

	/** 平台账户出款金额 **/
	public void setOutcome(BigDecimal outcome) {
		this.outcome = outcome;
	}

	/** 商户订单号 **/
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	/** 商户订单号 **/
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	/** 银行费率 **/
	public BigDecimal getBankRate() {
		return bankRate;
	}

	/** 银行费率 **/
	public void setBankRate(BigDecimal bankRate) {
		this.bankRate = bankRate;
	}

	/** 订单金额 **/
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	/** 订单金额 **/
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	/** 银行流水 **/
	public String getTradeNo() {
		return tradeNo;
	}

	/** 银行流水 **/
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	/** 交易类型 **/
	public String getTransType() {
		return transType;
	}

	/** 交易类型 **/
	public void setTransType(String transType) {
		this.transType = transType;
	}

	/** 交易时间 **/
	public Date getTransDate() {
		return transDate;
	}

	/** 交易时间 **/
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	/** 银行(支付宝)该笔订单收取的手续费 **/
	public BigDecimal getBankFee() {
		return bankFee;
	}

	/** 银行(支付宝)该笔订单收取的手续费 **/
	public void setBankFee(BigDecimal bankFee) {
		this.bankFee = bankFee;
	}

}
