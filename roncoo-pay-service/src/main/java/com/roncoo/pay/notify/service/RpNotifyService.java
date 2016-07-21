package com.roncoo.pay.notify.service;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.notify.entity.RpNotifyRecord;
import com.roncoo.pay.notify.entity.RpNotifyRecordLog;

import java.util.Map;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/6/2  上午10:41
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */


public interface RpNotifyService {

    /**
     * 发送消息通知
     * @param notifyUrl 通知地址
     * @param merchantOrderNo   商户订单号
     * @param merchantNo    商户编号
     */
    public void notifySend(String notifyUrl,String merchantOrderNo,String merchantNo);


    /**
     * 通过ID获取通知记录
     * @param id
     * @return
     */
    public RpNotifyRecord getNotifyRecordById(String id);

    /**
     * 根据商户编号,商户订单号,通知类型获取通知记录
     * @param merchantNo    商户编号
     * @param merchantOrderNo   商户订单号
     * @param notifyType    消息类型
     * @return
     */
    public RpNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo , String merchantOrderNo , String notifyType);


    public PageBean<RpNotifyRecord>  queryNotifyRecordListPage(PageParam pageParam , Map<String, Object> paramMap);
    /**
     * 创建消息通知
     * @param rpNotifyRecord
     */
    public long createNotifyRecord(RpNotifyRecord rpNotifyRecord);

    /**
     * 修改消息通知
     * @param rpNotifyRecord
     */
    public void updateNotifyRecord(RpNotifyRecord rpNotifyRecord);

    /**
     * 创建消息通知记录
     * @param rpNotifyRecordLog
     * @return
     */
    public long createNotifyRecordLog(RpNotifyRecordLog rpNotifyRecordLog);

}
