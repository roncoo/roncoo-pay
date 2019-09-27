package com.roncoo.pay.permission.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class LoginConfigUtil {

    private static final Logger logger = LoggerFactory.getLogger(LoginConfigUtil.class);

    private LoginConfigUtil() {

    }

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("login.properties"));

            init();
        } catch (IOException e) {
            logger.info("加载登录配置文件失败！");
        }
    }

    /**
     * 浏览器头部信息
     */
    public static String AGENT;

    /**
     * 客户端ID
     */
    public static String CLIENT_ID;

    /**
     * 登录地址
     */
    public static String LOGIN_URL;

    public static String SALT;

    private static void init() {

        AGENT = properties.getProperty("agent");

        CLIENT_ID = properties.getProperty("client_id");

        LOGIN_URL = properties.getProperty("login_url");

        SALT = properties.getProperty("salt");
    }
}
