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
package com.roncoo.pay.user.entity;

import com.roncoo.pay.common.core.entity.BaseEntity;

/**
 * 用户银行卡信息
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public class RpUserBankAccount extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 银行卡开户所在省 **/
	private String province;

	/** 银行卡开户所在城市 **/
	private String city;

	/** 银行卡开户所在区 **/
	private String areas;

	/** 银行卡开户具体地址 **/
	private String street;

	/** 银行卡开户名eg：张三 **/
	private String bankAccountName;

	/** 银行卡卡号 **/
	private String bankAccountNo;

	/** 银行卡类型 **/
	private String bankAccountType;

	/** 证件类型 **/
	private String cardType;

	/** 证件号码 **/
	private String cardNo;

	/** 手机号码 **/
	private String mobileNo;

	/** 银行名称 **/
	private String bankName;

	/** 银行编号eg:ICBC **/
	private String bankCode;

	/** 用户编号 **/
	private String userNo;

	/** 是否默认 **/
	private String isDefault;
	
	/** 银行卡开户所在省 **/
	public String getProvince() {
		return province;
	}

	/** 银行卡开户所在省 **/
	public void setProvince(String province) {
		this.province = province;
	}

	/** 银行卡开户所在城市 **/
	public String getCity() {
		return city;
	}

	/** 银行卡开户所在城市 **/
	public void setCity(String city) {
		this.city = city;
	}

	/** 银行卡开户所在区 **/
	public String getAreas() {
		return areas;
	}

	/** 银行卡开户所在区 **/
	public void setAreas(String areas) {
		this.areas = areas;
	}

	/** 银行卡开户具体地址 **/
	public String getStreet() {
		return street;
	}

	/** 银行卡开户具体地址 **/
	public void setStreet(String street) {
		this.street = street;
	}

	/** 银行卡开户名eg：张三 **/
	public String getBankAccountName() {
		return bankAccountName;
	}

	/** 银行卡开户名eg：张三 **/
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	/** 银行卡卡号 **/
	public String getBankAccountNo() {
		return bankAccountNo;
	}

	/** 银行卡卡号 **/
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	/** 银行编号eg:ICBC **/
	public String getBankCode() {
		return bankCode;
	}

	/** 银行编号eg:ICBC **/
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/** 银行卡类型 **/
	public String getBankAccountType() {
		return bankAccountType;
	}

	/** 银行卡类型 **/
	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	/** 证件类型 **/
	public String getCardType() {
		return cardType;
	}

	/** 证件类型 **/
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/** 证件号码 **/
	public String getCardNo() {
		return cardNo;
	}

	/** 证件号码 **/
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/** 手机号码 **/
	public String getMobileNo() {
		return mobileNo;
	}

	/** 手机号码 **/
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/** 银行名称 **/
	public String getBankName() {
		return bankName;
	}

	/** 银行名称 **/
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/** 用户编号 **/
	public String getUserNo() {
		return userNo;
	}

	/** 用户编号 **/
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	/** 是否默认 **/
	public String getIsDefault() {
		return isDefault;
	}

	/** 是否默认 **/
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
}
