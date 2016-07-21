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

import java.util.List;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.permission.entity.PmsRole;

/**
 * 角色service接口
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public interface PmsRoleService {

	/**
	 * 创建pmsRole
	 */
	void saveData(PmsRole pmsRole);

	/**
	 * 修改pmsRole
	 */
	void updateData(PmsRole pmsRole);

	/**
	 * 根据id获取数据pmsRole
	 * 
	 * @param id
	 * @return
	 */
	PmsRole getDataById(Long id);

	/**
	 * 分页查询pmsRole
	 * 
	 * @param pageParam
	 * @param ActivityVo
	 *            PmsRole
	 * @return
	 */
	PageBean listPage(PageParam pageParam, PmsRole pmsRole);

	/**
	 * 获取所有角色列表，以供添加操作员时选择.
	 * 
	 * @return roleList .
	 */
	public List<PmsRole> listAllRole();

	/**
	 * 判断此权限是否关联有角色
	 * 
	 * @param permissionId
	 * @return
	 */
	List<PmsRole> listByPermissionId(Long permissionId);

	/**
	 * 根据角色名或者角色编号查询角色
	 * 
	 * @param roleName
	 * @param roleCode
	 * @return
	 */
	PmsRole getByRoleNameOrRoleCode(String roleName, String roleCode);

	/**
	 * 删除
	 * 
	 * @param roleId
	 */
	void delete(Long roleId);

}
