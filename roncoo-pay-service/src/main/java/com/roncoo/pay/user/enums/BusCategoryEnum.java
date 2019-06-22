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
 * 经营类目枚举
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public enum BusCategoryEnum {

	XXLS("1001","线下零售","0","5000","0","24"),//线下零售

	CYSP("1002","餐饮/食品","0","2000","7","24"),//餐饮、食品

	PWLY("1003","票务/旅游","0","999","0","24"),//票务、旅游

	JYPX("1004","教育/培训","1000","5000","0","24"),//教育、培训

	YLJSFW("1006","娱乐/健身服务","0","1000","0","24"),//生活、健身服务
	
	YL("1007","医疗","0","1500","0","24"),//医疗

	SCPM("1008","收藏/拍卖","1000","5000","9","21"),

	WLKD("1009","物流/快递","0","300","9","20"),

	GY("1010","公益","0","500","9","20"),
	
	TX("1011","通讯","0","500","0","24"),
	
	JRBX("1012","金融/保险","500","5000","0","24"),
	
	WLXNFW("1013","网络虚拟服务","0","5000","0","24"),
	
	SHJF("1014","生活缴费","0","1000","0","24"),
	
	JD("1015","酒店","200","5000","0","24"),
	
	JJ("1016","家居","200","5000","0","24"),
	
	DSTG("1017","电商团购","0","5000","0","24"),
	QT("1018","其他","0","5000","0","24");
	
	/** 编码*/
	private String code;
	/** 描述 */
	private String desc;
	/** 最低金额*/
	private String minAmount;
	/** 最大金额*/
	private String maxAmount;
	/** 开始时间*/
	private String beginTime;
	/** 结束时间*/
	private String endTime;

	public String getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	private BusCategoryEnum(String code,String desc,String minAmount,String maxAmount,String beginTime,String endTime) {
		this.code = code;
		this.desc = desc;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static BusCategoryEnum getEnum(String enumName) {
		BusCategoryEnum resultEnum = null;
		BusCategoryEnum[] enumAry = BusCategoryEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].name().equals(enumName)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		BusCategoryEnum[] ary = BusCategoryEnum.values();
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
		BusCategoryEnum[] ary = BusCategoryEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", ary[i].name());
			map.put("desc", ary[i].getDesc());
			map.put("minAmount", ary[i].getMinAmount());
			map.put("maxAmount", ary[i].getMaxAmount());
			map.put("beginTime", ary[i].getBeginTime());
			map.put("endTime", ary[i].getEndTime());
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
		BusCategoryEnum[] enums = BusCategoryEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (BusCategoryEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
}
