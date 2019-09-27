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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.common.core.enums.PublicStatusEnum;
import com.roncoo.pay.permission.service.PmsOperatorService;
import com.roncoo.pay.permission.service.PmsRolePermissionService;
import com.roncoo.pay.permission.entity.PmsOperator;
import com.roncoo.pay.permission.exception.PermissionException;
import com.roncoo.pay.permission.service.PmsOperatorRoleService;
import com.roncoo.pay.permission.utils.LoginConfigUtil;
import com.roncoo.pay.permission.utils.PasswordHelper;
import com.roncoo.pay.permission.utils.RoncooHttpClientUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 自定义realm .
 * <p>
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

	/*@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String loginName = (String) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		PmsOperator operator = (PmsOperator) session.getAttribute("PmsOperator");
		if (operator == null) {
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

	@Override
	// 验证的核心方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String loginName = (String) token.getPrincipal();
		if (StringUtils.isEmpty(loginName.trim())) {
			throw new UnknownAccountException();// 没找到帐号
		}

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
	}*/


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
            if (!"admin_roncoo".equals(loginName)) {
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

            String loginPwd = new String((char[]) token.getCredentials());
            Map<String, Object> loginParam = new HashMap<>();
            loginParam.put("agent", LoginConfigUtil.AGENT);
            loginParam.put("clientId", LoginConfigUtil.CLIENT_ID);
            loginParam.put("cookie", LoginConfigUtil.AGENT);
            loginParam.put("ip", "139.224.63.56");
            loginParam.put("mobile", loginName);
            loginParam.put("password", loginPwd);
            String paramJson = JSON.toJSONString(loginParam);
            String httpResponse = RoncooHttpClientUtils.post(LoginConfigUtil.LOGIN_URL, paramJson);
            if (httpResponse.length() < 2) {
                throw new PermissionException(PermissionException.RONCOO_NETWORK_EXCEPTION, "网络异常,请联系龙果管理员");
            }
            Map<String, Object> resultMap = JSONObject.parseObject(httpResponse, Map.class);
            if ("200".equals(String.valueOf(resultMap.get("code")))) {
                return new SimpleAuthenticationInfo(loginName, PasswordHelper.getPwd(loginPwd, LoginConfigUtil.SALT), ByteSource.Util.bytes(LoginConfigUtil.SALT), getName());
            } else {
                throw new UnknownAccountException();//没找到账号
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
