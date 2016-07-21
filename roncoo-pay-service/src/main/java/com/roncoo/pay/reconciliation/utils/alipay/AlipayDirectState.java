package com.roncoo.pay.reconciliation.utils.alipay;
/**
 * @类功能说明：支付宝即时到账交易状态
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州韬睿艾德广告有限公司
 * @作者：Toreal-lrx 
 * @创建时间：2016-5-22 上午9:51:30 
 * @版本：V1.0
 */
public class AlipayDirectState {

    //交易创建，等待买家付款。
    public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
    
    //在指定时间段内未支付时关闭的交易；在交易完成全额退款成功时关闭的交易。
    public static final String TRADE_CLOSED = "TRADE_CLOSED";
    
    //交易成功，且可对该交易做操作，如：多级分润、退款等。
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
    
    //等待卖家收款（买家付款后，如果卖家账号被冻结）。
    public static final String TRADE_PENDING = "TRADE_PENDING";
    
    //交易成功且结束，即不可再做任何操作。
    public static final String TRADE_FINISHED = "TRADE_FINISHED";
    
}
