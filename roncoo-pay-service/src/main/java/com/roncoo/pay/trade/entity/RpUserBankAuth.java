package com.roncoo.pay.trade.entity;

import com.roncoo.pay.common.core.entity.BaseEntity;

import java.io.Serializable;

public class RpUserBankAuth extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6173089060762552675L;

    /**
     * 商户号
     */
    private String merchantNo;

    /**
     * 支付订单号
     */
    private String payOrderNo;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 银行卡号
     */
    private String bankAccountNo;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }
}
