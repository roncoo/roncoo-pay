package com.roncoo.pay.config;

import com.roncoo.pay.app.polling.entity.PollingParam;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PollingConfig {

    @Bean(name = "threadPool")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setKeepAliveSeconds(10000);
        threadPoolTaskExecutor.setMaxPoolSize(5);
        threadPoolTaskExecutor.setQueueCapacity(50);
        return threadPoolTaskExecutor;
    }

    @Bean(name = "pollingParam")
    public PollingParam pollingParam() {
        PollingParam pollingParam = new PollingParam();
        Map<Integer, Integer> notifyParams = new HashMap<>();
        notifyParams.put(1, 2);
        notifyParams.put(2, 3);
        notifyParams.put(3, 5);
        notifyParams.put(4, 10);
        notifyParams.put(5, 20);
        notifyParams.put(6, 30);
        pollingParam.setNotifyParams(notifyParams);
        pollingParam.setSuccessValue("SUCCESS");
        return pollingParam;
    }
}
