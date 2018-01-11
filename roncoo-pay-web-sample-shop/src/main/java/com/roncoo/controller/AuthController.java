package com.roncoo.controller;

import com.roncoo.utils.MerchantApiUtil;
import com.roncoo.utils.PayConfigUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @RequestMapping("/init")
    public void init() {

    }

    @RequestMapping("/doAuth")
    public String doAuth( Model model) {

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
}
