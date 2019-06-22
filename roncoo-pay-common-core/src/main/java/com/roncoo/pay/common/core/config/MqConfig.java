package com.roncoo.pay.common.core.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MqConfig {
	
	private static final Log LOG = LogFactory.getLog(MqConfig.class);
	
	/** 商户通知队列 **/
	public static String MERCHANT_NOTIFY_QUEUE = "";

	/** 订单通知队列 **/
	public static String ORDER_NOTIFY_QUEUE = "";
	
	private static Properties properties = null;
	
	static{
		if(null == properties){
			properties  = new Properties();
		}
		InputStream proFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("mq_config.properties");
		try {
			properties.load(proFile);
			init(properties);
		} catch (IOException e) {
			LOG.error("=== load and init mq exception:" + e);
		}
	}
	
	private static void init(Properties properties){
		MERCHANT_NOTIFY_QUEUE = properties.getProperty("tradeQueueName.notify");
		ORDER_NOTIFY_QUEUE = properties.getProperty("orderQueryQueueName.query");
	}
}
