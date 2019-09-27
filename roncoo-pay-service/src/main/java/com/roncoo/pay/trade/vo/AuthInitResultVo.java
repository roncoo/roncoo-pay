package com.roncoo.pay.trade.vo;

import com.roncoo.pay.common.core.enums.PayTypeEnum;
import com.roncoo.pay.common.core.enums.PayWayEnum;
import com.roncoo.pay.trade.enums.TradeStatusEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class AuthInitResultVo implements Serializable {

    private static final long serialVersionUID = 3312403855750120397L;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 商户号
     */
    private String merchantNo;

    /**
     * 商户订单号
     */
    private String merchantOrderNo;

    /**
     *
     */
    private String payKey;

    /**
     * 支付方式列表
     */
    private Map<String, PayTypeEnum> payTypeEnumMap;

    /**
     * 订单状态
     */
    private TradeStatusEnum tradeStatus = TradeStatusEnum.WAITING_PAYMENT;

    /**
     * 是否鉴权
     */
    private boolean isAuth = false;

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public TradeStatusEnum getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(TradeStatusEnum tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public Map<String, PayTypeEnum> getPayTypeEnumMap() {
        return payTypeEnumMap;
    }

    public void setPayTypeEnumMap(Map<String, PayTypeEnum> payTypeEnumMap) {
        this.payTypeEnumMap = payTypeEnumMap;
    }
}
