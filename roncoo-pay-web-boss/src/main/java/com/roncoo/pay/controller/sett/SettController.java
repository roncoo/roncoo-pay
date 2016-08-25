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
package com.roncoo.pay.controller.sett;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.roncoo.pay.account.entity.RpAccount;
import com.roncoo.pay.account.entity.RpSettRecord;
import com.roncoo.pay.account.service.RpAccountService;
import com.roncoo.pay.account.service.RpSettHandleService;
import com.roncoo.pay.account.service.RpSettQueryService;
import com.roncoo.pay.common.core.dwz.DWZ;
import com.roncoo.pay.common.core.dwz.DwzAjax;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
 
/**
 * 结算管理
 * @company：广州领课网络科技有限公司（龙果学院:www.roncoo.com）
 * @author：zenghao
 */
@Controller
@RequestMapping("/sett")
public class SettController {
	
	@Autowired
	private RpSettQueryService rpSettQueryService;
	@Autowired
	private RpSettHandleService rpSettHandleService;
	@Autowired
	private RpAccountService rpAccountService;

	/**
	 * 函数功能说明 ： 查询分页数据
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/list", method ={RequestMethod.POST,RequestMethod.GET})
	public String list(RpSettRecord rpSettRecord, PageParam pageParam, Model model) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", rpSettRecord.getUserName());
		PageBean pageBean = rpSettQueryService.querySettRecordListPage(pageParam, paramMap);
		model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpSettRecord", rpSettRecord);
		return "sett/list";
	}
	
	/**
	 * 函数功能说明 ：跳转发起结算
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("sett:record:add")
	@RequestMapping(value = "/launchSettUI", method = RequestMethod.GET)
	public String launchSettUI() {
		
		return "sett/add";
	}
	
	/**
	 * 函数功能说明 ： 发起结算
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("sett:record:add")
	@RequestMapping(value = "/launchSett", method = RequestMethod.POST)
	public String launchSett(HttpServletRequest request, Model model,DwzAjax dwz) {
		String userNo = request.getParameter("user.userNo");
		String settAmount = request.getParameter("settAmount");
		rpSettHandleService.launchSett(userNo, new BigDecimal(settAmount));
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
	
	/**
	 * 函数功能说明 ：跳转审核
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("sett:record:edit")
	@RequestMapping(value = "/auditUI", method = RequestMethod.GET)
	public String auditUI(Model model, @RequestParam("id") String id) {
		RpSettRecord settRecord = rpSettQueryService.getDataById(id);
		model.addAttribute("settRecord", settRecord);
		return "sett/audit";
	}
	
	/**
	 * 函数功能说明 ： 发起结算
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("sett:record:edit")
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	public String audit(HttpServletRequest request, Model model,DwzAjax dwz) {
		String settId = request.getParameter("settId");
		String settStatus = request.getParameter("settStatus");
		String remark = request.getParameter("remark");
		rpSettHandleService.audit(settId, settStatus, remark);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
	
	/**
	 * 函数功能说明 ：跳转打款
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("sett:record:edit")
	@RequestMapping(value = "/remitUI", method = RequestMethod.GET)
	public String remitUI(Model model, @RequestParam("id") String id) {
		RpSettRecord settRecord = rpSettQueryService.getDataById(id);
		model.addAttribute("settRecord", settRecord);
		return "sett/remit";
	}
	
	/**
	 * 函数功能说明 ： 发起打款
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("sett:record:edit")
	@RequestMapping(value = "/remit", method = RequestMethod.POST)
	public String remit(HttpServletRequest request, Model model,DwzAjax dwz) {
		String settId = request.getParameter("settId");
		String settStatus = request.getParameter("settStatus");
		String remark = request.getParameter("remark");
		rpSettHandleService.remit(settId, settStatus, remark);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
	
	/**
	 * 函数功能说明 ：查看
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, @RequestParam("id") String id) {
		RpSettRecord settRecord = rpSettQueryService.getDataById(id);
		model.addAttribute("settRecord", settRecord);
		return "sett/view";
	}
	
	/**
	 * 函数功能说明 ：根据支付产品获取支付方式
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/getSettAmount", method = RequestMethod.GET)
	@ResponseBody
	public RpAccount getSettAmount(@RequestParam("userNo") String userNo) {
		RpAccount rpAccount = rpAccountService.getDataByUserNo(userNo);
        return rpAccount;
	}
}
