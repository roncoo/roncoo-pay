package com.roncoo.pay.trade.vo;

import java.io.Serializable;

/**
 * 小程序支付返回实体
 */
public class ProgramPayResultVo implements Serializable {

    private static final long serialVersionUID = 651601163997960632L;

    /**
     * 交易状态
     */
    private String status;

    /**
     * 支付信息
     */
    private String payMessage;

    /**
     * 银行返回信息
     */
    private String bankReturnMsg;

    /**
     * 签名结果
     */
    private String sign;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
