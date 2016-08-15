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
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.controller.common.BaseController;
import com.roncoo.pay.permission.biz.PmsMenuBiz;
import com.roncoo.pay.permission.entity.PmsOperator;
import com.roncoo.pay.permission.entity.PmsPermission;
import com.roncoo.pay.permission.entity.PmsRole;
import com.roncoo.pay.permission.enums.OperatorTypeEnum;
import com.roncoo.pay.permission.service.PmsMenuRoleService;
import com.roncoo.pay.permission.service.PmsMenuService;
import com.roncoo.pay.permission.service.PmsOperatorRoleService;
import com.roncoo.pay.permission.service.PmsPermissionService;
import com.roncoo.pay.permission.service.PmsRolePermissionService;
import com.roncoo.pay.permission.service.PmsRoleService;
import com.roncoo.pay.permission.utils.ValidateUtils;

/**
 * 权限管理模块角色管理、.<br/>
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
@Controller
@RequestMapping("/pms/role")
public class PmsRoleController extends BaseController {

	@Autowired
	private PmsRoleService pmsRoleService;
	@Autowired
	private PmsMenuService pmsMenuService;
	@Autowired
	private PmsMenuRoleService pmsMenuRoleService;
	@Autowired
	private PmsPermissionService pmsPermissionService;
	@Autowired
	private PmsRolePermissionService pmsRolePermissionService;
	@Autowired
	private PmsOperatorRoleService pmsOperatorRoleService;

	private static Log log = LogFactory.getLog(PmsRoleController.class);

	/**
	 * 获取角色列表
	 * 
	 * @return listPmsRole or operateError .
	 */
	@RequiresPermissions("pms:role:view")
	@RequestMapping("/list")
	public String listPmsRole(HttpServletRequest req, PageParam pageParam, PmsRole pmsRole, Model model) {
		try {
			PageBean pageBean = pmsRoleService.listPage(pageParam, pmsRole);
			model.addAttribute(pageBean);
			model.addAttribute("pageParam", pageParam);
			model.addAttribute("pmsRole", pmsRole);
			return "pms/pmsRoleList";
		} catch (Exception e) {
			log.error("== listPmsRole exception:", e);
			return operateError("获取数据失败", model);
		}
	}

	/**
	 * 转到添加角色页面 .
	 * 
	 * @return addPmsRoleUI or operateError .
	 */
	@RequiresPermissions("pms:role:add")
	@RequestMapping("/addUI")
	public String addPmsRoleUI(HttpServletRequest req, Model model) {
		try {
			return "pms/pmsRoleAdd";
		} catch (Exception e) {
			log.error("== addPmsRoleUI get data exception:", e);
			return operateError("获取数据失败", model);
		}
	}

	/**
	 * 保存新添加的一个角色 .
	 * 
	 * @return operateSuccess or operateError .
	 */
	@RequiresPermissions("pms:role:add")
	@RequestMapping("/add")
	public String addPmsRole(HttpServletRequest req, Model model, @RequestParam("roleCode") String roleCode, @RequestParam("roleName") String roleName, @RequestParam("remark") String remark, DwzAjax dwz) {
		try {
			PmsRole roleNameCheck = pmsRoleService.getByRoleNameOrRoleCode(roleName, null);
			if (roleNameCheck != null) {
				return operateError("角色名【" + roleName + "】已存在", model);
			}

			PmsRole roleCodeCheck = pmsRoleService.getByRoleNameOrRoleCode(null, roleCode);
			if (roleCodeCheck != null) {
				return operateError("角色编码【" + roleCode + "】已存在", model);
			}

			// 保存基本角色信息
			PmsRole pmsRole = new PmsRole();
			pmsRole.setRoleCode(roleCode);
			pmsRole.setRoleName(roleName);
			pmsRole.setRemark(remark);
			pmsRole.setCreateTime(new Date());

			// 表单数据校验
			String validateMsg = validatePmsRole(pmsRole);
			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg, model); // 返回错误信息
			}
			pmsRoleService.saveData(pmsRole);
			return operateSuccess(model, dwz);
		} catch (Exception e) {
			log.error("== addPmsRole exception:", e);
			return operateError("保存数据失败", model);
		}
	}

	/**
	 * 校验角色表单数据.
	 * 
	 * @param pmsRole
	 *            角色信息.
	 * @return msg .
	 */
	private String validatePmsRole(PmsRole pmsRole) {
		String msg = ""; // 用于存放校验提示信息的变量
		String roleName = pmsRole.getRoleName(); // 角色名称
		String desc = pmsRole.getRemark(); // 描述
		// 角色名称 permissionName
		msg += ValidateUtils.lengthValidate("角色名称", roleName, true, 3, 90);
		// 描述 desc
		msg += ValidateUtils.lengthValidate("描述", desc, true, 3, 300);
		return msg;
	}

	/**
	 * 转到角色修改页面 .
	 * 
	 * @return editPmsRoleUI or operateError .
	 */
	@RequiresPermissions("pms:role:edit")
	@RequestMapping("/editUI")
	public String editPmsRoleUI(HttpServletRequest req, Model model, Long roleId) {
		try {
			PmsRole pmsRole = pmsRoleService.getDataById(roleId);
			if (pmsRole == null) {
				return operateError("获取数据失败", model);
			}

			model.addAttribute(pmsRole);
			return "/pms/pmsRoleEdit";
		} catch (Exception e) {
			log.error("== editPmsRoleUI exception:", e);
			return operateError("获取数据失败", model);
		}
	}

	/**
	 * 保存修改后的角色信息 .
	 * 
	 * @return operateSuccess or operateError .
	 */
	@RequiresPermissions("pms:role:edit")
	@RequestMapping("/edit")
	public String editPmsRole(HttpServletRequest req, Model model, PmsRole role, DwzAjax dwz) {
		try {
			Long id = role.getId();

			PmsRole pmsRole = pmsRoleService.getDataById(id);
			if (pmsRole == null) {
				return operateError("无法获取要修改的数据", model);
			}

			PmsRole roleNameCheck = pmsRoleService.getByRoleNameOrRoleCode(role.getRoleName(), null);
			if (roleNameCheck != null) {
				return operateError("角色名【" + role.getRoleName() + "】已存在", model);
			}

			PmsRole roleCodeCheck = pmsRoleService.getByRoleNameOrRoleCode(null, role.getRoleCode());
			if (roleCodeCheck != null) {
				return operateError("角色编码【" + role.getRoleCode() + "】已存在", model);
			}

			pmsRole.setRoleName(role.getRoleName());
			pmsRole.setRoleCode(role.getRoleCode());
			pmsRole.setRemark(role.getRemark());

			// 表单数据校验
			String validateMsg = validatePmsRole(pmsRole);
			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg, model); // 返回错误信息
			}
			pmsRoleService.updateData(pmsRole);
			return operateSuccess(model, dwz);
		} catch (Exception e) {
			log.error("== editPmsRole exception:", e);
			return operateError("保存失败", model);
		}
	}

	/**
	 * 删除一个角色
	 * 
	 * @return operateSuccess or operateError .
	 */
	@RequiresPermissions("pms:role:delete")
	@RequestMapping("/delete")
	public String deletePmsRole(HttpServletRequest req, Model model, Long roleId, DwzAjax dwz) {
		try {

			PmsRole role = pmsRoleService.getDataById(roleId);
			if (role == null) {
				return operateError("无法获取要删除的角色", model);
			}
			String msg = "";
			// 判断是否有操作员关联到此角色
			int operatorCount = pmsOperatorRoleService.countOperatorByRoleId(roleId);
			if (operatorCount > 0) {
				msg += "有【" + operatorCount + "】个操作员关联到此角色，要先解除所有关联后才能删除!";
				return operateError(msg, model);
			}

			pmsRoleService.delete(roleId);
			return operateSuccess(model, dwz);
		} catch (Exception e) {
			log.error("== deletePmsRole exception:", e);
			return operateError("删除失败", model);
		}
	}
	
	/**
	 * 分配权限UI
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequiresPermissions("pms:role:assignpermission")
	@RequestMapping("/assignPermissionUI")
	public String assignPermissionUI(HttpServletRequest req, Model model, Long roleId) {

		PmsRole role = pmsRoleService.getDataById(roleId);
		if (role == null) {
			return operateError("无法获取角色信息", model);
		}
		// 普通操作员没有修改超级管理员角色的权限
		if (OperatorTypeEnum.USER.name().equals(this.getPmsOperator().getType()) && "admin".equals(role.getRoleName())) {
			return operateError("权限不足", model);
		}

		String permissionIds = pmsPermissionService.getPermissionIdsByRoleId(roleId); // 根据角色查找角色对应的功能权限ID集
		List<PmsPermission> permissionList = pmsPermissionService.listAll();
		List<PmsOperator> operatorList = pmsOperatorRoleService.listOperatorByRoleId(roleId);

		model.addAttribute("permissionIds", permissionIds);
		model.addAttribute("permissionList", permissionList);
		model.addAttribute("operatorList", operatorList);
		model.addAttribute("role", role);
		return "/pms/assignPermissionUI";
	}

	/**
	 * 分配角色权限
	 */
	@RequiresPermissions("pms:role:assignpermission")
	@RequestMapping("/assignPermission")
	public String assignPermission(HttpServletRequest req, Model model, @RequestParam("roleId") Long roleId, DwzAjax dwz, @RequestParam("selectVal") String selectVal) {
		try {
			String rolePermissionStr = getRolePermissionStr(selectVal);
			pmsRolePermissionService.saveRolePermission(roleId, rolePermissionStr);
			return operateSuccess(model, dwz);
		} catch (Exception e) {
			log.error("== assignPermission exception:", e);
			return operateError("保存失败", model);
		}
	}
	
	/**
	 * 分配菜单UI
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/assignMenuUI")
	public String assignMenuUI(HttpServletRequest req, Model model, Long roleId) {
		PmsRole role = pmsRoleService.getDataById(roleId);
		if (role == null) {
			return operateError("无法获取角色信息", model);
		}
		// 普通操作员没有修改超级管理员角色的权限
		if (OperatorTypeEnum.USER.name().equals(this.getPmsOperator().getType()) && "admin".equals(role.getRoleName())) {
			return operateError("权限不足", model);
		}

		String menuIds = pmsMenuService.getMenuIdsByRoleId(roleId); // 根据角色查找角色对应的菜单ID集
		List menuList = pmsMenuService.getListByParent(null);
		List<PmsOperator> operatorList = pmsOperatorRoleService.listOperatorByRoleId(roleId);

		model.addAttribute("menuIds", menuIds);
		model.addAttribute("menuList", menuList);
		model.addAttribute("operatorList", operatorList);
		model.addAttribute("role", role);
		return "/pms/assignMenuUI";
	}

	/**
	 * 分配角色菜单
	 */
	@RequestMapping("/assignMenu")
	public String assignMenu(HttpServletRequest req, Model model, @RequestParam("roleId") Long roleId, DwzAjax dwz, @RequestParam("selectVal") String selectVal) {
		try {
			String roleMenuStr = getRolePermissionStr(selectVal);
			pmsMenuRoleService.saveRoleMenu(roleId, roleMenuStr);
			return operateSuccess(model, dwz);
		} catch (Exception e) {
			log.error("== assignPermission exception:", e);
			return operateError("保存失败", model);
		}
	}

	/**
	 * 得到角色和权限关联的ID字符串
	 * 
	 * @return
	 */
	private String getRolePermissionStr(String selectVal) throws Exception {
		String roleStr = selectVal;
		if (StringUtils.isNotBlank(roleStr) && roleStr.length() > 0) {
			roleStr = roleStr.substring(0, roleStr.length() - 1);
		}
		return roleStr;
	}
}
