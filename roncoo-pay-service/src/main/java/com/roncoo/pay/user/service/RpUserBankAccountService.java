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

import com.roncoo.pay.user.entity.RpUserBankAccount;

/**
 * 用户银行账户service接口
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public interface RpUserBankAccountService{
	
	/**
	 * 保存
	 */
	void saveData(RpUserBankAccount rpUserBankAccount);

	/**
	 * 更新
	 */
	void updateData(RpUserBankAccount rpUserBankAccount);

	/**
	 * 根据用户编号获取银行账户
	 */
	RpUserBankAccount getByUserNo(String userNo);

	/**
	 * 创建或更新
	 * @param rpUserBankAccount
	 */
	void createOrUpdate(RpUserBankAccount rpUserBankAccount);
}