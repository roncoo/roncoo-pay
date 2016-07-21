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
package com.roncoo.pay.app.settlement.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 线程池工具类
 * 龙果学院：www.roncoo.com
 * @author zenghao
 */
public class SettThreadPoolExecutor {
	private static final Log LOG = LogFactory.getLog(SettThreadPoolExecutor.class);
	/**
	 * 针对核心Thread的Max比率 ，以10为基数，8表示0.8
	 */
	private int notifyRadio = 4;
	
	/**
	 * 最少线程数.<br/>
	 * 当池子大小小于corePoolSize就新建线程，并处理请求.
	 */
	private int corePoolSize;
	
	/**
	 * 线程池缓冲队列大小.<br/>
	 * 当池子大小等于corePoolSize，把请求放入workQueue中，池子里的空闲线程就去从workQueue中取任务并处理.
	 */
	private int workQueueSize;

	/**
	 * 最大线程数.<br/>
	 * 当workQueue放不下新入的任务时，新建线程入池，并处理请求，<br/>
	 * 如果池子大小撑到了maximumPoolSize就用RejectedExecutionHandler来做拒绝处理.
	 */
	private int maxPoolSize;

	/**
	 * 允许线程闲置时间,单位：秒.<br/>
	 * 当池子的线程数大于corePoolSize的时候，多余的线程会等待keepAliveTime长的时间，如果无请求可处理就自行销毁.
	 */
	private long keepAliveTime;

	private ThreadPoolExecutor executor = null;

	public void init() {
		if (workQueueSize < 1) {
			workQueueSize = 1000;
		}
		if (this.keepAliveTime < 1) {
			this.keepAliveTime = 1000;
		}
		int coreSize = 0;
		if (this.corePoolSize < 1) {
			coreSize = Runtime.getRuntime().availableProcessors();
			maxPoolSize = Math.round(((float) (coreSize * notifyRadio)) / 10);
			corePoolSize = coreSize / 4;
			if (corePoolSize < 1) {
				corePoolSize = 1;
			}
		}

		// NOTICE: corePoolSize不能大于maxPoolSize，否则会出错
		if (maxPoolSize < corePoolSize) {
			maxPoolSize = corePoolSize;
		}

		/**
		 * ThreadPoolExecutor就是依靠BlockingQueue的阻塞机制来维持线程池，当池子里的线程无事可干的时候就通过workQueue.take()阻塞住
		 */
		BlockingQueue<Runnable> notifyWorkQueue = new ArrayBlockingQueue<Runnable>(workQueueSize);

		executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, notifyWorkQueue, new ThreadPoolExecutor.CallerRunsPolicy());

		LOG.info("NotifyExecutor Info : CPU = " + coreSize + 
				" | corePoolSize = " + corePoolSize + " | maxPoolSize = " + 
				maxPoolSize + " | workQueueSize = " + workQueueSize);
	}

	public void destroy() {
		executor.shutdownNow();
	}

	public void execute(Runnable command) {
		executor.execute(command);
	}

	public void showExecutorInfo() {
		LOG.info("NotifyExecutor Info : corePoolSize = " + corePoolSize + 
				" | maxPoolSize = " + maxPoolSize + " | workQueueSize = " + 
				workQueueSize + " | taskCount = " + executor.getTaskCount() + 
				" | activeCount = " + executor.getActiveCount() + 
				" | completedTaskCount = " + executor.getCompletedTaskCount());
	}
	
	
	public void setNotifyRadio(int notifyRadio) {
		this.notifyRadio = notifyRadio;
	}

	public void setWorkQueueSize(int workQueueSize) {
		this.workQueueSize = workQueueSize;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
}
