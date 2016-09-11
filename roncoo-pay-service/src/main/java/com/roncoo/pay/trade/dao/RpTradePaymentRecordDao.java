/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roncoo.pay.trade.dao;

import java.util.List;
import java.util.Map;

import com.roncoo.pay.common.core.dao.BaseDao;
import com.roncoo.pay.trade.entity.RpTradePaymentRecord;

/**
 * <b>功能说明:商户支付记录,dao层接口</b>
 * @author  Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public interface RpTradePaymentRecordDao extends BaseDao<RpTradePaymentRecord>{

    /**
     * 根据银行订单号获取支付信息
     * @param bankOrderNo
     * @return
     */
    RpTradePaymentRecord getByBankOrderNo(String bankOrderNo);

    /**
     * 根据商户编号及商户订单号获取支付成功的结果
     * @param merchantNo
     * @param merchantOrderNo
     * @return
     */
    RpTradePaymentRecord getSuccessRecordByMerchantNoAndMerchantOrderNo(String merchantNo , String merchantOrderNo);

    /**
	 * 根据支付流水号查询支付记录
	 * 
	 * @param trxNo
	 * @return
	 */
	RpTradePaymentRecord getByTrxNo(String trxNo);

	List<Map<String, String>> getPaymentReport(String merchantNo);

	List<Map<String, String>> getPayWayReport(String merchantNo);

}
