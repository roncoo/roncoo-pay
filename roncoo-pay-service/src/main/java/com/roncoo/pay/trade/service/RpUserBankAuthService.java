package com.roncoo.pay.trade.service;

import com.roncoo.pay.trade.entity.RpUserBankAuth;

public interface RpUserBankAuthService {

    RpUserBankAuth findByMerchantNoAndPayOrderNo(String merchantNo, String payOrderNo);

    void updateByKey(RpUserBankAuth userBankAuth);

}
