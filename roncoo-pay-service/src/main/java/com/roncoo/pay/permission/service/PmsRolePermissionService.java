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
package com.roncoo.pay.permission.service;

import java.util.Set;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.permission.entity.PmsRolePermission;

/**
 * 角色权限service接口
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public interface PmsRolePermissionService {

	/**
	 * 根据操作员ID，获取所有的功能权限集
	 * 
	 * @param operatorId
	 */
	public Set<String> getPermissionsByOperatorId(Long operatorId);

	/**
	 * 创建pmsRolePermission
	 */
	void saveData(PmsRolePermission pmsRolePermission);

	/**
	 * 修改pmsRolePermission
	 */
	void updateData(PmsRolePermission pmsRolePermission);

	/**
	 * 根据id获取数据pmsRolePermission
	 * 
	 * @param id
	 * @return
	 */
	PmsRolePermission getDataById(Long id);

	/**
	 * 分页查询pmsRolePermission
	 * 
	 * @param pageParam
	 * @param ActivityVo
	 *            PmsRolePermission
	 * @return
	 */
	PageBean listPage(PageParam pageParam, PmsRolePermission pmsRolePermission);
	
	/**
	 * 保存角色和权限之间的关联关系
	 */
	void saveRolePermission(Long roleId, String rolePermissionStr);

}
