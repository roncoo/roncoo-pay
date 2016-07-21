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

import java.util.List;
import java.util.Map;

/**
 * <b>功能说明:
 * </b>
 * @author  Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public class SimpleHttpResult {
	
	
	public SimpleHttpResult(int code){
		this.statusCode = code;
	}
	
	public SimpleHttpResult(int code, String _content){
		this.statusCode = code;
		this.content = _content;
	}
	
	public SimpleHttpResult(Exception e){
		if(e==null){
			throw new IllegalArgumentException("exception must be specified");
		}
		this.statusCode = -1;
		this.exception = e;
		this.exceptionMsg = e.getMessage();
	}
	/**
	 * HTTP状态码
	 */
	private int statusCode;
	
	/**
	 * HTTP结果
	 */
	private String content;
	
	private String exceptionMsg;
	
	private Exception exception;
	
	private Map<String,List<String>> headers;
	
	private String contentType;
	
	
	public String getHeaderField(String key){
		if(headers==null){
			return null;
		}
		List<String> headerValues = headers.get(key);
		if(headerValues==null || headerValues.isEmpty()){
			return null;
		}
		return headerValues.get(headerValues.size()-1);
	}
	
	public String getContentType(){
		return contentType;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}


	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}
	
	public Exception getException() {
		return exception;
	}

	public boolean isSuccess(){
		return statusCode==200;
	}
	
	public boolean isError(){
		return exception!=null;
	}
	

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
