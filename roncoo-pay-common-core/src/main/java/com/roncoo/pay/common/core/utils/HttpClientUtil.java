package com.roncoo.pay.common.core.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

public class HttpClientUtil {

    /** 链接超时时间 **/
    private static final Integer CONNECT_TIME_OUT = 40 * 1000;

    /** 数据传输超时时间 **/
    private static final Integer SOCKET_TIME_OUT = 40 * 1000;


    public static HttpClient getHttpClient(){
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        configBuilder.setConnectTimeout(CONNECT_TIME_OUT);
        configBuilder.setSocketTimeout(SOCKET_TIME_OUT);

        HttpClientBuilder clientBuilder = HttpClients.custom();
        clientBuilder.setDefaultRequestConfig(configBuilder.build());
        HttpClient httpClient = clientBuilder.build();

        return httpClient;
    }

}
