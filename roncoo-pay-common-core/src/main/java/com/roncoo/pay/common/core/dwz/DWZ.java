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
package com.roncoo.pay.common.core.dwz;

/**
 * 封装DWZ框架ajax请求及响应的参数. 
 * @company：广州领课网络科技有限公司（龙果学院 www.roncoo.com）.
 */
public final class DWZ {

	/**
	 * Ajax请求成功,statusCode 为200.
	 */
	public static final String SUCCESS = "200";
	/**
	 * Ajax请求失败,statusCode 为300.
	 */
	public static final String ERROR = "300";
	/**
	 * Ajax请求超时,statusCode 为301.
	 */
	public static final String TIMEOUT = "301";

	// //////////////////////////////////////////

	/**
	 * callbackType ajax请求回调类型. <br/>
	 * callbackType如果是closeCurrent就会关闭当前tab选项,
	 */
	public static final String CLOSE = "closeCurrent";

	/**
	 * 只有callbackType="forward"时需要forwardUrl值,以重定向到另一个URL.
	 */
	public static final String FORWARD = "forward";
	
	public static final String AJAX_DONE = "common/ajaxDone";
	
	public static final String SUCCESS_MSG = "操作成功";

	/**
	 * 私有构造方法,单例模式.
	 */
	private DWZ() {

	}
}
