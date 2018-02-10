package com.fast.pay.notify.dao;


import com.fast.pay.common.core.dao.BaseDao;
import com.fast.pay.notify.entity.RpNotifyRecordLog;
import com.fast.pay.notify.entity.RpNotifyRecordLog;


public interface RpNotifyRecordLogDao  extends BaseDao<RpNotifyRecordLog> {


    int deleteByPrimaryKey(String id);

    int insertSelective(RpNotifyRecordLog record);

    RpNotifyRecordLog selectByPrimaryKey(String id);


    int updateByPrimaryKey(RpNotifyRecordLog record);
}