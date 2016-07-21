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
package com.roncoo.pay.account.service;

import java.math.BigDecimal;

import com.roncoo.pay.account.entity.RpAccount;
import com.roncoo.pay.common.core.exception.BizException;

/**
 *  账户操作service接口
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public interface RpAccountTransactionService {

	/** 加款:有银行流水 **/
	RpAccount creditToAccount(String userNo, BigDecimal amount, String requestNo,String bankTrxNo, String trxType, String remark) throws BizException;

	/** 减款 :有银行流水**/
	RpAccount debitToAccount(String userNo, BigDecimal amount, String requestNo,String bankTrxNo, String trxType, String remark) throws BizException;
	
	/** 加款 **/
	RpAccount creditToAccount(String userNo, BigDecimal amount, String requestNo, String trxType, String remark) throws BizException;

	/** 减款 **/
	RpAccount debitToAccount(String userNo, BigDecimal amount, String requestNo, String trxType, String remark) throws BizException;


	/** 冻结 **/
	RpAccount freezeAmount(String userNo, BigDecimal freezeAmount) throws BizException;

	/** 结算成功：解冻+减款 **/
	RpAccount unFreezeAmount(String userNo, BigDecimal amount, String requestNo, String trxType, String remark) throws BizException;
	
	/** 结算失败：解冻 **/
	RpAccount unFreezeSettAmount(String userNo, BigDecimal amount) throws BizException;

	/** 更新账户历史中的结算状态，并且累加可结算金额 **/
	void settCollectSuccess(String accountNo, String collectDate, int riskDay, BigDecimal totalAmount) throws BizException;

}