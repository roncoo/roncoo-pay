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
package com.roncoo.pay.account.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.roncoo.pay.common.core.entity.BaseEntity;

/**
 * 账户信息
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public class RpAccount extends BaseEntity implements Serializable {

	/** 账户编号 **/
    private String accountNo;
    
    /** 账户余额 **/
    private BigDecimal balance;

    /** 不可用余额 **/
    private BigDecimal unbalance;

    /** 保证金 **/
    private BigDecimal securityMoney;

    /** 总收益 **/
    private BigDecimal totalIncome;

    /** 总支出 **/
    private BigDecimal totalExpend;

    /** 今日收益  **/
    private BigDecimal todayIncome;

    /** 今日支出 **/
    private BigDecimal todayExpend;

    /** 账户类型 **/
    private String accountType;

    /** 可结算金额 **/
    private BigDecimal settAmount;

    /** 用户编号 **/
    private String userNo;

    private static final long serialVersionUID = 1L;
    
    /************************* just show ************************************/
	private String userName;
	/************************* just show ************************************/
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取可用余额
	 * 
	 * @return
	 */
	public BigDecimal getAvailableBalance() {
		return this.balance.subtract(unbalance);
	}

	/**
	 * 获取实际可结算金额
	 * 
	 * @return
	 */
	public BigDecimal getAvailableSettAmount() {
		BigDecimal subSettAmount = this.settAmount.subtract(unbalance);
		if (getAvailableBalance().compareTo(subSettAmount) == -1) {
			return getAvailableBalance();
		}
		return subSettAmount;
	}

	/**
	 * 验证可用余额是否足够
	 * 
	 * @param amount
	 * @return
	 */
	public boolean availableBalanceIsEnough(BigDecimal amount) {

		return this.getAvailableBalance().compareTo(amount) >= 0;
	}


    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getUnbalance() {
        return unbalance;
    }

    public void setUnbalance(BigDecimal unbalance) {
        this.unbalance = unbalance;
    }

    public BigDecimal getSecurityMoney() {
        return securityMoney;
    }

    public void setSecurityMoney(BigDecimal securityMoney) {
        this.securityMoney = securityMoney;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalExpend() {
        return totalExpend;
    }

    public void setTotalExpend(BigDecimal totalExpend) {
        this.totalExpend = totalExpend;
    }

    public BigDecimal getTodayIncome() {
        return todayIncome;
    }

    public void setTodayIncome(BigDecimal todayIncome) {
        this.todayIncome = todayIncome;
    }

    public BigDecimal getTodayExpend() {
        return todayExpend;
    }

    public void setTodayExpend(BigDecimal todayExpend) {
        this.todayExpend = todayExpend;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public BigDecimal getSettAmount() {
        return settAmount;
    }

    public void setSettAmount(BigDecimal settAmount) {
        this.settAmount = settAmount;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

}