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

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.user.entity.RpUserPayInfo;

/**
 * 用户第三方支付信息service接口
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public interface RpUserPayInfoService{
	
	/**
	 * 保存
	 */
	void saveData(RpUserPayInfo rpUserPayInfo);

	/**
	 * 更新
	 */
	void updateData(RpUserPayInfo rpUserPayInfo);

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpUserPayInfo getDataById(String id);
	

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpUserPayInfo rpUserPayInfo);

	/**
	 * 通过商户编号获取商户支付配置信息
	 * @param userNo
	 * @param payWayCode
	 * @return
	 */
	public RpUserPayInfo getByUserNo(String userNo, String payWayCode);
	
}