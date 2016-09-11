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
package com.roncoo.pay.common.core.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加密工具类
 * @company：广州领课网络科技有限公司（龙果学院 www.roncoo.com）.
 * @author zenghao
 */
public class EncryptUtil {

	private static final Log LOG = LogFactory.getLog(EncryptUtil.class);

	// 密码盐
	public static final String PWDSALT = "PAY";

	/**
	 * 私有构造方法,将该工具类设为单例模式.
	 */
	private EncryptUtil() {
	}

	/**
	 * 用MD5算法进行加密
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return MD5加密后的结果
	 */
	public static String encodeMD5String(String str) {
		return encode(str, "MD5");
	}

	/**
	 * 用SHA算法进行加密
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return SHA加密后的结果
	 */
	public static String encodeSHAString(String str) {
		return encode(str, "SHA");
	}

	/**
	 * 用base64算法进行加密
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return base64加密后的结果
	 */
	public static String encodeBase64String(String str) {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(str.getBytes());
	}

	/**
	 * 用base64算法进行解密
	 * 
	 * @param str
	 *            需要解密的字符串
	 * @return base64解密后的结果
	 * @throws IOException
	 */
	public static String decodeBase64String(String str) throws IOException {
		BASE64Decoder encoder = new BASE64Decoder();
		return new String(encoder.decodeBuffer(str));
	}

	private static String encode(String str, String method) {
		MessageDigest mdInst = null;
		// 把密文转换成十六进制的字符串形式
		// 单线程用StringBuilder，速度快 多线程用stringbuffer，安全
		StringBuilder dstr = new StringBuilder();
		try {
			// 获得MD5摘要算法的 MessageDigest对象
			mdInst = MessageDigest.getInstance(method);
			// 使用指定的字节更新摘要
			mdInst.update(str.getBytes());
			// 获得密文
			byte[] md = mdInst.digest();
			for (int i = 0; i < md.length; i++) {
				int tmp = md[i];
				if (tmp < 0) {
					tmp += 256;
				}
				if (tmp < 16) {
					dstr.append("0");
				}
				dstr.append(Integer.toHexString(tmp));
			}
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e);
		}
		return dstr.toString();
	}

}
