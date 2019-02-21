package com.roncoo.pay.banklink.utils.weixin;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.roncoo.pay.trade.entity.RpMicroSubmitRecord;
import com.roncoo.pay.trade.utils.WeixinConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 微信小微商户工具
 * Created by Quanf
 * 2018/12/21
 */
public class WeiXinMicroUtils {

    private static final Logger LOG = LoggerFactory.getLogger(WeiXinMicroUtils.class);

    private static final String MCH_ID = WeixinConfigUtil.readConfig("service_mch_id");

    private static final String KEY_STORE_URL = WeixinConfigUtil.readConfig("service_key_store_url");

    private static final String PAY_KEY = WeixinConfigUtil.readConfig("service_pay_key");

    private static final String APIV3_SECRET = WeixinConfigUtil.readConfig("service_APIv3_secret");

    private static final String MICRO_QUERY_URL = "https://api.mch.weixin.qq.com/applyment/micro/getstate";

    private static final String MICRO_SUBMIT_URL = "https://api.mch.weixin.qq.com/applyment/micro/submit";


    /**
     * 小微商户-进件
     *
     * @param submitParam
     * @return
     */
    public static Map<String, Object> microSubmit(RpMicroSubmitRecord submitParam) {
        Map<String, Object> returnMap = new HashMap<>();

        LOG.info("官方微信--小微商户-进件");
        // 获取证书信息
        Map<String, Object> certificates = WxCommonUtil.getCertificates(MCH_ID, PAY_KEY);
        certificates = JSONObject.parseObject(certificates.get("certificates").toString());
        JsonElement jsonelement = new JsonParser().parse(certificates.get("data").toString());
        JsonArray arr = jsonelement.getAsJsonArray();
        Iterator<JsonElement> it = arr.iterator();
        String associatedData = null;
        String nonce = null;
        String ciphertextEncrypt = null;
        String serialNo = null;
        while (it.hasNext()) {
            JsonElement ele = it.next();
            JsonObject o = ele.getAsJsonObject();//数组里面的每一个元素都是一个对象，所以用getAsObject
            serialNo = o.get("serial_no").getAsString();
            JsonObject infoEle = o.get("encrypt_certificate").getAsJsonObject();//你要的是对象里面的info属性，info的值是一个object
            associatedData = infoEle.get("associated_data").getAsString();//从获取到的info对象获取name的值
            nonce = infoEle.get("nonce").getAsString();//同理
            ciphertextEncrypt = infoEle.get("ciphertext").getAsString();//同理
        }
        try {
            // 获取证书明文，备用字段1:APIv3Secret
            String ciphertext = WxCommonUtil.aesgcmDecrypt(associatedData, nonce, ciphertextEncrypt, APIV3_SECRET);
            SortedMap<String, Object> paramMap = new TreeMap<>();
            paramMap.put("version", "3.0");
            paramMap.put("cert_sn", serialNo);
            paramMap.put("mch_id", MCH_ID);
            paramMap.put("nonce_str", WxCommonUtil.createNonceStr());
            paramMap.put("sign_type", "HMAC-SHA256");
            paramMap.put("business_code", submitParam.getBusinessCode());// 业务申请编号，订单号，查询状态时需要
            paramMap.put("id_card_copy", submitParam.getIdCardCopy());
            paramMap.put("id_card_national", submitParam.getIdCardNational());
            paramMap.put("id_card_name", WxCommonUtil.rsaEncrypt(submitParam.getIdCardName(), ciphertext));
            paramMap.put("id_card_number", WxCommonUtil.rsaEncrypt(submitParam.getIdCardNumber(), ciphertext));
            paramMap.put("id_card_valid_time", submitParam.getIdCardValidTime());
            paramMap.put("account_name", WxCommonUtil.rsaEncrypt(submitParam.getIdCardName(), ciphertext));
            paramMap.put("account_bank", submitParam.getAccountBank());
            paramMap.put("bank_address_code", submitParam.getBankAddressCode());
            paramMap.put("account_number", WxCommonUtil.rsaEncrypt(submitParam.getAccountNumber(), ciphertext));
            paramMap.put("store_name", submitParam.getStoreName());
            paramMap.put("store_address_code", submitParam.getStoreAddressCode());
            paramMap.put("store_street", submitParam.getStoreStreet());
            paramMap.put("store_entrance_pic", submitParam.getStoreEntrancePic());
            paramMap.put("indoor_pic", submitParam.getIndoorPic());
            paramMap.put("merchant_shortname", submitParam.getMerchantShortname());//将在支付完成页向买家展示
            paramMap.put("service_phone", submitParam.getServicePhone());//将在交易记录中向买家展示，请确保电话畅通以便微信回拨确认
            paramMap.put("product_desc", submitParam.getProductDesc());
            paramMap.put("rate", submitParam.getRate());
            paramMap.put("contact", WxCommonUtil.rsaEncrypt(submitParam.getIdCardName(), ciphertext));
            paramMap.put("contact_phone", WxCommonUtil.rsaEncrypt(submitParam.getContactPhone(), ciphertext));
            paramMap.put("sign", WxCommonUtil.sha256Sign(paramMap, PAY_KEY));
            String data = WxCommonUtil.mapToXml(paramMap);
            String returnStr = WxCommonUtil.requestPostSSL(MCH_ID, KEY_STORE_URL, data, MICRO_SUBMIT_URL);
            returnMap = WxCommonUtil.xmlToMap(returnStr);
        } catch (Exception e) {
            returnMap.put("return_msg", e.getMessage());
            e.printStackTrace();
            LOG.error(e.getMessage());
        }
        return returnMap;
    }


    /**
     * 小微商户-进件查询
     *
     * @param businessCode
     * @return
     */
    public static Map<String, Object> microQuery(String businessCode) {
        Map<String, Object> returnMap = new HashMap<>();
        LOG.info("官方微信--小微商户-进件查询");
        try {
            // 获取证书明文，备用字段1:APIv3Secret
            SortedMap<String, Object> paramMap = new TreeMap<>();
            paramMap.put("version", "1.0");
            paramMap.put("mch_id", MCH_ID);
            paramMap.put("nonce_str", WxCommonUtil.createNonceStr());
            paramMap.put("business_code", businessCode);
            paramMap.put("sign_type", "HMAC-SHA256");
            paramMap.put("sign", WxCommonUtil.sha256Sign(paramMap, PAY_KEY));
            String data = WxCommonUtil.mapToXml(paramMap);
            String returnStr = WxCommonUtil.requestPostSSL(MCH_ID, KEY_STORE_URL, data, MICRO_QUERY_URL);
            returnMap = WxCommonUtil.xmlToMap(returnStr);
        } catch (Exception e) {
            returnMap.put("errMsg", e.getMessage());
            e.printStackTrace();
            LOG.error(e.getMessage());
        }
        return returnMap;
    }
}
