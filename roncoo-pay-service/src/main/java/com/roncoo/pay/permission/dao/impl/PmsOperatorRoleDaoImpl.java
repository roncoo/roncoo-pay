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

import com.roncoo.pay.permission.dao.PmsOperatorRoleDao;
import com.roncoo.pay.permission.entity.PmsOperatorRole;

/**
 * 权限-操作员与角色dao实现
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
@Repository
public class PmsOperatorRoleDaoImpl extends PermissionBaseDaoImpl<PmsOperatorRole> implements PmsOperatorRoleDao {

	/**
	 * 根据操作员ID查找该操作员关联的角色.
	 * 
	 * @param operatorId
	 *            .
	 * @return list .
	 */
	public List<PmsOperatorRole> listByOperatorId(Long operatorId) {
		return super.getSqlSession().selectList(getStatement("listByOperatorId"), operatorId);
	}

	/**
	 * 根据角色ID查找该操作员关联的操作员.
	 * 
	 * @param roleId
	 * @return
	 */
	public List<PmsOperatorRole> listByRoleId(Long roleId) {
		return super.getSqlSession().selectList(getStatement("listByRoleId"), roleId);
	}

	/**
	 * 根据操作员ID删除与角色的关联记录.
	 * 
	 * @param operatorId
	 *            .
	 */
	public void deleteByOperatorId(Long operatorId) {

		super.getSqlSession().delete(getStatement("deleteByOperatorId"), operatorId);
	}

	/**
	 * 根据角色ID删除操作员与角色的关联关系.
	 * 
	 * @param roleId
	 *            .
	 */
	public void deleteByRoleId(Long roleId) {
		super.getSqlSession().delete(getStatement("deleteByRoleId"), roleId);
	}

	/**
	 * 根据角色ID和操作员ID删除关联数据(用于更新操作员的角色).
	 * 
	 * @param roleId
	 *            角色ID.
	 * @param operatorId
	 *            操作员ID.
	 */
	@Override
	public void deleteByRoleIdAndOperatorId(Long roleId, Long operatorId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		paramMap.put("operatorId", operatorId);
		super.getSqlSession().delete(getStatement("deleteByRoleIdAndOperatorId"), paramMap);
	}

}
