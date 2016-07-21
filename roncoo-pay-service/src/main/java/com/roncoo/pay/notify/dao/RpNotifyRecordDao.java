package com.roncoo.pay.notify.dao;

import com.roncoo.pay.common.core.dao.BaseDao;
import com.roncoo.pay.notify.entity.RpNotifyRecord;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/6/2  上午11:20
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public interface RpNotifyRecordDao  extends BaseDao<RpNotifyRecord> {

    RpNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo, String notifyType);

    int deleteByPrimaryKey(String id);

    int insertSelective(RpNotifyRecord record);

    RpNotifyRecord selectByPrimaryKey(String id);

    int updateByPrimaryKey(RpNotifyRecord record);
}