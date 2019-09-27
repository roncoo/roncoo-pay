package com.roncoo.pay.trade.dao.impl;

import com.roncoo.pay.common.core.dao.impl.BaseDaoImpl;
import com.roncoo.pay.trade.dao.RpUserBankAuthDao;
import com.roncoo.pay.trade.entity.RpUserBankAuth;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("rpUserBankAuthDao")
public class RpUserBankAuthDaoImpl extends BaseDaoImpl<RpUserBankAuth> implements RpUserBankAuthDao {

    @Override
    public RpUserBankAuth findByMerchantNoAndPayOrderNo(String merchantNo, String payOrderNo) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("merchantNo", merchantNo);
        paramMap.put("payOrderNo", payOrderNo);
        System.out.println(paramMap.toString());
        return super.getByColumn(paramMap);
    }

    @Override
    public List<RpUserBankAuth> listByUserInfo(String userName, String phone, String idNo, String bankAccountNo) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userName", userName);
        paramMap.put("phone", phone);
        paramMap.put("idNo", idNo);
        paramMap.put("bankAccountNo", bankAccountNo);
        return super.listBy(paramMap);
    }
}
