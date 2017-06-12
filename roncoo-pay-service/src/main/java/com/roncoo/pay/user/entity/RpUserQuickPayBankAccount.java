package com.roncoo.pay.user.entity;

import com.roncoo.pay.common.core.entity.BaseEntity;
import com.roncoo.pay.common.core.enums.PublicStatusEnum;
import com.roncoo.pay.common.core.utils.StringUtil;

/**
 * 
 * 类功能说明：快捷支付银行卡<br/>
 * 类修改者：<br/>
 * 修改日期：<br/>
 * 修改说明：<br/>
 * 公司名称：广州市领课科技有限公司 <br/>
 * 作者：shenjialong <br/>
 * 创建时间：2016年12月27日 <br/>
 * 版本：V1.0 <br/>
 */
public class RpUserQuickPayBankAccount extends BaseEntity {

	/** 银行卡开户所在省 **/
	private String province;

	/** 银行卡开户所在城市 **/
	private String city;

	/** 银行卡开户所在区 **/
	private String areas;

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

	/** CVN2信用卡确认码 **/
	private String cvn2;

	/** 信用卡到期时间 **/
	private String expDate;

	/** 是否默认 **/
	private String isDefault;
	/** 是否认证 **/
	private String isAuth;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getCvn2() {
		return cvn2;
	}

	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

	public String getStatusDesc() {
		if (StringUtil.isEmpty(this.getStatus())) {
			return "";
		} else {
			return PublicStatusEnum.getEnum(this.getStatus()).getDesc();
		}
	}
}
