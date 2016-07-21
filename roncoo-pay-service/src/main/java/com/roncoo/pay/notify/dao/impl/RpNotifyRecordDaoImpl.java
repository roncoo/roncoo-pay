package com.roncoo.pay.notify.dao.impl;

import com.roncoo.pay.common.core.dao.impl.BaseDaoImpl;
import com.roncoo.pay.notify.dao.RpNotifyRecordDao;
import com.roncoo.pay.notify.entity.RpNotifyRecord;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/6/2  下午2:31
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
@Repository("rpNotifyRecordDao")
public class RpNotifyRecordDaoImpl extends BaseDaoImpl<RpNotifyRecord> implements RpNotifyRecordDao {


    @Override
    public RpNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo, String notifyType) {
        Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("merchantNo",merchantNo);
        paramMap.put("merchantOrderNo",merchantOrderNo);
        paramMap.put("notifyType",notifyType);

        return super.getBy(paramMap);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insertSelective(RpNotifyRecord record) {
        return 0;
    }

    @Override
    public RpNotifyRecord selectByPrimaryKey(String id) {
        return null;
    }


    @Override
    public int updateByPrimaryKey(RpNotifyRecord record) {
        return 0;
    }

}
