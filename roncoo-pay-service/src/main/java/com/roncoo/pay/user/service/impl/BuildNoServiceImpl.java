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
package com.roncoo.pay.user.service.impl;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.roncoo.pay.user.service.BuildNoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 生成编号service实现类,每个编号前面都会有一个前缀用来方便区分是那种编号
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
@Service("buildNoService")
public class BuildNoServiceImpl implements BuildNoService {

	private static final Log LOG = LogFactory.getLog(BuildNoServiceImpl.class);

	/** 对账批次号前缀 **/
	private static final String RECONCILIATION_BATCH_NO = "5";

	/** 银行订单号 **/
	private static final String BANK_ORDER_NO_PREFIX = "6";
	/** 支付流水号前缀 **/
	private static final String TRX_NO_PREFIX = "7";

	/** 用户编号前缀 **/
	private static final String USER_NO_PREFIX = "8";

	/** 账户编号前缀 **/
	private static final String ACCOUNT_NO_PREFIX = "9";

	/** 获取用户编号 **/
	@Transactional(rollbackFor = Exception.class)
	public String buildUserNo() {
		String userNo = USER_NO_PREFIX + IdWorker.getId() ;
		return userNo;
	}

	/** 获取账户编号 **/
	@Transactional(rollbackFor = Exception.class)
	public String buildAccountNo() {
		String accountNo = ACCOUNT_NO_PREFIX + IdWorker.getId();
		return accountNo;
	}

	/**
	 * 获取支付流水号
	 **/
	@Override
	public String buildTrxNo() {
		String trxNo = TRX_NO_PREFIX + IdWorker.getId();
		return trxNo;
	}

	/**
	 * 获取银行订单号
	 **/
	@Override
	public String buildBankOrderNo() {
		String bankOrderNo = BANK_ORDER_NO_PREFIX + IdWorker.getId();
		return bankOrderNo;
	}

	/** 获取对账批次号 **/
	public String buildReconciliationNo() {
		String batchNo = RECONCILIATION_BATCH_NO + IdWorker.getId();
		return batchNo;
	}

}
