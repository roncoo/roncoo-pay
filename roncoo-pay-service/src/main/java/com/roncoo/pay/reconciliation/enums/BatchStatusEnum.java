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
package com.roncoo.pay.reconciliation.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对账状态枚举
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public enum BatchStatusEnum {

	SUCCESS("对账操作成功"),

	FAIL("对账操作失败"),

	ERROR("银行返回错误信息"),

	NOBILL("银行没有订单信息");

	private String desc;

	private BatchStatusEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public static BatchStatusEnum getEnum(String name) {
		BatchStatusEnum[] arry = BatchStatusEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equals(name)) {
				return arry[i];
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		MistakeHandleStatusEnum[] ary = MistakeHandleStatusEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("desc", ary[i].getDesc());
			map.put("name", ary[i].name());
			list.add(map);
		}
		return list;
	}
}
