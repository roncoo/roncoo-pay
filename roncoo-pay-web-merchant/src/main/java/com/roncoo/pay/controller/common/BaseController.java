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
package com.roncoo.pay.controller.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * controller基类
 * 
 * @author zh
 * @version 2014-6-18
 */
public abstract class BaseController {

	private static final String UTF_8 = "utf-8";

	private static final String GBK = "GBK";

	private static final Log logger = LogFactory.getLog(BaseController.class);

	/**
	 * 获取request
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获取session
	 * 
	 * @return
	 */
	protected HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}

	/**
	 * 获取application
	 * 
	 * @return
	 */
	protected ServletContext getApplication() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getServletContext();
	}

	protected ServletContext getServletContext() {
		return ContextLoader.getCurrentWebApplicationContext().getServletContext();
	}

	public String getString(String name) {
		return getString(name, null);
	}

	public String getString(String name, String defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr == null || "".equals(resultStr) || "null".equals(resultStr) || "undefined".equals(resultStr)) {
			return defaultValue;
		} else {
			return resultStr;
		}
	}

	/**
	 * add by Along
	 * 
	 * @param name
	 * @param request
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public String getStringByRequest(String name, HttpServletRequest request, String defaultValue) {
		String resultStr = request.getParameter(name);
		if (resultStr == null || "".equals(resultStr) || "null".equals(resultStr) || "undefined".equals(resultStr)) {
			return defaultValue;
		} else {
			try {
				String decode = URLDecoder.decode(resultStr, UTF_8);
				return decode;
			} catch (UnsupportedEncodingException e) {
				logger.info(e);
				return defaultValue;
			}
		}
	}

	/**
	 * 获取请求中的参数值，如果参数值为null刚转为空字符串""
	 * 
	 * @return
	 */
	public Map<String, Object> getParamMap_NullStr(Map map) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		Set keys = map.keySet();
		for (Object key : keys) {
			String value = this.getString(key.toString());
			if (value == null) {
				value = "";
			}
			parameters.put(key.toString(), value);
		}
		return parameters;
	}

	public int getInt(String name) {
		return getInt(name, 0);
	}

	public int getInt(String name, int defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return Integer.parseInt(resultStr);
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public BigDecimal getBigDecimal(String name) {
		return getBigDecimal(name, null);
	}

	public BigDecimal getBigDecimal(String name, BigDecimal defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return BigDecimal.valueOf(Double.parseDouble(resultStr));
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * 根据参数名从HttpRequest中获取String类型的参数值，无值则返回"" .
	 * 
	 * @param key
	 *            .
	 * @return String .
	 */
	public String getString_UrlDecode_UTF8(String key) {
		try {
			return URLDecoder.decode(this.getString(key), UTF_8);
		} catch (Exception e) {
			return "";
		}

	}

	public String getString_UrlDecode_GBK(String key) {
		try {
			return new String(getString(key.toString()).getBytes("GBK"), "UTF-8");
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * 获取客户端的IP地址
	 * 
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) {
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * 获取refererUrl
	 */
	public String getRefererUrl(HttpServletRequest request) {
		return request.getHeader("referer");
	}

	/**
	 * 
	 * @param request
	 *            请求
	 * @return 返回请求的数据流
	 * @throws IOException
	 */
	public String parseRequestString(HttpServletRequest request) throws IOException {
		String inputLine;
		String notityXml = "";
		while ((inputLine = request.getReader().readLine()) != null) {
			notityXml += inputLine;
		}
		request.getReader().close();
		return notityXml;
	}
	
    /**  
    * 把json对象串转换成map对象  
    * @param jsonObjStr e.g. {'name':'get','int':1,'double',1.1,'null':null}  
    * @return Map  
    */  
    public HashMap<String, String> convertToMap(JSONParam[] params) {   
    	HashMap<String, String> map = new HashMap<String, String>();
    	for(JSONParam param:params){
    		map.put(param.getName(), param.getValue());
    	}
       return map;   
    }   
}
