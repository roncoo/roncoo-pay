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
package com.roncoo.pay.permission.dao;

import com.roncoo.pay.permission.entity.PmsRolePermission;

import java.util.List;

/**
 * 角色权限dao
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public interface PmsRolePermissionDao extends PermissionBaseDao<PmsRolePermission> {

	/**
	 * 根据角色ID找到角色关联的权限点.
	 * 
	 * @param roleId
	 *            .
	 * @return rolePermissionList .
	 */
	public List<PmsRolePermission> listByRoleId(final long roleId);

	/**
	 * 根据角色ID字符串获取相应角色-权限关联信息.
	 * 
	 * @param roleIds
	 * @return
	 */
	public List<PmsRolePermission> listByRoleIds(String roleIdsStr);

	public void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId);
	
	public void deleteByRoleId(Long roleId);
}
