package com.roncoo.pay.notify.dao;


import com.roncoo.pay.common.core.dao.BaseDao;
import com.roncoo.pay.notify.entity.RpNotifyRecordLog;


public interface RpNotifyRecordLogDao  extends BaseDao<RpNotifyRecordLog> {


    int deleteByPrimaryKey(String id);

    int insertSelective(RpNotifyRecordLog record);

    RpNotifyRecordLog selectByPrimaryKey(String id);


    int updateByPrimaryKey(RpNotifyRecordLog record);
}