package com.fast.pay.permission.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fast.pay.common.core.exception.BizException;

/**
 * 管理系统权限模块异常.
 */
public class PermissionException extends BizException {
	private static final long serialVersionUID = -5371846727040729628L;
	private static final Logger logger = LoggerFactory.getLogger(PermissionException.class);
	/** 该用户没有分配菜单权限 */
	public static final Integer PERMISSION_USER_NOT_MENU = 1001;
	/** 根据角色查询菜单出现错误 **/
	public static final Integer PERMISSION_QUERY_MENU_BY_ROLE_ERROR = 1002;
	/** 分配菜单权限时，角色不能为空 **/
	public static final Integer PERMISSION_ASSIGN_MENU_ROLE_NULL = 1003;
	/** 对接云架构社区平台用户体系异常 **/
	public static final Integer FAST_NETWORK_EXCEPTION = 1004;

	public PermissionException() {
	}

	public PermissionException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public PermissionException(int code, String msg) {
		super(code, msg);
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public PermissionException newInstance(String msgFormat, Object... args) {
		return new PermissionException(this.code, msgFormat, args);
	}

	public PermissionException print() {
		logger.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
		return this;
	}
}
