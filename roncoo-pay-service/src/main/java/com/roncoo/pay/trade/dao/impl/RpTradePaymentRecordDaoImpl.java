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
package com.roncoo.pay.trade.dao.impl;

import com.roncoo.pay.common.core.dao.impl.BaseDaoImpl;
import com.roncoo.pay.trade.dao.RpTradePaymentRecordDao;
import com.roncoo.pay.trade.entity.RpTradePaymentRecord;

import com.roncoo.pay.trade.enums.TradeStatusEnum;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>功能说明:商户支付记录,dao层实现类</b>
 * @author  Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
@Repository("rpTradePaymentRecordDao")
public class RpTradePaymentRecordDaoImpl extends BaseDaoImpl<RpTradePaymentRecord> implements RpTradePaymentRecordDao {

	/**
	 * 根据银行订单号获取支付信息
	 *
	 * @param bankOrderNo
	 * @return
	 */
	@Override
	public RpTradePaymentRecord getByBankOrderNo(String bankOrderNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bankOrderNo", bankOrderNo);
		return super.getBy(paramMap);
	}

	/**
	 * 根据商户编号及商户订单号获取支付结果
	 *
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @return
	 */
	@Override
	public RpTradePaymentRecord getSuccessRecordByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", TradeStatusEnum.SUCCESS.name());
		paramMap.put("merchantNo", merchantNo);
		paramMap.put("merchantOrderNo", merchantOrderNo);
		return super.getBy(paramMap);
	}

	/**
	 * 根据支付流水号查询支付记录
	 * 
	 * @param trxNo
	 * @return
	 */
	public RpTradePaymentRecord getByTrxNo(String trxNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("trxNo", trxNo);
		return super.getBy(paramMap);
	}
	
	public List<Map<String, String>> getPaymentReport(String merchantNo){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", TradeStatusEnum.SUCCESS.name());
		paramMap.put("merchantNo", merchantNo);
		return super.getSqlSession().selectList(getStatement("getPaymentReport"),paramMap);
	}

	public List<Map<String, String>> getPayWayReport(String merchantNo){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", TradeStatusEnum.SUCCESS.name());
		paramMap.put("merchantNo", merchantNo);
		return super.getSqlSession().selectList(getStatement("getPayWayReport"),paramMap);
	}

}
