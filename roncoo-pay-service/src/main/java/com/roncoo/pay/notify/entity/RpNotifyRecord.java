package com.roncoo.pay.notify.entity;

import com.roncoo.pay.common.core.entity.BaseEntity;
import com.roncoo.pay.notify.enums.NotifyStatusEnum;
import com.roncoo.pay.notify.enums.NotifyTypeEnum;

import java.util.Date;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/6/2  上午11:20
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public class RpNotifyRecord  extends BaseEntity {

    private static final long serialVersionUID = -6104194914044220447L;

//    private Long notifyId;
//
//    public Long getNotifyId() {
//        return notifyId;
//    }

    private Date createTime;

    /** 最后一次通知时间 **/
    private Date lastNotifyTime;

    /** 通知次数 **/
    private Integer notifyTimes = 0;

    /** 限制通知次数 **/
    private Integer limitNotifyTimes = 5;

    /** 通知URL **/
    private String url;

    /** 商户编号 **/
    private String merchantNo;

    /** 商户订单号 **/
    private String merchantOrderNo;

    /** 通知类型 NotifyTypeEnum **/
    private String notifyType;

    public RpNotifyRecord() {
        super();
    }

    public RpNotifyRecord(Date createTime, Date lastNotifyTime, Integer notifyTimes, Integer limitNotifyTimes, String url, String merchantNo,
                          String merchantOrderNo, NotifyStatusEnum status, NotifyTypeEnum type) {
        super();
        this.createTime = createTime;
        this.lastNotifyTime = lastNotifyTime;
        this.notifyTimes = notifyTimes;
        this.limitNotifyTimes = limitNotifyTimes;
        this.url = url;
        this.merchantNo = merchantNo;
        this.merchantOrderNo = merchantOrderNo;
        this.notifyType = type.name();
        super.setStatus(status.name());
    }


    /** 最后一次通知时间 **/
    public Date getLastNotifyTime() {
        return lastNotifyTime;
    }

    /** 最后一次通知时间 **/
    public void setLastNotifyTime(Date lastNotifyTime) {
        this.lastNotifyTime = lastNotifyTime;
    }

    /** 通知次数 **/
    public Integer getNotifyTimes() {
        return notifyTimes;
    }

    /** 通知次数 **/
    public void setNotifyTimes(Integer notifyTimes) {
        this.notifyTimes = notifyTimes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 限制通知次数 **/
    public Integer getLimitNotifyTimes() {
        return limitNotifyTimes;
    }

    /** 限制通知次数 **/
    public void setLimitNotifyTimes(Integer limitNotifyTimes) {
        this.limitNotifyTimes = limitNotifyTimes;
    }

    /** 通知URL **/
    public String getUrl() {
        return url;
    }

    /** 通知URL **/
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /** 商户编号 **/
    public String getMerchantNo() {
        return merchantNo;
    }

    /** 商户编号 **/
    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    /** 商户订单号 **/
    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    /** 商户订单号 **/
    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

}
