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
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.pay.common.core.enums.PublicEnum;
import com.roncoo.pay.common.core.enums.PublicStatusEnum;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.user.dao.RpPayProductDao;
import com.roncoo.pay.user.entity.RpPayProduct;
import com.roncoo.pay.user.entity.RpPayWay;
import com.roncoo.pay.user.entity.RpUserPayConfig;
import com.roncoo.pay.user.exception.PayBizException;
import com.roncoo.pay.user.service.RpPayProductService;
import com.roncoo.pay.user.service.RpPayWayService;
import com.roncoo.pay.user.service.RpUserPayConfigService;

/**
 * 支付产品service实现类
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
@Service("rpPayProductService")
public class RpPayProductServiceImpl implements RpPayProductService{

	@Autowired
	private RpPayProductDao rpPayProductDao;
	
	@Autowired
	private RpPayWayService rpPayWayService;
	
	@Autowired
	private RpUserPayConfigService rpUserPayConfigService;
	
	@Override
	public void saveData(RpPayProduct rpPayProduct) {
		rpPayProductDao.insert(rpPayProduct);
	}

	@Override
	public void updateData(RpPayProduct rpPayProduct) {
		rpPayProductDao.update(rpPayProduct);
	}

	@Override
	public RpPayProduct getDataById(String id) {
		return rpPayProductDao.getById(id);
	}

	@Override
	public PageBean listPage(PageParam pageParam, RpPayProduct rpPayProduct) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		paramMap.put("auditStatus", rpPayProduct.getAuditStatus());
		paramMap.put("productName", rpPayProduct.getProductName());
		return rpPayProductDao.listPage(pageParam, paramMap);
	}
	
	/**
	 * 根据产品编号获取支付产品
	 */
	@Override
	public RpPayProduct getByProductCode(String productCode, String auditStatus){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productCode", productCode);
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		paramMap.put("auditStatus", auditStatus);
		return rpPayProductDao.getBy(paramMap);
	}
	
	/**
	 * 创建支付产品
	 * @param productCode
	 * @param productName
	 */
	@Override
	public void createPayProduct(String productCode, String productName) throws PayBizException {
		RpPayProduct rpPayProduct = getByProductCode(productCode, null);
		if(rpPayProduct != null){
			throw new PayBizException(PayBizException.PAY_PRODUCT_IS_EXIST,"支付产品已存在");
		}
		rpPayProduct = new RpPayProduct();
		rpPayProduct.setStatus(PublicStatusEnum.ACTIVE.name());
		rpPayProduct.setCreateTime(new Date());
		rpPayProduct.setId(StringUtil.get32UUID());
		rpPayProduct.setProductCode(productCode);
		rpPayProduct.setProductName(productName);
		rpPayProduct.setAuditStatus(PublicEnum.NO.name());
		saveData(rpPayProduct);
	}
	
	/**
	 * 删除支付产品
	 * @param productCode
	 */
	@Override
	public void deletePayProduct(String productCode) throws PayBizException{
		
		List<RpPayWay> payWayList = rpPayWayService.listByProductCode(productCode);
		if(!payWayList.isEmpty()){
			throw new PayBizException(PayBizException.PAY_PRODUCT_HAS_DATA,"支付产品已关联支付方式，无法删除！");
		}
		
		List<RpUserPayConfig> payConfigList = rpUserPayConfigService.listByProductCode(productCode);
		if(!payConfigList.isEmpty()){
			throw new PayBizException(PayBizException.PAY_PRODUCT_HAS_DATA,"支付产品已关联用户，无法删除！");
		}
		
		RpPayProduct rpPayProduct = getByProductCode(productCode, null);
		if(rpPayProduct.getAuditStatus().equals(PublicEnum.YES.name())){
			throw new PayBizException(PayBizException.PAY_PRODUCT_IS_EFFECTIVE,"支付产品已生效，无法删除！");
		}
		
		rpPayProduct.setStatus(PublicStatusEnum.UNACTIVE.name());
		updateData(rpPayProduct);
	}
	
	/**
	 * 获取所有支付产品
	 * @param productCode
	 */
	@Override
	public List<RpPayProduct> listAll(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		return rpPayProductDao.listBy(paramMap);
	}
	
	/**
	 * 审核
	 * @param productCode
	 * @param auditStatus
	 */
	@Override
	public void audit(String productCode, String auditStatus) throws PayBizException{
		RpPayProduct rpPayProduct = getByProductCode(productCode, null);
		if(rpPayProduct == null){
			throw new PayBizException(PayBizException.PAY_PRODUCT_IS_NOT_EXIST,"支付产品不存在！");
		}
		
		if(auditStatus.equals(PublicEnum.YES.name())){
			//检查是否已设置支付方式
			List<RpPayWay> payWayList = rpPayWayService.listByProductCode(productCode);
			if(payWayList.isEmpty()){
				throw new PayBizException(PayBizException.PAY_TYPE_IS_NOT_EXIST,"支付方式未设置，无法操作！");
			}
			
		}else if(auditStatus.equals(PublicEnum.NO.name())){
			//检查是否已有支付配置
			List<RpUserPayConfig> payConfigList = rpUserPayConfigService.listByProductCode(productCode);
			if(!payConfigList.isEmpty()){
				throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_EXIST,"支付产品已关联用户支付配置，无法操作！");
			}
		}
		rpPayProduct.setAuditStatus(auditStatus);
		rpPayProduct.setEditTime(new Date());
		updateData(rpPayProduct);
	}
}