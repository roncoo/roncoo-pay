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
package com.roncoo.pay.permission.controller;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.roncoo.pay.common.core.dwz.DwzAjax;
import com.roncoo.pay.common.core.enums.PublicStatusEnum;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.controller.common.BaseController;
import com.roncoo.pay.permission.entity.PmsOperator;
import com.roncoo.pay.permission.entity.PmsOperatorRole;
import com.roncoo.pay.permission.enums.OperatorTypeEnum;
import com.roncoo.pay.permission.service.PmsOperatorRoleService;
import com.roncoo.pay.permission.service.PmsOperatorService;
import com.roncoo.pay.permission.service.PmsRoleService;
import com.roncoo.pay.permission.utils.PasswordHelper;
import com.roncoo.pay.permission.utils.ValidateUtils;

/**
 * 权限管理模块操作员管理
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
@Controller
@RequestMapping("/pms/operator")
public class PmsOperatorController extends BaseController {

	private static Log log = LogFactory.getLog(PmsOperatorController.class);

	@Autowired
	private PmsOperatorService pmsOperatorService;
	@Autowired
	private PmsRoleService pmsRoleService;
	@Autowired
	private PmsOperatorRoleService pmsOperatorRoleService;

	/**
	 * 分页列出操作员信息，并可按登录名获姓名进行查询.
	 * 
	 * @return listPmsOperator or operateError .
	 * 
	 */
	@RequiresPermissions("pms:operator:view")
	@RequestMapping("/list")
	public String listPmsOperator(HttpServletRequest req, PageParam pageParam, PmsOperator operator, Model model) {
		try {

			PageBean pageBean = pmsOperatorService.listPage(pageParam, operator);
			model.addAttribute(pageBean);
			model.addAttribute("OperatorStatusEnum", PublicStatusEnum.toMap());
			model.addAttribute("OperatorTypeEnum", OperatorTypeEnum.toMap());
			return "pms/pmsOperatorList";
		} catch (Exception e) {
			log.error("== listPmsOperator exception:", e);
			return operateError("获取数据失败", model);
		}
	}

	/**
	 * 查看操作员详情.
	 * 
	 * @return .
	 */
	@RequiresPermissions("pms:operator:view")
	@RequestMapping("/viewUI")
	public String viewPmsOperatorUI(HttpServletRequest req, Long id, Model model) {
		try {
			PmsOperator pmsOperator = pmsOperatorService.getDataById(id);
			if (pmsOperator == null) {
				return operateError("无法获取要查看的数据", model);
			}

			// 普通操作员没有查看超级管理员的权限
			if (OperatorTypeEnum.USER.name().equals(this.getPmsOperator().getType()) && OperatorTypeEnum.ADMIN.name().equals(pmsOperator.getType())) {
				return operateError("权限不足", model);
			}

			// 准备角色列表
			model.addAttribute("rolesList", pmsRoleService.listAllRole());

			// 准备该用户拥有的角色ID字符串
			List<PmsOperatorRole> lisPmsOperatorRoles = pmsOperatorRoleService.listOperatorRoleByOperatorId(id);
			StringBuffer owenedRoleIdBuffer = new StringBuffer("");
			for (PmsOperatorRole pmsOperatorRole : lisPmsOperatorRoles) {
				owenedRoleIdBuffer.append(pmsOperatorRole.getRoleId());
				owenedRoleIdBuffer.append(",");
			}
			String owenedRoleIds = owenedRoleIdBuffer.toString();
			if (StringUtils.isNotBlank(owenedRoleIds) && owenedRoleIds.length() > 0) {
				owenedRoleIds = owenedRoleIds.substring(0, owenedRoleIds.length() - 1);
			}
			model.addAttribute("pmsOperator", pmsOperator);
			model.addAttribute("owenedRoleIds", owenedRoleIds);
			return "/pms/pmsOperatorView";
		} catch (Exception e) {
			log.error("== viewPmsOperatorUI exception:", e);
			return operateError("获取数据失败", model);
		}
	}

	/**
	 * 转到添加操作员页面 .
	 * 
	 * @return addPmsOperatorUI or operateError .
	 */
	@RequiresPermissions("pms:operator:add")
	@RequestMapping("/addUI")
	public String addPmsOperatorUI(HttpServletRequest req, Model model) {
		try {
			model.addAttribute("rolesList", pmsRoleService.listAllRole());
			model.addAttribute("OperatorStatusEnumList", PublicStatusEnum.toList());
			return "/pms/pmsOperatorAdd";
		} catch (Exception e) {
			log.error("== addPmsOperatorUI exception:", e);
			return operateError("获取角色列表数据失败", model);
		}
	}

	/**
	 * 保存一个操作员
	 * 
	 */
	@RequiresPermissions("pms:operator:add")
	@RequestMapping("/add")
	public String addPmsOperator(HttpServletRequest req, PmsOperator pmsOperator, @RequestParam("selectVal") String selectVal, Model model, DwzAjax dwz) {
		try {
			pmsOperator.setType(OperatorTypeEnum.USER.name()); // 类型（
																// "0":'普通操作员',"1":'超级管理员'），只能添加普通操作员
			String roleOperatorStr = getRoleOperatorStr(selectVal);

			// 表单数据校验
			String validateMsg = validatePmsOperator(pmsOperator, roleOperatorStr);


			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg, model); // 返回错误信息
			}

			// 校验操作员登录名是否已存在
			PmsOperator loginNameCheck = pmsOperatorService.findOperatorByLoginName(pmsOperator.getLoginName());
			if (loginNameCheck != null) {
				return operateError("登录名【" + pmsOperator.getLoginName() + "】已存在", model);
			}

			PasswordHelper.encryptPassword(pmsOperator);
			pmsOperator.setCreater(getPmsOperator().getLoginName());
			pmsOperator.setCreateTime(new Date());
			pmsOperatorService.saveOperator(pmsOperator, roleOperatorStr);

			return operateSuccess(model, dwz);
		} catch (Exception e) {
			log.error("== addPmsOperator exception:", e);
			return operateError("保存操作员信息失败", model);
		}
	}

	/**
	 * 验证输入的邮箱格式是否符合
	 * 
	 * @param email
	 * @return 是否合法
	 */
	public static boolean emailFormat(String email) {
		// boolean tag = true;
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		boolean result = Pattern.matches(check, email);
		return result;
	}

	/**
	 * 验证输入的密码格式是否符合
	 * 
	 * @param loginPwd
	 * @return 是否合法
	 */
	public static boolean loginPwdFormat(String loginPwd) {
		return loginPwd.matches(".*?[^a-zA-Z\\d]+.*?") && loginPwd.matches(".*?[a-zA-Z]+.*?") && loginPwd.matches(".*?[\\d]+.*?");
	}

	/**
	 * 验证输入的操作员姓名格式是否符合
	 * 
	 * @param loginPwd
	 * @return 是否合法
	 */
	public static boolean realNameFormat(String realName) {
		return realName.matches("[^\\x00-\\xff]+");
	}

	/**
	 * 校验Pms操作员表单数据.
	 * 
	 * @param PmsOperator
	 *            操作员信息.
	 * @param roleOperatorStr
	 *            关联的角色ID串.
	 * @return
	 */
	private String validatePmsOperator(PmsOperator operator, String roleOperatorStr) {
		String msg = ""; // 用于存放校验提示信息的变量
		msg += ValidateUtils.lengthValidate("真实姓名", operator.getRealName(), true, 2, 15);
		msg += ValidateUtils.lengthValidate("登录名", operator.getLoginName(), true, 3, 50);

		/*
		 * String specialChar = "`!@#$%^&*()_+\\/"; if
		 * (operator.getLoginName().contains(specialChar)) { msg +=
		 * "登录名不能包含特殊字符，"; }
		 */
//		if (!realNameFormat(operator.getRealName())) {
//			msg += "操作员姓名必须为中文！";
//		}

		// if (!emailFormat(operator.getLoginName())) {
		// msg += "账户名格式必须为邮箱地址！";
		// }

		// 登录密码
//		String loginPwd = operator.getLoginPwd();
//		String loginPwdMsg = ValidateUtils.lengthValidate("登录密码", loginPwd, true, 6, 50);
//		/*
//		 * if (StringUtils.isBlank(loginPwdMsg) &&
//		 * !ValidateUtils.isAlphanumeric(loginPwd)) { loginPwdMsg +=
//		 * "登录密码应为字母或数字组成，"; }
//		 */
//		msg += loginPwdMsg;

		// 手机号码
		String mobileNo = operator.getMobileNo();
		String mobileNoMsg = ValidateUtils.lengthValidate("手机号", mobileNo, true, 0, 12);
		if (StringUtils.isBlank(mobileNoMsg) && !ValidateUtils.isMobile(mobileNo)) {
			mobileNoMsg += "手机号格式不正确，";
		}
		msg += mobileNoMsg;

		// 状态
		String status = operator.getStatus();
		if (status == null) {
			msg += "请选择状态，";
		} else if (!PublicStatusEnum.ACTIVE.name().equals(status) || PublicStatusEnum.UNACTIVE.name().equals(status)) {
			msg += "状态值不正确，";
		}

		msg += ValidateUtils.lengthValidate("描述", operator.getRemark(), true, 3, 100);

		// 新增操作员的权限不能为空，为空没意义
		if (StringUtils.isBlank(roleOperatorStr) && operator.getId() == null) {
			msg += "操作员关联的角色不能为空";
		}
		return msg;
	}

	/**
	 * 删除操作员
	 * 
	 * @return
	 * */
	@RequestMapping("/delete")
	public String deleteOperatorStatus(HttpServletRequest req, Long id, Model model, DwzAjax dwz) {
		pmsOperatorService.deleteOperatorById(id);
		return this.operateSuccess(model, dwz);
	}

	/**
	 * 转到修改操作员界面
	 * 
	 * @return PmsOperatorEdit or operateError .
	 */
	@RequiresPermissions("pms:operator:edit")
	@RequestMapping("/editUI")
	public String editPmsOperatorUI(HttpServletRequest req, Long id, Model model) {
		try {
			PmsOperator pmsOperator = pmsOperatorService.getDataById(id);
			if (pmsOperator == null) {
				return operateError("无法获取要修改的数据", model);
			}

			// 普通操作员没有修改超级管理员的权限
			if (OperatorTypeEnum.USER.name().equals(this.getPmsOperator().getType()) && OperatorTypeEnum.ADMIN.name().equals(pmsOperator.getType())) {
				return operateError("权限不足", model);
			}
			// 准备角色列表
			model.addAttribute("rolesList", pmsRoleService.listAllRole());

			// 准备该用户拥有的角色ID字符串
			List<PmsOperatorRole> lisPmsOperatorRoles = pmsOperatorRoleService.listOperatorRoleByOperatorId(id);
			StringBuffer owenedRoleIdBuffer = new StringBuffer("");
			for (PmsOperatorRole pmsOperatorRole : lisPmsOperatorRoles) {
				owenedRoleIdBuffer.append(pmsOperatorRole.getRoleId());
				owenedRoleIdBuffer.append(",");
			}
			String owenedRoleIds = owenedRoleIdBuffer.toString();
			if (StringUtils.isNotBlank(owenedRoleIds) && owenedRoleIds.length() > 0) {
				owenedRoleIds = owenedRoleIds.substring(0, owenedRoleIds.length() - 1);
			}
			model.addAttribute("owenedRoleIds", owenedRoleIds);

			model.addAttribute("OperatorStatusEnum", PublicStatusEnum.toMap());
			model.addAttribute("OperatorTypeEnum", OperatorTypeEnum.toMap());
			model.addAttribute("pmsOperator", pmsOperator);
			return "pms/pmsOperatorEdit";
		} catch (Exception e) {
			log.error("== editPmsOperatorUI exception:", e);
			return operateError("获取修改数据失败", model);
		}
	}

	/**
	 * 保存修改后的操作员信息
	 * 
	 * @return operateSuccess or operateError .
	 */
	@RequiresPermissions("pms:operator:edit")
	@RequestMapping("/edit")
	public String editPmsOperator(HttpServletRequest req, PmsOperator operator, String selectVal, Model model, DwzAjax dwz) {
		try {
			Long id = operator.getId();

			PmsOperator pmsOperator = pmsOperatorService.getDataById(id);
			if (pmsOperator == null) {
				return operateError("无法获取要修改的操作员信息", model);
			}

			// 普通操作员没有修改超级管理员的权限
			if ("USER".equals(this.getPmsOperator().getType()) && "ADMIN".equals(pmsOperator.getType())) {
				return operateError("权限不足", model);
			}

			pmsOperator.setRemark(operator.getRemark());
			pmsOperator.setMobileNo(operator.getMobileNo());
			pmsOperator.setRealName(operator.getRealName());
			// 修改时不能修状态
			// pmsOperator.setStatus(getInteger("status"));

			String roleOperatorStr = getRoleOperatorStr(selectVal);

			// 表单数据校验
			String validateMsg = validatePmsOperator(pmsOperator, roleOperatorStr);
			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg, model); // 返回错误信息
			}

			pmsOperatorService.updateOperator(pmsOperator, roleOperatorStr);
			return operateSuccess(model, dwz);
		} catch (Exception e) {
			log.error("== editPmsOperator exception:", e);
			return operateError("更新操作员信息失败", model);
		}
	}

	/**
	 * 根据ID冻结或激活操作员.
	 * 
	 * @return operateSuccess or operateError .
	 */
	@RequiresPermissions("pms:operator:changestatus")
	@RequestMapping("/changeStatus")
	public String changeOperatorStatus(HttpServletRequest req, PmsOperator operator, Model model, DwzAjax dwz) {
		try {
			Long operatorId = operator.getId();
			PmsOperator pmsOperator = pmsOperatorService.getDataById(operatorId);
			if (pmsOperator == null) {
				return operateError("无法获取要操作的数据", model);
			}

			if (this.getPmsOperator().getId() == operatorId) {
				return operateError("不能修改自己账户的状态", model);
			}

			// 普通操作员没有修改超级管理员的权限
			if ("USER".equals(this.getPmsOperator().getType()) && "ADMIN".equals(pmsOperator.getType())) {
				return operateError("权限不足", model);
			}

			// 2014-01-02,由删除改为修改状态
			// pmsPermissionBiz.deleteOperator(id);
			// 激活的变冻结，冻结的则变激活
			if (pmsOperator.getStatus().equals(PublicStatusEnum.ACTIVE.name())) {
				if ("ADMIN".equals(pmsOperator.getType())) {
					return operateError("【" + pmsOperator.getLoginName() + "】为超级管理员，不能冻结", model);
				}
				pmsOperator.setStatus(PublicStatusEnum.UNACTIVE.name());
				pmsOperatorService.updateData(pmsOperator);
			} else {
				pmsOperator.setStatus(PublicStatusEnum.ACTIVE.name());
				pmsOperatorService.updateData(pmsOperator);
			}
			return operateSuccess(model, dwz);
		} catch (Exception e) {
			log.error("== changeOperatorStatus exception:", e);
			return operateError("删除操作员失败:" + e.getMessage(), model);
		}
	}

	/***
	 * 重置操作员的密码（注意：不是修改当前登录操作员自己的密码） .
	 * 
	 * @return
	 */
	@RequiresPermissions("pms:operator:resetpwd")
	@RequestMapping("/resetPwdUI")
	public String resetOperatorPwdUI(HttpServletRequest req, Long id, Model model) {
		PmsOperator operator = pmsOperatorService.getDataById(id);
		if (operator == null) {
			return operateError("无法获取要重置的信息", model);
		}

		// 普通操作员没有修改超级管理员的权限
		if ("USER".equals(this.getPmsOperator().getType()) && "ADMIN".equals(operator.getType())) {
			return operateError("权限不足", model);
		}

		model.addAttribute("operator", operator);

		return "pms/pmsOperatorResetPwd";
	}

	/**
	 * 重置操作员密码.
	 * 
	 * @return
	 */
	@RequiresPermissions("pms:operator:resetpwd")
	@RequestMapping("/resetPwd")
	public String resetOperatorPwd(HttpServletRequest req, Long id, String newPwd, String newPwd2, Model model, DwzAjax dwz) {
		try {
			PmsOperator operator = pmsOperatorService.getDataById(id);
			if (operator == null) {
				return operateError("无法获取要重置密码的操作员信息", model);
			}

			// 普通操作员没有修改超级管理员的权限
			if ("USER".equals(this.getPmsOperator().getType()) && "ADMIN".equals(operator.getType())) {
				return operateError("权限不足", model);
			}


			String validateMsg = validatePassword(newPwd, newPwd2);
			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg, model); // 返回错误信息
			}
			operator.setLoginPwd(newPwd);
			PasswordHelper.encryptPassword(operator);
			pmsOperatorService.updateData(operator);
			return operateSuccess(model, dwz);
		} catch (Exception e) {
			log.error("== resetOperatorPwd exception:", e);
			return operateError("密码重置出错:" + e.getMessage(), model);
		}
	}

	/**
	 * 得到角色和操作员关联的ID字符串
	 * 
	 * @return
	 */
	private String getRoleOperatorStr(String selectVal) throws Exception {
		String roleStr = selectVal;
		if (StringUtils.isNotBlank(roleStr) && roleStr.length() > 0) {
			roleStr = roleStr.substring(0, roleStr.length() - 1);
		}
		return roleStr;
	}

	/***
	 * 验证重置密码
	 * 
	 * @param newPwd
	 * @param newPwd2
	 * @return
	 */
	private String validatePassword(String newPwd, String newPwd2) {
		String msg = ""; // 用于存放校验提示信息的变量
		if (StringUtils.isBlank(newPwd)) {
			msg += "新密码不能为空，";
		} else if (newPwd.length() < 6) {
			msg += "新密码不能少于6位长度，";
		}

		if (!newPwd.equals(newPwd2)) {
			msg += "两次输入的密码不一致";
		}
		return msg;
	}
}
