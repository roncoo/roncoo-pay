package com.roncoo.pay.trade.vo;

import com.roncoo.pay.trade.enums.TradeStatusEnum;

/**
 * <b>功能说明:
 * </b>
 *
 * @author Peter
 *         <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public class F2FPayResultVo {

    /**
     * 交易状态
     */
    private String status;

    /**
     * 交易流水号流水号
     */
    private String trxNo;

    /**
     * 商户订单号
     */
    private String orderNo;

    /**
     * 支付KEY
     */
    private String payKey;

    /** 产品名称 **/
    private String productName;

    /** 支付备注 **/
    private String  remark;

    /** 下单Ip **/
    private String orderIp;

    /** 备注字段1 **/
    private String field1;

    /** 备注字段2 **/
    private String field2;

    /** 备注字段3 **/
    private String field3;

    /** 备注字段4 **/
    private String field4;

    /** 备注字段5 **/
    private String field5;

    /**
     * 签名数据
     */
    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTrxNo() {
        return trxNo;
    }

    public void setTrxNo(String trxNo) {
        this.trxNo = trxNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderIp() {
        return orderIp;
    }

    public void setOrderIp(String orderIp) {
        this.orderIp = orderIp;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
