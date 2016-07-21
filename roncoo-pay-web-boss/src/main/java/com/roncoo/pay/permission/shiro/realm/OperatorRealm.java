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
package com.roncoo.pay.permission.shiro.realm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.common.core.enums.PublicStatusEnum;
import com.roncoo.pay.permission.entity.PmsOperator;
import com.roncoo.pay.permission.exception.PermissionException;
import com.roncoo.pay.permission.service.PmsOperatorRoleService;
import com.roncoo.pay.permission.service.PmsOperatorService;
import com.roncoo.pay.permission.service.PmsRolePermissionService;
import com.roncoo.pay.permission.utils.EncryptUtil;
import com.roncoo.pay.permission.utils.PasswordHelper;
import com.roncoo.pay.permission.utils.RonCooSignUtil;
import com.roncoo.pay.permission.utils.RoncooHttpClientUtils;

/**
 * 自定义realm .
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class OperatorRealm extends AuthorizingRealm {

	@Autowired
	private PmsOperatorService pmsOperatorService;
	@Autowired
	private PmsOperatorRoleService pmsOperatorRoleService;
	@Autowired
	private PmsRolePermissionService pmsRolePermissionService;

	@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String loginName = (String) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		PmsOperator operator = (PmsOperator) session.getAttribute("PmsOperator");
		if (operator == null) {
			// 对接龙果平台
			if (!"admin".equals(loginName)) {
				loginName = "guest";
			}
			operator = pmsOperatorService.findOperatorByLoginName(loginName);
			session.setAttribute("PmsOperator", operator);
		}
		// 根据登录名查询操作员
		Long operatorId = operator.getId();

		Set<String> roles = (Set<String>) session.getAttribute("ROLES");
		if (roles == null || roles.isEmpty()) {
			roles = pmsOperatorRoleService.getRoleCodeByOperatorId(operatorId);
			session.setAttribute("ROLES", roles);
		}
		// 查询角色信息
		authorizationInfo.setRoles(roles);

		Set<String> permisstions = (Set<String>) session.getAttribute("PERMISSIONS");
		if (permisstions == null || permisstions.isEmpty()) {
			permisstions = pmsRolePermissionService.getPermissionsByOperatorId(operatorId);
			session.setAttribute("PERMISSIONS", permisstions);
		}
		// 根据用户名查询权限
		authorizationInfo.setStringPermissions(permisstions);
		return authorizationInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 验证的核心方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String loginName = (String) token.getPrincipal();
		if (StringUtils.isEmpty(loginName)) {
			throw new UnknownAccountException();// 没找到帐号
		}
		if (!"admin".equals(loginName)) {
			// 打通龙果平台
			String pwd = new String((char[]) token.getCredentials());
			Long timeStamp = System.currentTimeMillis();
			String key = "rcPayLoginSign268";
			String sign = RonCooSignUtil.getSign(key, timeStamp, loginName);

			// String url =
			// "http://192.168.1.181:8080/roncoo-dev-admin/mydata/getByLoginName";
			String url = "http://boss.roncoo.com/mydata/getByLoginName";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userName", loginName);
			params.put("timeStamp", timeStamp);
			params.put("sign", sign);

			String json = JSON.toJSONString(params);

			String httpResponse = RoncooHttpClientUtils.post(url, json);
			if (httpResponse.length() < 2) {
				throw new PermissionException(PermissionException.RONCOO_NETWORK_EXCEPTION, "网络异常,请联系龙果管理员");
			}
			Map<String, Object> parseObject = JSONObject.parseObject(httpResponse, Map.class);
			String code = (String) parseObject.get("code");

			if ("100".equals(code)) {
				throw new UnknownAccountException();// 没找到帐号
			} else {
				JSONObject data = (JSONObject) parseObject.get("data");
				Map<String, Object> mapInfo = JSONObject.parseObject(data.toJSONString(), Map.class);
				String returnPWD = (String) mapInfo.get("pwd");
				String userId = (String) mapInfo.get("userId");
				String str = userId.trim() + pwd.trim();
				String getPwd = EncryptUtil.encodeSHAString(str);

				if (getPwd.trim().equals(returnPWD.trim())) {

					String salt = "183d9f2f0f2ce760e98427a5603d1c73";
					String password = PasswordHelper.getPwd(pwd, salt);
					// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
					SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(loginName, // 登录名
							password, // 密码
							ByteSource.Util.bytes(salt),// salt=username+salt
							getName() // realm name
					);
					return authenticationInfo;

				} else {
					throw new IncorrectCredentialsException();// 密码错误
				}
			}
		} else {
			// 根据登录名查询操作员
			PmsOperator operator = pmsOperatorService.findOperatorByLoginName(loginName);

			if (operator == null) {
				throw new UnknownAccountException();// 没找到帐号
			}

			if (PublicStatusEnum.UNACTIVE.equals(operator.getStatus())) {
				throw new LockedAccountException(); // 帐号锁定
			}

			// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(operator.getLoginName(), // 登录名
					operator.getLoginPwd(), // 密码
					ByteSource.Util.bytes(operator.getCredentialsSalt()), // salt=username+salt
					getName() // realm name
			);

			return authenticationInfo;
		}

	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

}
