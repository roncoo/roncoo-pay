package com.roncoo.pay.trade.vo;

import com.roncoo.pay.trade.enums.AuthStatusEnum;

import java.io.Serializable;

public class AuthResultVo implements Serializable {

    private static final long serialVersionUID = 841503118815819232L;

    private String merchantNo;

    private String orderNo;

    private String userName;

    private String phone;

    private String idNo;

    private String bankAccountNo;

    private AuthStatusEnum authStatusEnum;

    private String authMsg;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public AuthStatusEnum getAuthStatusEnum() {
        return authStatusEnum;
    }

    public void setAuthStatusEnum(AuthStatusEnum authStatusEnum) {
        this.authStatusEnum = authStatusEnum;
    }

    public String getAuthMsg() {
        return authMsg;
    }

    public void setAuthMsg(String authMsg) {
        this.authMsg = authMsg;
    }
}
