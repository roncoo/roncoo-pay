package com.roncoo.pay.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@PropertySource("classpath:mq_config.properties")
public class ActiveMqConfig {

    @Value("${mq.brokerURL}")
    private String mqBrokerURL;
    @Value("${mq.userName}")
    private String mqUserName;
    @Value("${mq.password}")
    private String mqPassword;
    @Value("#{10}")
    private Integer maxConnections;

    @Value("${tradeQueueName.notify}")
    private String tradeQueueDestinationName;
    @Value("${orderQueryQueueName.query}")
    private String orderQueryDestinationName;

    /**
     * 真正可以产生Connection的ConnectionFactory，由对应的 JMS服务厂商提供
     *
     * @return 真正的连接工厂
     */
    @Bean(name = "targetConnectionFactory")
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(mqBrokerURL);
        activeMQConnectionFactory.setUserName(mqUserName);
        activeMQConnectionFactory.setPassword(mqPassword);
        return activeMQConnectionFactory;
    }

    /**
     * Spring用于管理真正的ConnectionFactory的ConnectionFactory
     *
     * @param pooledConnectionFactory Pooled连接工厂
     * @return 连接工厂
     */
    @Primary
    @Bean(name = "connectionFactory")
    public SingleConnectionFactory singleConnectionFactory(@Qualifier("pooledConnectionFactory") PooledConnectionFactory pooledConnectionFactory) {
        SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory();
        singleConnectionFactory.setTargetConnectionFactory(pooledConnectionFactory);
        return singleConnectionFactory;
    }

    /**
     * ActiveMQ为我们提供了一个PooledConnectionFactory，通过往里面注入一个ActiveMQConnectionFactory
     * 可以用来将Connection、Session和MessageProducer池化，这样可以大大的减少我们的资源消耗。
     * 要依赖于 activemq-pool包
     *
     * @param activeMQConnectionFactory 目标连接工厂
     * @return Pooled连接工厂
     */
    @Bean(name = "pooledConnectionFactory")
    public PooledConnectionFactory pooledConnectionFactory(@Qualifier("targetConnectionFactory") ActiveMQConnectionFactory activeMQConnectionFactory) {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory);
        pooledConnectionFactory.setMaxConnections(maxConnections);
        return pooledConnectionFactory;
    }

    /**
     * 商户通知队列模板
     *
     * @param singleConnectionFactory 连接工厂
     * @return 商户通知队列模板
     */
    @Bean(name = "notifyJmsTemplate")
    public JmsTemplate notifyJmsTemplate(@Qualifier("connectionFactory") SingleConnectionFactory singleConnectionFactory) {
        JmsTemplate notifyJmsTemplate = new JmsTemplate();
        notifyJmsTemplate.setConnectionFactory(singleConnectionFactory);
        notifyJmsTemplate.setDefaultDestinationName(tradeQueueDestinationName);
        return notifyJmsTemplate;
    }

    /**
     * 队列模板
     *
     * @param singleConnectionFactory 连接工厂
     * @return 队列模板
     */
    @Bean(name = "jmsTemplate")
    public JmsTemplate jmsTemplate(@Qualifier("connectionFactory") SingleConnectionFactory singleConnectionFactory) {
        JmsTemplate notifyJmsTemplate = new JmsTemplate();
        notifyJmsTemplate.setConnectionFactory(singleConnectionFactory);
        notifyJmsTemplate.setDefaultDestinationName(orderQueryDestinationName);
        return notifyJmsTemplate;
    }
}
