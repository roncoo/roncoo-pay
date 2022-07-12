package com.roncoo.pay.trade.utils.auth;

import com.roncoo.pay.trade.utils.alipay.config.AlipayConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class AuthConfigUtil {

    private static final Logger logger = LoggerFactory.getLogger(AuthConfigUtil.class);

    private static Properties properties = new Properties();

    private static final String CONF_URL = "auth_config.properties";

    public static String PAYKEY = "";
    public static String PAYSECRET = "";
    public static String AUTHURL = "";
    public static String AUTH_ORDER_QUERY_URL = "";
    public static String AUTH_AMOUNT = "";

    private AuthConfigUtil() {

    }

    static {
        try {
            // 从类路径下读取属性文件
            properties.load(AlipayConfigUtil.class.getClassLoader().getResourceAsStream(CONF_URL));
            init();
        } catch (IOException e) {
            logger.error("鉴权配置文件加载失败!{}", e);
        }
    }

    private static void init() {
        PAYKEY = properties.getProperty("pay_key");
        PAYSECRET = properties.getProperty("pay_secret");
        AUTHURL = properties.getProperty("auth_url");
        AUTH_ORDER_QUERY_URL = properties.getProperty("auth_order_query_url");
        AUTH_AMOUNT = properties.getProperty("auth_amount");
    }
}
