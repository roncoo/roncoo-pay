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
package com.roncoo.pay.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.roncoo.pay.common.core.utils.EncryptUtil;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.controller.common.BaseController;
import com.roncoo.pay.controller.common.ConstantClass;
import com.roncoo.pay.user.entity.RpUserInfo;
import com.roncoo.pay.user.service.RpUserInfoService;

/**
 * 登录
 * 龙果学院：www.roncoo.com
 * @author zenghao
 */
@Controller
public class LoginController extends BaseController {
	@Autowired
	private RpUserInfoService rpUserInfoService;

	/**
	 * 函数功能说明 ：登录
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/login", method ={RequestMethod.POST,RequestMethod.GET})
	public String login(HttpServletRequest request, Model model) {
		
		return "system/login";
	}
	
	/**
	 * 函数功能说明 ：退出
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/logout", method ={RequestMethod.POST,RequestMethod.GET})
	public String logout(HttpServletRequest request, Model model) {
		request.getSession().removeAttribute(ConstantClass.USER);
		return "system/login";
	}
	
	/**
	 * 函数功能说明 ：首页
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/index", method ={RequestMethod.POST,RequestMethod.GET})
	public String index(HttpServletRequest request, Model model) {
		// 获取登录的用户
        RpUserInfo rpUserInfo = (RpUserInfo)request.getSession().getAttribute(ConstantClass.USER);
        if(rpUserInfo != null){
        	return "system/index";
        }
		
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");
		String msg = "";
		if(StringUtil.isEmpty(mobile)){
			msg = "请输入手机号/密码";
			model.addAttribute("msg", msg);
			return "system/login";
		}
		if(StringUtil.isEmpty(password)){
			msg = "请输入手机号/密码";
			model.addAttribute("msg", msg);
			return "system/login";
		}
		rpUserInfo = rpUserInfoService.getDataByMobile(mobile);
		if (rpUserInfo == null) {
			msg = "用户名/密码错误";
		}
		else if(!EncryptUtil.encodeMD5String(password).equals(rpUserInfo.getPassword())){
			msg = "用户名/密码错误";
		}
		model.addAttribute("mobile", mobile);
		model.addAttribute("password", password);
		request.getSession().setAttribute(ConstantClass.USER, rpUserInfo);
		if(!StringUtil.isEmpty(msg)){
			model.addAttribute("msg", msg);
			return "system/login";
		}
		return "system/index";
	}

}
