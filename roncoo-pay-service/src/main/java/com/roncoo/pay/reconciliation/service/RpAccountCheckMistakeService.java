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
package com.roncoo.pay.reconciliation.service;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.reconciliation.entity.RpAccountCheckMistake;

import java.util.List;
import java.util.Map;

/**
 * 对账差错接口 .
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public interface RpAccountCheckMistakeService {

	/**
	 * 保存
	 */
	void saveData(RpAccountCheckMistake rpAccountCheckMistake);

	/**
	 * 更新
	 */
	void updateData(RpAccountCheckMistake rpAccountCheckMistake);

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpAccountCheckMistake getDataById(String id);

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap);

	/**
	 * 批量保存差错记录
	 * 
	 * @param mistakeList
	 */
	void saveListDate(List<RpAccountCheckMistake> mistakeList);

}