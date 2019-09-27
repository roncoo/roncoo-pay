package com.roncoo.pay.trade.vo;

import com.roncoo.pay.trade.enums.TradeStatusEnum;

import java.io.Serializable;

public class AuthProgramInitResultVo implements Serializable {

    private static final long serialVersionUID = -5167161500901848253L;

    private String mchOrderNo;

    /**
     * 交易状态 YES 成功,其他失败
     */
    private String errCode;

    /**
     * 支付信息
     */
    private String payMessage;

    /**
     * 银行返回信息
     */
    private String bankReturnMsg;

    /**
     * 订单状态
     */
    private TradeStatusEnum tradeStatus = TradeStatusEnum.WAITING_PAYMENT;

    /**
     * 是否鉴权
     */
    private boolean isAuth = false;

    /**
     * 签名结果
     */
    private String sign;

    public String getMchOrderNo() {
        return mchOrderNo;
    }

    public void setMchOrderNo(String mchOrderNo) {
        this.mchOrderNo = mchOrderNo;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getPayMessage() {
        return payMessage;
    }

    public void setPayMessage(String payMessage) {
        this.payMessage = payMessage;
    }

    public String getBankReturnMsg() {
        return bankReturnMsg;
    }

    public void setBankReturnMsg(String bankReturnMsg) {
        this.bankReturnMsg = bankReturnMsg;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
