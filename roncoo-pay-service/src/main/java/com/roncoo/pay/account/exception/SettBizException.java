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
package com.roncoo.pay.account.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.roncoo.pay.common.core.exception.BizException;

/**
 * 结算服务业务异常类,异常代码8位数字组成,前4位固定1001打头,后4位自定义
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public class SettBizException extends BizException {

	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(SettBizException.class);

	public static final SettBizException SETT_STATUS_ERROR = new SettBizException(10010001, "结算状态错误");

	public SettBizException() {
	}

	public SettBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public SettBizException(int code, String msg) {
		super(code, msg);
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public SettBizException newInstance(String msgFormat, Object... args) {
		return new SettBizException(this.code, msgFormat, args);
	}

	public SettBizException print() {
		LOG.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
		return this;
	}
}
