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
package com.roncoo.pay.app.settlement.biz;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.roncoo.pay.account.entity.RpAccount;
import com.roncoo.pay.account.service.RpSettHandleService;
import com.roncoo.pay.user.entity.RpUserPayConfig;
import com.roncoo.pay.user.service.RpUserPayConfigService;

/**
 * 每日待结算数据汇总.
 * 龙果学院：www.roncoo.com
 * @author zenghao
 */
@Component("dailySettCollectBiz")
public class DailySettCollectBiz {

	private static final Log LOG = LogFactory.getLog(DailySettCollectBiz.class);

	@Autowired
	private RpSettHandleService rpSettHandleService;
	
	@Autowired
	private RpUserPayConfigService rpUserPayConfigService;

	/**
	 * 按单个商户发起每日待结算数据统计汇总.<br/>
	 * 
	 * @param userEnterprise
	 *            单个商户的结算规则.<br/>
	 * @param endDate
	 *            统计日期 ==定时器执行的日期<br/>
	 */
	public void dailySettCollect(RpAccount rpAccount, Date endDate) {
		LOG.info("按单个商户发起每日待结算数据统计汇总");
		RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByUserNo(rpAccount.getUserNo());
		if (rpUserPayConfig == null) {
			LOG.info("userNo:" + rpAccount.getUserNo() + ":没有商家设置信息，不进行汇总");
			return;
		}
		int riskDay = rpUserPayConfig.getRiskDay();
		rpSettHandleService.dailySettlementCollect(rpUserPayConfig.getUserNo(), endDate, riskDay,rpUserPayConfig.getUserName());
	}
}
