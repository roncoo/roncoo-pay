package com.fast.pay.notify.dao.impl;

import com.fast.pay.common.core.dao.impl.BaseDaoImpl;
import com.fast.pay.notify.entity.RpNotifyRecordLog;
import com.fast.pay.notify.dao.RpNotifyRecordLogDao;
import com.fast.pay.notify.entity.RpNotifyRecordLog;

import org.springframework.stereotype.Repository;

@Repository("rpNotifyRecordLogDao")
public class RpNotifyRecordLogDaoImpl extends BaseDaoImpl<RpNotifyRecordLog> implements RpNotifyRecordLogDao {
    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insertSelective(RpNotifyRecordLog record) {
        return 0;
    }

    @Override
    public RpNotifyRecordLog selectByPrimaryKey(String id) {
        return null;
    }


    @Override
    public int updateByPrimaryKey(RpNotifyRecordLog record) {
        return 0;
    }
}
