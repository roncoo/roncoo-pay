package com.roncoo.pay.controller;

import com.alibaba.fastjson.JSON;
import com.roncoo.pay.utils.MerchantApiUtil;
import com.roncoo.pay.utils.PayConfigUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController {

    private static final Logger logger = Logger.getLogger("AuthController");

    @RequestMapping("/init")
    public void init() {

    }

    @RequestMapping("/doAuth")
    public String doAuth(Model model) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("payKey", PayConfigUtil.readConfig("payKey"));
        String userName = getString_UrlDecode_UTF8("userName"); //用户姓名
        paramMap.put("userName", userName);
        String idNo = getString_UrlDecode_UTF8("idNo"); // 身份证号
        paramMap.put("idNo", idNo);
        String phone = getString_UrlDecode_UTF8("phone"); // 手机号码
        paramMap.put("phone", phone);
        String bankAccountNo = getString_UrlDecode_UTF8("bankAccountNo"); // 银行卡号
        paramMap.put("bankAccountNo", bankAccountNo);
        String orderNo = String.valueOf(System.currentTimeMillis());    // 订单编号
        paramMap.put("orderNo", orderNo);
        String remark = getString_UrlDecode_UTF8("remark"); // 支付备注
        paramMap.put("remark", remark);
        /////签名及生成请求API的方法///
        String sign = MerchantApiUtil.getSign(paramMap, PayConfigUtil.readConfig("paySecret"));
        paramMap.put("sign", sign);

        String buildRequest = MerchantApiUtil.buildRequest(paramMap, "get", "确定", PayConfigUtil.readConfig("authUrl"));

        model.addAttribute("payMessage", buildRequest);
        return "toPay";
    }


    @RequestMapping(value = "/initProgramAuth", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String initProgramAuth(HttpServletRequest request) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("payKey", PayConfigUtil.readConfig("payKey"));
        String userName = getString_UrlDecode_UTF8("userName"); //用户姓名
        paramMap.put("userName", userName);
        String idNo = getString_UrlDecode_UTF8("idNo"); // 身份证号
        paramMap.put("idNo", idNo);
        String phone = getString_UrlDecode_UTF8("phone"); // 手机号码
        paramMap.put("phone", phone);
        String bankAccountNo = getString_UrlDecode_UTF8("bankAccountNo"); // 银行卡号
        paramMap.put("bankAccountNo", bankAccountNo);
        String payWayCode = getString_UrlDecode_UTF8("payWayCode"); // 支付方式编码 支付宝: ALIPAY  微信:WEIXIN
        paramMap.put("payWayCode", payWayCode);
        String openId = getString_UrlDecode_UTF8("openId"); // 商品名称
        paramMap.put("openId", openId);
        String orderNo = String.valueOf(System.currentTimeMillis());    // 订单编号
        paramMap.put("orderNo", orderNo);
        String remark = getString_UrlDecode_UTF8("remark"); // 支付备注
        paramMap.put("remark", remark);
        /////签名及生成请求API的方法///
        String sign = MerchantApiUtil.getSign(paramMap, PayConfigUtil.readConfig("paySecret"));
        paramMap.put("sign", sign);
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(PayConfigUtil.readConfig("initProgramAuthUrl"));
        try {
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }
            post.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(post);
            String result = EntityUtils.toString(httpResponse.getEntity());
            logger.info("小程序初始化鉴权--返回返回结果:" + result);

            Map<String, Object> resultMap = JSON.parseObject(result);
            if ("YES".equals(resultMap.get("errCode"))) {
                String resultSign = MerchantApiUtil.getSign(resultMap, PayConfigUtil.readConfig("paySecret"));
                logger.info("小程序初始化鉴权--返回报文签名结果:" + resultSign);
                if (resultSign.equals(resultMap.get("sign"))) {
                    logger.info("小程序初始化鉴权--返回结果验签成功!");
                    return result;
                }
            }
            logger.info("请求失败,不需要验签!");
            return result;
        } catch (Exception e) {
            logger.info("小程序初始化鉴权--调用小程序支付失败，失败原因:" + e);
        }
        return null;
    }

    @RequestMapping(value = "/doProgramAuth", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String doProgramAuth() {
        String paykey = PayConfigUtil.readConfig("payKey");
        String orderNo = getString_UrlDecode_UTF8("orderNo");
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(PayConfigUtil.readConfig("doProgramAuthUrl") + paykey + "/" + orderNo);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            String result = EntityUtils.toString(httpResponse.getEntity());
            logger.info("小程序鉴权--返回返回结果:" + result);
            return result;
        } catch (Exception e) {
            logger.info("小程序鉴权--调用小程序鉴权失败，失败原因:" + e);
        }
        return null;
    }
}
