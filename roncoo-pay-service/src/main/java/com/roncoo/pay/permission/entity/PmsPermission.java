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
 * 权限-权限表实体..
 * 
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class PmsPermission extends PermissionBaseEntity {

	private static final long serialVersionUID = -2175597348886393330L;
	private String permissionName; // 权限名称
	private String permission; // 权限标识

	/**
	 * 权限名称
	 * 
	 * @return
	 */
	public String getPermissionName() {
		return permissionName;
	}

	/**
	 * 权限名称
	 * 
	 * @return
	 */
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	/**
	 * 权限标识
	 * 
	 * @return
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * 权限标识
	 * 
	 * @return
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}

}
