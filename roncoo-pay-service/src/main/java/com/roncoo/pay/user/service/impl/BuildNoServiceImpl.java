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

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.roncoo.pay.common.core.exception.BizException;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.user.dao.BuildNoDao;
import com.roncoo.pay.user.entity.SeqBuild;
import com.roncoo.pay.user.service.BuildNoService;

/**
 * 生成编号service实现类,每个编号前面都会有一个前缀用来方便区分是那种编号
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
@Service("buildNoService")
public class BuildNoServiceImpl implements BuildNoService {

	private static final Log LOG = LogFactory.getLog(BuildNoServiceImpl.class);

	/** 对账批次号前缀 **/
	private static final String RECONCILIATION_BATCH_NO = "5555";

	/** 银行订单号 **/
	private static final String BANK_ORDER_NO_PREFIX = "6666";
	/** 支付流水号前缀 **/
	private static final String TRX_NO_PREFIX = "7777";

	/** 用户编号前缀 **/
	private static final String USER_NO_PREFIX = "8888";

	/** 账户编号前缀 **/
	private static final String ACCOUNT_NO_PREFIX = "9999";

	@Autowired
	private BuildNoDao buildNoDao;

	/** 获取用户编号 **/
	@Transactional(rollbackFor = Exception.class)
	public String buildUserNo() {
		// 获取用户编号序列
		String userNoSeq = this.getSeqNextValue("USER_NO_SEQ");

		// 20位的用户编号规范：'8888' + yyyyMMdd(时间) + 序列的后8位
		String dateString = DateUtils.toString(new Date(), "yyyyMMdd");
		String userNo = USER_NO_PREFIX + dateString + userNoSeq.substring(userNoSeq.length() - 8, userNoSeq.length());
		return userNo;
	}

	/** 获取账户编号 **/
	@Transactional(rollbackFor = Exception.class)
	public String buildAccountNo() {
		// 获取账户编号序列值，用于生成20位的账户编号
		String accountNoSeq = this.getSeqNextValue("ACCOUNT_NO_SEQ");
		// 20位的账户编号规范：'9999' + yyyyMMdd(时间) + 序列的后8位
		String dateString = DateUtils.toString(new Date(), "yyyyMMdd");
		String accountNo = ACCOUNT_NO_PREFIX + dateString + accountNoSeq.substring(accountNoSeq.length() - 8, accountNoSeq.length());

		return accountNo;
	}

	/**
	 * 获取支付流水号
	 **/
	@Override
	public String buildTrxNo() {

		String trxNoSeq = this.getSeqNextValue("TRX_NO_SEQ");
		// 20位的支付流水号规范：'8888' + yyyyMMdd(时间) + 序列的后8位
		String dateString = DateUtils.toString(new Date(), "yyyyMMdd");
		String trxNo = TRX_NO_PREFIX + dateString + trxNoSeq.substring(trxNoSeq.length() - 8, trxNoSeq.length());
		return trxNo;
	}

	/**
	 * 获取银行订单号
	 **/
	@Override
	public String buildBankOrderNo() {

		String bankOrderNoSeq = this.getSeqNextValue("BANK_ORDER_NO_SEQ");
		// 20位的用户编号规范：'8888' + yyyyMMdd(时间) + 序列的后8位
		String dateString = DateUtils.toString(new Date(), "yyyyMMdd");
		String bankOrderNo = BANK_ORDER_NO_PREFIX + dateString + bankOrderNoSeq.substring(bankOrderNoSeq.length() - 8, bankOrderNoSeq.length());
		return bankOrderNo;
	}

	/** 获取对账批次号 **/
	public String buildReconciliationNo() {
		String batchNoSeq = this.getSeqNextValue("RECONCILIATION_BATCH_NO_SEQ");
		String dateString = DateUtils.toString(new Date(), "yyyyMMdd");
		String batchNo = RECONCILIATION_BATCH_NO + dateString + batchNoSeq.substring(batchNoSeq.length() - 8, batchNoSeq.length());
		return batchNo;
	}

	/**
	 * 根据序列名称,获取序列值
	 */
	@Transactional(rollbackFor = Exception.class)
	public String getSeqNextValue(String seqName) {
		String seqNextValue = null;
		try {
			SeqBuild seqBuild = new SeqBuild();
			seqBuild.setSeqName(seqName);
			seqNextValue = buildNoDao.getSeqNextValue(seqBuild);
		} catch (Exception e) {
			LOG.error("生成序号异常：" + "seqName=" + seqName, e);
			throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
		}
		if (StringUtils.isEmpty(seqNextValue)) {
			throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
		}
		return seqNextValue;
	}

}