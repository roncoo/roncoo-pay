package com.roncoo.pay.trade.bo;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 小程序支付请求Bo
 */
public class ProgramPayRequestBo implements Serializable {

    @Size(min = 16 , max = 32 , message = "商户Key[payKey]长度最小16位最大32位")
    @NotNull(message = "商户Key[payKey]不能为空")
    private String payKey;

    @Size(min = 1 , max = 30 , message = "[openId]长度最大30位")
    @NotNull(message = "[openId]不能为空")
    private String openId;

    @Size(max = 200 , message = "商品名称[productName]长度最大200位")
    @NotNull(message = "商品名称[productName]不能为空")
    private String productName;

    @Size(min = 5 ,  max = 20 , message = "商品订单号[orderNo]长度最小5位，最大20位")
    @NotNull(message = "商品订单号[orderNo]不能为空")
    private String orderNo;

    @Digits(integer = 12, fraction = 2, message = "订单金额格式有误")
    @NotNull(message = "订单金额[orderPrice]不能为空")
    private BigDecimal orderPrice;

    @Size(min = 1 ,  max = 20 , message = "订单IP[orderIp]长度最小1位，最大20位")
    @NotNull(message = "订单IP[orderIp]不能为空")
    private String orderIp;

    @Size(min = 1 ,  max = 8 , message = "订单日期[orderDate]长度最小1位，最大8位")
    @NotNull(message = "订单日期[orderDate]不能为空")
    private String orderDate;

    @Size(min = 1 ,  max = 14 , message = "订单时间[orderTime]长度最小1位，最大14位")
    @NotNull(message = "订单时间[orderTime]不能为空")
    private String orderTime;

    @Size(min = 1 ,  max = 200 , message = "异步通知地址[notifyUrl]长度最小1位，最大200位")
    @NotNull(message = "异步通知地址[notifyUrl]不能为空")
    private String notifyUrl;

    @NotNull(message = "签名[sign]不能为空")
    private String sign;

    private String remark;//支付备注

    @Size(min = 1 ,  max = 14 , message = "交易类型[payType]长度最小1位，最大14位")
    @NotNull(message = "交易类型[payType]不能为空")
    private String payType;//支付类型

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderIp() {
        return orderIp;
    }

    public void setOrderIp(String orderIp) {
        this.orderIp = orderIp;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    @Override
    public String toString() {
        return "ProgramPayRequestBo{" +
                "payKey='" + payKey + '\'' +
                ", openId='" + openId + '\'' +
                ", productName='" + productName + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", orderPrice=" + orderPrice +
                ", orderIp='" + orderIp + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", sign='" + sign + '\'' +
                ", remark='" + remark + '\'' +
                ", payType='" + payType + '\'' +
                '}';
    }
}
