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
    private TradeStatusEnum tradeStatusEnum;

    /**
     * 银行流水号
     */
    private String bankTrxNo;

    /**
     * 银行返回结果
     */
    private String bankReturnMsg;


    public TradeStatusEnum getTradeStatusEnum() {
        return tradeStatusEnum;
    }

    public void setTradeStatusEnum(TradeStatusEnum tradeStatusEnum) {
        this.tradeStatusEnum = tradeStatusEnum;
    }

    public String getBankTrxNo() {
        return bankTrxNo;
    }

    public void setBankTrxNo(String bankTrxNo) {
        this.bankTrxNo = bankTrxNo;
    }

    public String getBankReturnMsg() {
        return bankReturnMsg;
    }

    public void setBankReturnMsg(String bankReturnMsg) {
        this.bankReturnMsg = bankReturnMsg;
    }
}
