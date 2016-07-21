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
package com.roncoo.pay.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.pay.common.core.enums.PublicEnum;
import com.roncoo.pay.common.core.enums.PublicStatusEnum;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.user.dao.RpUserBankAccountDao;
import com.roncoo.pay.user.entity.RpUserBankAccount;
import com.roncoo.pay.user.enums.BankCodeEnum;
import com.roncoo.pay.user.service.RpUserBankAccountService;

/**
 * 用户银行账户service实现类
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
@Service("rpUserBankAccountService")
public class RpUserBankAccountServiceImpl implements RpUserBankAccountService{

	@Autowired
	private RpUserBankAccountDao rpUserBankAccountDao;
	
	@Override
	public void saveData(RpUserBankAccount rpUserBankAccount) {
		rpUserBankAccountDao.insert(rpUserBankAccount);
	}

	@Override
	public void updateData(RpUserBankAccount rpUserBankAccount) {
		rpUserBankAccountDao.update(rpUserBankAccount);
	}

	/**
	 * 根据用户编号获取银行账户
	 */
	@Override
	public RpUserBankAccount getByUserNo(String userNo){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		return rpUserBankAccountDao.getBy(paramMap);
	}
	
	/**
	 * 创建或更新
	 * @param rpUserBankAccount
	 */
	@Override
	public void createOrUpdate(RpUserBankAccount rpUserBankAccount){
		RpUserBankAccount bankAccount = getByUserNo(rpUserBankAccount.getUserNo());
		if(bankAccount == null){
			bankAccount = new RpUserBankAccount();
			bankAccount.setId(StringUtil.get32UUID());
			bankAccount.setCreateTime(new Date());
			bankAccount.setEditTime(new Date());
			bankAccount.setAreas(rpUserBankAccount.getAreas());
			bankAccount.setBankAccountName(rpUserBankAccount.getBankAccountName());
			bankAccount.setBankAccountNo(rpUserBankAccount.getBankAccountNo());
			bankAccount.setBankAccountType(rpUserBankAccount.getBankAccountType());
			bankAccount.setBankCode(rpUserBankAccount.getBankCode());
			bankAccount.setBankName(BankCodeEnum.getEnum(rpUserBankAccount.getBankCode()).getDesc());
			bankAccount.setCardNo(rpUserBankAccount.getCardNo());
			bankAccount.setCardType(rpUserBankAccount.getCardType());
			bankAccount.setCity(rpUserBankAccount.getCity());
			bankAccount.setIsDefault(PublicEnum.YES.name());
			bankAccount.setMobileNo(rpUserBankAccount.getMobileNo());
			bankAccount.setProvince(rpUserBankAccount.getProvince());
			bankAccount.setRemark(rpUserBankAccount.getRemark());
			bankAccount.setStatus(PublicStatusEnum.ACTIVE.name());
			bankAccount.setUserNo(rpUserBankAccount.getUserNo());
			bankAccount.setStreet(rpUserBankAccount.getStreet());
			rpUserBankAccountDao.insert(bankAccount);
		}else{
			bankAccount.setEditTime(new Date());
			bankAccount.setAreas(rpUserBankAccount.getAreas());
			bankAccount.setBankAccountName(rpUserBankAccount.getBankAccountName());
			bankAccount.setBankAccountNo(rpUserBankAccount.getBankAccountNo());
			bankAccount.setBankAccountType(rpUserBankAccount.getBankAccountType());
			bankAccount.setBankCode(rpUserBankAccount.getBankCode());
			bankAccount.setBankName(BankCodeEnum.getEnum(rpUserBankAccount.getBankCode()).getDesc());
			bankAccount.setCardNo(rpUserBankAccount.getCardNo());
			bankAccount.setCardType(rpUserBankAccount.getCardType());
			bankAccount.setCity(rpUserBankAccount.getCity());
			bankAccount.setIsDefault(PublicEnum.YES.name());
			bankAccount.setMobileNo(rpUserBankAccount.getMobileNo());
			bankAccount.setProvince(rpUserBankAccount.getProvince());
			bankAccount.setRemark(rpUserBankAccount.getRemark());
			bankAccount.setStatus(PublicStatusEnum.ACTIVE.name());
			bankAccount.setUserNo(rpUserBankAccount.getUserNo());
			bankAccount.setStreet(rpUserBankAccount.getStreet());
			rpUserBankAccountDao.update(bankAccount);
		}
	}
}