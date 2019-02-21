package com.roncoo.pay.config;

import com.roncoo.pay.app.polling.listener.PollingMessageListener;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

@Configuration
@PropertySource("classpath:mq_config.properties")
public class ActiveMqListenerConfig {

    @Value("${orderQueryQueueName.query}")
    private String orderQueryQueueDestinationName;

    /**
     * 队列目的地
     *
     * @return 队列目的地
     */
    @Bean(name = "orderQueryQueueDestination")
    public ActiveMQQueue orderQueryQueueDestination() {
        return new ActiveMQQueue(orderQueryQueueDestinationName);
    }

    /**
     * 消息监听容器
     *
     * @param singleConnectionFactory    连接工厂
     * @param orderQueryQueueDestination 消息目的地
     * @param pollingMessageListener     监听器实现
     * @return 消息监听容器
     */
    @Bean(name = "orderQueryQueueMessageListenerContainer")
    public DefaultMessageListenerContainer orderQueryQueueMessageListenerContainer(@Qualifier("connectionFactory") SingleConnectionFactory singleConnectionFactory, @Qualifier("orderQueryQueueDestination") ActiveMQQueue orderQueryQueueDestination, @Qualifier("pollingMessageListener") PollingMessageListener pollingMessageListener) {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(singleConnectionFactory);
        messageListenerContainer.setDestination(orderQueryQueueDestination);
        messageListenerContainer.setMessageListener(pollingMessageListener);
        return messageListenerContainer;
    }


}
