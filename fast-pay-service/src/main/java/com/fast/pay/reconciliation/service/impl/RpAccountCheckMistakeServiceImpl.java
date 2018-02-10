/*
 * Copyright 2015-2102 Fast(http://www.cloudate.net) Group.
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
package com.fast.pay.reconciliation.service.impl;

import java.util.List;
import java.util.Map;

import com.fast.pay.reconciliation.dao.RpAccountCheckMistakeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fast.pay.common.core.page.PageBean;
import com.fast.pay.common.core.page.PageParam;
import com.fast.pay.reconciliation.dao.RpAccountCheckMistakeDao;
import com.fast.pay.reconciliation.entity.RpAccountCheckMistake;
import com.fast.pay.reconciliation.service.RpAccountCheckMistakeService;

/**
 * 对账批次接口实现 .
 */
@Service("rpAccountCheckMistakeService")
public class RpAccountCheckMistakeServiceImpl implements RpAccountCheckMistakeService {

	@Autowired
	private RpAccountCheckMistakeDao rpAccountCheckMistakeDao;

	@Override
	public void saveData(RpAccountCheckMistake rpAccountCheckMistake) {
		rpAccountCheckMistakeDao.insert(rpAccountCheckMistake);
	}

	@Override
	public void updateData(RpAccountCheckMistake rpAccountCheckMistake) {
		rpAccountCheckMistakeDao.update(rpAccountCheckMistake);
	}

	@Override
	public RpAccountCheckMistake getDataById(String id) {
		return rpAccountCheckMistakeDao.getById(id);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return rpAccountCheckMistakeDao.listPage(pageParam, paramMap);
	}

	/**
	 * 批量保存差错记录
	 * 
	 * @param mistakeList
	 */
	public void saveListDate(List<RpAccountCheckMistake> mistakeList) {
		for (RpAccountCheckMistake mistake : mistakeList) {
			rpAccountCheckMistakeDao.insert(mistake);
		}

	}
}