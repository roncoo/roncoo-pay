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
package com.roncoo.pay.permission.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.roncoo.pay.permission.dao.PmsRoleDao;
import com.roncoo.pay.permission.entity.PmsRole;

/**
 * 权限角色dao实现
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
@Repository
public class PmsRoleDaoImpl extends PermissionBaseDaoImpl<PmsRole> implements PmsRoleDao {

	/**
	 * 获取所有角色列表，以供添加操作员时选择.
	 * 
	 * @return roleList .
	 */
	public List<PmsRole> listAll() {
		return super.getSqlSession().selectList(getStatement("listAll"));
	}

	/**
	 * 判断此权限是否关联有角色
	 * 
	 * @param permissionId
	 * @return
	 */
	public List<PmsRole> listByPermissionId(Long permissionId) {
		return super.getSqlSession().selectList(getStatement("listByPermissionId"), permissionId);
	}

	/**
	 * 根据角色名或者角色编号查询角色
	 * 
	 * @param roleName
	 * @param roleCode
	 * @return
	 */
	public PmsRole getByRoleNameOrRoleCode(String roleName, String roleCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleName", roleName);
		paramMap.put("roleCode", roleCode);
		return super.getSqlSession().selectOne(getStatement("getByRoleNameOrRoleCode"), paramMap);
	}
}
