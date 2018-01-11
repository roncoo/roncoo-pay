package com.roncoo.pay.trade.service.impl;

import com.roncoo.pay.trade.dao.RpUserBankAuthDao;
import com.roncoo.pay.trade.entity.RpUserBankAuth;
import com.roncoo.pay.trade.service.RpUserBankAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rpUserBankAuthServiceImpl")
public class RpUserBankAuthServiceImpl implements RpUserBankAuthService {

    @Autowired
    private RpUserBankAuthDao userBankAuthDao;

    @Override
    public RpUserBankAuth findByMerchantNoAndPayOrderNo(String merchantNo, String payOrderNo) {
        return userBankAuthDao.findByMerchantNoAndPayOrderNo(merchantNo, payOrderNo);
    }

    @Override
    public void updateByKey(RpUserBankAuth userBankAuth) {
        userBankAuthDao.update(userBankAuth);
    }
}
