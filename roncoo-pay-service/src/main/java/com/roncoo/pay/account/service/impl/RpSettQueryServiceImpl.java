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
package com.roncoo.pay.account.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.pay.account.dao.RpSettDailyCollectDao;
import com.roncoo.pay.account.dao.RpSettRecordDao;
import com.roncoo.pay.account.entity.RpSettRecord;
import com.roncoo.pay.account.service.RpSettQueryService;
import com.roncoo.pay.common.core.exception.BizException;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;

/**
 * 结算查询service实现类
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
@Service("rpSettQueryService")
public class RpSettQueryServiceImpl implements RpSettQueryService {

	@Autowired
	private RpSettRecordDao rpSettRecordDao;
	@Autowired
	private RpSettDailyCollectDao settDailyCollectMapper;

	/**
	 * 根据参数分页查询结算记录
	 * 
	 * @param pageParam
	 * @param params
	 *            ：map的key为 ：accountNo、userNo、settStatus...可以参考实体
	 * 
	 * @return
	 * @throws BizException
	 */
	public PageBean querySettRecordListPage(PageParam pageParam, Map<String, Object> params) throws BizException{
		return rpSettRecordDao.listPage(pageParam, params);
	}

	/**
	 * 根据参数分页查询结算日汇总记录
	 * 
	 * @param pageParam
	 * @param params
	 *            ：map的key为 ：accountNo...可以参考实体
	 * 
	 * @return
	 * @throws BizException
	 */
	public PageBean querySettDailyCollectListPage(PageParam pageParam, Map<String, Object> params) throws BizException{
		return settDailyCollectMapper.listPage(pageParam, params);
	}
	
	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	public RpSettRecord getDataById(String id){
		return rpSettRecordDao.getById(id);
	}
}
