package com.fast.pay.notify.dao;

import com.fast.pay.common.core.dao.BaseDao;
import com.fast.pay.notify.entity.RpNotifyRecord;
import com.fast.pay.notify.entity.RpNotifyRecord;

public interface RpNotifyRecordDao  extends BaseDao<RpNotifyRecord> {

    RpNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo, String notifyType);

    int deleteByPrimaryKey(String id);

    int insertSelective(RpNotifyRecord record);

    RpNotifyRecord selectByPrimaryKey(String id);

    int updateByPrimaryKey(RpNotifyRecord record);
}