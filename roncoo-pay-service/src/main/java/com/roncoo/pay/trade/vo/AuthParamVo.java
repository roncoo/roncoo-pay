package com.roncoo.pay.trade.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class AuthParamVo implements Serializable {

    private static final long serialVersionUID = -428316450503841861L;
    /**
     * 支付Key
     */
    @Size(max = 32, message = "支付Key[payKey]长度最大32位")
    @NotNull(message = "支付Key[payKey]不能为空")
    private String payKey;

    /**
     * 订单号
     */
    @Size(max = 32, message = "订单号[orderNo]长度最大32位")
    @NotNull(message = "订单号[orderNo]不能为空")
    private String orderNo;

    /**
     * 用户姓名
     */
    @Size(max = 50, message = "用户姓名[userName]长度最大50位")
    @NotNull(message = "用户姓名[userName]不能为空")
    private String userName;

    /**
     * 手机号
     */
    @Size(max = 11, message = "手机号码[phone]长度最大11位")
    @NotNull(message = "手机号码[phone]不能为空")
    private String phone;

    /**
     * 身份证号
     */
    @Size(max = 18, message = "身份证号[idNo]长度最大18位")
    @NotNull(message = "身份证号[idNo]不能为空")
    private String idNo;

    /**
     * 银行卡号
     */
    @Size(max = 20, message = "银行卡号[bankAccountNo]长度最大20位")
    @NotNull(message = "银行卡号[bankAccountNo]不能为空")
    private String bankAccountNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 签名
     */
    @NotNull(message = "数据签名[sign]不能为空")
    private String sign;

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
