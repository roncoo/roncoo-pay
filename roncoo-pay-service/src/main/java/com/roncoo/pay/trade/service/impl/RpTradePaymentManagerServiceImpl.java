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
package com.roncoo.pay.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.roncoo.pay.account.service.RpAccountTransactionService;
import com.roncoo.pay.common.core.enums.PayTypeEnum;
import com.roncoo.pay.common.core.enums.PayWayEnum;
import com.roncoo.pay.common.core.enums.PublicEnum;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.notify.service.RpNotifyService;
import com.roncoo.pay.trade.bo.F2FPayRequestBo;
import com.roncoo.pay.trade.bo.ProgramPayRequestBo;
import com.roncoo.pay.trade.bo.ScanPayRequestBo;
import com.roncoo.pay.trade.dao.RpTradePaymentOrderDao;
import com.roncoo.pay.trade.dao.RpTradePaymentRecordDao;
import com.roncoo.pay.trade.dao.RpUserBankAuthDao;
import com.roncoo.pay.trade.entity.RoncooPayGoodsDetails;
import com.roncoo.pay.trade.entity.RpTradePaymentOrder;
import com.roncoo.pay.trade.entity.RpTradePaymentRecord;
import com.roncoo.pay.trade.entity.RpUserBankAuth;
import com.roncoo.pay.trade.entity.weixinpay.WeiXinPrePay;
import com.roncoo.pay.trade.enums.AuthStatusEnum;
import com.roncoo.pay.trade.enums.OrderFromEnum;
import com.roncoo.pay.trade.enums.TradeStatusEnum;
import com.roncoo.pay.trade.enums.TrxTypeEnum;
import com.roncoo.pay.trade.enums.alipay.AliPayTradeStateEnum;
import com.roncoo.pay.trade.enums.weixinpay.WeiXinTradeTypeEnum;
import com.roncoo.pay.trade.enums.weixinpay.WeixinTradeStateEnum;
import com.roncoo.pay.trade.exception.TradeBizException;
import com.roncoo.pay.trade.service.RpTradePaymentManagerService;
import com.roncoo.pay.trade.utils.MerchantApiUtil;
import com.roncoo.pay.trade.utils.WeiXinPayUtils;
import com.roncoo.pay.trade.utils.WeixinConfigUtil;
import com.roncoo.pay.trade.utils.alipay.AliPayUtil;
import com.roncoo.pay.trade.utils.alipay.config.AlipayConfigUtil;
import com.roncoo.pay.trade.utils.auth.AuthUtil;
import com.roncoo.pay.trade.utils.weixin.WeiXinPayUtil;
import com.roncoo.pay.trade.vo.*;
import com.roncoo.pay.user.entity.RpPayWay;
import com.roncoo.pay.user.entity.RpUserInfo;
import com.roncoo.pay.user.entity.RpUserPayConfig;
import com.roncoo.pay.user.entity.RpUserPayInfo;
import com.roncoo.pay.user.enums.FundInfoTypeEnum;
import com.roncoo.pay.user.exception.PayBizException;
import com.roncoo.pay.user.exception.UserBizException;
import com.roncoo.pay.user.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * <b>功能说明:交易模块管理实现类实现</b>
 *
 * @author Peter <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
@Service("rpTradePaymentManagerService")
public class RpTradePaymentManagerServiceImpl implements RpTradePaymentManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(RpTradePaymentManagerServiceImpl.class);

    @Autowired
    private RpTradePaymentOrderDao rpTradePaymentOrderDao;
    @Autowired
    private RpTradePaymentRecordDao rpTradePaymentRecordDao;
    @Autowired
    private RpUserInfoService rpUserInfoService;
    @Autowired
    private RpUserPayInfoService rpUserPayInfoService;
    @Autowired
    private RpUserPayConfigService rpUserPayConfigService;
    @Autowired
    private RpPayWayService rpPayWayService;
    @Autowired
    private BuildNoService buildNoService;
    @Autowired
    private RpNotifyService rpNotifyService;
    @Autowired
    private RpAccountTransactionService rpAccountTransactionService;
    @Autowired
    private RpUserBankAuthDao userBankAuthDao;

    /**
     * 初始化直连扫码支付数据,直连扫码支付初始化方法规则
     * 1:根据(商户编号 + 商户订单号)确定订单是否存在
     * 1.1:如果订单不存在,创建支付订单
     * 2:创建支付记录
     * 3:根据相应渠道方法
     * 4:调转到相应支付渠道扫码界面
     **/

    @Override
    public ScanPayResultVo initDirectScanPay(RpUserPayConfig rpUserPayConfig, ScanPayRequestBo scanPayRequestBo) {
        // 根据支付产品及支付方式获取费率
        RpPayWay payWay = null;
        PayTypeEnum payType = PayTypeEnum.getEnum(scanPayRequestBo.getPayType());
        if (payType == null){
            LOG.info("支付类型有误");
            throw new PayBizException(PayBizException.REQUEST_PARAM_ERR ,"支付类型有误");
        }

        if (!PayTypeEnum.SCANPAY.name().equals(scanPayRequestBo.getPayType()) && !PayTypeEnum.DIRECT_PAY.name().equals(scanPayRequestBo.getPayType()) && !PayTypeEnum.HUA_BEI_FEN_QI_PAY.name().equals(scanPayRequestBo.getPayType())){
            LOG.info("支付类型非用户主扫交易");
            throw new PayBizException(PayBizException.REQUEST_PARAM_ERR ,"不支持改支付类型");
        }

        String payWayCode = payType.getWay();
        payWay = rpPayWayService.getByPayWayTypeCode(rpUserPayConfig.getProductCode(), payWayCode, payType.name());

        if (payWay == null) {
            LOG.info("支付配置有误，未配置支付方式{}" , payType.name());
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        String merchantNo = rpUserPayConfig.getUserNo();// 商户编号
        RpUserInfo rpUserInfo = rpUserInfoService.getDataByMerchentNo(merchantNo);
        if (rpUserInfo == null) {
            throw new UserBizException(UserBizException.USER_IS_NULL, "用户不存在");
        }

        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, scanPayRequestBo.getOrderNo());
        if (rpTradePaymentOrder == null) {
            rpTradePaymentOrder = sealScanPayRpTradePaymentOrder( rpUserPayConfig ,  scanPayRequestBo , rpUserInfo);
            rpTradePaymentOrderDao.insert(rpTradePaymentOrder);
        } else {
            if (TradeStatusEnum.SUCCESS.name().equals(rpTradePaymentOrder.getStatus())) {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单已支付成功,无需重复支付");
            }
            if (rpTradePaymentOrder.getOrderAmount().compareTo(scanPayRequestBo.getOrderPrice()) != 0) {
                rpTradePaymentOrder.setOrderAmount(scanPayRequestBo.getOrderPrice());// 如果金额不一致,修改金额为最新的金额
            }
        }

        return getScanPayResultVo(rpTradePaymentOrder, payWay , scanPayRequestBo.getNumberOfStages());
    }

    /**
     * 条码支付,对应的是支付宝的条码支付或者微信的刷卡支付
     */
    @Override
    public F2FPayResultVo f2fPay(RpUserPayConfig rpUserPayConfig, F2FPayRequestBo f2FPayRequestBo) {

        // 根据支付产品及支付方式获取费率
        PayTypeEnum payType = PayTypeEnum.getEnum(f2FPayRequestBo.getPayType());

        if (payType == null){
            LOG.info("交易类型错误");
            throw new PayBizException(PayBizException.REQUEST_PARAM_ERR,"请求参数异常");
        }

        if (!PayTypeEnum.F2F_PAY.name().equals(payType.name()) && !PayTypeEnum.MICRO_PAY.name().equals(payType.name())){
            LOG.info("交易类型错误");
            throw new PayBizException(PayBizException.REQUEST_PARAM_ERR,"交易类型有误，不支持该交易");
        }

        PayWayEnum payWayEnum = PayWayEnum.getEnum(payType.getWay());
        String payWayCode = payWayEnum.name();

        RpPayWay payWay = rpPayWayService.getByPayWayTypeCode(rpUserPayConfig.getProductCode(), payWayCode, payType.name());

        if (payWay == null) {
            LOG.info("用户支付配置有误");
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        String merchantNo = rpUserPayConfig.getUserNo();// 商户编号
        RpUserInfo rpUserInfo = rpUserInfoService.getDataByMerchentNo(merchantNo);
        if (rpUserInfo == null) {
            throw new UserBizException(UserBizException.USER_IS_NULL, "用户不存在");
        }

        //根据商户号和订单号去查询订单是否存在
        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, f2FPayRequestBo.getOrderNo());
        if (rpTradePaymentOrder == null) {
            //订单不存在，创建订单
            rpTradePaymentOrder = sealF2FRpTradePaymentOrder( rpUserPayConfig ,  f2FPayRequestBo ,  rpUserInfo ,  payWay);
            rpTradePaymentOrderDao.insert(rpTradePaymentOrder);
        } else {
            //订单已存在，订单金额与传入金额不相等
            if (rpTradePaymentOrder.getOrderAmount().compareTo(f2FPayRequestBo.getOrderPrice()) != 0) {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "错误的订单");
            }
            //订单已存在，且订单状态为支付成功
            if (TradeStatusEnum.SUCCESS.name().equals(rpTradePaymentOrder.getStatus())) {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单已支付成功,无需重复支付");
            }
        }

        return getF2FPayResultVo(rpTradePaymentOrder, payWay, f2FPayRequestBo.getPayKey(), rpUserPayConfig.getPaySecret(), f2FPayRequestBo.getAuthCode(), null);
    }

    /**
     * 通过支付订单及商户费率生成支付记录
     *
     * @param rpTradePaymentOrder 支付订单
     * @param payWay              商户支付配置
     * @return
     */
    private F2FPayResultVo getF2FPayResultVo(RpTradePaymentOrder rpTradePaymentOrder, RpPayWay payWay, String payKey, String merchantPaySecret, String authCode, List<RoncooPayGoodsDetails> roncooPayGoodsDetailses) {

        F2FPayResultVo f2FPayResultVo = new F2FPayResultVo();
        String payWayCode = payWay.getPayWayCode();// 支付方式

        PayTypeEnum payType = null;
        if (PayWayEnum.WEIXIN.name().equals(payWay.getPayWayCode())) {
            payType = PayTypeEnum.MICRO_PAY;
        } else if (PayWayEnum.ALIPAY.name().equals(payWay.getPayWayCode())) {
            payType = PayTypeEnum.F2F_PAY;
        }

        rpTradePaymentOrder.setPayTypeCode(payType.name());// 支付类型
        rpTradePaymentOrder.setPayTypeName(payType.getDesc());// 支付方式
        rpTradePaymentOrder.setPayWayCode(payWay.getPayWayCode());//支付通道编号
        rpTradePaymentOrder.setPayWayName(payWay.getPayWayName());//支付通道名称

        //生成支付流水
        RpTradePaymentRecord rpTradePaymentRecord = sealRpTradePaymentRecord(rpTradePaymentOrder.getMerchantNo(), rpTradePaymentOrder.getMerchantName(), rpTradePaymentOrder.getProductName(), rpTradePaymentOrder.getMerchantOrderNo(), rpTradePaymentOrder.getOrderAmount(), payWay.getPayWayCode(), payWay.getPayWayName(), payType, rpTradePaymentOrder.getFundIntoType(), BigDecimal.valueOf(payWay.getPayRate()), rpTradePaymentOrder.getOrderIp(), rpTradePaymentOrder.getReturnUrl(), rpTradePaymentOrder.getNotifyUrl(), rpTradePaymentOrder.getRemark(), rpTradePaymentOrder.getField1(), rpTradePaymentOrder.getField2(), rpTradePaymentOrder.getField3(), rpTradePaymentOrder.getField4(), rpTradePaymentOrder.getField5());
        rpTradePaymentRecordDao.insert(rpTradePaymentRecord);

        if (PayWayEnum.WEIXIN.name().equals(payWayCode)) {// 微信支付
            RpUserPayInfo rpUserPayInfo = rpUserPayInfoService.getByUserNo(rpTradePaymentOrder.getMerchantNo(), payWayCode);
            if (rpUserPayInfo == null) {
                throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "商户支付配置有误");
            }
            Map<String, Object> wxResultMap = WeiXinPayUtil.micropay(rpTradePaymentRecord.getBankOrderNo(), rpTradePaymentOrder.getProductName(), rpTradePaymentRecord.getOrderAmount(), rpTradePaymentRecord.getOrderIp(), authCode);
            if (wxResultMap == null || wxResultMap.isEmpty()) {
                //返回结果为空，支付结果未知需要轮询
                rpNotifyService.orderSend(rpTradePaymentRecord.getBankOrderNo());
            } else {
                if ("YES".equals(wxResultMap.get("verify"))) {
                    //验签成功
                    if ("SUCCESS".equals(wxResultMap.get("return_code")) && "SUCCESS".equals(wxResultMap.get("result_code"))) {
                        //通讯成功且业务结果为成功
                        completeSuccessOrder(rpTradePaymentRecord, String.valueOf(wxResultMap.get("transaction_id")), new Date(), "支付成功");
                    } else if ("SUCCESS".equals(wxResultMap.get("return_code")) && !StringUtil.isEmpty(wxResultMap.get("err_code")) && !"BANKERROR".equals(wxResultMap.get("err_code")) && !"USERPAYING".equals(wxResultMap.get("err_code")) && !"SYSTEMERROR".equals(wxResultMap.get("err_code"))) {
                        //支付失败
                        completeFailOrder(rpTradePaymentRecord, String.valueOf(wxResultMap.get("err_code_des")));
                    } else {
                        //返回结果未知，需要轮询
                        rpNotifyService.orderSend(rpTradePaymentRecord.getBankOrderNo());
                    }
                } else {
                    completeFailOrder(rpTradePaymentRecord, "签名校验失败!");
                    //验签失败
                }
            }

        } else if (PayWayEnum.ALIPAY.name().equals(payWayCode)) {// 支付宝支付
            RpUserPayInfo rpUserPayInfo = rpUserPayInfoService.getByUserNo(rpTradePaymentOrder.getMerchantNo(), payWayCode);
            if (rpUserPayInfo == null) {
                throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "商户支付配置有误");
            }
            Map<String, Object> resultMap = AliPayUtil.tradePay(rpTradePaymentRecord.getBankOrderNo(), authCode, rpTradePaymentOrder.getProductName(), rpTradePaymentRecord.getOrderAmount(), "", roncooPayGoodsDetailses);
            //支付条码支付--统一根据订单轮询去确认支付结果
            rpNotifyService.orderSend(rpTradePaymentRecord.getBankOrderNo());
        } else {
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR, "错误的支付方式");
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        f2FPayResultVo.setStatus(rpTradePaymentRecord.getStatus());// 支付结果
        paramMap.put("status", rpTradePaymentRecord.getStatus());

        f2FPayResultVo.setOrderIp(rpTradePaymentRecord.getOrderIp());// 下单ip
        paramMap.put("orderIp", rpTradePaymentRecord.getOrderIp());

        f2FPayResultVo.setOrderNo(rpTradePaymentRecord.getMerchantOrderNo());// 商户订单号
        paramMap.put("merchantOrderNo", rpTradePaymentRecord.getMerchantOrderNo());

        f2FPayResultVo.setPayKey(payKey);// 支付号
        paramMap.put("payKey", payKey);

        f2FPayResultVo.setProductName(rpTradePaymentRecord.getProductName());// 产品名称
        paramMap.put("productName", rpTradePaymentRecord.getProductName());

        f2FPayResultVo.setRemark(rpTradePaymentRecord.getRemark());// 支付备注
        paramMap.put("remark", rpTradePaymentRecord.getRemark());

        f2FPayResultVo.setTrxNo(rpTradePaymentRecord.getTrxNo());// 交易流水号
        paramMap.put("trxNo", rpTradePaymentRecord.getTrxNo());

        String sign = MerchantApiUtil.getSign(paramMap, merchantPaySecret);

        f2FPayResultVo.setSign(sign);
        return f2FPayResultVo;
    }

    /**
     * 支付成功方法
     *
     * @param rpTradePaymentRecord
     */
    @Transactional(rollbackFor = Exception.class)
    void completeSuccessOrder(RpTradePaymentRecord rpTradePaymentRecord, String bankTrxNo, Date timeEnd, String bankReturnMsg) {
        LOG.info("订单支付成功!");
        rpTradePaymentRecord.setPaySuccessTime(timeEnd);
        rpTradePaymentRecord.setBankTrxNo(bankTrxNo);// 设置银行流水号
        rpTradePaymentRecord.setBankReturnMsg(bankReturnMsg);
        rpTradePaymentRecord.setStatus(TradeStatusEnum.SUCCESS.name());
        rpTradePaymentRecordDao.update(rpTradePaymentRecord);

        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(rpTradePaymentRecord.getMerchantNo(), rpTradePaymentRecord.getMerchantOrderNo());
        rpTradePaymentOrder.setStatus(TradeStatusEnum.SUCCESS.name());
        rpTradePaymentOrder.setTrxNo(rpTradePaymentRecord.getTrxNo());// 设置支付平台支付流水号
        rpTradePaymentOrderDao.update(rpTradePaymentOrder);

        if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(rpTradePaymentRecord.getFundIntoType())) {
            rpAccountTransactionService.creditToAccount(rpTradePaymentRecord.getMerchantNo(), rpTradePaymentRecord.getOrderAmount().subtract(rpTradePaymentRecord.getPlatIncome()), rpTradePaymentRecord.getBankOrderNo(), rpTradePaymentRecord.getBankTrxNo(), rpTradePaymentRecord.getTrxType(), rpTradePaymentRecord.getRemark());
        }

        if (PayTypeEnum.F2F_PAY.name().equals(rpTradePaymentOrder.getPayTypeCode())) {// 支付宝
            // 条码支付实时返回支付结果,不需要商户通知（修改后，条码支付结果通过订单轮询去确认订单状态，成功后通知商户）
            String notifyUrl = getMerchantNotifyUrl(rpTradePaymentRecord, rpTradePaymentOrder, rpTradePaymentRecord.getNotifyUrl(), TradeStatusEnum.SUCCESS);
            rpNotifyService.notifySend(notifyUrl, rpTradePaymentRecord.getMerchantOrderNo(), rpTradePaymentRecord.getMerchantNo());
            //return;
        } else {
            String notifyUrl = getMerchantNotifyUrl(rpTradePaymentRecord, rpTradePaymentOrder, rpTradePaymentRecord.getNotifyUrl(), TradeStatusEnum.SUCCESS);
            rpNotifyService.notifySend(notifyUrl, rpTradePaymentRecord.getMerchantOrderNo(), rpTradePaymentRecord.getMerchantNo());
        }
    }

    private String getMerchantNotifyUrl(RpTradePaymentRecord rpTradePaymentRecord, RpTradePaymentOrder rpTradePaymentOrder, String sourceUrl, TradeStatusEnum tradeStatusEnum) {

        RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByUserNo(rpTradePaymentRecord.getMerchantNo());
        if (rpUserPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        Map<String, Object> paramMap = new HashMap<>();

        String payKey = rpUserPayConfig.getPayKey();// 企业支付KEY
        paramMap.put("payKey", payKey);
        String productName = rpTradePaymentRecord.getProductName(); // 商品名称
        paramMap.put("productName", productName);
        String orderNo = rpTradePaymentRecord.getMerchantOrderNo(); // 订单编号
        paramMap.put("orderNo", orderNo);
        BigDecimal orderPrice = rpTradePaymentRecord.getOrderAmount(); // 订单金额 ,
        // 单位:元
        paramMap.put("orderPrice", orderPrice);
        String payWayCode = rpTradePaymentRecord.getPayWayCode(); // 支付方式编码 支付宝:
        // ALIPAY
        // 微信:WEIXIN
        paramMap.put("payWayCode", payWayCode);
        paramMap.put("tradeStatus", tradeStatusEnum);// 交易状态
        String orderDateStr = DateUtils.formatDate(rpTradePaymentOrder.getOrderDate(), "yyyyMMdd"); // 订单日期
        paramMap.put("orderDate", orderDateStr);
        String orderTimeStr = DateUtils.formatDate(rpTradePaymentOrder.getOrderTime(), "yyyyMMddHHmmss"); // 订单时间
        paramMap.put("orderTime", orderTimeStr);
        String remark = rpTradePaymentRecord.getRemark(); // 支付备注
        paramMap.put("remark", remark);
        String trxNo = rpTradePaymentRecord.getTrxNo();// 支付流水号
        paramMap.put("trxNo", trxNo);

        String paramStr = MerchantApiUtil.getParamStr(paramMap);
        String sign = MerchantApiUtil.getSign(paramMap, rpUserPayConfig.getPaySecret());
        String notifyUrl = sourceUrl + "?" + paramStr + "&sign=" + sign;

        return notifyUrl;
    }

    /**
     * 支付失败方法
     *
     * @param rpTradePaymentRecord
     */
    private void completeFailOrder(RpTradePaymentRecord rpTradePaymentRecord, String bankReturnMsg) {
        rpTradePaymentRecord.setBankReturnMsg(bankReturnMsg);
        rpTradePaymentRecord.setStatus(TradeStatusEnum.FAILED.name());
        rpTradePaymentRecordDao.update(rpTradePaymentRecord);

        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(rpTradePaymentRecord.getMerchantNo(), rpTradePaymentRecord.getMerchantOrderNo());
        rpTradePaymentOrder.setStatus(TradeStatusEnum.FAILED.name());
        rpTradePaymentOrderDao.update(rpTradePaymentOrder);

        String notifyUrl = getMerchantNotifyUrl(rpTradePaymentRecord, rpTradePaymentOrder, rpTradePaymentRecord.getNotifyUrl(), TradeStatusEnum.FAILED);
        rpNotifyService.notifySend(notifyUrl, rpTradePaymentRecord.getMerchantOrderNo(), rpTradePaymentRecord.getMerchantNo());
    }


    /**
     * 初始化非直连扫码支付数据,非直连扫码支付初始化方法规则
     *      1:根据(商户编号 + 商户订单号)确定订单是否存在
     *       1.1:如果订单不存在,创建支付订单
     *      2:获取商户支付配置,跳转到支付网关,选择支付方式
     * @param rpUserPayConfig
     * @param scanPayRequestBo
     * @return
     */
    @Override
    public RpPayGateWayPageShowVo initNonDirectScanPay(RpUserPayConfig rpUserPayConfig , ScanPayRequestBo scanPayRequestBo) {

        String merchantNo = rpUserPayConfig.getUserNo();// 商户编号
        RpUserInfo rpUserInfo = rpUserInfoService.getDataByMerchentNo(merchantNo);
        if (rpUserInfo == null) {
            throw new UserBizException(UserBizException.USER_IS_NULL, "用户不存在");
        }

        List<RpPayWay> payWayList = rpPayWayService.listByProductCode(rpUserPayConfig.getProductCode());
        if (payWayList == null || payWayList.size() <= 0) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "支付产品配置有误");
        }

        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, scanPayRequestBo.getOrderNo());
        if (rpTradePaymentOrder == null) {
            rpTradePaymentOrder = sealScanPayRpTradePaymentOrder( rpUserPayConfig ,  scanPayRequestBo ,  rpUserInfo);
            rpTradePaymentOrderDao.insert(rpTradePaymentOrder);
        } else {

            if (TradeStatusEnum.SUCCESS.name().equals(rpTradePaymentOrder.getStatus())) {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单已支付成功,无需重复支付");
            }

            if (rpTradePaymentOrder.getOrderAmount().compareTo(scanPayRequestBo.getOrderPrice()) != 0) {
                rpTradePaymentOrder.setOrderAmount(scanPayRequestBo.getOrderPrice());// 如果金额不一致,修改金额为最新的金额
                rpTradePaymentOrderDao.update(rpTradePaymentOrder);
            }
        }

        RpPayGateWayPageShowVo payGateWayPageShowVo = new RpPayGateWayPageShowVo();
        payGateWayPageShowVo.setProductName(rpTradePaymentOrder.getProductName());// 产品名称
        payGateWayPageShowVo.setMerchantName(rpTradePaymentOrder.getMerchantName());// 商户名称
        payGateWayPageShowVo.setOrderAmount(rpTradePaymentOrder.getOrderAmount());// 订单金额
        payGateWayPageShowVo.setMerchantOrderNo(rpTradePaymentOrder.getMerchantOrderNo());// 商户订单号
        payGateWayPageShowVo.setPayKey(scanPayRequestBo.getPayKey());// 商户支付key

        Map<String, PayTypeEnum> payTypeEnumMap = new HashMap<String, PayTypeEnum>();
        for (RpPayWay payWay : payWayList) {
            PayTypeEnum payTypeEnum = PayTypeEnum.getEnum(payWay.getPayTypeCode());
            if (PayTypeEnum.SCANPAY.name().equals(payTypeEnum.name()) || PayTypeEnum.DIRECT_PAY.name().equals(payTypeEnum.name()) || PayTypeEnum.HUA_BEI_FEN_QI_PAY.name().equals(payTypeEnum.name())){
                payTypeEnumMap.put(payWay.getPayWayCode(), payTypeEnum);
            }
        }

        payGateWayPageShowVo.setPayTypeEnumMap(payTypeEnumMap);

        return payGateWayPageShowVo;
    }

    /**
     * 非直连扫码支付,选择支付方式后,去支付
     *
     * @param payKey
     * @param orderNo
     * @param payType
     * @param numberOfStages
     * @return
     */
    @Override
    public ScanPayResultVo toNonDirectScanPay(String payKey, String orderNo, String payType , Integer numberOfStages) {

        RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByPayKey(payKey);
        if (rpUserPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }


        PayTypeEnum payTypeEnum = PayTypeEnum.getEnum(payType);
        if (payTypeEnum == null ){
            throw new PayBizException(PayBizException.REQUEST_PARAM_ERR , "请求参数异常");
        }
        // 根据支付产品及支付方式获取费率
        RpPayWay payWay = rpPayWayService.getByPayWayTypeCode(rpUserPayConfig.getProductCode(), payTypeEnum.getWay(), payTypeEnum.name());

        if (payWay == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        String merchantNo = rpUserPayConfig.getUserNo();// 商户编号
        RpUserInfo rpUserInfo = rpUserInfoService.getDataByMerchentNo(merchantNo);
        if (rpUserInfo == null) {
            throw new UserBizException(UserBizException.USER_IS_NULL, "用户不存在");
        }

        // 根据商户订单号获取订单信息
        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (rpTradePaymentOrder == null) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单不存在");
        }

        if (TradeStatusEnum.SUCCESS.name().equals(rpTradePaymentOrder.getStatus())) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单已支付成功,无需重复支付");
        }

        return getScanPayResultVo(rpTradePaymentOrder, payWay ,numberOfStages);

    }

    /**
     * 通过支付订单及商户费率生成支付记录
     *
     * @param rpTradePaymentOrder 支付订单
     * @param payWay              商户支付配置
     * @return
     */
    private ScanPayResultVo getScanPayResultVo(RpTradePaymentOrder rpTradePaymentOrder, RpPayWay payWay ,Integer numberOfStages) {

        ScanPayResultVo scanPayResultVo = new ScanPayResultVo();
        String payWayCode = payWay.getPayWayCode();// 支付方式

        PayTypeEnum payType = PayTypeEnum.getEnum(payWay.getPayTypeCode());

        rpTradePaymentOrder.setPayTypeCode(payType.name());
        rpTradePaymentOrder.setPayTypeName(payType.getDesc());
        rpTradePaymentOrder.setPayWayCode(payWay.getPayWayCode());
        rpTradePaymentOrder.setPayWayName(payWay.getPayWayName());
        rpTradePaymentOrderDao.update(rpTradePaymentOrder);

        RpTradePaymentRecord rpTradePaymentRecord = sealRpTradePaymentRecord(rpTradePaymentOrder.getMerchantNo(), rpTradePaymentOrder.getMerchantName(), rpTradePaymentOrder.getProductName(), rpTradePaymentOrder.getMerchantOrderNo(), rpTradePaymentOrder.getOrderAmount(), payWay.getPayWayCode(), payWay.getPayWayName(), payType, rpTradePaymentOrder.getFundIntoType(), BigDecimal.valueOf(payWay.getPayRate()), rpTradePaymentOrder.getOrderIp(), rpTradePaymentOrder.getReturnUrl(), rpTradePaymentOrder.getNotifyUrl(), rpTradePaymentOrder.getRemark(), rpTradePaymentOrder.getField1(), rpTradePaymentOrder.getField2(), rpTradePaymentOrder.getField3(), rpTradePaymentOrder.getField4(), rpTradePaymentOrder.getField5());
        rpTradePaymentRecordDao.insert(rpTradePaymentRecord);

        if (PayWayEnum.WEIXIN.name().equals(payWayCode)) {// 微信支付
            String appid = "";
            String mch_id = "";
            String partnerKey = "";
            if (FundInfoTypeEnum.MERCHANT_RECEIVES.name().equals(rpTradePaymentOrder.getFundIntoType())) {// 商户收款
                // 根据资金流向获取配置信息
                RpUserPayInfo rpUserPayInfo = rpUserPayInfoService.getByUserNo(rpTradePaymentOrder.getMerchantNo(), payWayCode);
                appid = rpUserPayInfo.getAppId();
                mch_id = rpUserPayInfo.getMerchantId();
                partnerKey = rpUserPayInfo.getPartnerKey();
            } else if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(rpTradePaymentOrder.getFundIntoType())) {// 平台收款
                appid = WeixinConfigUtil.readConfig("appId");
                mch_id = WeixinConfigUtil.readConfig("mch_id");
                partnerKey = WeixinConfigUtil.readConfig("partnerKey");
            }

            WeiXinPrePay weiXinPrePay = sealWeixinPerPay(appid, mch_id, rpTradePaymentOrder.getProductName(), rpTradePaymentOrder.getRemark(), rpTradePaymentRecord.getBankOrderNo(), rpTradePaymentOrder.getOrderAmount(), rpTradePaymentOrder.getOrderTime(), rpTradePaymentOrder.getOrderPeriod(), WeiXinTradeTypeEnum.NATIVE, rpTradePaymentRecord.getBankOrderNo(), "", rpTradePaymentOrder.getOrderIp());
            String prePayXml = WeiXinPayUtils.getPrePayXml(weiXinPrePay, partnerKey);
            LOG.info("扫码支付，微信请求报文:{}", prePayXml);
            // 调用微信支付的功能,获取微信支付code_url
            Map<String, Object> prePayRequest = WeiXinPayUtils.httpXmlRequest(WeixinConfigUtil.readConfig("prepay_url"), "POST", prePayXml);
            LOG.info("扫码支付，微信返回报文:{}", prePayRequest.toString());
            if (WeixinTradeStateEnum.SUCCESS.name().equals(prePayRequest.get("return_code")) && WeixinTradeStateEnum.SUCCESS.name().equals(prePayRequest.get("result_code"))) {
                String weiXinPrePaySign = WeiXinPayUtils.geWeiXintPrePaySign(appid, mch_id, weiXinPrePay.getDeviceInfo(), WeiXinTradeTypeEnum.NATIVE.name(), prePayRequest, partnerKey);
                String codeUrl = String.valueOf(prePayRequest.get("code_url"));
                LOG.info("预支付生成成功,{}", codeUrl);
                if (prePayRequest.get("sign").equals(weiXinPrePaySign)) {
                    rpTradePaymentRecord.setBankReturnMsg(prePayRequest.toString());
                    rpTradePaymentRecordDao.update(rpTradePaymentRecord);
                    scanPayResultVo.setCodeUrl(codeUrl);// 设置微信跳转地址
                    scanPayResultVo.setPayWayCode(PayWayEnum.WEIXIN.name());
                    scanPayResultVo.setProductName(rpTradePaymentOrder.getProductName());
                    scanPayResultVo.setOrderAmount(rpTradePaymentOrder.getOrderAmount());
                } else {
                    throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR, "微信返回结果签名异常");
                }
            } else {
                throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR, "请求微信异常");
            }
        } else if (PayWayEnum.ALIPAY.name().equals(payWayCode)) {// 支付宝支付

            String appId = "";
            String mchPrivateKey = "";

            if (FundInfoTypeEnum.MERCHANT_RECEIVES.name().equals(rpTradePaymentOrder.getFundIntoType())) {// 商户收款
                RpUserPayInfo rpUserPayInfo = rpUserPayInfoService.getByUserNo(rpTradePaymentOrder.getMerchantNo(), payWayCode);
                appId = rpUserPayInfo.getOfflineAppId();
                mchPrivateKey = rpUserPayInfo.getRsaPrivateKey();
            }else if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(rpTradePaymentOrder.getFundIntoType())) {// 平台收款
                appId = AlipayConfigUtil.app_id;
                mchPrivateKey = AlipayConfigUtil.mch_private_key;
            }

            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfigUtil.trade_pay_url, appId, mchPrivateKey, "json", "utf-8", AlipayConfigUtil.ali_public_key, "RSA2");

            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setNotifyUrl(AlipayConfigUtil.notify_url);
            alipayRequest.setReturnUrl(AlipayConfigUtil.return_url);

            if (PayTypeEnum.DIRECT_PAY.name().equals(payType.name())){

                alipayRequest.setBizContent("{\"out_trade_no\":\""+ rpTradePaymentRecord.getBankOrderNo() +"\","
                        + "\"total_amount\":\""+ rpTradePaymentOrder.getOrderAmount() +"\","
                        + "\"subject\":\""+ rpTradePaymentOrder.getProductName() +"\","
                        + "\"body\":\""+ rpTradePaymentOrder.getProductName() +"\","
                        + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

            }else if (PayTypeEnum.HUA_BEI_FEN_QI_PAY.name().equals(payType.name())){

                alipayRequest.setBizContent("{\"out_trade_no\":\""+ rpTradePaymentRecord.getBankOrderNo() +"\","
                        + "\"total_amount\":\""+ rpTradePaymentOrder.getOrderAmount() +"\","
                        + "\"subject\":\""+ rpTradePaymentOrder.getProductName() +"\","
                        + "\"body\":\""+ rpTradePaymentOrder.getProductName() +"\","
                        + "\"extend_params\":{\"hb_fq_num\":\""+numberOfStages+"\",\"hb_fq_seller_percent\":\""+0+"\"},"
                        + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            }

            try {
                String alipayTradePagePayResponse = alipayClient.pageExecute(alipayRequest).getBody();
                rpTradePaymentRecord.setBankReturnMsg(alipayTradePagePayResponse);
                rpTradePaymentRecordDao.update(rpTradePaymentRecord);
                scanPayResultVo.setCodeUrl(alipayTradePagePayResponse);// 设置支付宝跳转地址
                scanPayResultVo.setPayWayCode(PayWayEnum.ALIPAY.name());
                scanPayResultVo.setProductName(rpTradePaymentOrder.getProductName());
                scanPayResultVo.setOrderAmount(rpTradePaymentOrder.getOrderAmount());

            } catch (AlipayApiException e) {
                LOG.error("支付宝API异常：" , e);
                throw new PayBizException(PayBizException.REQUEST_BANK_ERR , "请求支付宝异常");
            }

        } else {
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR, "错误的支付方式");
        }
        rpNotifyService.orderSend(rpTradePaymentRecord.getBankOrderNo());
        return scanPayResultVo;
    }

    /**
     * 完成扫码支付(支付宝即时到账支付)
     *
     * @param payWayCode
     * @param notifyMap
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String completeScanPay(String payWayCode, Map<String, String> notifyMap) {
        LOG.info("接收到{}支付结果{}", payWayCode, notifyMap);

        String returnStr = null;
        String bankOrderNo = notifyMap.get("out_trade_no");
        // 根据银行订单号获取支付信息
        RpTradePaymentRecord rpTradePaymentRecord = rpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
        if (rpTradePaymentRecord == null) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, ",非法订单,订单不存在");
        }

        if (TradeStatusEnum.SUCCESS.name().equals(rpTradePaymentRecord.getStatus())) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单为成功状态");
        }
        String merchantNo = rpTradePaymentRecord.getMerchantNo();// 商户编号

        // 根据支付订单获取配置信息
        String fundIntoType = rpTradePaymentRecord.getFundIntoType();// 获取资金流入类型
        String partnerKey = "";

        if (FundInfoTypeEnum.MERCHANT_RECEIVES.name().equals(fundIntoType)) {// 商户收款
            // 根据资金流向获取配置信息
            RpUserPayInfo rpUserPayInfo = rpUserPayInfoService.getByUserNo(merchantNo, PayWayEnum.WEIXIN.name());
            partnerKey = rpUserPayInfo.getPartnerKey();

        } else if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(fundIntoType)) {// 平台收款
            partnerKey = WeixinConfigUtil.readConfig("partnerKey");

            RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByUserNo(merchantNo);
            if (rpUserPayConfig == null) {
                throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
            }
            // 根据支付产品及支付方式获取费率
            RpPayWay payWay = rpPayWayService.getByPayWayTypeCode(rpUserPayConfig.getProductCode(), rpTradePaymentRecord.getPayWayCode(), rpTradePaymentRecord.getPayTypeCode());
            if (payWay == null) {
                throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
            }
        }

        if (PayWayEnum.WEIXIN.name().equals(payWayCode)) {
            String sign = notifyMap.remove("sign");
            if (WeiXinPayUtils.notifySign(notifyMap, sign, partnerKey)) {// 根据配置信息验证签名
                if (WeixinTradeStateEnum.SUCCESS.name().equals(notifyMap.get("result_code"))) {// 业务结果
                    // 成功
                    String timeEndStr = notifyMap.get("time_end");
                    Date timeEnd = null;
                    if (!StringUtil.isEmpty(timeEndStr)) {
                        timeEnd = DateUtils.getDateFromString(timeEndStr, "yyyyMMddHHmmss");// 订单支付完成时间
                    }
                    completeSuccessOrder(rpTradePaymentRecord, notifyMap.get("transaction_id"), timeEnd, notifyMap.toString());
                    returnStr = "<xml>\n" + "  <return_code><![CDATA[SUCCESS]]></return_code>\n" + "  <return_msg><![CDATA[OK]]></return_msg>\n" + "</xml>";
                } else {
                    completeFailOrder(rpTradePaymentRecord, notifyMap.toString());
                }
            } else {
                throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR, "微信签名失败");
            }

        } else if ("WEIXIN_PROGRAM".equals(payWayCode)) {

            String sign = notifyMap.remove("sign");
            if (WeiXinPayUtils.notifySign(notifyMap, sign, WeixinConfigUtil.xPayKey)) {// 根据配置信息验证签名
                if (WeixinTradeStateEnum.SUCCESS.name().equals(notifyMap.get("result_code"))) {// 业务结果
                    // 成功
                    String timeEndStr = notifyMap.get("time_end");
                    Date timeEnd = null;
                    if (!StringUtil.isEmpty(timeEndStr)) {
                        timeEnd = DateUtils.getDateFromString(timeEndStr, "yyyyMMddHHmmss");// 订单支付完成时间
                    }
                    completeSuccessOrder(rpTradePaymentRecord, notifyMap.get("transaction_id"), timeEnd, notifyMap.toString());
                    returnStr = "<xml>\n" + "  <return_code><![CDATA[SUCCESS]]></return_code>\n" + "  <return_msg><![CDATA[OK]]></return_msg>\n" + "</xml>";
                } else {
                    completeFailOrder(rpTradePaymentRecord, notifyMap.toString());
                }
            } else {
                throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR, "微信签名失败");
            }


        } else if (PayWayEnum.ALIPAY.name().equals(payWayCode)) {
//            if (AlipayNotify.verify(notifyMap)) {// 验证成功
            try {
                if (AlipaySignature.rsaCheckV1(notifyMap, AlipayConfigUtil.ali_public_key, "UTF-8", "RSA2")){
                    String tradeStatus = notifyMap.get("trade_status");

                    if (AliPayTradeStateEnum.TRADE_FINISHED.name().equals(tradeStatus)) {
                        // 判断该笔订单是否在商户网站中已经做过处理
                        // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                        // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                        // 如果有做过处理，不执行商户的业务程序

                        // 注意：
                        // 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                    } else if (AliPayTradeStateEnum.TRADE_SUCCESS.name().equals(tradeStatus)) {

                        String gmtPaymentStr = notifyMap.get("gmt_payment");// 付款时间
                        Date timeEnd = null;
                        if (!StringUtil.isEmpty(gmtPaymentStr)) {
                            timeEnd = DateUtils.getDateFromString(gmtPaymentStr, "yyyy-MM-dd HH:mm:ss");
                        }
                        completeSuccessOrder(rpTradePaymentRecord, notifyMap.get("trade_no"), timeEnd, notifyMap.toString());
                        returnStr = "success";
                    } else {
                        completeFailOrder(rpTradePaymentRecord, notifyMap.toString());
                        returnStr = "fail";
                    }
                } else {// 验证失败
                    throw new TradeBizException(TradeBizException.TRADE_ALIPAY_ERROR, "支付宝签名异常");
                }
            } catch (AlipayApiException e) {
                LOG.error("验签失败：" , e);
                throw new TradeBizException(TradeBizException.TRADE_ALIPAY_ERROR, "支付宝签名异常");
            }
        } else {
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR, "错误的支付方式");
        }

        LOG.info("返回支付通道{}信息{}", payWayCode, returnStr);
        return returnStr;
    }

    /**
     * 支付成功后,又是会出现页面通知早与后台通知 现页面通知,暂时不做数据处理功能,只生成页面通知URL
     *
     * @param payWayCode
     * @param resultMap
     * @return
     */
    @Override
    public OrderPayResultVo completeScanPayByResult(String payWayCode, Map<String, String> resultMap) {

        OrderPayResultVo orderPayResultVo = new OrderPayResultVo();

        String bankOrderNo = resultMap.get("out_trade_no");
        // 根据银行订单号获取支付信息
        RpTradePaymentRecord rpTradePaymentRecord = rpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
        if (rpTradePaymentRecord == null) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, ",非法订单,订单不存在");
        }

        orderPayResultVo.setOrderPrice(rpTradePaymentRecord.getOrderAmount());// 订单金额
        orderPayResultVo.setProductName(rpTradePaymentRecord.getProductName());// 产品名称

        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(rpTradePaymentRecord.getMerchantNo(), rpTradePaymentRecord.getMerchantOrderNo());

        // 计算得出通知验证结果
        boolean verify_result = false;

        try {
            verify_result = AlipaySignature.rsaCheckV1(resultMap, AlipayConfigUtil.ali_public_key, "UTF-8", "RSA2");
        } catch (AlipayApiException e) {
            LOG.error("签名异常：" , e);
        }

        if (verify_result) {// 验证成功

            TradeStatusEnum tradeStatusEnum = TradeStatusEnum.getEnum(rpTradePaymentOrder.getStatus());

                String resultUrl = getMerchantNotifyUrl(rpTradePaymentRecord, rpTradePaymentOrder, rpTradePaymentRecord.getReturnUrl(), tradeStatusEnum);
                orderPayResultVo.setReturnUrl(resultUrl);
                orderPayResultVo.setStatus(tradeStatusEnum.name());

        } else {
            throw new TradeBizException(TradeBizException.TRADE_ALIPAY_ERROR, "支付宝签名异常");
        }
        return orderPayResultVo;
    }

    /**
     * 封装主扫扫码付交易订单
     * @param rpUserPayConfig
     * @param scanPayRequestBo
     * @param rpUserInfo
     * @return
     */
    private RpTradePaymentOrder sealScanPayRpTradePaymentOrder(RpUserPayConfig rpUserPayConfig , ScanPayRequestBo scanPayRequestBo , RpUserInfo rpUserInfo){

        RpTradePaymentOrder rpTradePaymentOrder = new RpTradePaymentOrder();
        rpTradePaymentOrder.setProductName(scanPayRequestBo.getProductName());// 商品名称

        rpTradePaymentOrder.setMerchantOrderNo(scanPayRequestBo.getOrderNo());// 订单号

        rpTradePaymentOrder.setOrderAmount(scanPayRequestBo.getOrderPrice());// 订单金额

        rpTradePaymentOrder.setMerchantName(rpUserInfo.getUserName());// 商户名称

        rpTradePaymentOrder.setMerchantNo(rpUserInfo.getUserNo());// 商户编号

        rpTradePaymentOrder.setOrderDate(DateUtils.parseDate(scanPayRequestBo.getOrderDate(), "yyyyMMdd"));// 下单日期
        Date orderTime = DateUtils.parseDate(scanPayRequestBo.getOrderTime(), "yyyyMMddHHmmss");

        rpTradePaymentOrder.setOrderTime(orderTime);// 下单时间
        rpTradePaymentOrder.setOrderIp(scanPayRequestBo.getOrderIp());// 下单IP
        rpTradePaymentOrder.setOrderRefererUrl("");// 下单前页面
        rpTradePaymentOrder.setReturnUrl(scanPayRequestBo.getReturnUrl());// 页面通知地址
        rpTradePaymentOrder.setNotifyUrl(scanPayRequestBo.getNotifyUrl());// 后台通知地址

        rpTradePaymentOrder.setOrderPeriod(scanPayRequestBo.getOrderPeriod());// 订单有效期

        Date expireTime = DateUtils.addMinute(orderTime, scanPayRequestBo.getOrderPeriod());// 订单过期时间
        rpTradePaymentOrder.setExpireTime(expireTime);// 订单过期时间
        rpTradePaymentOrder.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());// 订单状态
        // 等待支付

        PayTypeEnum payType = PayTypeEnum.getEnum(scanPayRequestBo.getPayType());
        if (payType != null){

            rpTradePaymentOrder.setPayTypeCode(payType.name());// 支付类型
            rpTradePaymentOrder.setPayTypeName(payType.getDesc());// 支付方式

            PayWayEnum payWayEnum = PayWayEnum.getEnum(payType.getWay());
            rpTradePaymentOrder.setPayWayCode(payWayEnum.name());// 支付通道编码
            rpTradePaymentOrder.setPayWayName(payWayEnum.getDesc());// 支付通道名称
        }


        rpTradePaymentOrder.setFundIntoType(rpUserPayConfig.getFundIntoType());// 资金流入方向
        rpTradePaymentOrder.setRemark(scanPayRequestBo.getRemark());// 支付备注

        return rpTradePaymentOrder;
    }


    /**
     * 支付订单实体封装
     *
     * @param merchantNo   商户编号
     * @param merchantName 商户名称
     * @param productName  产品名称
     * @param orderNo      商户订单号
     * @param orderDate    下单日期
     * @param orderTime    下单时间
     * @param orderPrice   订单金额
     * @param payWay       支付方式
     * @param payWayName   支付方式名称
     * @param payType      支付类型
     * @param fundIntoType 资金流入类型
     * @param orderIp      下单IP
     * @param orderPeriod  订单有效期
     * @param returnUrl    页面通知地址
     * @param notifyUrl    后台通知地址
     * @param remark       支付备注
     * @param field1       扩展字段1
     * @param field2       扩展字段2
     * @param field3       扩展字段3
     * @param field4       扩展字段4
     * @param field5       扩展字段5
     * @return
     */
    private RpTradePaymentOrder sealRpTradePaymentOrder(String merchantNo, String merchantName, String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String payWay, String payWayName, PayTypeEnum payType, String fundIntoType, String orderIp, Integer orderPeriod, String returnUrl, String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5) {

    return null;
    }

    /**
     * 封装小程序支付订单
     * @param rpUserPayConfig
     * @param programPayRequestBo
     * @param rpUserInfo
     * @param rpPayWay
     * @return
     */
    private RpTradePaymentOrder sealProgramRpTradePaymentOrder(RpUserPayConfig rpUserPayConfig , ProgramPayRequestBo programPayRequestBo , RpUserInfo rpUserInfo , RpPayWay rpPayWay){
        RpTradePaymentOrder rpTradePaymentOrder = new RpTradePaymentOrder();
        rpTradePaymentOrder.setProductName(programPayRequestBo.getProductName());// 商品名称
        rpTradePaymentOrder.setMerchantOrderNo(programPayRequestBo.getOrderNo());// 订单号
        rpTradePaymentOrder.setOrderAmount(programPayRequestBo.getOrderPrice());// 订单金额
        rpTradePaymentOrder.setMerchantName(rpUserInfo.getUserName());// 商户名称
        rpTradePaymentOrder.setMerchantNo(rpUserInfo.getUserNo());// 商户编号

        Date orderDate = DateUtils.parseDate(programPayRequestBo.getOrderDate(), "yyyyMMdd");
        Date orderTime = DateUtils.parseDate(programPayRequestBo.getOrderTime(), "yyyyMMddHHmmss");
        rpTradePaymentOrder.setOrderDate(orderDate);// 下单日期
        rpTradePaymentOrder.setOrderTime(orderTime);// 下单时间

        rpTradePaymentOrder.setOrderIp(programPayRequestBo.getOrderIp());// 下单IP
        rpTradePaymentOrder.setOrderRefererUrl("");// 下单前页面
        rpTradePaymentOrder.setReturnUrl("");// 页面通知地址

        rpTradePaymentOrder.setNotifyUrl(programPayRequestBo.getNotifyUrl());// 后台通知地址
        rpTradePaymentOrder.setOrderPeriod(0);// 订单有效期
        rpTradePaymentOrder.setExpireTime(new Date());// 订单过期时间

        rpTradePaymentOrder.setPayWayCode(rpPayWay.getPayWayCode());// 支付通道编码
        rpTradePaymentOrder.setPayWayName(rpPayWay.getPayWayName());// 支付通道名称
        rpTradePaymentOrder.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());// 订单状态
        // 等待支付

        rpTradePaymentOrder.setPayTypeCode(rpPayWay.getPayTypeCode());// 支付类型
        rpTradePaymentOrder.setPayTypeName(rpPayWay.getPayTypeName());// 支付方式
        rpTradePaymentOrder.setFundIntoType(rpUserPayConfig.getFundIntoType());// 资金流入方向

        rpTradePaymentOrder.setRemark(programPayRequestBo.getRemark());// 支付备注

        return rpTradePaymentOrder;
    }

    /**
     * 支付订单实体封装
     *
     * @return
     */
    private RpTradePaymentOrder sealF2FRpTradePaymentOrder(RpUserPayConfig rpUserPayConfig , F2FPayRequestBo f2FPayRequestBo , RpUserInfo rpUserInfo , RpPayWay rpPayWay) {

        RpTradePaymentOrder rpTradePaymentOrder = new RpTradePaymentOrder();
        rpTradePaymentOrder.setProductName(f2FPayRequestBo.getProductName());// 商品名称
        rpTradePaymentOrder.setMerchantOrderNo(f2FPayRequestBo.getOrderNo());// 订单号
        rpTradePaymentOrder.setOrderAmount(f2FPayRequestBo.getOrderPrice());// 订单金额
        rpTradePaymentOrder.setMerchantName(rpUserInfo.getUserName());// 商户名称
        rpTradePaymentOrder.setMerchantNo(rpUserInfo.getUserNo());// 商户编号
        Date orderDate = DateUtils.parseDate(f2FPayRequestBo.getOrderDate(), "yyyyMMdd");
        Date orderTime = DateUtils.parseDate(f2FPayRequestBo.getOrderTime(), "yyyyMMddHHmmss");
        rpTradePaymentOrder.setOrderDate(orderDate);// 下单日期
        rpTradePaymentOrder.setOrderTime(orderTime);// 下单时间
        rpTradePaymentOrder.setOrderIp(f2FPayRequestBo.getOrderIp());// 下单IP
        rpTradePaymentOrder.setOrderRefererUrl("");// 下单前页面
        rpTradePaymentOrder.setReturnUrl("");// 页面通知地址
        rpTradePaymentOrder.setNotifyUrl("");// 后台通知地址
        rpTradePaymentOrder.setOrderPeriod(0);// 订单有效期
        rpTradePaymentOrder.setExpireTime(new Date());// 订单过期时间
        rpTradePaymentOrder.setPayWayCode(rpPayWay.getPayWayCode());// 支付通道编码
        rpTradePaymentOrder.setPayWayName(rpPayWay.getPayWayName());// 支付通道名称
        rpTradePaymentOrder.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());// 订单状态
        rpTradePaymentOrder.setPayTypeCode(rpPayWay.getPayTypeCode());// 支付类型
        rpTradePaymentOrder.setPayTypeName(rpPayWay.getPayTypeName());// 支付方式
        rpTradePaymentOrder.setFundIntoType(rpUserPayConfig.getFundIntoType());// 资金流入方向
        rpTradePaymentOrder.setRemark(f2FPayRequestBo.getRemark());// 支付备注

        return rpTradePaymentOrder;
    }

    /**
     * 封装支付流水记录实体
     *
     * @param merchantNo   商户编号
     * @param merchantName 商户名称
     * @param productName  产品名称
     * @param orderNo      商户订单号
     * @param orderPrice   订单金额
     * @param payWay       支付方式编码
     * @param payWayName   支付方式名称
     * @param payType      支付类型
     * @param fundIntoType 资金流入方向
     * @param feeRate      支付费率
     * @param orderIp      订单IP
     * @param returnUrl    页面通知地址
     * @param notifyUrl    后台通知地址
     * @param remark       备注
     * @param field1       扩展字段1
     * @param field2       扩展字段2
     * @param field3       扩展字段3
     * @param field4       扩展字段4
     * @param field5       扩展字段5
     * @return
     */
    private RpTradePaymentRecord sealRpTradePaymentRecord(String merchantNo, String merchantName, String productName, String orderNo, BigDecimal orderPrice, String payWay, String payWayName, PayTypeEnum payType, String fundIntoType, BigDecimal feeRate, String orderIp, String returnUrl, String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5) {
        RpTradePaymentRecord rpTradePaymentRecord = new RpTradePaymentRecord();
        rpTradePaymentRecord.setProductName(productName);// 产品名称
        rpTradePaymentRecord.setMerchantOrderNo(orderNo);// 产品编号

        String trxNo = buildNoService.buildTrxNo();
        rpTradePaymentRecord.setTrxNo(trxNo);// 支付流水号

        String bankOrderNo = buildNoService.buildBankOrderNo();
        rpTradePaymentRecord.setBankOrderNo(bankOrderNo);// 银行订单号
        rpTradePaymentRecord.setMerchantName(merchantName);
        rpTradePaymentRecord.setMerchantNo(merchantNo);// 商户编号
        rpTradePaymentRecord.setOrderIp(orderIp);// 下单IP
        rpTradePaymentRecord.setOrderRefererUrl("");// 下单前页面
        rpTradePaymentRecord.setReturnUrl(returnUrl);// 页面通知地址
        rpTradePaymentRecord.setNotifyUrl(notifyUrl);// 后台通知地址
        rpTradePaymentRecord.setPayWayCode(payWay);// 支付通道编码
        rpTradePaymentRecord.setPayWayName(payWayName);// 支付通道名称
        rpTradePaymentRecord.setTrxType(TrxTypeEnum.EXPENSE.name());// 交易类型
        rpTradePaymentRecord.setOrderFrom(OrderFromEnum.USER_EXPENSE.name());// 订单来源
        rpTradePaymentRecord.setOrderAmount(orderPrice);// 订单金额
        rpTradePaymentRecord.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());// 订单状态
        // 等待支付

        rpTradePaymentRecord.setPayTypeCode(payType.name());// 支付类型
        rpTradePaymentRecord.setPayTypeName(payType.getDesc());// 支付方式
        rpTradePaymentRecord.setFundIntoType(fundIntoType);// 资金流入方向

        if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(fundIntoType)) {// 平台收款
            // 需要修改费率
            // 成本
            // 利润
            // 收入
            // 以及修改商户账户信息
            BigDecimal orderAmount = rpTradePaymentRecord.getOrderAmount();// 订单金额
            BigDecimal platIncome = orderAmount.multiply(feeRate).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP); // 平台收入
            // =
            // 订单金额
            // *
            // 支付费率(设置的费率除以100为真实费率)
            BigDecimal platCost = orderAmount.multiply(BigDecimal.valueOf(Double.valueOf(WeixinConfigUtil.readConfig("pay_rate")))).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);// 平台成本
            // =
            // 订单金额
            // *
            // 微信费率(设置的费率除以100为真实费率)
            BigDecimal platProfit = platIncome.subtract(platCost);// 平台利润 = 平台收入
            // - 平台成本

            rpTradePaymentRecord.setFeeRate(feeRate);// 费率
            rpTradePaymentRecord.setPlatCost(platCost);// 平台成本
            rpTradePaymentRecord.setPlatIncome(platIncome);// 平台收入
            rpTradePaymentRecord.setPlatProfit(platProfit);// 平台利润

        }

        rpTradePaymentRecord.setRemark(remark);// 支付备注
        rpTradePaymentRecord.setField1(field1);// 扩展字段1
        rpTradePaymentRecord.setField2(field2);// 扩展字段2
        rpTradePaymentRecord.setField3(field3);// 扩展字段3
        rpTradePaymentRecord.setField4(field4);// 扩展字段4
        rpTradePaymentRecord.setField5(field5);// 扩展字段5
        return rpTradePaymentRecord;
    }

    /**
     * 封装预支付实体
     *
     * @param appId               公众号ID
     * @param mchId               商户号
     * @param productName         商品描述
     * @param remark              支付备注
     * @param bankOrderNo         银行订单号
     * @param orderPrice          订单价格
     * @param orderTime           订单下单时间
     * @param orderPeriod         订单有效期
     * @param weiXinTradeTypeEnum 微信支付方式
     * @param productId           商品ID
     * @param openId              用户标识
     * @param orderIp             下单IP
     * @return
     */
    private WeiXinPrePay sealWeixinPerPay(String appId, String mchId, String productName, String remark, String bankOrderNo, BigDecimal orderPrice, Date orderTime, Integer orderPeriod, WeiXinTradeTypeEnum weiXinTradeTypeEnum, String productId, String openId, String orderIp) {
        WeiXinPrePay weiXinPrePay = new WeiXinPrePay();

        weiXinPrePay.setAppid(appId);
        weiXinPrePay.setMchId(mchId);
        weiXinPrePay.setBody(productName);// 商品描述
        weiXinPrePay.setAttach(remark);// 支付备注
        weiXinPrePay.setOutTradeNo(bankOrderNo);// 银行订单号

        Integer totalFee = orderPrice.multiply(BigDecimal.valueOf(100d)).intValue();
        weiXinPrePay.setTotalFee(totalFee);// 订单金额
        weiXinPrePay.setTimeStart(DateUtils.formatDate(orderTime, "yyyyMMddHHmmss"));// 订单开始时间
        weiXinPrePay.setTimeExpire(DateUtils.formatDate(DateUtils.addMinute(orderTime, orderPeriod), "yyyyMMddHHmmss"));// 订单到期时间
        weiXinPrePay.setNotifyUrl(WeixinConfigUtil.readConfig("notify_url"));// 通知地址
        weiXinPrePay.setTradeType(weiXinTradeTypeEnum);// 交易类型
        weiXinPrePay.setProductId(productId);// 商品ID
        weiXinPrePay.setOpenid(openId);// 用户标识
        weiXinPrePay.setSpbillCreateIp(orderIp);// 下单IP

        return weiXinPrePay;
    }

    /**
     * 处理交易记录 如果交易记录是成功或者本地未支付,查询上游已支付,返回TRUE 如果上游支付结果为未支付,返回FALSE
     *
     * @param bankOrderNo 银行订单号
     * @return
     */
    @Override
    public boolean processingTradeRecord(String bankOrderNo) {
        RpTradePaymentRecord byBankOrderNo = rpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
        if (byBankOrderNo == null) {
            LOG.info("不存在该银行订单号[{}]对应的交易记录", bankOrderNo);
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "非法订单号");
        }
        LOG.info("订单号:[{}],交易类型：[{}]", byBankOrderNo.getBankOrderNo(), byBankOrderNo.getPayWayCode());

        if (!TradeStatusEnum.WAITING_PAYMENT.name().equals(byBankOrderNo.getStatus())) {
            LOG.info("该银行订单号[{}]对应的交易记录状态为:{},不需要再处理", bankOrderNo, byBankOrderNo.getStatus());
            return true;
        }

        // 微信
        if (byBankOrderNo.getPayWayCode().equals(PayWayEnum.WEIXIN.name())) {
            Map<String, Object> resultMap;
            if (PayTypeEnum.WX_PROGRAM_PAY.name().equals(byBankOrderNo.getPayTypeCode())) {
                LOG.info("微信--小程序订单查询!订单号:[{}]", byBankOrderNo.getBankOrderNo());
                resultMap = WeiXinPayUtils.orderQuery(byBankOrderNo.getBankOrderNo(), WeixinConfigUtil.xAppId, WeixinConfigUtil.xMchId, WeixinConfigUtil.xPayKey);
            } else {
                LOG.info("微信--订单查询!订单号:[{}]", byBankOrderNo.getBankOrderNo());
                resultMap = WeiXinPayUtils.orderQuery(byBankOrderNo.getBankOrderNo(), WeixinConfigUtil.appId, WeixinConfigUtil.mch_id, WeixinConfigUtil.partnerKey);
            }
            LOG.info("微信订单查询结果:{}", resultMap.toString());
            if (resultMap == null || resultMap.isEmpty()) {
                return false;
            }
            Object returnCode = resultMap.get("return_code");
            // 查询失败
            if (null == returnCode || "FAIL".equals(returnCode)) {
                return false;
            }
            // 当trade_state为SUCCESS时才返回result_code
            if ("SUCCESS".equals(resultMap.get("trade_state"))) {
                completeSuccessOrder(byBankOrderNo, byBankOrderNo.getBankTrxNo(), new Date(), "订单交易成功");
                return true;
            }
            return false;
        }

        //支付宝
        if (byBankOrderNo.getPayWayCode().equals(PayWayEnum.ALIPAY.name())) {
            if (PayTypeEnum.DIRECT_PAY.name().equals(byBankOrderNo.getPayTypeCode())) {
                //支付宝--即时到账
                LOG.info("支付宝--即时到账订单查询!订单号:[{}]", byBankOrderNo.getBankOrderNo());
                Map<String, Object> resultMap = AliPayUtil.singleTradeQuery(byBankOrderNo.getBankOrderNo());
                if (resultMap.isEmpty() || !"T".equals(resultMap.get("is_success"))) {
                    return false;
                }
                // 当返回状态为“TRADE_FINISHED”交易成功结束和“TRADE_SUCCESS”支付成功时更新交易状态
                if ("TRADE_SUCCESS".equals(resultMap.get("trade_status")) || "TRADE_FINISHED".equals(resultMap.get("trade_status"))) {
                    completeSuccessOrder(byBankOrderNo, byBankOrderNo.getBankTrxNo(), new Date(), "订单交易成功");
                    return true;
                }
            } else if (PayTypeEnum.F2F_PAY.name().equals(byBankOrderNo.getPayTypeCode())) {
                //支付宝--条码支付
                LOG.info("支付宝--条码支付订单查询!订单号:[{}]", byBankOrderNo.getBankOrderNo());
                Map<String, Object> resultMap = AliPayUtil.tradeQuery(byBankOrderNo.getBankOrderNo());
                if (!"10000".equals(resultMap.get("code"))) {
                    return false;
                }
                // 当返回状态为“TRADE_FINISHED”交易成功结束和“TRADE_SUCCESS”支付成功时更新交易状态
                if ("TRADE_SUCCESS".equals(resultMap.get("tradeStatus")) || "TRADE_FINISHED".equals(resultMap.get("tradeStatus"))) {
                    completeSuccessOrder(byBankOrderNo, byBankOrderNo.getBankTrxNo(), new Date(), "订单交易成功");
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * 小程序支付
     * @param rpUserPayConfig
     * @param programPayRequestBo
     * @return
     */
    @Override
    public ProgramPayResultVo programPay(RpUserPayConfig rpUserPayConfig, ProgramPayRequestBo programPayRequestBo) {

        // 根据支付产品及支付方式获取费率
        PayTypeEnum payType = PayTypeEnum.getEnum(programPayRequestBo.getPayType());
        RpPayWay payWay = rpPayWayService.getByPayWayTypeCode(rpUserPayConfig.getProductCode(), payType.getWay(), payType.name());
        if (payWay == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        String merchantNo = rpUserPayConfig.getUserNo();// 商户编号
        RpUserInfo rpUserInfo = rpUserInfoService.getDataByMerchentNo(merchantNo);
        if (rpUserInfo == null) {
            throw new UserBizException(UserBizException.USER_IS_NULL, "用户不存在");
        }

        //生产订单记录
        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, programPayRequestBo.getOrderNo());
        if (rpTradePaymentOrder == null) {
            rpTradePaymentOrder = sealProgramRpTradePaymentOrder( rpUserPayConfig ,  programPayRequestBo ,  rpUserInfo ,  payWay);

            rpTradePaymentOrderDao.insert(rpTradePaymentOrder);
        } else {
            if (TradeStatusEnum.SUCCESS.name().equals(rpTradePaymentOrder.getStatus())) {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单已支付成功,无需重复支付");
            }
            if (rpTradePaymentOrder.getOrderAmount().compareTo(programPayRequestBo.getOrderPrice()) != 0) {
                rpTradePaymentOrder.setOrderAmount(programPayRequestBo.getOrderPrice());// 如果金额不一致,修改金额为最新的金额
            }
        }

        return getProgramPayResultVo(rpTradePaymentOrder, payWay, rpUserPayConfig.getPaySecret(), programPayRequestBo.getOpenId(), null);
    }

    @Transactional
    @Override
    public AuthInitResultVo initDirectAuth(String productName, BigDecimal orderPrice, String orderIp, AuthParamVo paramVo, RpUserPayConfig userPayConfig) {
        if (userPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        String merchantNo = userPayConfig.getUserNo();// 商户编号
        RpUserInfo userInfo = rpUserInfoService.getDataByMerchentNo(merchantNo);
        if (userInfo == null) {
            throw new UserBizException(UserBizException.USER_IS_NULL, "用户不存在");
        }

        List<RpPayWay> payWayList = rpPayWayService.listByProductCode(userPayConfig.getProductCode());
        if (payWayList == null || payWayList.size() <= 0) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "支付产品配置有误");
        }

        Date orderDate = new Date();
        Date orderTime = orderDate;
        //通知地址，因为订单为不能为空，没有什么意义
        String returnUrl = "http://www.roncoo.com";
        String notifyUrl = "http://www.roncoo.com";

        AuthInitResultVo authInitResultVo = new AuthInitResultVo();
        RpTradePaymentOrder tradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, paramVo.getOrderNo());
        if (tradePaymentOrder == null) {
            tradePaymentOrder = sealRpTradePaymentOrder(merchantNo, userInfo.getUserName(), productName, paramVo.getOrderNo(), orderDate, orderTime, orderPrice, null, null, null, userPayConfig.getFundIntoType(), orderIp, 30, returnUrl, notifyUrl, paramVo.getRemark(), null, null, null, null, null);
            rpTradePaymentOrderDao.insert(tradePaymentOrder);
        } else {
            if (TradeStatusEnum.SUCCESS.name().equals(tradePaymentOrder.getStatus())) {
                authInitResultVo.setTradeStatus(TradeStatusEnum.SUCCESS);
            } else if (tradePaymentOrder.getOrderAmount().compareTo(orderPrice) != 0) {
                tradePaymentOrder.setOrderAmount(orderPrice);// 如果金额不一致,修改金额为最新的金额
            }
        }

        RpUserBankAuth userBankAuth = userBankAuthDao.findByMerchantNoAndPayOrderNo(merchantNo, paramVo.getOrderNo());
        if (TradeStatusEnum.SUCCESS.equals(authInitResultVo.getTradeStatus())) {
            if (userBankAuth == null) {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "用户鉴权非法订单请求,鉴权记录不存在!");
            }
            if (AuthStatusEnum.WAITING_AUTH.name().equals(userBankAuth.getStatus()) || AuthStatusEnum.SUCCESS.name().equals(userBankAuth.getStatus()) || AuthStatusEnum.FAILED.name().equals(userBankAuth.getStatus())) {
                authInitResultVo.setAuth(true);
            } else {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "非法鉴权记录");
            }
        } else {
            if (userBankAuth == null) {
                userBankAuth = new RpUserBankAuth();
                userBankAuth.setMerchantNo(merchantNo);
                userBankAuth.setPayOrderNo(paramVo.getOrderNo());
                userBankAuth.setUserName(paramVo.getUserName());
                userBankAuth.setPhone(paramVo.getPhone());
                userBankAuth.setIdNo(paramVo.getIdNo());
                userBankAuth.setBankAccountNo(paramVo.getBankAccountNo());
                userBankAuth.setStatus(AuthStatusEnum.WAITING_AUTH.name());
                userBankAuth.setRemark(paramVo.getRemark());
                userBankAuthDao.insert(userBankAuth);
            } else if (AuthStatusEnum.WAITING_AUTH.name().equals(userBankAuth.getStatus())) {
                userBankAuth.setUserName(paramVo.getUserName());
                userBankAuth.setPhone(paramVo.getPhone());
                userBankAuth.setIdNo(paramVo.getIdNo());
                userBankAuth.setBankAccountNo(paramVo.getBankAccountNo());
                userBankAuthDao.update(userBankAuth);
            } else {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "用户鉴权,非法请求");
            }
        }

        authInitResultVo.setProductName(tradePaymentOrder.getProductName());// 产品名称
        authInitResultVo.setMerchantName(tradePaymentOrder.getMerchantName());// 商户名称
        authInitResultVo.setMerchantNo(merchantNo);
        authInitResultVo.setOrderAmount(tradePaymentOrder.getOrderAmount());// 订单金额
        authInitResultVo.setMerchantOrderNo(tradePaymentOrder.getMerchantOrderNo());// 商户订单号
        authInitResultVo.setPayKey(paramVo.getPayKey());// 商户支付key

        Map<String, PayTypeEnum> payWayEnumMap = new HashMap<String, PayTypeEnum>();
        for (RpPayWay payWay : payWayList) {
            PayTypeEnum payTypeEnum = PayTypeEnum.getEnum(payWay.getPayTypeCode());
            payWayEnumMap.put(payWay.getPayWayCode(), payTypeEnum);
        }
        authInitResultVo.setPayTypeEnumMap(payWayEnumMap);
        return authInitResultVo;
    }

    @Override
    public AuthProgramInitResultVo initProgramDirectAuth(String productName, BigDecimal orderPrice, String orderIp, AuthProgramInitParamVo paramVo, RpUserPayConfig userPayConfig) {
        if (userPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        String merchantNo = userPayConfig.getUserNo();// 商户编号
        RpUserInfo userInfo = rpUserInfoService.getDataByMerchentNo(merchantNo);
        if (userInfo == null) {
            throw new UserBizException(UserBizException.USER_IS_NULL, "用户不存在");
        }

        // 根据支付产品及支付方式获取费率
        RpPayWay payWay;
        PayTypeEnum payType;
        LOG.info("支付[payWayCode]:{}", paramVo.getPayWayCode());
        if (PayWayEnum.WEIXIN.name().equals(paramVo.getPayWayCode())) {
            payType = PayTypeEnum.WX_PROGRAM_PAY;
            payWay = rpPayWayService.getByPayWayTypeCode(userPayConfig.getProductCode(), paramVo.getPayWayCode(), payType.name());
        } else {
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR, "暂不支持此支付方式");
        }
        if (payWay == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        Date orderDate = new Date();
        Date orderTime = orderDate;
        //通知地址，因为订单为不能为空，没有什么意义
        String returnUrl = "http://www.roncoo.com";
        String notifyUrl = "http://www.roncoo.com";

        AuthProgramInitResultVo resultVo = new AuthProgramInitResultVo();
        resultVo.setMchOrderNo(paramVo.getOrderNo());
        RpTradePaymentOrder tradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, paramVo.getOrderNo());

        LOG.info("====>小程序鉴权--创建订单!");
        if (tradePaymentOrder == null) {
            tradePaymentOrder = sealRpTradePaymentOrder(merchantNo, userInfo.getUserName(), productName, paramVo.getOrderNo(), orderDate, orderTime, orderPrice, null, null, null, userPayConfig.getFundIntoType(), orderIp, 30, returnUrl, notifyUrl, paramVo.getRemark(), null, null, null, null, null);
            rpTradePaymentOrderDao.insert(tradePaymentOrder);
        } else {
            if (TradeStatusEnum.SUCCESS.name().equals(tradePaymentOrder.getStatus())) {
                resultVo.setTradeStatus(TradeStatusEnum.SUCCESS);
            } else if (tradePaymentOrder.getOrderAmount().compareTo(orderPrice) != 0) {
                tradePaymentOrder.setOrderAmount(orderPrice);// 如果金额不一致,修改金额为最新的金额
            }
        }

        RpUserBankAuth userBankAuth = userBankAuthDao.findByMerchantNoAndPayOrderNo(merchantNo, paramVo.getOrderNo());
        if (TradeStatusEnum.SUCCESS.equals(resultVo.getTradeStatus())) {
            if (userBankAuth == null) {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "用户鉴权非法订单请求,鉴权记录不存在!");
            }
            if (AuthStatusEnum.WAITING_AUTH.name().equals(userBankAuth.getStatus()) || AuthStatusEnum.SUCCESS.name().equals(userBankAuth.getStatus()) || AuthStatusEnum.FAILED.name().equals(userBankAuth.getStatus())) {
                resultVo.setAuth(true);
            } else {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "非法鉴权记录");
            }
        } else {
            //调用小程序支付
            ProgramPayResultVo programPayResultVo = getProgramPayResultVo(tradePaymentOrder, payWay, userPayConfig.getPaySecret(), paramVo.getOpenId(), null);
            if (!PublicEnum.YES.name().equals(programPayResultVo.getStatus())) {
                throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "请求小程序支付失败!");
            }
            resultVo.setPayMessage(programPayResultVo.getPayMessage());
            resultVo.setBankReturnMsg(programPayResultVo.getBankReturnMsg());
            if (userBankAuth == null) {
                LOG.info("小程序鉴权--创建鉴权记录!");
                userBankAuth = new RpUserBankAuth();
                userBankAuth.setMerchantNo(merchantNo);
                userBankAuth.setPayOrderNo(paramVo.getOrderNo());
                userBankAuth.setUserName(paramVo.getUserName());
                userBankAuth.setPhone(paramVo.getPhone());
                userBankAuth.setIdNo(paramVo.getIdNo());
                userBankAuth.setBankAccountNo(paramVo.getBankAccountNo());
                userBankAuth.setStatus(AuthStatusEnum.WAITING_AUTH.name());
                userBankAuth.setRemark(paramVo.getRemark());
                userBankAuthDao.insert(userBankAuth);
            } else if (AuthStatusEnum.WAITING_AUTH.name().equals(userBankAuth.getStatus())) {
                LOG.info("小程序鉴权--鉴权记录已存在，更新记录!");
                userBankAuth.setUserName(paramVo.getUserName());
                userBankAuth.setPhone(paramVo.getPhone());
                userBankAuth.setIdNo(paramVo.getIdNo());
                userBankAuth.setBankAccountNo(paramVo.getBankAccountNo());
                userBankAuthDao.update(userBankAuth);
            } else {
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "用户鉴权,非法请求");
            }
        }

        resultVo.setErrCode(PublicEnum.YES.name());
        Map<String, Object> paramMap = JSON.parseObject(JSON.toJSONString(resultVo));
        resultVo.setSign(MerchantApiUtil.getSign(paramMap, userPayConfig.getPaySecret()));
        return resultVo;
    }

    @Override
    public AuthResultVo userAuth(String merchantNo, String orderNo) {
        AuthResultVo resultVo = new AuthResultVo();

        //查询订单
        RpTradePaymentOrder tradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (tradePaymentOrder == null) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "鉴权订单不存在");
        }
        if (!TradeStatusEnum.SUCCESS.name().equals(tradePaymentOrder.getStatus())) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "鉴权订单未支付成功");
        }

        RpUserBankAuth userBankAuth = userBankAuthDao.findByMerchantNoAndPayOrderNo(merchantNo, orderNo);
        if (userBankAuth == null) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "鉴权记录不存在");
        }

        resultVo.setMerchantNo(merchantNo);
        resultVo.setOrderNo(orderNo);
        resultVo.setUserName(userBankAuth.getUserName());
        resultVo.setPhone(userBankAuth.getPhone());
        resultVo.setIdNo(userBankAuth.getIdNo());
        resultVo.setBankAccountNo(userBankAuth.getBankAccountNo());
        if (AuthStatusEnum.WAITING_AUTH.name().equals(userBankAuth.getStatus())) {
            LOG.info("鉴权记录，未鉴权需要再次调用鉴权接口");
            Map<String, Object> resultMap = AuthUtil.auth(orderNo, userBankAuth.getUserName(), userBankAuth.getPhone(), userBankAuth.getIdNo(), userBankAuth.getBankAccountNo());
            if (resultMap == null || resultMap.isEmpty()) {
                resultVo.setAuthStatusEnum(AuthStatusEnum.FAILED);
                return resultVo;
            }
            if ("0000".equals(resultMap.get("resultCode"))) {
                userBankAuth.setStatus(AuthStatusEnum.SUCCESS.name());
            } else if ("9998".equals(resultMap.get("resultCode"))) {
                LOG.info("鉴权返回余额不足!");
                userBankAuth.setStatus(AuthStatusEnum.WAITING_AUTH.name());
            } else {
                //除了成功的，其他的状态置为失败
                userBankAuth.setStatus(AuthStatusEnum.FAILED.name());
            }
            userBankAuth.setEditTime(new Date());
            userBankAuthDao.update(userBankAuth);
            resultVo.setAuthStatusEnum(AuthStatusEnum.valueOf(userBankAuth.getStatus()));
        } else {
            LOG.info("鉴权记录，已鉴权不需要再次调用鉴权接口");
            resultVo.setAuthStatusEnum(AuthStatusEnum.valueOf(userBankAuth.getStatus()));
        }
        return resultVo;
    }


    /**
     * 通过支付订单及商户费率生成支付记录
     *
     * @param tradePaymentOrder 支付订单
     * @param payWay            商户支付配置
     * @return
     */
    private ProgramPayResultVo getProgramPayResultVo(RpTradePaymentOrder tradePaymentOrder, RpPayWay payWay, String merchantPaySecret, String openId, List<RoncooPayGoodsDetails> roncooPayGoodsDetailses) {

        ProgramPayResultVo resultVo = new ProgramPayResultVo();
        String payWayCode = payWay.getPayWayCode();// 支付方式

        PayTypeEnum payType = null;
        if (PayWayEnum.WEIXIN.name().equals(payWay.getPayWayCode())) {
            payType = PayTypeEnum.WX_PROGRAM_PAY;
        } else if (PayWayEnum.ALIPAY.name().equals(payWay.getPayWayCode())) {
            // TODO 支付宝小程序支付，需要自定义枚举
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR, "暂不支持此支付方式");
        }

        tradePaymentOrder.setPayTypeCode(payType.name());// 支付类型
        tradePaymentOrder.setPayTypeName(payType.getDesc());// 支付方式
        tradePaymentOrder.setPayWayCode(payWay.getPayWayCode());//支付通道编号
        tradePaymentOrder.setPayWayName(payWay.getPayWayName());//支付通道名称

        //生成支付流水
        RpTradePaymentRecord rpTradePaymentRecord = sealRpTradePaymentRecord(tradePaymentOrder.getMerchantNo(), tradePaymentOrder.getMerchantName(), tradePaymentOrder.getProductName(), tradePaymentOrder.getMerchantOrderNo(), tradePaymentOrder.getOrderAmount(), payWay.getPayWayCode(), payWay.getPayWayName(), payType, tradePaymentOrder.getFundIntoType(), BigDecimal.valueOf(payWay.getPayRate()), tradePaymentOrder.getOrderIp(), tradePaymentOrder.getReturnUrl(), tradePaymentOrder.getNotifyUrl(), tradePaymentOrder.getRemark(), tradePaymentOrder.getField1(), tradePaymentOrder.getField2(), tradePaymentOrder.getField3(), tradePaymentOrder.getField4(), tradePaymentOrder.getField5());
        rpTradePaymentRecordDao.insert(rpTradePaymentRecord);

        if (PayWayEnum.WEIXIN.name().equals(payWayCode)) {// 微信支付
            Map<String, Object> resultMap = WeiXinPayUtil.appletPay(rpTradePaymentRecord.getBankOrderNo(), rpTradePaymentRecord.getProductName(), rpTradePaymentRecord.getOrderAmount(), rpTradePaymentRecord.getOrderIp(), WeixinConfigUtil.x_notify_url, openId, roncooPayGoodsDetailses);
            if (resultMap == null || resultMap.isEmpty()) {
                resultVo.setStatus(PublicEnum.NO.name());
                resultVo.setBankReturnMsg("请求支付失败!");
            } else {
                if ("YES".equals(resultMap.get("verify"))) {
                    if ("SUCCESS".equals(resultMap.get("return_code")) && "SUCCESS".equals(resultMap.get("result_code"))) {
                        resultVo.setStatus(PublicEnum.YES.name());
                        resultVo.setBankReturnMsg(String.valueOf(resultMap.get("return_msg")));

                        Object prepayId = resultMap.get("prepay_id");
                        Object appid = resultMap.get("appid");
                        SortedMap<String, Object> returnMap = new TreeMap<>();
                        returnMap.put("appId", appid);//appId
                        returnMap.put("timeStamp", System.currentTimeMillis());//当前时间戳
                        returnMap.put("nonceStr", WeiXinPayUtil.getnonceStr());//随机数
                        returnMap.put("package", "prepay_id=" + prepayId);//
                        returnMap.put("signType", "MD5");//签名方式
                        returnMap.put("paySign", WeiXinPayUtil.getSign(returnMap, WeixinConfigUtil.xPayKey));
                        returnMap.remove("appId");
                        String jsonString = JSON.toJSONString(returnMap);
                        resultVo.setPayMessage(jsonString);
                        resultVo.setStatus(PublicEnum.YES.name());
                        //请求成功，发起轮询
                        rpNotifyService.orderSend(rpTradePaymentRecord.getBankOrderNo());
                    } else {
                        resultVo.setStatus(PublicEnum.NO.name());
                        resultVo.setBankReturnMsg(String.valueOf(resultMap.get("return_msg")));
                    }
                } else {
                    resultVo.setStatus(PublicEnum.NO.name());
                    resultVo.setBankReturnMsg("请求微信返回信息验签不通过！");
                }
            }
        } else if (PayWayEnum.ALIPAY.name().equals(payWayCode)) {// 支付宝支付
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR, "暂不支持此支付方式");
        }

        Map<String, Object> paramMap = new HashMap<>();
        if (!StringUtil.isEmpty(resultVo.getPayMessage())) {
            paramMap.put("payMessage", resultVo.getPayMessage());//支付信息
        }
        if (!StringUtil.isEmpty(resultVo.getBankReturnMsg())) {
            paramMap.put("bankReturnMsg", resultVo.getBankReturnMsg());
        }
        paramMap.put("status", resultVo.getStatus());
        String sign = MerchantApiUtil.getSign(paramMap, merchantPaySecret);
        resultVo.setSign(sign);
        return resultVo;
    }
}
