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
import com.roncoo.pay.user.entity.RpPayProduct;
import com.roncoo.pay.user.exception.PayBizException;

/**
 * 支付产品service接口
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public interface RpPayProductService{
	
	/**
	 * 保存
	 */
	void saveData(RpPayProduct rpPayProduct);

	/**
	 * 更新
	 */
	void updateData(RpPayProduct rpPayProduct);

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpPayProduct getDataById(String id);
	

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpPayProduct rpPayProduct);
	
	/**
	 * 根据产品编号获取支付产品
	 * @param productCode
	 * @return
	 */
	RpPayProduct getByProductCode(String productCode, String auditStatus);
	
	/**
	 * 创建支付产品
	 * @param productCode
	 * @param productName
	 */
	void createPayProduct(String productCode, String productName) throws PayBizException;
	
	/**
	 * 删除支付产品
	 * @param productCode
	 */
	void deletePayProduct(String productCode) throws PayBizException;
	
	/**
	 * 获取所有支付产品
	 * @param productCode
	 */
	List<RpPayProduct> listAll();

	/**
	 * 审核
	 * @param productCode
	 * @param auditStatus
	 */
	void audit(String productCode, String auditStatus) throws PayBizException;
	
}