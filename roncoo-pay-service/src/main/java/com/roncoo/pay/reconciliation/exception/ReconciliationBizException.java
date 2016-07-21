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
package com.roncoo.pay.reconciliation.exception;

import com.roncoo.pay.common.core.exception.BizException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 对账业务 .
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class ReconciliationBizException extends BizException {

	private static final long serialVersionUID = 1L;

	/** 根据银行订单号查询的支付记录不存在 **/
	public static final int TRADE_ORDER_NO_EXCIT = 20020001;

	private static final Log LOG = LogFactory.getLog(ReconciliationBizException.class);

	public ReconciliationBizException() {
	}

	public ReconciliationBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public ReconciliationBizException(int code, String msg) {
		super(code, msg);
	}

	public ReconciliationBizException print() {
		LOG.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
		return this;
	}
}
