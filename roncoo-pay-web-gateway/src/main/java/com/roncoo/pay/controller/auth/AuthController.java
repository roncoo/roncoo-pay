package com.roncoo.pay.controller.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.service.CnpPayService;
import com.roncoo.pay.trade.entity.RpUserBankAuth;
import com.roncoo.pay.trade.enums.TradeStatusEnum;
import com.roncoo.pay.trade.exception.TradeBizException;
import com.roncoo.pay.trade.service.RpTradePaymentManagerService;
import com.roncoo.pay.trade.service.RpUserBankAuthService;
import com.roncoo.pay.trade.utils.MerchantApiUtil;
import com.roncoo.pay.trade.utils.auth.AuthConfigUtil;
import com.roncoo.pay.trade.vo.AuthInitResultVo;
import com.roncoo.pay.trade.vo.AuthParamVo;
import com.roncoo.pay.trade.vo.AuthResultVo;
import com.roncoo.pay.trade.vo.RpPayGateWayPageShowVo;
import com.roncoo.pay.user.entity.RpUserPayConfig;
import com.roncoo.pay.user.exception.UserBizException;
import com.roncoo.pay.user.service.RpUserPayConfigService;
import com.roncoo.pay.utils.NetworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private RpUserPayConfigService userPayConfigService;
    @Autowired
    private CnpPayService cnpPayService;
    @Autowired
    private RpTradePaymentManagerService tradePaymentManagerService;
    @Autowired
    private RpUserBankAuthService userBankAuthService;

    @RequestMapping(value = "/initAuth")
    public String initPay(@Valid AuthParamVo paramVo, BindingResult bindingResult, HttpServletRequest request, ModelMap modelMap) {

        //参数校验
        if (bindingResult.hasErrors()) {
            String errMsg = getErrorMsg(bindingResult);
            logger.info("用户鉴权--请求参数异常：[{}]", errMsg);
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR, errMsg);
        }

        RpUserPayConfig userPayConfig = userPayConfigService.getByPayKey(paramVo.getPayKey());
        if (userPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }
        //ip校验
        cnpPayService.checkIp(userPayConfig, request);

        //验签
        Map<String, Object> paramMap = JSONObject.parseObject(JSON.toJSONString(paramVo));
        paramMap.remove("sign");
        if (!MerchantApiUtil.isRightSign(paramMap, userPayConfig.getPaySecret(), paramVo.getSign())) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单签名异常");
        }

        String productName = "鉴权产品";
        String orderIp = null;
        try {
            orderIp = NetworkUtil.getIpAddress(request);
        } catch (IOException e) {
            logger.info("获取Ip失败!");
        }
        if(StringUtil.isEmpty(orderIp)){
            orderIp = "127.0.0.1";
        }
        AuthInitResultVo resultVo = tradePaymentManagerService.initDirectAuth(productName, BigDecimal.valueOf(Double.valueOf(AuthConfigUtil.AUTH_AMOUNT)), orderIp, paramVo, userPayConfig);
        if (TradeStatusEnum.SUCCESS.equals(resultVo.getTradeStatus()) && resultVo.isAuth()) {
            return "redirect:/auth/doAuth/" + resultVo.getMerchantNo() + "/" + resultVo.getMerchantOrderNo();
        }
        RpPayGateWayPageShowVo payGateWayPageShowVo = new RpPayGateWayPageShowVo();
        payGateWayPageShowVo.setMerchantName(resultVo.getMerchantName());
        payGateWayPageShowVo.setMerchantOrderNo(resultVo.getMerchantOrderNo());
        payGateWayPageShowVo.setOrderAmount(resultVo.getOrderAmount());
        payGateWayPageShowVo.setPayKey(resultVo.getPayKey());
        payGateWayPageShowVo.setProductName(resultVo.getProductName());
        payGateWayPageShowVo.setPayWayEnumMap(resultVo.getPayWayEnumMap());
        modelMap.addAttribute("payGateWayPageShowVo", payGateWayPageShowVo);//支付网关展示数据
        return "gateway";
    }

    @RequestMapping(value = "/doAuth/{merchantNo}/{orderNo}")
    public String doAuth(@PathVariable(value = "merchantNo") String merchantNo, @PathVariable(value = "orderNo") String orderNo, ModelMap modelMap) {
        logger.info("鉴权，商户号:[{}],订单号:[{}]", merchantNo, orderNo);
        AuthResultVo resultVo = tradePaymentManagerService.userAuth(merchantNo, orderNo);
        modelMap.put("resultVo", resultVo);
        return "auth";
    }

    @RequestMapping("/orderQuery")
    @ResponseBody
    public String orderQuery(String payKey, String orderNo) {
        logger.info("鉴权记录查询,payKey:[{}],订单号:[{}]", payKey, orderNo);
        RpUserPayConfig rpUserPayConfig = userPayConfigService.getByPayKey(payKey);
        if (rpUserPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        String merchantNo = rpUserPayConfig.getUserNo();// 商户编号
        RpUserBankAuth userBankAuth = userBankAuthService.findByMerchantNoAndPayOrderNo(merchantNo, orderNo);
        JSONObject resultJson = new JSONObject();
        if (userBankAuth == null) {
            resultJson.put("status", "NO");
        } else {
            resultJson.put("status", "YES");
            resultJson.put("returnUrl", AuthConfigUtil.AUTH_ORDER_QUERY_URL + userBankAuth.getMerchantNo() + "/" + userBankAuth.getPayOrderNo());
        }
        return resultJson.toJSONString();
    }


    /**
     * 获取错误返回信息
     *
     * @param bindingResult
     * @return
     */
    public String getErrorMsg(BindingResult bindingResult) {
        StringBuffer sb = new StringBuffer();
        for (ObjectError objectError : bindingResult.getAllErrors()) {
            sb.append(objectError.getDefaultMessage()).append(",");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }
}
