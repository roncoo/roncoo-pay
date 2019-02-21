package com.roncoo.pay.banklink.utils.weixin;

import com.roncoo.pay.trade.utils.WeixinConfigUtil;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信图片上传
 */
public class UploadUtils {

    private static final Logger logger = LoggerFactory.getLogger(UploadUtils.class);

    public static Map<String, Object> upload(File file) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("return_msg", "上传失败");
        try {
            String mch_id = WeixinConfigUtil.readConfig("service_mch_id");
            String media_hash = WxCommonUtil.md5HashCode(new FileInputStream(file));
            String sign_type = "HMAC-SHA256";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("mch_id", mch_id);
            paramMap.put("media_hash", media_hash);
            paramMap.put("sign_type", sign_type);
            String sign = WxCommonUtil.sha256Sign(paramMap, WeixinConfigUtil.readConfig("service_pay_key"));
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.addTextBody("mch_id", mch_id, ContentType.MULTIPART_FORM_DATA);
            multipartEntityBuilder.addBinaryBody("media", file, ContentType.create("image/png"), file.getName());
            multipartEntityBuilder.addTextBody("media_hash", media_hash, ContentType.MULTIPART_FORM_DATA);
            multipartEntityBuilder.addTextBody("sign_type", sign_type, ContentType.MULTIPART_FORM_DATA);
            multipartEntityBuilder.addTextBody("sign", sign, ContentType.MULTIPART_FORM_DATA);

            FileInputStream instream = null;
            SSLContext sslcontext = null;
            try {
                KeyStore keyStore = KeyStore.getInstance("PKCS12");
                instream = new FileInputStream(new File(WeixinConfigUtil.readConfig("service_key_store_url")));
                keyStore.load(instream, mch_id.toCharArray());
                sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
            } catch (Exception e) {
                returnMap.put("return_msg", e.getMessage());
                logger.error("官方微信--证书加载失败!{}", e);
            } finally {
                try {
                    if (instream != null) {
                        instream.close();
                    }
                } catch (IOException e) {
                    returnMap.put("return_msg", e.getMessage());
                    logger.error("官方微信--证书加载失败!{}", e);
                }
            }
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            try {
                // 请求微信图片上传接口
                HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/secapi/mch/uploadmedia");
                RequestConfig config = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
                httpPost.setConfig(config);
                httpPost.addHeader(HTTP.CONTENT_TYPE, "multipart/form-data; charset=UTF-8");
                httpPost.addHeader(HTTP.USER_AGENT, "wxpay sdk java v1.0 " + mch_id);
                httpPost.setEntity(multipartEntityBuilder.build());
                CloseableHttpResponse response = httpclient.execute(httpPost);
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("官方微信--请求返回结果：{}", result);
                Map<String, Object> resultMap = WxCommonUtil.xmlToMap(result);
                return resultMap;
            } catch (Exception e) {
                logger.error("官方微信--请求失败！{}", e);
                return returnMap;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return returnMap;
        }
    }

    /**
     * 删除临时文件
     *
     * @param files
     */
    public static void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
