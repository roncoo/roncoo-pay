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
 * 权限管理-操作员
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class PmsOperator extends PermissionBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String loginName;// 登录名
	private String loginPwd; // 登录密码
	private String realName; // 姓名
	private String mobileNo; // 手机号
	private String type; // 操作员类型（admin:超级管理员，common:普通操作员），超级管理员由系统初始化时添加，不能删除
	private String salt;// 盐

	/**
	 * 登录名
	 * 
	 * @return
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 登录名
	 * 
	 * @return
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 登录密码
	 * 
	 * @return
	 */
	public String getLoginPwd() {
		return loginPwd;
	}

	/**
	 * 登录密码
	 * 
	 * @return
	 */
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	/**
	 * 姓名
	 * 
	 * @return
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * 姓名
	 * 
	 * @return
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 手机号
	 * 
	 * @return
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * 手机号
	 * 
	 * @return
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * 操作员类型
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 操作员类型
	 * 
	 * @return
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 盐
	 * 
	 * @return
	 */
	public String getsalt() {
		return salt;
	}

	/**
	 * 盐
	 * 
	 * @param salt
	 */
	public void setsalt(String salt) {
		this.salt = salt;
	}

	/**
	 * 认证加密的盐
	 * 
	 * @return
	 */
	public String getCredentialsSalt() {
		return loginName + salt;
	}

	public PmsOperator() {

	}

}
