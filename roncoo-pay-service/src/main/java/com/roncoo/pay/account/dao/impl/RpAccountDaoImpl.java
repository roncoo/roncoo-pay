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
package com.roncoo.pay.account.dao.impl;

import com.roncoo.pay.account.dao.RpAccountDao;
import com.roncoo.pay.account.entity.RpAccount;
import com.roncoo.pay.common.core.dao.impl.BaseDaoImpl;
import com.roncoo.pay.common.core.enums.PublicStatusEnum;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;


/**
 * 账户dao实现类
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
@Repository
public class RpAccountDaoImpl  extends BaseDaoImpl<RpAccount> implements RpAccountDao{
	public RpAccount getByAccountNo(String accountNo){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountNo", accountNo);
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		return this.getBy(paramMap);
	}

	public RpAccount getByUserNo(Map<String, Object> map){
		return this.getSessionTemplate().selectOne(getStatement("getByUserNo"), map);
	}
}