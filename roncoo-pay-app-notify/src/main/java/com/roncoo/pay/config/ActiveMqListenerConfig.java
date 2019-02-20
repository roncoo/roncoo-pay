package com.roncoo.pay.config;

import com.roncoo.pay.app.notify.message.ConsumerSessionAwareMessageListener;
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

    @Value("${tradeQueueName.notify}")
    private String tradeQueueDestinationName;

    /**
     * 队列目的地
     *
     * @return 队列目的地
     */
    @Bean(name = "tradeQueueDestination")
    public ActiveMQQueue tradeQueueDestination() {
        return new ActiveMQQueue(tradeQueueDestinationName);
    }

    /**
     * 消息监听容器
     *
     * @param singleConnectionFactory             连接工厂
     * @param tradeQueueDestination               消息目的地
     * @param consumerSessionAwareMessageListener 监听器实现
     * @return 消息监听容器
     */
    @Bean(name = "tradeQueueMessageListenerContainer")
    public DefaultMessageListenerContainer tradeQueueMessageListenerContainer(@Qualifier("connectionFactory") SingleConnectionFactory singleConnectionFactory, @Qualifier("tradeQueueDestination") ActiveMQQueue tradeQueueDestination, @Qualifier("consumerSessionAwareMessageListener") ConsumerSessionAwareMessageListener consumerSessionAwareMessageListener) {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(singleConnectionFactory);
        messageListenerContainer.setMessageListener(consumerSessionAwareMessageListener);
        messageListenerContainer.setDestination(tradeQueueDestination);
        return messageListenerContainer;
    }
}
