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

import java.util.List;
import java.util.Map;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.reconciliation.entity.RpAccountCheckMistakeScratchPool;

/**
 * 对账暂存池接口 .
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public interface RpAccountCheckMistakeScratchPoolService {

	/**
	 * 保存
	 */
	void saveData(RpAccountCheckMistakeScratchPool rpAccountCheckMistakeScratchPool);

	/**
	 * 批量保存记录
	 * 
	 * @param ScratchPoolList
	 */
	public void savaListDate(List<RpAccountCheckMistakeScratchPool> scratchPoolList);

	/**
	 * 更新
	 */
	void updateData(RpAccountCheckMistakeScratchPool rpAccountCheckMistakeScratchPool);

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpAccountCheckMistakeScratchPool getDataById(String id);

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpAccountCheckMistakeScratchPool rpAccountCheckMistakeScratchPool);

	/**
	 * 从缓冲池中删除数据
	 * 
	 * @param scratchPoolList
	 */
	void deleteFromPool(List<RpAccountCheckMistakeScratchPool> scratchPoolList);

	/**
	 * 查询出缓存池中所有的数据
	 * 
	 * @return
	 */
	List<RpAccountCheckMistakeScratchPool> listScratchPoolRecord(Map<String, Object> paramMap);

}