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
package com.roncoo.pay.user.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银行枚举
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public enum BankCodeEnum {

	ICBC("工商银行"),

	CMBCHINA("招商银行"),

	ABC("中国农业银行"),

	CCB("建设银行"),

	BCCB("北京银行"),

	BOCO("交通银行"),

	CMBC("中国民生银行"),

	PINGANBANK("平安银行"),

	CIB("兴业银行"),

	NJCB("南京银行"),

	CEB("光大银行"),

	BOC("中国银行"),

	CGB("广发银行"),

	SHB("上海银行"),

	SPDB("上海浦东发展银行"),

	POST("中国邮政"),

	CBHB("渤海银行"),

	HKBEA("东亚银行"),

	NBCB("宁波银行"),

	ECITIC("中信银行"),

	BJRCB("北京农村商业银行"),

	HXB("华夏银行"),

	CZ("浙商银行"),

	HZBANK("杭州银行"),

	SRCB("上海农村商业银行"),

	NCBBANK("南洋商业银行"),

	SCCB("河北银行"),

	ZJTLCB("泰隆银行"),

	HKB("汉口银行"),
	
	OTHER("其他");

	/** 描述 */
	private String desc;

	private BankCodeEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static BankCodeEnum getEnum(String enumName) {
		BankCodeEnum resultEnum = null;
		BankCodeEnum[] enumAry = BankCodeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].name().equals(enumName)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		BankCodeEnum[] ary = BankCodeEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = ary[num].name();
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		BankCodeEnum[] ary = BankCodeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", ary[i].name());
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

	/**
	 * 取枚举的json字符串
	 * 
	 * @return
	 */
	public static String getJsonStr() {
		BankCodeEnum[] enums = BankCodeEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (BankCodeEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
}
