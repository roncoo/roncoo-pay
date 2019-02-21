package com.roncoo.pay;

import com.roncoo.pay.app.polling.core.PollingPersist;
import com.roncoo.pay.app.polling.core.PollingTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.util.concurrent.DelayQueue;

@SpringBootApplication
public class AppOrderPollingApplication {

    private static final Log LOG = LogFactory.getLog(AppOrderPollingApplication.class);

    public static DelayQueue<PollingTask> tasks = new DelayQueue<PollingTask>();

    @Autowired
    private ThreadPoolTaskExecutor threadPool;
    @Autowired
    public PollingPersist pollingPersist;

    private static ThreadPoolTaskExecutor cacheThreadPool;

    public static PollingPersist cachePollingPersist;

    public static void main(String[] args) {
        SpringApplication.run(AppOrderPollingApplication.class, args);
    }

    @PostConstruct
    public void init() {
        cacheThreadPool = threadPool;
        cachePollingPersist = pollingPersist;

        startThread();

    }

    private void startThread() {
        LOG.info("==>startThread");

        cacheThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(100);
                        LOG.info("==>threadPool.getActiveCount():" + cacheThreadPool.getActiveCount());
                        LOG.info("==>threadPool.getMaxPoolSize():" + cacheThreadPool.getMaxPoolSize());
                        // 如果当前活动线程等于最大线程，那么不执行
                        if (cacheThreadPool.getActiveCount() < cacheThreadPool.getMaxPoolSize()) {
                            LOG.info("==>tasks.size():" + tasks.size());
                            final PollingTask task = tasks.take(); //使用take方法获取过期任务,如果获取不到,就一直等待,知道获取到数据
                            if (task != null) {
                                cacheThreadPool.execute(new Runnable() {
                                    public void run() {
                                        tasks.remove(task);
                                        task.run(); // 执行通知处理
                                        LOG.info("==>tasks.size():" + tasks.size());
                                    }
                                });
                            }
                        }
                    }
                } catch (Exception e) {
                    LOG.error("系统异常;", e);
                }
            }
        });
    }
}

