/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roncoo.pay.controller;

import com.roncoo.pay.common.core.enums.PayWayEnum;
import com.roncoo.pay.common.core.exception.BizException;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.controller.common.BaseController;
import com.roncoo.pay.service.CnpPayService;
import com.roncoo.pay.trade.bo.ScanPayRequestBo;
import com.roncoo.pay.trade.exception.TradeBizException;
import com.roncoo.pay.trade.service.RpTradePaymentManagerService;
import com.roncoo.pay.trade.service.RpTradePaymentQueryService;
import com.roncoo.pay.trade.utils.MerchantApiUtil;
import com.roncoo.pay.trade.utils.WeixinConfigUtil;
import com.roncoo.pay.trade.vo.OrderPayResultVo;
import com.roncoo.pay.trade.vo.RpPayGateWayPageShowVo;
import com.roncoo.pay.trade.vo.ScanPayResultVo;
import com.roncoo.pay.user.entity.RpUserPayConfig;
import com.roncoo.pay.user.exception.UserBizException;
import com.roncoo.pay.user.service.RpUserPayConfigService;
import com.roncoo.pay.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>功能说明:扫码支付控制类
 * </b>
 *
 * @author Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
@Controller
@RequestMapping(value = "/scanPay")
public class ScanPayController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ScanPayController.class);

    @Autowired
    private RpTradePaymentManagerService rpTradePaymentManagerService;

    @Autowired
    private RpTradePaymentQueryService rpTradePaymentQueryService;

    @Autowired
    private CnpPayService cnpPayService;

    /**
     * 扫码支付,预支付页面
     * 用户进行扫码支付时,商户后台调用该接口
     * 支付平台根据商户传入的参数是否包含支付通道,决定需要跳转的页面
     * 1:传入支付通道参数,跳转到相应的支付通道扫码页面
     * 2:未传入支付通道参数,跳转到
     *
     * @return
     */
    @RequestMapping("/initPay")
    public String initPay(@ModelAttribute ScanPayRequestBo scanPayRequestBo, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest) {
        logger.info("======>进入扫码支付{}" , scanPayRequestBo);

    try{
        RpUserPayConfig rpUserPayConfig = cnpPayService.checkParamAndGetUserPayConfig(scanPayRequestBo,  bindingResult, httpServletRequest);

        if (StringUtil.isEmpty(scanPayRequestBo.getPayType())) {//非直连方式
            logger.info("======>扫码支付，非直连方式");
            RpPayGateWayPageShowVo payGateWayPageShowVo = rpTradePaymentManagerService.initNonDirectScanPay( rpUserPayConfig , scanPayRequestBo);
            model.addAttribute("payGateWayPageShowVo", payGateWayPageShowVo);//支付网关展示数据
            return "gateway";

        } else {//直连方式
            logger.info("======>扫码支付，直连方式");
            BigDecimal orderPrice = scanPayRequestBo.getOrderPrice();
            ScanPayResultVo scanPayResultVo = rpTradePaymentManagerService.initDirectScanPay(rpUserPayConfig , scanPayRequestBo);

            model.addAttribute("codeUrl", scanPayResultVo.getCodeUrl());//支付二维码

            if (PayWayEnum.WEIXIN.name().equals(scanPayResultVo.getPayWayCode())) {
                model.addAttribute("queryUrl", WeixinConfigUtil.readConfig("order_query_url") + "?orderNO=" + scanPayRequestBo.getOrderNo() + "&payKey=" + scanPayRequestBo.getPayKey());
                model.addAttribute("productName", scanPayRequestBo.getProductName());//产品名称
                model.addAttribute("orderPrice", orderPrice);//订单价格
                model.addAttribute("orderNo", scanPayRequestBo.getOrderNo());//订单号
                model.addAttribute("payKey", scanPayRequestBo.getPayKey());//支付Key

                return "weixinPayScanPay";
            } else if (PayWayEnum.ALIPAY.name().equals(scanPayResultVo.getPayWayCode())) {
                return "alipayDirectPay";
            }
        }
        return "gateway";

        } catch (BizException e) {
            logger.error("业务异常:", e);
            model.addAttribute("errorMsg", e.getMsg());//订单价格
            return "exception/exception";

        } catch (Exception e) {
            logger.error("系统异常:", e);
            model.addAttribute("errorMsg", "系统异常");//订单价格
            return "exception/exception";
        }

    }

    @RequestMapping("/toPay/{orderNo}/{payType}/{payKey}")
    public String toPay(@PathVariable("payKey") String payKey, @PathVariable("orderNo") String orderNo, @PathVariable("payType") String payType, Model model) {

        ScanPayResultVo scanPayResultVo = rpTradePaymentManagerService.toNonDirectScanPay(payKey, orderNo, payType , 3);

        model.addAttribute("codeUrl", scanPayResultVo.getCodeUrl());//支付二维码

        if (PayWayEnum.WEIXIN.name().equals(scanPayResultVo.getPayWayCode())) {
            model.addAttribute("queryUrl", WeixinConfigUtil.readConfig("order_query_url") + "?orderNO=" + orderNo + "&payKey=" + payKey);
            model.addAttribute("productName", scanPayResultVo.getProductName());//产品名称
            model.addAttribute("orderPrice", scanPayResultVo.getOrderAmount());//订单价格
            return "weixinPayScanPay";
        } else if (PayWayEnum.ALIPAY.name().equals(scanPayResultVo.getPayWayCode())) {
            return "alipayDirectPay";
        }

        return null;
    }

    /**
     * 支付结果查询接口
     *
     * @param httpServletResponse
     */
    @RequestMapping("orderQuery")
    public void orderQuery(HttpServletResponse httpServletResponse) throws IOException {

        String payKey = getString_UrlDecode_UTF8("payKey"); // 企业支付KEY
        String orderNO = getString_UrlDecode_UTF8("orderNO"); // 订单号

        OrderPayResultVo payResult = rpTradePaymentQueryService.getPayResult(payKey, orderNO);
        httpServletResponse.setContentType("text/text;charset=UTF-8");
        JsonUtils.responseJson(httpServletResponse, payResult);

    }

}
