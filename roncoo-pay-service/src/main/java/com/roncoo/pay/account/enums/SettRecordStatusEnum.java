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
package com.roncoo.pay.account.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结算记录，状态
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public enum SettRecordStatusEnum {

	/**
	 * 等待确认
	 */
	WAIT_CONFIRM("等待审核"),

	/**
	 * 已审核
	 */
	CONFIRMED("已审核"),

	/**
	 * 审核不通过
	 */
	CANCEL("审核不通过"),

	/**
	 * 打款中
	 */
	REMITTING("打款中"),

	/**
	 * 打款成功
	 */
	REMIT_SUCCESS("打款成功"),

	/**
	 * 打款失败
	 */
	REMIT_FAIL("打款失败");

	/** 描述 */
	private String desc;

	private SettRecordStatusEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static SettRecordStatusEnum getEnum(String enumName) {
		SettRecordStatusEnum resultEnum = null;
		SettRecordStatusEnum[] enumAry = SettRecordStatusEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].name().equals(enumName)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		SettRecordStatusEnum[] ary = SettRecordStatusEnum.values();
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
		SettRecordStatusEnum[] ary = SettRecordStatusEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}


	/**
	 * 判断填入审核状态
	 * 
	 * @param enumName
	 * @return
	 */
	public static boolean checkConfirm(String enumName) {
		SettRecordStatusEnum[] enumAry = { SettRecordStatusEnum.CANCEL, SettRecordStatusEnum.CONFIRMED };
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].name().equals(enumName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断填入打款状态
	 * 
	 * @param enumName
	 * @return
	 */
	public static boolean checkRemit(String enumName) {
		SettRecordStatusEnum[] enumAry = { SettRecordStatusEnum.REMIT_FAIL, SettRecordStatusEnum.REMIT_SUCCESS };
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].name().equals(enumName)) {
				return true;
			}
		}
		return false;
	}
}
