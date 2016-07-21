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
package com.roncoo.pay.app.settlement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.roncoo.pay.app.settlement.scheduled.SettScheduled;

/**
 * 结算定时任务.(分商户统计账户历史进行汇总)
 * 龙果学院：www.roncoo.com
 * @author zenghao
 */
public class SettTask {
	
	private static final Log LOG = LogFactory.getLog(SettTask.class);
	
	private static final long MILLIS = 1000L;
	
	private SettTask(){
		
	}
	

	public static void main(String[] args) {

		try {

			@SuppressWarnings("resource")
			final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring-context.xml" });
			context.start();
			LOG.debug("结算定时任务开始执行");
			SettScheduled settScheduled = (SettScheduled) context.getBean("settScheduled");

			LOG.debug("执行(每日待结算数据汇总)任务开始");
			settScheduled.launchDailySettCollect();
			LOG.debug("执行(每日待结算数据汇总)任务结束");
			
			Thread.sleep(MILLIS);

			LOG.debug("执行(定期自动结算)任务开始");
			settScheduled.launchAutoSett();
			LOG.debug("执行(定期自动结算)任务结束");

			context.stop();
		} catch (Exception e) {
			LOG.error("SettTask execute error:", e);
		} finally {
			System.exit(0);
			LOG.debug("SettTask Complete");
		}
	}

}
