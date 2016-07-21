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
package com.roncoo.pay.account.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.roncoo.pay.account.enums.SettModeTypeEnum;
import com.roncoo.pay.account.enums.SettRecordStatusEnum;
import com.roncoo.pay.common.core.entity.BaseEntity;
import com.roncoo.pay.user.enums.BankAccountTypeEnum;

/**
 * 结算记录
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public class RpSettRecord extends BaseEntity {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -507346932227359104L;

	/** 结算发起方式(参考 SettModeTypeEnum) **/
	private String settMode;

	/** 账户编号 **/
	private String accountNo;

	/** 用户编号 **/
	private String userNo;

	/** 用户类型 **/
	private String userType;

	/** 用户姓名 **/
	private String userName;

	/** 结算日期 **/
	private Date settDate;

	/** 银行编码 **/
	private String bankCode;
	
	/** 银行名称 **/
	private String bankName;

	/** 开户名 **/
	private String bankAccountName;

	/** 开户账户 **/
	private String bankAccountNo;

	/** 开户银行卡类型 **/
	private String bankAccountType;

	/** 开户行所在国家 **/
	private String country;

	/** 开户行所在省份 **/
	private String province;

	/** 开户行所在城市 **/
	private String city;

	/** 开户行所在区域 **/
	private String areas;

	/** 开户行全称 **/
	private String bankAccountAddress;

	/** 收款人手机号 **/
	private String mobileNo;

	/** 结算金额 **/
	private BigDecimal settAmount = BigDecimal.ZERO;

	/** 结算手续费 **/
	private BigDecimal settFee = BigDecimal.ZERO;

	/** 结算打款金额 **/
	private BigDecimal remitAmount = BigDecimal.ZERO;

	/** 结算状态(参考枚举:SettRecordStatusEnum) **/
	private String settStatus;

	/** 打款发送时间 **/
	private Date remitRequestTime;

	/** 打款确认时间 **/
	private Date remitConfirmTime;

	/** 打款备注 **/
	private String remitRemark;

	/** 操作员登录名 **/
	private String operatorLoginname;

	/** 操作员姓名 **/
	private String operatorRealname;

	/**
	 * 结算发起方式(参考SettModeTypeEnum)
	 * 
	 * @return
	 */
	public String getSettMode() {
		return settMode;
	}

	/**
	 * 结算发起方式(参考SettModeTypeEnum)
	 */
	public void setSettMode(String settMode) {
		this.settMode = settMode;
	}
	
	public String getSettModeDesc(){
		return SettModeTypeEnum.getEnum(this.getSettMode()).getDesc();
	}

	/** 收款人手机号 **/
	public String getMobileNo() {
		return mobileNo;
	}

	/** 收款人手机号 **/
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * 账户编号
	 * 
	 * @return
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * 账户编号
	 * 
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo == null ? null : accountNo.trim();
	}

	/**
	 * userType
	 * 
	 * @return the userType
	 * @since 1.0
	 */

	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * 用户编号
	 * 
	 * @return
	 */
	public String getUserNo() {
		return userNo;
	}

	/**
	 * 用户编号
	 * 
	 * @param userNo
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo == null ? null : userNo.trim();
	}

	/**
	 * 用户姓名
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 用户姓名
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	/**
	 * 结算日期
	 * 
	 * @return
	 */
	public Date getSettDate() {
		return settDate;
	}

	/**
	 * 结算日期
	 * 
	 * @param settDate
	 */
	public void setSettDate(Date settDate) {
		this.settDate = settDate;
	}

	/**
	 * 银行编码
	 * 
	 * @return
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * 银行编码
	 * 
	 * @param bankCode
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode == null ? null : bankCode.trim();
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * 开户名
	 * 
	 * @return
	 */
	public String getBankAccountName() {
		return bankAccountName;
	}

	/**
	 * 开户名
	 * 
	 * @param bankAccountName
	 */
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName == null ? null : bankAccountName.trim();
	}

	/**
	 * 开户账户
	 * 
	 * @return
	 */
	public String getBankAccountNo() {
		return bankAccountNo;
	}

	/**
	 * 开户账户
	 * 
	 * @param bankAccountNo
	 */
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo == null ? null : bankAccountNo.trim();
	}

	/** 开户银行卡类型 **/
	public String getBankAccountType() {
		return bankAccountType;
	}

	/** 开户银行卡类型 **/
	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}
	
	public String getBankAccountTypeDesc(){
		return BankAccountTypeEnum.getEnum(this.getBankAccountType()).getDesc();
	}

	/**
	 * 开户行所在国家
	 * 
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * 开户行所在国家
	 * 
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}

	/**
	 * 开户行所在省份
	 * 
	 * @return
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 开户行所在省份
	 * 
	 * @param province
	 */
	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	/**
	 * 开户行所在城市
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 开户行所在城市
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	/** 开户行所在区域 **/
	public String getAreas() {
		return areas;
	}

	/** 开户行所在区域 **/
	public void setAreas(String areas) {
		this.areas = areas == null ? null : areas.trim();
	}

	/**
	 * 开户行全称
	 * 
	 * @return
	 */
	public String getBankAccountAddress() {
		return bankAccountAddress;
	}

	/**
	 * 开户行全称
	 * 
	 * @param bankAccountAddress
	 */
	public void setBankAccountAddress(String bankAccountAddress) {
		this.bankAccountAddress = bankAccountAddress == null ? null : bankAccountAddress.trim();
	}

	/**
	 * 结算金额
	 * 
	 * @return
	 */
	public BigDecimal getSettAmount() {
		return settAmount;
	}

	/**
	 * 结算金额
	 * 
	 * @param settAmount
	 */
	public void setSettAmount(BigDecimal settAmount) {
		this.settAmount = settAmount;
	}

	/**
	 * 结算手续费
	 * 
	 * @return
	 */
	public BigDecimal getSettFee() {
		return settFee;
	}

	/**
	 * 结算手续费
	 * 
	 * @param settFee
	 */
	public void setSettFee(BigDecimal settFee) {
		this.settFee = settFee;
	}

	/**
	 * 结算打款金额
	 * 
	 * @return
	 */
	public BigDecimal getRemitAmount() {
		return remitAmount;
	}

	/**
	 * 结算打款金额
	 * 
	 * @param remitAmount
	 */
	public void setRemitAmount(BigDecimal remitAmount) {
		this.remitAmount = remitAmount;
	}

	/** 结算状态(参考枚举:SettRecordStatusEnum) **/
	public String getSettStatus() {
		return settStatus;
	}

	/** 结算状态(参考枚举:SettRecordStatusEnum) **/
	public void setSettStatus(String settStatus) {
		this.settStatus = settStatus;
	}

	/**
	 * 打款发送时间
	 * 
	 * @return
	 */
	public Date getRemitRequestTime() {
		return remitRequestTime;
	}

	/**
	 * 打款发送时间
	 * 
	 * @param remitRequestTime
	 */
	public void setRemitRequestTime(Date remitRequestTime) {
		this.remitRequestTime = remitRequestTime;
	}

	/**
	 * 打款确认时间
	 * 
	 * @return
	 */
	public Date getRemitConfirmTime() {
		return remitConfirmTime;
	}

	/**
	 * 打款确认时间
	 * 
	 * @param remitConfirmTime
	 */
	public void setRemitConfirmTime(Date remitConfirmTime) {
		this.remitConfirmTime = remitConfirmTime;
	}

	/**
	 * 打款备注
	 * 
	 * @return
	 */
	public String getRemitRemark() {
		return remitRemark;
	}

	/**
	 * 打款备注
	 * 
	 * @param remitRemark
	 */
	public void setRemitRemark(String remitRemark) {
		this.remitRemark = remitRemark == null ? null : remitRemark.trim();
	}

	/**
	 * 操作员登录名
	 * 
	 * @return
	 */
	public String getOperatorLoginname() {
		return operatorLoginname;
	}

	/**
	 * 操作员登录名
	 * 
	 * @param operatorLoginname
	 */
	public void setOperatorLoginname(String operatorLoginname) {
		this.operatorLoginname = operatorLoginname == null ? null : operatorLoginname.trim();
	}

	/**
	 * 操作员姓名
	 * 
	 * @return
	 */
	public String getOperatorRealname() {
		return operatorRealname;
	}

	/**
	 * 操作员姓名
	 * 
	 * @param operatorRealname
	 */
	public void setOperatorRealname(String operatorRealname) {
		this.operatorRealname = operatorRealname == null ? null : operatorRealname.trim();
	}
	
    public String getSettStatusDesc() {
    	return SettRecordStatusEnum.getEnum(this.getSettStatus()).getDesc();
    }

}