package com.roncoo.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.common.core.exception.BizException;
import com.roncoo.pay.controller.common.BaseController;
import com.roncoo.pay.service.CnpPayService;
import com.roncoo.pay.trade.bo.F2FPayRequestBo;
import com.roncoo.pay.trade.service.RpTradePaymentManagerService;
import com.roncoo.pay.trade.service.RpTradePaymentQueryService;
import com.roncoo.pay.trade.vo.F2FPayResultVo;
import com.roncoo.pay.user.entity.RpUserPayConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * <b>功能说明:
 * </b>
 *
 * @author Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
@Controller
@RequestMapping(value = "/f2fPay")
public class F2FPayController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(F2FPayController.class);

    @Autowired
    private RpTradePaymentManagerService rpTradePaymentManagerService;

    @Autowired
    private CnpPayService cnpPayService;

    @Autowired
    private RpTradePaymentQueryService queryService;

    /**
     * 条码支付,商户通过前置设备获取到用户支付授权码后,请求支付网关支付.
     *
     * @return
     */
    @RequestMapping("/doPay")
    public String initPay(@ModelAttribute F2FPayRequestBo f2FPayRequestBo, BindingResult bindingResult, HttpServletRequest httpServletRequest, ModelMap modelMap) {
         try{
            //获取支付配置
            RpUserPayConfig rpUserPayConfig = cnpPayService.checkParamAndGetUserPayConfig(f2FPayRequestBo,  bindingResult, httpServletRequest);

            //发起支付
            F2FPayResultVo f2FPayResultVo = rpTradePaymentManagerService.f2fPay(rpUserPayConfig ,f2FPayRequestBo);

            logger.debug("条码支付--支付结果==>{}", f2FPayResultVo);
            modelMap.put("result", f2FPayResultVo);
            return "/f2fAffirmPay";
        } catch (BizException e) {
            logger.error("业务异常:", e);
             modelMap.put("errorMsg", e.getMsg());
            return "exception/exception";

        } catch (Exception e) {
            logger.error("系统异常:", e);
             modelMap.put("errorMsg", "系统异常");
            return "exception/exception";
        }
    }

    @RequestMapping("/order/query")
    @ResponseBody
    public String orderQuery(String trxNo) {
        return JSONObject.toJSONString(queryService.getRecordByTrxNo(trxNo));
    }

}
