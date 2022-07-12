package com.roncoo.pay.trade.service.impl;

import com.roncoo.pay.banklink.utils.weixin.WeiXinMicroUtils;
import com.roncoo.pay.banklink.utils.weixin.WxCityNo;
import com.roncoo.pay.common.core.enums.PublicEnum;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.trade.dao.RpMicroSubmitRecordDao;
import com.roncoo.pay.trade.dao.RpTradePaymentRecordDao;
import com.roncoo.pay.trade.entity.RpMicroSubmitRecord;
import com.roncoo.pay.trade.service.RpMicroSubmitRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统配置service实现
 *
 * @author Quanf
 */
@Service("rpMicroSubmitRecordService")
public class RpMicroSubmitRecordServiceImpl implements RpMicroSubmitRecordService {

    @Autowired
    private RpMicroSubmitRecordDao rpMicroSubmitRecordDao;

    @Autowired
    private RpTradePaymentRecordDao rpTradePaymentRecordDao;

    private static final Logger LOG = LoggerFactory.getLogger(RpMicroSubmitRecordServiceImpl.class);

    @Override
    public PageBean listPage(PageParam pageParam, RpMicroSubmitRecord rpMicroSubmitRecord) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("storeName", rpMicroSubmitRecord.getStoreName());
        paramMap.put("businessCode", rpMicroSubmitRecord.getBusinessCode());
        paramMap.put("dCardName", rpMicroSubmitRecord.getIdCardName());
        return rpMicroSubmitRecordDao.listPage(pageParam, paramMap);
    }

    @Override
    public void saveData(RpMicroSubmitRecord rpMicroSubmitRecord) {
        rpMicroSubmitRecord.setEditTime(new Date());
        if (StringUtil.isEmpty(rpMicroSubmitRecord.getStatus())) {
            rpMicroSubmitRecord.setStatus(PublicEnum.YES.name());
        }
        rpMicroSubmitRecordDao.insert(rpMicroSubmitRecord);
    }

    @Override
    public Map<String, Object> microSubmit(RpMicroSubmitRecord rpMicroSubmitRecord) {
        rpMicroSubmitRecord.setBusinessCode(StringUtil.get32UUID());
        rpMicroSubmitRecord.setIdCardValidTime("[\"" + rpMicroSubmitRecord.getIdCardValidTimeBegin() + "\",\"" + rpMicroSubmitRecord.getIdCardValidTimeEnd() + "\"]");
        Map<String, Object> returnMap = WeiXinMicroUtils.microSubmit(rpMicroSubmitRecord);
        rpMicroSubmitRecord.setStoreStreet(WxCityNo.getCityNameByNo(rpMicroSubmitRecord.getStoreAddressCode()) + "：" + rpMicroSubmitRecord.getStoreStreet());
        if ("SUCCESS".equals(returnMap.get("result_code"))) {
            saveData(rpMicroSubmitRecord);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> microQuery(String businessCode) {
        Map<String, Object> returnMap = WeiXinMicroUtils.microQuery(businessCode);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("businessCode", businessCode);
        RpMicroSubmitRecord rpMicroSubmitRecord = rpMicroSubmitRecordDao.getBy(paramMap);
        if (StringUtil.isNotNull(returnMap.get("sub_mch_id")) && StringUtil.isEmpty(rpMicroSubmitRecord.getSubMchId())) {
            rpMicroSubmitRecord.setSubMchId(String.valueOf(returnMap.get("sub_mch_id")));
            rpMicroSubmitRecordDao.update(rpMicroSubmitRecord);
        }
        return returnMap;
    }

}
