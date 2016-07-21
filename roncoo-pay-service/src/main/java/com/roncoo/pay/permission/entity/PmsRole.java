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
package com.roncoo.pay.permission.entity;

/**
 * 权限管理-角色..
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class PmsRole extends PermissionBaseEntity {

	private static final long serialVersionUID = -1850274607153125161L;
	private String roleCode; // 角色编码：例如：admin
	private String roleName; // 角色名称

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * 角色名称
	 * 
	 * @return
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 角色名称
	 * 
	 * @return
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public PmsRole() {

	}
}
