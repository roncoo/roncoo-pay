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
package com.roncoo.pay.account.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算日汇总vo.
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public class DailyCollectAccountHistoryVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2451289258390618916L;

	/**
	 * 账户编号
	 */
	private String accountNo;

	/**
	 * 汇总日期
	 */
	private Date collectDate;

	/**
	 * 总金额
	 */
	private BigDecimal totalAmount = BigDecimal.ZERO;

	/**
	 * 总笔数
	 */
	private Integer totalNum = 0;

	/**
	 * 最后ID
	 */
	private Long lastId = 0L;

	/**
	 * 风险预存期
	 */
	private Integer riskDay;

	/**
	 * 账户编号
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * 账户编号
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * 汇总日期
	 */
	public Date getCollectDate() {
		return collectDate;
	}

	/**
	 * 汇总日期
	 */
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	/**
	 * 总金额
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * 总金额
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * 总笔数
	 */
	public Integer getTotalNum() {
		return totalNum;
	}

	/**
	 * 总笔数
	 */
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * 最后ID
	 * 
	 * @return
	 */
	public Long getLastId() {
		return lastId;
	}

	/**
	 * 最后ID
	 * 
	 * @return
	 */
	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}

	/**
	 * 风险预存期
	 */
	public Integer getRiskDay() {
		return riskDay;
	}

	/**
	 * 风险预存期
	 */
	public void setRiskDay(Integer riskDay) {
		this.riskDay = riskDay;
	}

}
