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
package com.roncoo.pay.common.core.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * api请求正常返回结果,该实体作为API请求时,按照规范返回的实体. code 为返回码 msg 为返回描述 data 为返回的具体结果 Created
 * 龙果学院：www.roncoo.com
 * @author zenghao
 */
public class ApiCommonResultVo {

	public ApiCommonResultVo(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		if (data != null) {
			this.data = data;
		}
	}

	public ApiCommonResultVo(Object data) {
		this.code = 0;
		this.msg = "";
		if (data != null) {
			this.data = data;
		}
	}

	/**
	 * 返回码
	 */
	private int code;

	/**
	 * 返回描述
	 */
	private String msg = "";

	/**
	 * 返回数据
	 */
	private Object data = new Object();

	public void setCode(int code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public Object getData() {
		return data;
	}

	public static void main(String[] args) {
		System.out.println(JSONObject.toJSONString(new ApiCommonResultVo(-1, "", null)));
	}
}
