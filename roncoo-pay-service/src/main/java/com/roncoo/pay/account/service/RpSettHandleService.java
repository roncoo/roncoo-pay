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
package com.roncoo.pay.account.service;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  结算核心业务处理接口
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public interface RpSettHandleService {

	/**
	 * 按单个商户发起每日待结算数据统计汇总.<br/>
	 * 
	 * @param userNo
	 *            用户编号.
	 * @param endDate
	 *            汇总结束日期.
	 * @param riskDay
	 *            风险预存期.
	 * @param userName
	 *            用户名称
	 * @param codeNum
	 *            企业代号
	 */
	public void dailySettlementCollect(String userNo, Date endDate, int riskDay, String userName);

	/**
	 * 发起结算
	 * 
	 * @param userNo
	 * @param accountNo
	 * @param settAmount
	 * @param bankAccount
	 */
	public void launchSett(String userNo, BigDecimal settAmount);

	/**
	 * 发起自动结算
	 * 
	 * @param userNo 
	 */
	public void launchAutoSett(String userNo);
	
	
	/**
	 * 结算审核
	 */
	public void audit(String settId, String settStatus, String remark);
	
	/**
	 * 打款
	 */
	public void remit(String settId, String settStatus, String remark);
	
}