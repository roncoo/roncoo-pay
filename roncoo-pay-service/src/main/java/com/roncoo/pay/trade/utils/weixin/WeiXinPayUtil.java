package com.roncoo.pay.trade.utils.weixin;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.trade.entity.RoncooPayGoodsDetails;
import com.roncoo.pay.trade.utils.MD5Util;
import com.roncoo.pay.trade.utils.WeiXinPayUtils;
import com.roncoo.pay.trade.utils.WeixinConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public class WeiXinPayUtil {

    private static final Logger logger = LoggerFactory.getLogger(WeiXinPayUtil.class);

    private WeiXinPayUtil() {

    }

    /**
     * 微信被扫(扫码设备)
     *
     * @param outTradeNo
     * @param body
     * @param totalAmount
     * @param spbillCreateIp
     * @param authCode
     * @return
     */
    public static Map<String, Object> micropay(String outTradeNo, String body, BigDecimal totalAmount, String spbillCreateIp, String authCode) {
        String nonce_str = getnonceStr();
        Integer total_fee = totalAmount.multiply(BigDecimal.valueOf(100L)).intValue();

        SortedMap<String, Object> paramMap = new TreeMap<>();
        paramMap.put("appid", WeixinConfigUtil.appId);
        paramMap.put("mch_id", WeixinConfigUtil.mch_id);
        paramMap.put("nonce_str", nonce_str);
        paramMap.put("sign_type", "MD5");
        paramMap.put("body", body);
        paramMap.put("out_trade_no", outTradeNo);
        paramMap.put("total_fee", total_fee);
        paramMap.put("fee_type", "CNY");
        paramMap.put("spbill_create_ip", spbillCreateIp);
        paramMap.put("auth_code", authCode);
        paramMap.put("sign", getSign(paramMap, WeixinConfigUtil.partnerKey));
        String data = mapToXml(paramMap);
        logger.info("微信条码请求报文:{}", data);
        Map<String, Object> resultMap = WeiXinPayUtils.httpXmlRequest("https://api.mch.weixin.qq.com/pay/micropay", "POST", data);
        logger.info("微信条码返回报文:{}", resultMap);
        if (resultMap == null || resultMap.isEmpty()) {
            return null;
        }
        SortedMap<String, Object> responseMap = new TreeMap<>();
        responseMap.putAll(resultMap);
        String resultSign = getSign(responseMap, WeixinConfigUtil.partnerKey);
        if (resultSign.equals(resultMap.get("sign"))) {
            resultMap.put("verify", "YES");
        } else {
            logger.info("返回报文验签失败,返回报文签名:{},返回签名:{}", resultSign, resultMap.get("sign"));
            resultMap.put("verify", "NO");
        }
        return resultMap;
    }

    /**
     * 小程序支付
     *
     * @return
     */
    public static Map<String, Object> appletPay(String outTradeNo, String body, BigDecimal totalAmount, String spbillCreateIp, String notifyUrl, String openid, List<RoncooPayGoodsDetails> goodsDetails) {
        String nonce_str = getnonceStr();
        Integer totalFee = totalAmount.multiply(BigDecimal.valueOf(100L)).intValue();
        String tradeType = "JSAPI";

        SortedMap<String, Object> paramMap = new TreeMap<>();
        paramMap.put("appid", WeixinConfigUtil.xAppId);
        paramMap.put("mch_id", WeixinConfigUtil.xMchId);
        paramMap.put("nonce_str", nonce_str);
        paramMap.put("sign_type", "MD5");
        paramMap.put("body", body);
        paramMap.put("out_trade_no", outTradeNo);
        paramMap.put("total_fee", totalFee);
        paramMap.put("spbill_create_ip", spbillCreateIp);
        paramMap.put("notify_url", notifyUrl);
        paramMap.put("trade_type", tradeType);
        paramMap.put("openid", openid);
        if (goodsDetails != null && !goodsDetails.isEmpty()) {
            List<SortedMap<String, Object>> goodList = new ArrayList<>();
            for (RoncooPayGoodsDetails goodsDetail : goodsDetails) {
                SortedMap<String, Object> goodsDetailMap = new TreeMap<>();
                goodsDetailMap.put("goods_id", goodsDetail.getGoodsId());
                goodsDetailMap.put("quantity", goodsDetail.getNums());
                goodsDetailMap.put("goods_name", goodsDetail.getGoodsName());
                goodsDetailMap.put("price", goodsDetail.getSinglePrice());
                goodList.add(goodsDetailMap);
            }
            JSONObject goodsDetailJson = new JSONObject();
            goodsDetailJson.put("goods_detail", goodList);
            paramMap.put("detail", goodsDetailJson.toJSONString());
        }
        paramMap.put("sign", getSign(paramMap, WeixinConfigUtil.xPayKey));
        String data = mapToXml(paramMap);
        logger.info("微信小程序统一下单，请求报文:{}", data);
        Map<String, Object> resultMap = WeiXinPayUtils.httpXmlRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", data);
        logger.info("微信小程序统一下单，返回报文:{}", resultMap);
        if (resultMap == null || resultMap.isEmpty()) {
            return null;
        }
        SortedMap<String, Object> responseMap = new TreeMap<>();
        responseMap.putAll(resultMap);
        String resultSign = getSign(responseMap, WeixinConfigUtil.xPayKey);
        if (resultSign.equals(resultMap.get("sign"))) {
            resultMap.put("verify", "YES");
        } else {
            logger.info("返回报文验签失败,返回报文签名:{},返回签名:{}", resultSign, resultMap.get("sign"));
            resultMap.put("verify", "NO");
        }
        return resultMap;
    }

    /**
     * 生成随机字符串
     *
     * @return
     */
    public static String getnonceStr() {
        Random random = new Random();
        StringBuilder nonceStrBuilder = new StringBuilder();
        for (int i = 0; i < 31; i++) {
            nonceStrBuilder.append(random.nextInt(10));
        }
        return nonceStrBuilder.toString();
    }

    /**
     * 签名
     *
     * @param paramMap
     * @param key
     * @return
     */
    public static String getSign(SortedMap<String, Object> paramMap, String key) {
        StringBuilder signBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (!"sign".equals(entry.getKey()) && !StringUtil.isEmpty(entry.getValue())) {
                signBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        signBuilder.append("key=").append(key);
        logger.info("微信待签名参数字符串:{}", signBuilder.toString());
        return MD5Util.encode(signBuilder.toString()).toUpperCase();
    }

    /**
     * 转xml格式
     *
     * @param paramMap
     * @return
     */
    private static String mapToXml(SortedMap<String, Object> paramMap) {
        StringBuilder dataBuilder = new StringBuilder("<xml>");
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (!StringUtil.isEmpty(entry.getValue())) {
                dataBuilder.append("<").append(entry.getKey()).append(">").append(entry.getValue()).append("</").append(entry.getKey()).append(">");
            }
        }
        dataBuilder.append("</xml>");
        logger.info("Map转Xml结果:{}", dataBuilder.toString());
        return dataBuilder.toString();
    }

}
