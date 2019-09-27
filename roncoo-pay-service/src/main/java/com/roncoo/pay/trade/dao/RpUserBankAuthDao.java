package com.roncoo.pay.trade.dao;

import com.roncoo.pay.common.core.dao.BaseDao;
import com.roncoo.pay.trade.entity.RpUserBankAuth;

import java.util.List;

public interface RpUserBankAuthDao extends BaseDao<RpUserBankAuth> {

    RpUserBankAuth findByMerchantNoAndPayOrderNo(String merchantNo,String payOrderNo);

    List<RpUserBankAuth> listByUserInfo(String userName, String phone, String idNo, String bankAccountNo);
}
