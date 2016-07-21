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
package com.roncoo.pay.trade.utils.httpclient;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpException;

import java.io.IOException;
import java.util.Map;

/**
 * <b>功能说明:封装了HttpClient 往服务器发送请求的接口<br/>
 * 		让用户 不适用任何HttpClient的api
 * 		直接调用该接口就可以实现相应的操作
 * </b>
 * @author  Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public interface HttpClientWrapper {
	/**
	 * 设置协议头的信息 用户可以根据自己的需求而设定，否则使用默认的设置
	 * @param headers
	 */
	
	public void addHttpHeader(Map<String, String> headers);
		
	
	/**
	 * 清除cookie信息
	 */
	public void clearCookie();

	/**
	 * 把一组cookies加到 httpclient 中 
	 * @param cookies
	 */
    
	public void addCookies(Cookie[] cookies);
   /**
    *  增加单个的cookie
    * @param cookie
    */
	public void addCookie(Cookie cookie);

	/**
	 * 
	 * @param method
	 * @param url
	 * @param params
	 * @param charset
	 * @return 返回带编码集的字符串
	 * @throws HttpException
	 * @throws IOException
	 */
	public String doRequest(MethodType method, String url,
							Map<String, String> params, String charset) throws HttpException,
			IOException;
 /**
  * 
  * @param method
  * @param url
  * @param charset
  * @return 返回带编码集的结果
  * @throws HttpException
  * @throws IOException
  */
	public String doRequest(MethodType method, String url, String charset)
			throws HttpException, IOException;

	/**
	 * 无返回值 实现HttpResponseCallBack接口 对流进行处理封转返回值 外部可以利用到流来得到结果 主要考虑的是多线程下载的情况
	 * 
	 * @param method
	 * @param url
	 * @param params
	 * @param charset
	 * @throws HttpException
	 * @throws IOException
	 */
	
	// HttpResponseCallBack 是设置的一个回调类 ，主要是考虑由于httpClient 返回的流 当连接关闭时流也关闭了
	// 所以利用回调的方式在流关闭之间嵌入用户代码，来操做流
	public void doRequest(HttpResponseCallBack callback, MethodType method,
						  String url, Map<String, String> params, String charset)
			throws HttpException, IOException;

	/**
	 * 无返回值 外部可以利用到流来得到结果 主要考虑的是多线程下载的情况
	 * 
	 * @param method
	 * @param url
	 * @param charset
	 * @param 　callback
	 * @throws HttpException
	 * @throws IOException
	 */

	// HttpResponseCallBack 是设置的一个回调类 ，主要是考虑由于httpClient 返回的流 当连接关闭时流也关闭了
	// 所以利用回调的方式在流关闭之间嵌入用户代码，来操做流
	public void doRequest(HttpResponseCallBack callback, MethodType method,
						  String url, String charset) throws HttpException, IOException;

}
