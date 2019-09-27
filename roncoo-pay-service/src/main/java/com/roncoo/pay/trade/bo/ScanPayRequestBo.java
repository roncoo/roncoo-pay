package com.roncoo.pay.trade.bo;


import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户主扫请求Bo
 */
public class ScanPayRequestBo implements Serializable {

    @Size(min = 16 , max = 32 , message = "商户Key[payKey]长度最小16位最大32位")
    @NotNull(message = "商户Key[payKey]不能为空")
    private String payKey;

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

    @NotNull(message = "订单有效期[orderPeriod]不能为空")
    private Integer orderPeriod;

    @Size(min = 1 ,  max = 200 , message = "页面跳转地址[returnUrl]长度最小1位，最大200位")
    @NotNull(message = "页面跳转地址[returnUrl]不能为空")
    private String returnUrl;

    @Size(min = 1 ,  max = 200 , message = "异步通知地址[notifyUrl]长度最小1位，最大200位")
    @NotNull(message = "异步通知地址[notifyUrl]不能为空")
    private String notifyUrl;

    @NotNull(message = "签名[sign]不能为空")
    private String sign;

    private String remark;//支付备注

    /**
     * 支付类型
     */
    private String payType;//支付类型

    /**
     *	分期付款笔数
     */
    private Integer numberOfStages;//分期笔数

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

    public Integer getOrderPeriod() {
        return orderPeriod;
    }

    public void setOrderPeriod(Integer orderPeriod) {
        this.orderPeriod = orderPeriod;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
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

    public Integer getNumberOfStages() {
        return numberOfStages;
    }

    public void setNumberOfStages(Integer numberOfStages) {
        this.numberOfStages = numberOfStages;
    }

    @Override
    public String toString() {
        return "ScanPayRequestBo{" +
                "payKey='" + payKey + '\'' +
                ", productName='" + productName + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", orderPrice=" + orderPrice +
                ", orderIp='" + orderIp + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", orderPeriod=" + orderPeriod +
                ", returnUrl='" + returnUrl + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", sign='" + sign + '\'' +
                ", remark='" + remark + '\'' +
                ", payType='" + payType + '\'' +
                ", numberOfStages=" + numberOfStages +
                '}';
    }
}
