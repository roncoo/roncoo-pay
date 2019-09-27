package com.roncoo.pay.trade.utils.auth;

import com.alibaba.fastjson.JSON;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.trade.utils.MD5Util;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 鉴权工具类
 */
public class AuthUtil {

    private static final Logger logger = LoggerFactory.getLogger(AuthUtil.class);

    private static final String PRODUCTTYPE = "13000104";//产品类型

    private AuthUtil() {

    }

    public static Map<String, Object> auth(String orderNo, String name, String phone, String idNo, String bankAccountNo) {

        SortedMap<String, String> paramMap = new TreeMap<>();
        paramMap.put("payKey", AuthConfigUtil.PAYKEY);
        paramMap.put("outTradeNo", orderNo);
        paramMap.put("productType", PRODUCTTYPE);
        paramMap.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        paramMap.put("name", name);
        paramMap.put("accountNo", bankAccountNo);
        paramMap.put("certNo", idNo);
        paramMap.put("phone", phone);
        paramMap.put("sign", getSign(paramMap, AuthConfigUtil.PAYSECRET));
        Map<String, Object> resultMap = request(paramMap, AuthConfigUtil.AUTHURL);
        if (resultMap == null || resultMap.isEmpty()) {
            return null;
        }
        SortedMap<String, String> responseMap = new TreeMap<>();
        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            responseMap.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        String resultSign = getSign(responseMap, AuthConfigUtil.PAYSECRET);
        if (resultSign.equals(resultMap.get("sign"))) {
            return resultMap;
        } else {
            logger.error("鉴权返回结果验签失败,返回签名：[{}]，结果签名：[{}]", resultMap.get("sign"), resultSign);
            return null;
        }
    }

    /**
     * post请求
     *
     * @param paramMap   请求参数
     * @param requestUrl 请求地址
     * @return
     */
    private static Map<String, Object> request(SortedMap<String, String> paramMap, String requestUrl) {
        logger.info("鉴权请求地址:[{}],请求参数:[{}]", requestUrl, paramMap);
        HttpClient httpClient = new HttpClient();
        HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
        // 设置连接超时时间(单位毫秒)
        managerParams.setConnectionTimeout(9000);
        // 设置读数据超时时间(单位毫秒)
        managerParams.setSoTimeout(12000);
        PostMethod postMethod = new PostMethod(requestUrl);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        NameValuePair[] pairs = new NameValuePair[paramMap.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            pairs[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        postMethod.setRequestBody(pairs);
        try {
            Integer code = httpClient.executeMethod(postMethod);
            if (code.compareTo(200) == 0) {
                String result = postMethod.getResponseBodyAsString();
                logger.info("鉴权请求成功，同步返回数据:{}", result);
                return JSON.parseObject(result);
            } else {
                logger.error("鉴权请求失败,返回状态码:[{}]", code);
            }
        } catch (IOException e) {
            logger.info("鉴权请求异常:{}", e);
            return null;
        }
        return null;
    }

    /**
     * 签名
     *
     * @param paramMap  签名参数
     * @param paySecret 签名秘钥
     * @return
     */
    private static String getSign(SortedMap<String, String> paramMap, String paySecret) {
        StringBuilder signBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (!"sign".equals(entry.getKey()) && entry.getValue() != null && !StringUtil.isEmpty(entry.getValue())) {
                signBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        signBuilder.append("paySecret=").append(paySecret);
        logger.info("鉴权签名原文:[{}]", signBuilder);
        String sign = MD5Util.encode(signBuilder.toString()).toUpperCase();
        logger.info("鉴权签名结果:[{}]", sign);
        return sign;
    }
}
