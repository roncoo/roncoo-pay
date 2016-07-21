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
package com.roncoo.pay.permission.utils;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 验证工具类
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class ValidateUtils {

	/**
	 * 判断传入参数是否为字母与数字的组合字符串,而不是单一的字母或者数字
	 * 
	 * @param psw
	 * @return
	 */
	public static boolean isPassword(String pwd) {
		if (ValidateUtils.isInteger(pwd)) {// 是否全部为数字
			return false;// 全部为数字
		} else {
			if (ValidateUtils.isLettersOnly(pwd)) {
				return false;// 全部为字母
			} else {
				if (ValidateUtils.isAlphanumeric(pwd)) {
					return true;// 正常数据
				} else {
					return false;// 包含了非法数据
				}
			}
		}
	}

	/**
	 * 判断字母、数字（适用于密码校验）.
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是整数返回true,否则返回false capital
	 */
	public static boolean isAlphanumeric(String str) {
		Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 必须为字母.
	 * 
	 * @param str
	 *            传入的字符串
	 * @return true or false .
	 */
	public static boolean isLettersOnly(String str) {
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否为整数
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否为浮点数，包括double和float
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是浮点数返回true,否则返回false
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否为有且只有小数点后面包含两位的数
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是浮点数返回true,否则返回false
	 */
	public static boolean isDoubleAnd2decimals(String str) {
		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))([.](\\d){1,2})?$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 验证一个字符串是否完全由纯数字组成的字符串，当字符串为空时也返回false.
	 * 
	 * @author WuShuicheng .
	 * @param str
	 *            要判断的字符串 .
	 * @return true or false .
	 */
	public static boolean isNumeric(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		} else {
			return str.matches("\\d*");
		}
	}

	/**
	 * 判断输入的字符串是否符合Email样式.
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是Email样式返回true,否则返回false
	 */
	public static boolean isEmail(String str) {
		Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断输入的字符串是否为纯汉字
	 * 
	 * @param str
	 *            传入的字符窜
	 * @return 如果是纯汉字返回true,否则返回false
	 */
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否为质数
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isPrime(int x) {
		if (x <= 7) {
			if (x == 2 || x == 3 || x == 5 || x == 7)
				return true;
		}
		int c = 7;
		if (x % 2 == 0)
			return false;
		if (x % 3 == 0)
			return false;
		if (x % 5 == 0)
			return false;
		int end = (int) Math.sqrt(x);
		while (c <= end) {
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 6;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 6;
		}
		return true;
	}

	/**
	 * 判断是不是合法手机号码
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		Pattern pattern = Pattern.compile("^1[3|4|5|7|8][0-9]{9}$");
		return pattern.matcher(mobile).matches();

	}

	/**
	 * 是否为座机 (010-66571346)
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		Pattern pattern = Pattern.compile("^0[0-9]{2,3}[-|－][0-9]{7,8}([-|－][0-9]{1,4})?$");
		return pattern.matcher(phone).matches();
	}

	/**
	 * 是否为邮编
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPostCode(String post) {
		Pattern pattern = Pattern.compile("^[0-9]{6}$");
		return pattern.matcher(post).matches();
	}

	/**
	 * 是否为日期格式：yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isDate(String dateStr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			return false;
		}
		return date != null;
	}

	/**
	 * 是否为时间格式：hh:mm:ss
	 * 
	 * @param timeStr
	 * @return
	 */
	public static boolean isTime(String timeStr) {
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
		Date date = null;
		try {
			date = df.parse(timeStr);
		} catch (ParseException e) {
			return false;
		}
		return date != null;
	}

	/**
	 * 是否为日期时间格式：yyyy-MM-dd hh:mm:ss or yyyy-MM-dd hh:mm
	 * 
	 * @param dateTime
	 * @return
	 */
	public static boolean isDateTime(String dateTime) {
		int first = dateTime.indexOf(":");
		int last = dateTime.lastIndexOf(":");
		if (first == -1) {
			return false;
		}
		SimpleDateFormat df = null;
		if (first == last) {
			df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		} else {
			df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		}
		Date date = null;
		try {
			date = df.parse(dateTime);
		} catch (ParseException e) {
			return false;
		}
		return date == null;
	}

	public static void main(String[] args) {
		String url = "http://192.168.88.106:8082/roncoo-web-gateway/bankPaySuccess_paySuccess.action";
		// String url =
		// "http://192.168.88.247:8088/roncoo-web-shop/Xotify_url.jsp";
		Pattern pattern = Pattern
		// .compile("");
				.compile("^(https?|ftp):\\/\\/(((([a-z]|[A-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:)*@)?(((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]))|((([a-z]|[A-Z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[A-Z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|[A-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[A-Z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[A-Z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[A-Z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|[A-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[A-Z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?)(:\\d*)?)(\\/((([a-z]|[A-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)+(\\/(([a-z]|[A-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)*)*)?)?(\\?((([a-z]|[A-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)|[\\uE000-\\uF8FF]|\\/|\\?)*)?(\\#((([a-z]|[A-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)|\\/|\\?)*)?$");
		Boolean bool = pattern.matcher(url).matches();
		System.out.println(bool);
	}

	/**
	 * 是否为url
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isURL(String url) {
		Pattern pattern = Pattern
				.compile("^(https?|ftp):\\/\\/(((([a-z]|[A-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:)*@)?(((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]))|((([a-z]|[A-Z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[A-Z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|[A-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[A-Z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[A-Z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[A-Z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|[A-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[A-Z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?)(:\\d*)?)(\\/((([a-z]|[A-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)+(\\/(([a-z]|[A-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)*)*)?)?(\\?((([a-z]|[A-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)|[\\uE000-\\uF8FF]|\\/|\\?)*)?(\\#((([a-z]|[A-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)|\\/|\\?)*)?$");
		return pattern.matcher(url).matches();
	}

	/**
	 * 例如：ABC-B2C$ABC-B2C
	 * 
	 * @param frpCode
	 * @return
	 */
	public static boolean isCapitalBar(String frpCode) {
		Pattern pattern = Pattern.compile("^[A-Z]+[-－][A-Z[22]]+(\\$[A-Z]+[-－][A-Z[22]]+)*");
		return pattern.matcher(frpCode).matches();
	}

	/**
	 * 是否为合法IP地址
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean isIP(String ip) {
		Pattern pattern = Pattern.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");
		return pattern.matcher(ip).matches();
	}

	/**
	 * 是否为合法MAC地址，验证十六进制格式
	 * 
	 * @param mac
	 * @return
	 */
	public static boolean isMac(String mac) {
		Pattern pattern = Pattern.compile("^([0-9a-fA-F]{2})(([\\s:-][0-9a-fA-F]{2}){5})$");
		return pattern.matcher(mac).matches();
	}

	/**
	 * 是否为合法的QQ号码.<br/>
	 * 1、QQ号的开头不能是0为开头 .<br/>
	 * 2、QQ号必须由数字构成，不包含其他字符.<br/>
	 * 3、QQ号长度为5到15位，多的算违规.
	 * 
	 * @param qq
	 *            .
	 * @return true/false .
	 */
	public static boolean isQQ(String qq) {
		Pattern pattern = Pattern.compile("^[1-9][0-9]{4,14}$");
		return pattern.matcher(qq).matches();
	}

	/**
	 * 是否为合法的银行卡号
	 * 
	 * @param bankCard
	 *            银行卡号
	 * @return
	 */
	public static boolean isBankCard(String bankCard) {
		if (!StringUtils.isBlank(bankCard)) {
			String nonCheckCodeCardId = bankCard.substring(0, bankCard.length() - 1);
			if (nonCheckCodeCardId.matches("\\d+")) {
				char[] chs = nonCheckCodeCardId.toCharArray();
				int luhmSum = 0;
				for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
					int k = chs[i] - '0';
					if (j % 2 == 0) {
						k *= 2;
						k = k / 10 + k % 10;
					}
					luhmSum += k;
				}
				char b = (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
				return bankCard.charAt(bankCard.length() - 1) == b;
			}
		}
		return false;
	}

	private static List<String> generateBankCard(int count) {
		long l = 100000000000000000l;
		List<String> list = new ArrayList<String>();
		for (int a = 1; a <= count; a++) {
			String s = String.valueOf(l + a);
			char[] chs = s.toCharArray();
			int luhmSum = 0;
			for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
				int k = chs[i] - '0';
				if (j % 2 == 0) {
					k *= 2;
					k = k / 10 + k % 10;
				}
				luhmSum += k;
			}
			char b = (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
			String bankcard = s + b;
			if (isBankCard(bankcard)) {
				list.add(s + b);
			}
		}
		return list;
	}

	/**
	 * 检查字符串长度是否小于最小长度
	 * 
	 * @param str
	 *            待检查的字符串
	 * @param minLength
	 *            最小长度
	 * @param message
	 *            异常信息
	 */
	public static void checkStrMinLength(String str, Integer minLength, String message) {
		if (str.trim().length() < minLength) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 检查字符串的字节长度是否小于最小长度
	 * 
	 * @param str
	 *            待检查的字符串
	 * @param minLength
	 *            最小长度
	 */
	public static boolean checkStrMinLengthByBytes(String str, Integer minLength) {
		int length = str.getBytes().length;
		if (length >= minLength) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查字符串的字节长度是否大于最大长度
	 * 
	 * @param str
	 *            待检查的字符串
	 * @param minLength
	 *            最小长度
	 */
	public static boolean checkStrMaxLengthByBytes(String str, Integer maxLength) {
		int length = str.getBytes().length;
		if (length <= maxLength) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查字符串是否大于最大长度
	 * 
	 * @param str
	 *            待检查的字符串
	 * @param maxLength
	 *            最大长度
	 * @param message
	 *            异常信息
	 */
	public static void checkStrMaxLength(String str, Integer maxLength, String message) {
		if (str.trim().length() > maxLength) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 验证对象是否为空
	 * 
	 * @param obj
	 *            被验证的对象
	 * @param message
	 *            异常信息
	 */
	public static void checkNotNull(Object obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 验证对象是否为NULL,空字符串，空数组，空的Collection或Map(只有空格的字符串也认为是空串)
	 * 
	 * @param obj
	 *            被验证的对象
	 * @param message
	 *            异常信息
	 */
	@SuppressWarnings("rawtypes")
	public static void checkNotEmpty(Object obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message);
		}
		if (obj instanceof String && obj.toString().trim().length() == 0) {
			throw new IllegalArgumentException(message);
		}
		if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
			throw new IllegalArgumentException(message);
		}
		if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
			throw new IllegalArgumentException(message);
		}
		if (obj instanceof Map && ((Map) obj).isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 判断参数否非空
	 * 
	 * @param obj
	 * @param message
	 * @return
	 */
	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	/**
	 * 判断参数是否非NULL,空字符串，空数组，空的Collection或Map(只有空格的字符串也认为是空串)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String && obj.toString().trim().length() == 0) {
			return true;
		}
		if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
			return true;
		}
		if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
			return true;
		}
		if (obj instanceof Map && ((Map) obj).isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isIdCard(String idCard) {
		Pattern pattern = Pattern.compile("^(^\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$");
		return pattern.matcher(idCard).matches();
	}

	/**
	 * 组织机构代码
	 * 
	 * @param orgCode
	 * @return
	 */
	public static final boolean isOrgCode(String orgCode) {
		String[] codeNo = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		String[] staVal = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35" };
		Pattern pat = Pattern.compile("^[0-9A-Z]{8}-[0-9X]$");
		Matcher matcher = pat.matcher(orgCode);
		if (!matcher.matches()) {
			return false;
		}
		Map map = new HashMap();
		for (int i = 0; i < codeNo.length; i++) {
			map.put(codeNo[i], staVal[i]);
		}
		final int[] wi = { 3, 7, 9, 10, 5, 8, 4, 2 };
		String[] all = orgCode.split("-");
		final char[] values = all[0].toCharArray();
		int parity = 0;
		for (int i = 0; i < values.length; i++) {
			final String val = Character.toString(values[i]);
			parity += wi[i] * Integer.parseInt(map.get(val).toString());
		}
		String cheak = (11 - parity % 11) == 10 ? "X" : Integer.toString((11 - parity % 11));

		return cheak.equals(all[1]);
	}

	/**
	 * 参数长度校验
	 * 
	 * @param propertyName
	 * @param property
	 * @param isRequire
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static String lengthValidate(String propertyName, String property, boolean isRequire, int minLength, int maxLength) {
		int propertyLenght = property.length();
		if (isRequire && propertyLenght == 0) {
			return propertyName + "不能为空，"; // 校验不能为空
		} else if (isRequire && minLength != 0 && propertyLenght < minLength) {
			return propertyName + "不能少于" + minLength + "个字符，"; // 必填情况下校验最少长度
		} else if (maxLength != 0 && propertyLenght > maxLength) {
			return propertyName + "不能多于" + maxLength + "个字符，"; // 校验最大长度
		} else {
			return ""; // 校验通过则返回空字符串 .
		}
	}

}
