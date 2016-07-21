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
package com.roncoo.pay.controller.pay;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.roncoo.pay.common.core.dwz.DWZ;
import com.roncoo.pay.common.core.dwz.DwzAjax;
import com.roncoo.pay.common.core.enums.PayTypeEnum;
import com.roncoo.pay.common.core.enums.PayWayEnum;
import com.roncoo.pay.common.core.enums.PublicEnum;
import com.roncoo.pay.common.core.enums.PublicStatusEnum;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.user.entity.RpPayProduct;
import com.roncoo.pay.user.entity.RpPayWay;
import com.roncoo.pay.user.exception.PayBizException;
import com.roncoo.pay.user.service.RpPayProductService;
import com.roncoo.pay.user.service.RpPayWayService;

/**
 * 支付方式管理
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
@Controller
@RequestMapping("/pay/way")
public class PayWayController {
	
	@Autowired
	private RpPayWayService rpPayWayService;
	
	@Autowired
	private RpPayProductService rpPayProductService;

	/**
	 * 函数功能说明 ： 查询分页数据
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/list", method ={RequestMethod.POST,RequestMethod.GET})
	public String list(RpPayWay rpPayWay, PageParam pageParam, Model model) {
		// payProductCode 每次添加或编辑后 会变成以“,”分隔的重复数据
		if(!StringUtil.isEmpty(rpPayWay.getPayProductCode())&&rpPayWay.getPayProductCode().contains(",")){
			String[] payProductCodes = rpPayWay.getPayProductCode().split(",");
			rpPayWay.setPayProductCode(payProductCodes[0]);
		}
		RpPayProduct rpPayProduct = rpPayProductService.getByProductCode(rpPayWay.getPayProductCode(), null);
		PageBean pageBean = rpPayWayService.listPage(pageParam, rpPayWay);
		model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpPayWay", rpPayWay);
        model.addAttribute("rpPayProduct", rpPayProduct);
		return "pay/way/list";
	}
	
	/**
	 * 函数功能说明 ：跳转添加
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("pay:way:add")
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(Model model, @RequestParam("payProductCode") String payProductCode) {
		model.addAttribute("PayWayEnums", PayWayEnum.toList());
		model.addAttribute("PayTypeEnums", PayTypeEnum.toList());
		model.addAttribute("payProductCode", payProductCode);
		return "pay/way/add";
	}
	
	/**
	 * 函数功能说明 ： 保存
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("pay:way:add")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Model model, RpPayWay rpPayWay,DwzAjax dwz) {
		rpPayWayService.createPayWay(rpPayWay.getPayProductCode(), rpPayWay.getPayWayCode(), rpPayWay.getPayTypeCode(), rpPayWay.getPayRate());
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}

	/**
	 * 函数功能说明 ：跳转编辑
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("pay:way:edit")
	@RequestMapping(value = "/editUI", method = RequestMethod.GET)
	public String editUI(Model model,@RequestParam("id") String id) {
		RpPayWay rpPayWay = rpPayWayService.getDataById(id);
		model.addAttribute("PayWayEnums", PayWayEnum.toList());
		model.addAttribute("PayTypeEnums", PayTypeEnum.toList());
		model.addAttribute("rpPayWay", rpPayWay);
		return "pay/way/edit";
	}
	
	/**
	 * 函数功能说明 ： 更新
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("pay:way:edit")
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(Model model, RpPayWay rpPayWay,DwzAjax dwz) {
		RpPayWay rpPayWayOld = rpPayWayService.getDataById(rpPayWay.getId());
		rpPayWayOld.setEditTime(new Date());
		rpPayWayOld.setPayRate(rpPayWay.getPayRate());
		RpPayProduct rpPayProduct = rpPayProductService.getByProductCode(rpPayWay.getPayProductCode(), null);
		if(rpPayProduct.getAuditStatus().equals(PublicEnum.YES.name())){
			throw new PayBizException(PayBizException.PAY_PRODUCT_IS_EFFECTIVE,"支付产品已生效，无法删除！");
		}
		rpPayWayService.updateData(rpPayWayOld);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
	
	/**
	 * 函数功能说明 ： 删除
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("pay:way:delete")
	@RequestMapping(value = "/delete", method ={RequestMethod.POST,RequestMethod.GET})
	public String delete(Model model, DwzAjax dwz, @RequestParam("id") String id) {
		RpPayWay rpPayWay = rpPayWayService.getDataById(id);
		RpPayProduct rpPayProduct = rpPayProductService.getByProductCode(rpPayWay.getPayProductCode(), null);
		if(rpPayProduct.getAuditStatus().equals(PublicEnum.YES.name())){
			throw new PayBizException(PayBizException.PAY_PRODUCT_IS_EFFECTIVE,"支付产品已生效，无法删除！");
		}
		rpPayWay.setStatus(PublicStatusEnum.UNACTIVE.name());
		rpPayWayService.updateData(rpPayWay);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
	
	/**
	 * 函数功能说明 ：根据支付方式获取支付类型
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/getPayType", method = RequestMethod.GET)
	@ResponseBody
	public List getPayType(@RequestParam("payWayCode") String payWayCode) {
		return PayTypeEnum.getWayList(payWayCode);
	}
	
	/**
	 * 函数功能说明 ：根据支付产品获取支付方式
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/getPayWay", method = RequestMethod.GET)
	@ResponseBody
	public List getPayWay(@RequestParam("productCode") String productCode) {
		List<RpPayWay> payWayList = rpPayWayService.listByProductCode(productCode);
		
		Map<String, String> map = new HashMap<String, String>();
		//过滤重复数据
		for(RpPayWay payWay : payWayList){
	        map.put(payWay.getPayWayCode(), payWay.getPayWayName());
		}
		
		//转换json
		List list = new ArrayList();
		for (String key : map.keySet()) {
			Map<String, String> mapJson = new HashMap<String, String>();
			mapJson.put("desc", map.get(key));
			mapJson.put("name", key);
            list.add(mapJson);
		}
        return list;
	}
}
