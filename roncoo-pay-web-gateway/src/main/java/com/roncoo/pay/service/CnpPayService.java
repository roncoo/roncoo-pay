package com.roncoo.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.common.core.enums.SecurityRatingEnum;
import com.roncoo.pay.common.core.exception.BizException;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.trade.utils.MerchantApiUtil;
import com.roncoo.pay.user.entity.RpUserPayConfig;
import com.roncoo.pay.trade.exception.TradeBizException;
import com.roncoo.pay.user.exception.PayBizException;
import com.roncoo.pay.user.service.RpUserPayConfigService;
import com.roncoo.pay.utils.NetworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Service(value = "cnpPayService")
public class CnpPayService {

    private static final Logger LOG = LoggerFactory.getLogger(CnpPayService.class);

    @Autowired
    private Validator validator;

    @Autowired
    private RpUserPayConfigService rpUserPayConfigService;

    /**
     *
     * @param rpUserPayConfig
     * @param httpServletRequest
     */
    public void checkIp(RpUserPayConfig rpUserPayConfig, HttpServletRequest httpServletRequest) {
        try {

            if (!SecurityRatingEnum.MD5_IP.name().equals(rpUserPayConfig.getSecurityRating())) {
                return;
            }

            String ip = NetworkUtil.getIpAddress(httpServletRequest);
            if (StringUtil.isEmpty(ip)) {
                throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR, "获取请求IP错误");
            }

            String merchantServerIp = rpUserPayConfig.getMerchantServerIp();
            if (merchantServerIp.indexOf(ip) < 0) {
                throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR, "非法IP请求");
            }

        } catch (IOException e) {
            LOG.error("获取请求IP异常:", e);
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR, "获取请求IP异常");
        }
    }

    /**
     * 校验请求参数及支付配置
     * @param object
     * @param bindingResult
     * @param httpServletRequest
     * @return
     * @throws BizException
     */
    public RpUserPayConfig checkParamAndGetUserPayConfig(Object object, BindingResult bindingResult, HttpServletRequest httpServletRequest)throws BizException{
        validator.validate(object, bindingResult);
        if (bindingResult.hasErrors()) {// 校验银行卡信息是否完整
            String errorResponse = getErrorResponse(bindingResult);
            LOG.info("请求参数异常:{}", errorResponse);
            throw new PayBizException(PayBizException.REQUEST_PARAM_ERR, errorResponse);
        }

        Object jsonObject = JSONObject.toJSON(object);

        Map<String, Object> jsonParamMap = JSONObject.parseObject(jsonObject.toString(), Map.class);
        LOG.info("parseObject:" + jsonParamMap);

        String payKey = String.valueOf(jsonParamMap.get("payKey"));

        RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByPayKey(payKey);
        if (rpUserPayConfig == null) {
            LOG.info("payKey[{}]的商户不存在", payKey);
            throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_NOT_EXIST, "用户异常");
        }

        checkIp(rpUserPayConfig, httpServletRequest );// ip校验

        String sign = String.valueOf(jsonParamMap.get("sign"));
        if (!MerchantApiUtil.isRightSign(jsonParamMap, rpUserPayConfig.getPaySecret(), sign)) {
            LOG.info("参数[{}],MD5签名验证异常sign:{}", jsonParamMap, sign);
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单签名异常");
        }

        return rpUserPayConfig;
    }

    /**
     * 获取错误返回信息
     *
     * @param bindingResult
     * @return
     */
    public String getErrorResponse(BindingResult bindingResult) {
        StringBuffer sb = new StringBuffer();
        for (ObjectError objectError : bindingResult.getAllErrors()) {
            sb.append(objectError.getDefaultMessage()).append(",");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }


}
