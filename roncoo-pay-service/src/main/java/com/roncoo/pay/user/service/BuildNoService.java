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
package com.roncoo.pay.user.service;

/**
 *  生成编号service接口
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public interface BuildNoService {

	/** 获取用户编号 **/
	String buildUserNo();

	/** 获取账户编号 **/
	String buildAccountNo();

	/** 获取支付流水号 **/
	String buildTrxNo();

	/** 获取银行订单号 **/
	String buildBankOrderNo();

	/** 获取对账批次号 **/
	String buildReconciliationNo();

}
