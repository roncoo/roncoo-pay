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
package com.roncoo.pay.app.notify;

import com.roncoo.pay.app.notify.core.NotifyPersist;
import com.roncoo.pay.app.notify.core.NotifyQueue;
import com.roncoo.pay.app.notify.core.NotifyTask;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.notify.entity.RpNotifyRecord;
import com.roncoo.pay.notify.service.RpNotifyService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.DelayQueue;

/**
 * <b>功能说明:消息APP启动类
 * </b>
 * @author  Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public class App 
{
    private static final Log LOG = LogFactory.getLog(App.class);

    public static DelayQueue<NotifyTask> tasks = new DelayQueue<NotifyTask>();

    private static ClassPathXmlApplicationContext context;

    private static ThreadPoolTaskExecutor threadPool;

    public static RpNotifyService rpNotifyService;

    private static NotifyQueue notifyQueue;

    public static NotifyPersist notifyPersist;

    public static void main(String[] args) {
        try {
            context = new ClassPathXmlApplicationContext(new String[] { "spring/spring-context.xml" });
            context.start();
            threadPool = (ThreadPoolTaskExecutor) context.getBean("threadPool");
            rpNotifyService = (RpNotifyService) context.getBean("rpNotifyService");
            notifyQueue = (NotifyQueue) context.getBean("notifyQueue");
            notifyPersist = (NotifyPersist) context.getBean("notifyPersist");
            startInitFromDB();
            startThread();
            LOG.info("== context start");
        } catch (Exception e) {
            LOG.error("== application start error:", e);
            return;
        }
        synchronized (App.class) {
            while (true) {
                try {
                    App.class.wait();
                } catch (InterruptedException e) {
                    LOG.error("== synchronized error:", e);
                }
            }
        }
    }

    private static void startThread() {
        LOG.info("startThread");

        threadPool.execute(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(50);//50毫秒执行一次
                        // 如果当前活动线程等于最大线程，那么不执行
                        if (threadPool.getActiveCount() < threadPool.getMaxPoolSize()) {
                            final NotifyTask task = tasks.poll();
                            if (task != null) {
                                threadPool.execute(new Runnable() {
                                    public void run() {
                                        LOG.info(threadPool.getActiveCount() + "---------");
                                        tasks.remove(task);
                                        task.run();
                                    }
                                });
                            }
                        }
                    }
                } catch (Exception e) {
                    LOG.error("系统异常",e);
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 从数据库中取一次数据用来当系统启动时初始化
     */
    @SuppressWarnings("unchecked")
    private static void startInitFromDB() {
        LOG.info("get data from database");

        int pageNum = 1;
        int numPerPage = 500;
        PageParam pageParam = new PageParam(pageNum, numPerPage);

        // 查询状态和通知次数符合以下条件的数据进行通知
        String[] status = new String[] { "101", "102", "200", "201" };
        Integer[] notifyTime = new Integer[] { 0, 1, 2, 3, 4 };
        // 组装查询条件
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("statusList", status);
        paramMap.put("notifyTimeList", notifyTime);

        PageBean<RpNotifyRecord> pager = rpNotifyService.queryNotifyRecordListPage(pageParam, paramMap);
        int totalSize = (pager.getNumPerPage()-1)/numPerPage+1;//总页数
        while (pageNum <= totalSize) {
            List<RpNotifyRecord> list = pager.getRecordList();
            for (int i = 0; i < list.size(); i++) {
                RpNotifyRecord notifyRecord = list.get(i);
                notifyRecord.setLastNotifyTime(new Date());
                notifyQueue.addElementToList(notifyRecord);
            }
            pageNum++;
            LOG.info(String.format("调用通知服务.rpNotifyService.queryNotifyRecordListPage(%s, %s, %s)", pageNum, numPerPage, paramMap));
            pageParam = new PageParam(pageNum, numPerPage);
            pager = rpNotifyService.queryNotifyRecordListPage(pageParam, paramMap);
        }
    }

}
