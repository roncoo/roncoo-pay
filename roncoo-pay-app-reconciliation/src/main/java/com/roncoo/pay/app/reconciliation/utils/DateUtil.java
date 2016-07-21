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
package com.roncoo.pay.app.reconciliation.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类 .
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class DateUtil {

	private DateUtil() {
		super();
	}

	/***
	 * 查询当前小时
	 * 
	 * @return
	 */
	public static int getCurrentHour() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.HOUR_OF_DAY); // 获取当前小时
	}

	/***
	 * 查询当前分钟
	 * 
	 * @return
	 */
	public static int getCurrentMinute() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.MINUTE); // 获取当前分钟
	}

	/***
	 * 获取几天前的日期格式
	 * 
	 * @param dayNum
	 * @return
	 */
	public static String getDateByDayNum(int dayNum) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -dayNum);
		String result = sdf.format(cal.getTime());
		return result;
	}

	/**
	 * 计算 day 天后的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

}
