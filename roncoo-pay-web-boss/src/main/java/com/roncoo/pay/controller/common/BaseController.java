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
package com.roncoo.pay.controller.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;

import com.roncoo.pay.common.core.dwz.DWZ;
import com.roncoo.pay.common.core.dwz.DwzAjax;
import com.roncoo.pay.permission.entity.PmsOperator;


/**
 * controller基类
 * 龙果学院：www.roncoo.com
 * @author zenghao
 */ 
public abstract class BaseController {

	/**
	 * 获取shiro 的session
	 * 
	 * @return
	 */
	protected Session getSession() {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		return session;
	}

	/**
	 * 获取当前用户信息
	 * 
	 * @return
	 */
	protected PmsOperator getPmsOperator() {
		PmsOperator operator = (PmsOperator) this.getSession().getAttribute("PmsOperator");
		return operator;
	}

	/**
	 * 响应DWZ的ajax失败请求,跳转到ajaxDone视图.
	 * 
	 * @param message
	 *            提示消息.
	 * @param model
	 *            model.
	 * @return ajaxDone .
	 */
	protected String operateError(String message, Model model) {
		DwzAjax dwz = new DwzAjax();
		dwz.setStatusCode(DWZ.ERROR);
		dwz.setMessage(message);
		model.addAttribute("dwz", dwz);
		return "common/ajaxDone";
	}

	/**
	 * 响应DWZ的ajax失败成功,跳转到ajaxDone视图.
	 * 
	 * @param model
	 *            model.
	 * @param dwz
	 *            页面传过来的dwz参数
	 * @return ajaxDone .
	 */
	protected String operateSuccess(Model model, DwzAjax dwz) {
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage("操作成功");
		model.addAttribute("dwz", dwz);
		return "common/ajaxDone";
	}

}
