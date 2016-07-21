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
package com.roncoo.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * <b>功能说明:模拟商户商城控制层基础类
 * </b>
 * @author  Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public abstract class BaseController {

    private static final Log log = LogFactory.getLog(BaseController.class);
    
	private static final String UTF_8 = "utf-8";

	private static final String GBK = "GBK";

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
	 * 获取请求中的参数值，如果参数值为null刚转为空字符串""
	 * 
	 * @return
	 */
	public Map<String, Object> getParamMap_NullStr(Map map) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		Set keys = map.keySet();
		for (Object key : keys) {
			String value = this.getString(key.toString());
			if (value == null){
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
	
    public String readRequest(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
        } finally {
            request.getReader().close();
        }
        return sb.toString();
    }
    
    public void write(HttpServletResponse response, String s) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(s);
        } catch (IOException e) {
            log.error("返回支付结果接收状态到微信支付错误", e);
        } finally {
            out.close();
        }
    }
}
