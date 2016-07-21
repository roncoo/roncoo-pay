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
package com.roncoo.pay.trade.service;

import com.roncoo.pay.reconciliation.entity.RpAccountCheckMistake;

/**
 * <b>功能说明:交易模块对账差错处理接口</b>
 * @author  Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public interface RpTradeReconciliationService {

	/**
	 * 平台成功，银行记录不存在，或者银行失败，以银行为准
	 * 
	 * @param trxNo
	 *            平台交易流水
	 */
	public void bankMissOrBankFailBaseBank(String trxNo);

	/**
	 * 银行成功，平台失败。
	 * 
	 * @param trxNo
	 *            平台交易流水
	 * @param bankTrxNo
	 *            银行返回流水
	 */
	public void platFailBankSuccess(String trxNo, String bankTrxNo);

	/**
	 * 处理金额不匹配异常(都是以银行数据为准才需要调整)
	 * 
	 * @param mistake
	 *            差错记录
	 * @param isBankMore
	 *            是否是银行金额多
	 */
	public void handleAmountMistake(RpAccountCheckMistake mistake, boolean isBankMore) ;

	/**
	 * 处理手续费不匹配差错（默认以银行为准）
	 * 
	 * @param mistake
	 */
	public void handleFeeMistake(RpAccountCheckMistake mistake);

}
