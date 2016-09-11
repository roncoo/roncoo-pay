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

import java.util.List;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.user.entity.RpUserInfo;

/**
 * 用户信息service接口
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public interface RpUserInfoService{
	
	/**
	 * 保存
	 */
	void saveData(RpUserInfo rpUserInfo);

	/**
	 * 更新
	 */
	void updateData(RpUserInfo rpUserInfo);

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpUserInfo getDataById(String id);
	

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpUserInfo rpUserInfo);
	
	/**
     * 用户线下注册
     * 
     * @param userName
     *            用户名
     * @param mobile
     *            手机号
     * @param password
     *            密码
     * @return
     */
    void registerOffline(String userName, String mobile, String password) ;

	/**
	 * 根据商户编号获取商户信息
	 * @param merchantNo
	 * @return
	 */
	RpUserInfo getDataByMerchentNo(String merchantNo);
	
	/**
	 * 根据手机号获取商户信息
	 * @param mobile
	 * @return
	 */
	RpUserInfo getDataByMobile(String mobile);
	
	/**
	 * 获取所有用户
	 * @return
	 */
	List<RpUserInfo> listAll();
	
}