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

import com.roncoo.pay.account.service.RpAccountTransactionService;
import com.roncoo.pay.common.core.enums.PayTypeEnum;
import com.roncoo.pay.common.core.enums.PayWayEnum;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.notify.service.RpNotifyService;
import com.roncoo.pay.trade.dao.RpTradePaymentOrderDao;
import com.roncoo.pay.trade.dao.RpTradePaymentRecordDao;
import com.roncoo.pay.trade.entity.RoncooPayGoodsDetails;
import com.roncoo.pay.trade.entity.RpTradePaymentOrder;
import com.roncoo.pay.trade.entity.RpTradePaymentRecord;
import com.roncoo.pay.trade.entity.weixinpay.WeiXinPrePay;
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
import com.roncoo.pay.trade.utils.alipay.config.AlipayConfigUtil;
import com.roncoo.pay.trade.utils.alipay.f2fpay.AliF2FPaySubmit;
import com.roncoo.pay.trade.utils.alipay.util.AlipayNotify;
import com.roncoo.pay.trade.utils.alipay.util.AlipaySubmit;
import com.roncoo.pay.trade.vo.F2FPayResultVo;
import com.roncoo.pay.trade.vo.OrderPayResultVo;
import com.roncoo.pay.trade.vo.RpPayGateWayPageShowVo;
import com.roncoo.pay.trade.vo.ScanPayResultVo;
import com.roncoo.pay.user.entity.RpPayWay;
import com.roncoo.pay.user.entity.RpUserInfo;
import com.roncoo.pay.user.entity.RpUserPayConfig;
import com.roncoo.pay.user.entity.RpUserPayInfo;
import com.roncoo.pay.user.enums.FundInfoTypeEnum;
import com.roncoo.pay.user.exception.UserBizException;
import com.roncoo.pay.user.service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>功能说明:交易模块管理实现类实现</b>
 * @author  Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
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
    private AliF2FPaySubmit aliF2FPaySubmit;

    /**
     * 初始化直连扫码支付数据,直连扫码支付初始化方法规则
     * 1:根据(商户编号 + 商户订单号)确定订单是否存在
     * 1.1:如果订单存在,抛异常,提示订单已存在
     * 1.2:如果订单不存在,创建支付订单
     * 2:创建支付记录
     * 3:根据相应渠道方法
     * 4:调转到相应支付渠道扫码界面
     *
     * @param payKey  商户支付KEY
     * @param productName 产品名称
     * @param orderNo     商户订单号
     * @param orderDate   下单日期
     * @param orderTime   下单时间
     * @param orderPrice  订单金额(元)
     * @param payWayCode      支付方式编码
     * @param orderIp     下单IP
     * @param orderPeriod 订单有效期(分钟)
     * @param returnUrl   支付结果页面通知地址
     * @param notifyUrl   支付结果后台通知地址
     * @param remark      支付备注
     * @param field1      扩展字段1
     * @param field2      扩展字段2
     * @param field3      扩展字段3
     * @param field4      扩展字段4
     * @param field5      扩展字段5
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScanPayResultVo initDirectScanPay(String payKey, String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String payWayCode, String orderIp, Integer orderPeriod, String returnUrl, String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5) {

        RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByPayKey(payKey);
        if (rpUserPayConfig == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        //根据支付产品及支付方式获取费率
        RpPayWay payWay = null;
        PayTypeEnum payType = null;
        if (PayWayEnum.WEIXIN.name().equals(payWayCode)){
            payWay = rpPayWayService.getByPayWayTypeCode(rpUserPayConfig.getProductCode(), payWayCode, PayTypeEnum.SCANPAY.name());
            payType = PayTypeEnum.SCANPAY;
        }else if (PayWayEnum.ALIPAY.name().equals(payWayCode)){
            payWay = rpPayWayService.getByPayWayTypeCode(rpUserPayConfig.getProductCode(), payWayCode, PayTypeEnum.DIRECT_PAY.name());
            payType = PayTypeEnum.DIRECT_PAY;
        }

        if(payWay == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        String merchantNo = rpUserPayConfig.getUserNo();//商户编号

        RpUserInfo rpUserInfo = rpUserInfoService.getDataByMerchentNo(merchantNo);
        if (rpUserInfo == null){
            throw new UserBizException(UserBizException.USER_IS_NULL,"用户不存在");
        }

        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (rpTradePaymentOrder == null){
            rpTradePaymentOrder = sealRpTradePaymentOrder( merchantNo,  rpUserInfo.getUserName() , productName,  orderNo,  orderDate,  orderTime,  orderPrice, payWayCode, PayWayEnum.getEnum(payWayCode).getDesc() , payType, rpUserPayConfig.getFundIntoType() ,  orderIp,  orderPeriod,  returnUrl,  notifyUrl,  remark,  field1,  field2,  field3,  field4,  field5);
            rpTradePaymentOrderDao.insert(rpTradePaymentOrder);
        }else{

            if (TradeStatusEnum.SUCCESS.name().equals(rpTradePaymentOrder.getStatus())){
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单已支付成功,无需重复支付");
            }

            if (rpTradePaymentOrder.getOrderAmount().compareTo(orderPrice) != 0 ){
                rpTradePaymentOrder.setOrderAmount(orderPrice);//如果金额不一致,修改金额为最新的金额
            }
        }

        return getScanPayResultVo(rpTradePaymentOrder , payWay);

    }

    /**
     * 条码支付,对应的是支付宝的条码支付或者微信的刷卡支付
     *
     * @param payKey      商户支付key
     * @param authCode    支付授权码
     * @param productName 产品名称
     * @param orderNo     商户订单号
     * @param orderDate   下单日期
     * @param orderTime   下单时间
     * @param orderPrice  订单金额(元)
     * @param payWayCode  支付方式
     * @param orderIp     下单IP
     * @param remark      支付备注
     * @param field1      扩展字段1
     * @param field2      扩展字段2
     * @param field3      扩展字段3
     * @param field4      扩展字段4
     * @param field5      扩展字段5
     * @return
     */
    @Override
    public F2FPayResultVo f2fPay(String payKey, String authCode, String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String payWayCode, String orderIp, String remark, String field1, String field2, String field3, String field4, String field5) {

        RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByPayKey(payKey);
        if (rpUserPayConfig == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        if (StringUtil.isEmpty(authCode)){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"支付授权码不能为空");
        }
        //根据支付产品及支付方式获取费率
        RpPayWay payWay = null;
        PayTypeEnum payType = null;
        if (PayWayEnum.WEIXIN.name().equals(payWayCode)){
//            payWay = rpPayWayService.getByPayWayTypeCode(rpUserPayConfig.getProductCode(), payWayCode, PayTypeEnum.SCANPAY.name());
            payType = PayTypeEnum.SCANPAY;//TODO 具体需要根据接口修改
        }else if (PayWayEnum.ALIPAY.name().equals(payWayCode)){
            payWay = rpPayWayService.getByPayWayTypeCode(rpUserPayConfig.getProductCode(), payWayCode, PayTypeEnum.F2F_PAY.name());
            payType = PayTypeEnum.F2F_PAY;
        }

        if(payWay == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        String merchantNo = rpUserPayConfig.getUserNo();//商户编号
        RpUserInfo rpUserInfo = rpUserInfoService.getDataByMerchentNo(merchantNo);
        if (rpUserInfo == null){
            throw new UserBizException(UserBizException.USER_IS_NULL,"用户不存在");
        }

        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (rpTradePaymentOrder == null){
            rpTradePaymentOrder = sealRpTradePaymentOrder( merchantNo,  rpUserInfo.getUserName() , productName,  orderNo,  orderDate,  orderTime,  orderPrice, payWayCode, PayWayEnum.getEnum(payWayCode).getDesc() , payType, rpUserPayConfig.getFundIntoType() ,  orderIp,  5,  "f2fPay",  "f2fPay",  remark,  field1,  field2,  field3,  field4,  field5);
            rpTradePaymentOrderDao.insert(rpTradePaymentOrder);
        }else{
            if (rpTradePaymentOrder.getOrderAmount().compareTo(orderPrice) != 0 ){
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"错误的订单");
            }

            if (TradeStatusEnum.SUCCESS.name().equals(rpTradePaymentOrder.getStatus())){
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单已支付成功,无需重复支付");
            }
        }

        return getF2FPayResultVo( rpTradePaymentOrder , payWay ,  payKey , rpUserPayConfig.getPaySecret() , authCode ,null);
    }

    /**
     * 通过支付订单及商户费率生成支付记录
     * @param rpTradePaymentOrder   支付订单
     * @param payWay   商户支付配置
     * @return
     */
    private F2FPayResultVo getF2FPayResultVo(RpTradePaymentOrder rpTradePaymentOrder ,RpPayWay payWay , String  payKey , String merchantPaySecret , String authCode ,List< RoncooPayGoodsDetails > roncooPayGoodsDetailses){

        F2FPayResultVo f2FPayResultVo = new F2FPayResultVo();
        String payWayCode = payWay.getPayWayCode();//支付方式

        PayTypeEnum payType = null;
        if (PayWayEnum.WEIXIN.name().equals(payWay.getPayWayCode())){
            payType = PayTypeEnum.SCANPAY;//TODO 微信条码支付需要修改成对应的枚举 支付类型
        }else if(PayWayEnum.ALIPAY.name().equals(payWay.getPayWayCode())){
            payType = PayTypeEnum.F2F_PAY;
        }

        rpTradePaymentOrder.setPayTypeCode(payType.name());//支付类型
        rpTradePaymentOrder.setPayTypeName(payType.getDesc());//支付方式

        rpTradePaymentOrder.setPayWayCode(payWay.getPayWayCode());
        rpTradePaymentOrder.setPayWayName(payWay.getPayWayName());

        RpTradePaymentRecord rpTradePaymentRecord = sealRpTradePaymentRecord(rpTradePaymentOrder.getMerchantNo(),  rpTradePaymentOrder.getMerchantName() , rpTradePaymentOrder.getProductName(),  rpTradePaymentOrder.getMerchantOrderNo(),  rpTradePaymentOrder.getOrderAmount(), payWay.getPayWayCode(),  payWay.getPayWayName() , payType, rpTradePaymentOrder.getFundIntoType()  , BigDecimal.valueOf(payWay.getPayRate()) ,  rpTradePaymentOrder.getOrderIp(),  rpTradePaymentOrder.getReturnUrl(),  rpTradePaymentOrder.getNotifyUrl(),  rpTradePaymentOrder.getRemark(),  rpTradePaymentOrder.getField1(),  rpTradePaymentOrder.getField2(),  rpTradePaymentOrder.getField3(),  rpTradePaymentOrder.getField4(),  rpTradePaymentOrder.getField5());
        rpTradePaymentRecordDao.insert(rpTradePaymentRecord);

        if (PayWayEnum.WEIXIN.name().equals(payWayCode)){//微信支付
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR,"暂未开通微信刷卡支付");
        }else {
            if (PayWayEnum.ALIPAY.name().equals(payWayCode)) {//支付宝支付

                RpUserPayInfo rpUserPayInfo = rpUserPayInfoService.getByUserNo(rpTradePaymentOrder.getMerchantNo(),payWayCode);
                if (rpUserPayInfo == null){
                    throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"商户支付配置有误");
                }

                aliF2FPaySubmit.initConfigs(rpTradePaymentOrder.getFundIntoType(), rpUserPayInfo.getOfflineAppId(), rpUserPayInfo.getAppId(), rpUserPayInfo.getRsaPrivateKey(), rpUserPayInfo.getRsaPublicKey());
                Map<String , String > aliPayReturnMsg = aliF2FPaySubmit.f2fPay(rpTradePaymentRecord.getBankOrderNo(), rpTradePaymentOrder.getProductName(), "", authCode, rpTradePaymentRecord.getOrderAmount(), roncooPayGoodsDetailses);

                if(TradeStatusEnum.SUCCESS.name().equals(aliPayReturnMsg.get("status"))){//支付成功
                    completeSuccessOrder( rpTradePaymentRecord ,  aliPayReturnMsg.get("bankTrxNo") ,new Date() ,  aliPayReturnMsg.get("bankReturnMsg"));
                }else if(TradeStatusEnum.FAILED.name().equals(aliPayReturnMsg.get("status"))){//支付失败
                    completeFailOrder(rpTradePaymentRecord , aliPayReturnMsg.get("bankReturnMsg"));
                }else{
                    //TODO 未知支付结果,需要在后续添加订单结果轮询功能后处理
                }

            } else {
                throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR, "错误的支付方式");
            }
        }

        Map<String , Object> paramMap = new HashMap<String , Object>();
        f2FPayResultVo.setStatus(rpTradePaymentRecord.getStatus());//支付结果
        paramMap.put("status",rpTradePaymentRecord.getStatus());

        f2FPayResultVo.setField1(rpTradePaymentRecord.getField1());//扩展字段1
        paramMap.put("field1",rpTradePaymentRecord.getField1());

        f2FPayResultVo.setField2(rpTradePaymentRecord.getField2());//扩展字段2
        paramMap.put("field2",rpTradePaymentRecord.getField2());

        f2FPayResultVo.setField3(rpTradePaymentRecord.getField3());//扩展字段3
        paramMap.put("field3",rpTradePaymentRecord.getField3());

        f2FPayResultVo.setField4(rpTradePaymentRecord.getField4());//扩展字段4
        paramMap.put("field4",rpTradePaymentRecord.getField4());

        f2FPayResultVo.setField5(rpTradePaymentRecord.getField5());//扩展字段5
        paramMap.put("field5",rpTradePaymentRecord.getField5());

        f2FPayResultVo.setOrderIp(rpTradePaymentRecord.getOrderIp());//下单ip
        paramMap.put("orderIp",rpTradePaymentRecord.getOrderIp());

        f2FPayResultVo.setOrderNo(rpTradePaymentRecord.getMerchantOrderNo());//商户订单号
        paramMap.put("merchantOrderNo",rpTradePaymentRecord.getMerchantOrderNo());

        f2FPayResultVo.setPayKey(payKey);//支付号
        paramMap.put("payKey",payKey);

        f2FPayResultVo.setProductName(rpTradePaymentRecord.getProductName());//产品名称
        paramMap.put("productName",rpTradePaymentRecord.getProductName());

        f2FPayResultVo.setRemark(rpTradePaymentRecord.getRemark());//支付备注
        paramMap.put("remark",rpTradePaymentRecord.getRemark());

        f2FPayResultVo.setTrxNo(rpTradePaymentRecord.getTrxNo());//交易流水号
        paramMap.put("trxNo", rpTradePaymentRecord.getTrxNo());

        String sign = MerchantApiUtil.getSign(paramMap, merchantPaySecret);

        f2FPayResultVo.setSign(sign);
        return f2FPayResultVo;
    }



    /**
     * 支付成功方法
     * @param rpTradePaymentRecord
     */
    private void completeSuccessOrder(RpTradePaymentRecord rpTradePaymentRecord , String bankTrxNo ,Date timeEnd ,  String bankReturnMsg){

            rpTradePaymentRecord.setPaySuccessTime(timeEnd);
            rpTradePaymentRecord.setBankTrxNo(bankTrxNo);//设置银行流水号
            rpTradePaymentRecord.setBankReturnMsg(bankReturnMsg);
            rpTradePaymentRecord.setStatus(TradeStatusEnum.SUCCESS.name());
            rpTradePaymentRecordDao.update(rpTradePaymentRecord);

            RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(rpTradePaymentRecord.getMerchantNo(), rpTradePaymentRecord.getMerchantOrderNo());
            rpTradePaymentOrder.setStatus(TradeStatusEnum.SUCCESS.name());
            rpTradePaymentOrder.setTrxNo(rpTradePaymentRecord.getTrxNo());//设置支付平台支付流水号
            rpTradePaymentOrderDao.update(rpTradePaymentOrder);

            if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(rpTradePaymentRecord.getFundIntoType())){
                rpAccountTransactionService.creditToAccount( rpTradePaymentRecord.getMerchantNo(), rpTradePaymentRecord.getOrderAmount().subtract(rpTradePaymentRecord.getPlatIncome()), rpTradePaymentRecord.getBankOrderNo(),rpTradePaymentRecord.getBankTrxNo(), rpTradePaymentRecord.getTrxType(), rpTradePaymentRecord.getRemark());
            }

            if (PayTypeEnum.F2F_PAY.name().equals(rpTradePaymentOrder.getPayTypeCode())){//支付宝 条码支付实时返回支付结果,不需要商户通知
                return;
            }else{
                String  notifyUrl = getMerchantNotifyUrl(rpTradePaymentRecord , rpTradePaymentOrder , rpTradePaymentRecord.getNotifyUrl() ,TradeStatusEnum.SUCCESS );
                rpNotifyService.notifySend(notifyUrl, rpTradePaymentRecord.getMerchantOrderNo(), rpTradePaymentRecord.getMerchantNo());
            }
    }


    private String getMerchantNotifyUrl(RpTradePaymentRecord rpTradePaymentRecord ,RpTradePaymentOrder rpTradePaymentOrder ,String sourceUrl , TradeStatusEnum tradeStatusEnum){

        RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByUserNo(rpTradePaymentRecord.getMerchantNo());
        if (rpUserPayConfig == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        Map<String , Object> paramMap = new HashMap<>();

        String payKey = rpUserPayConfig.getPayKey();// 企业支付KEY
        paramMap.put("payKey",payKey);
        String productName = rpTradePaymentRecord.getProductName(); // 商品名称
        paramMap.put("productName",productName);
        String orderNo = rpTradePaymentRecord.getMerchantOrderNo(); // 订单编号
        paramMap.put("orderNo",orderNo);
        BigDecimal orderPrice = rpTradePaymentRecord.getOrderAmount(); // 订单金额 , 单位:元
        paramMap.put("orderPrice",orderPrice);
        String payWayCode = rpTradePaymentRecord.getPayWayCode(); // 支付方式编码 支付宝: ALIPAY  微信:WEIXIN
        paramMap.put("payWayCode",payWayCode);
        paramMap.put("tradeStatus",tradeStatusEnum);//交易状态
        String orderDateStr = DateUtils.formatDate(rpTradePaymentOrder.getOrderDate(),"yyyyMMdd"); // 订单日期
        paramMap.put("orderDate",orderDateStr);
        String orderTimeStr = DateUtils.formatDate(rpTradePaymentOrder.getOrderTime(), "yyyyMMddHHmmss"); // 订单时间
        paramMap.put("orderTime",orderTimeStr);
        String remark = rpTradePaymentRecord.getRemark(); // 支付备注
        paramMap.put("remark",remark);
        String trxNo = rpTradePaymentRecord.getTrxNo();//支付流水号
        paramMap.put("trxNo",trxNo);

        String field1 = rpTradePaymentOrder.getField1(); // 扩展字段1
        paramMap.put("field1",field1);
        String field2 = rpTradePaymentOrder.getField2(); // 扩展字段2
        paramMap.put("field2",field2);
        String field3 = rpTradePaymentOrder.getField3(); // 扩展字段3
        paramMap.put("field3",field3);
        String field4 = rpTradePaymentOrder.getField4(); // 扩展字段4
        paramMap.put("field4",field4);
        String field5 = rpTradePaymentOrder.getField5(); // 扩展字段5
        paramMap.put("field5",field5);

        String paramStr = MerchantApiUtil.getParamStr(paramMap);
        String sign = MerchantApiUtil.getSign(paramMap, rpUserPayConfig.getPaySecret());
        String notifyUrl = sourceUrl + "?" + paramStr + "&sign=" + sign;

        return notifyUrl;
    }


    /**
     * 支付失败方法
     * @param rpTradePaymentRecord
     */
    private void completeFailOrder(RpTradePaymentRecord rpTradePaymentRecord , String bankReturnMsg){
        rpTradePaymentRecord.setBankReturnMsg(bankReturnMsg);
        rpTradePaymentRecord.setStatus(TradeStatusEnum.FAILED.name());
        rpTradePaymentRecordDao.update(rpTradePaymentRecord);

        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(rpTradePaymentRecord.getMerchantNo(), rpTradePaymentRecord.getMerchantOrderNo());
        rpTradePaymentOrder.setStatus(TradeStatusEnum.FAILED.name());
        rpTradePaymentOrderDao.update(rpTradePaymentOrder);

        String  notifyUrl = getMerchantNotifyUrl(rpTradePaymentRecord , rpTradePaymentOrder , rpTradePaymentRecord.getNotifyUrl() ,TradeStatusEnum.FAILED );
        rpNotifyService.notifySend(notifyUrl, rpTradePaymentRecord.getMerchantOrderNo(), rpTradePaymentRecord.getMerchantNo());
    }

    /**
     * 初始化非直连扫码支付数据,非直连扫码支付初始化方法规则
     * 1:根据(商户编号 + 商户订单号)确定订单是否存在
     * 1.1:如果订单存在且为未支付,抛异常提示订单已存在
     * 1.2:如果订单不存在,创建支付订单
     * 2:获取商户支付配置,跳转到支付网关,选择支付方式
     *
     * @param payKey  商户支付KEY
     * @param productName 产品名称
     * @param orderNo     商户订单号
     * @param orderDate   下单日期
     * @param orderTime   下单时间
     * @param orderPrice  订单金额(元)
     * @param orderIp     下单IP
     * @param orderPeriod 订单有效期(分钟)
     * @param returnUrl   支付结果页面通知地址
     * @param notifyUrl   支付结果后台通知地址
     * @param remark      支付备注
     * @param field1      扩展字段1
     * @param field2      扩展字段2
     * @param field3      扩展字段3
     * @param field4      扩展字段4
     * @param field5      扩展字段5
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RpPayGateWayPageShowVo initNonDirectScanPay(String payKey, String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String orderIp, Integer orderPeriod, String returnUrl, String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5) {

        RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByPayKey(payKey);
        if (rpUserPayConfig == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        String merchantNo = rpUserPayConfig.getUserNo();//商户编号
        RpUserInfo rpUserInfo = rpUserInfoService.getDataByMerchentNo(merchantNo);
        if (rpUserInfo == null){
            throw new UserBizException(UserBizException.USER_IS_NULL,"用户不存在");
        }

        List<RpPayWay> payWayList = rpPayWayService.listByProductCode(rpUserPayConfig.getProductCode());
        if (payWayList == null || payWayList.size() <= 0){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"支付产品配置有误");
        }

        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (rpTradePaymentOrder == null){
            rpTradePaymentOrder = sealRpTradePaymentOrder( merchantNo,  rpUserInfo.getUserName() , productName,  orderNo,  orderDate,  orderTime,  orderPrice, null, null ,null , rpUserPayConfig.getFundIntoType() ,  orderIp,  orderPeriod,  returnUrl,  notifyUrl,  remark,  field1,  field2,  field3,  field4,  field5);
            rpTradePaymentOrderDao.insert(rpTradePaymentOrder);
        }else{

            if (TradeStatusEnum.SUCCESS.name().equals(rpTradePaymentOrder.getStatus())){
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单已支付成功,无需重复支付");
            }

            if (rpTradePaymentOrder.getOrderAmount().compareTo(orderPrice) != 0 ){
                rpTradePaymentOrder.setOrderAmount(orderPrice);//如果金额不一致,修改金额为最新的金额
                rpTradePaymentOrderDao.update(rpTradePaymentOrder);
            }
        }

        RpPayGateWayPageShowVo payGateWayPageShowVo = new RpPayGateWayPageShowVo();
        payGateWayPageShowVo.setProductName(rpTradePaymentOrder.getProductName());//产品名称
        payGateWayPageShowVo.setMerchantName(rpTradePaymentOrder.getMerchantName());//商户名称
        payGateWayPageShowVo.setOrderAmount(rpTradePaymentOrder.getOrderAmount());//订单金额
        payGateWayPageShowVo.setMerchantOrderNo(rpTradePaymentOrder.getMerchantOrderNo());//商户订单号
        payGateWayPageShowVo.setPayKey(payKey);//商户支付key

        Map<String , PayWayEnum> payWayEnumMap = new HashMap<String , PayWayEnum>();
        for (RpPayWay payWay :payWayList){
            payWayEnumMap.put(payWay.getPayWayCode(), PayWayEnum.getEnum(payWay.getPayWayCode()));
        }

        payGateWayPageShowVo.setPayWayEnumMap(payWayEnumMap);

        return payGateWayPageShowVo;

    }

    /**
     * 非直连扫码支付,选择支付方式后,去支付
     * @param payKey
     * @param orderNo
     * @param payWayCode
     * @return
     */
    @Override
    public ScanPayResultVo toNonDirectScanPay(String payKey , String orderNo, String payWayCode) {

        RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByPayKey(payKey);
        if (rpUserPayConfig == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        //根据支付产品及支付方式获取费率
        RpPayWay payWay = null;
        if (PayWayEnum.WEIXIN.name().equals(payWayCode)){
            payWay = rpPayWayService.getByPayWayTypeCode(rpUserPayConfig.getProductCode(), payWayCode, PayTypeEnum.SCANPAY.name());
        }else if (PayWayEnum.ALIPAY.name().equals(payWayCode)){
            payWay = rpPayWayService.getByPayWayTypeCode(rpUserPayConfig.getProductCode(), payWayCode, PayTypeEnum.DIRECT_PAY.name());
        }

        if(payWay == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        String merchantNo = rpUserPayConfig.getUserNo();//商户编号
        RpUserInfo rpUserInfo = rpUserInfoService.getDataByMerchentNo(merchantNo);
        if (rpUserInfo == null){
            throw new UserBizException(UserBizException.USER_IS_NULL,"用户不存在");
        }

        //根据商户订单号获取订单信息
        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (rpTradePaymentOrder == null){
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单不存在");
        }

        if (TradeStatusEnum.SUCCESS.name().equals(rpTradePaymentOrder.getStatus())){
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单已支付成功,无需重复支付");
        }

        return getScanPayResultVo(rpTradePaymentOrder , payWay);

    }


    /**
     * 通过支付订单及商户费率生成支付记录
     * @param rpTradePaymentOrder   支付订单
     * @param payWay   商户支付配置
     * @return
     */
    private ScanPayResultVo getScanPayResultVo(RpTradePaymentOrder rpTradePaymentOrder ,RpPayWay payWay){

        ScanPayResultVo scanPayResultVo = new ScanPayResultVo();

        String payWayCode = payWay.getPayWayCode();//支付方式

        PayTypeEnum payType = null;
        if (PayWayEnum.WEIXIN.name().equals(payWay.getPayWayCode())){
            payType = PayTypeEnum.SCANPAY;
        }else if(PayWayEnum.ALIPAY.name().equals(payWay.getPayWayCode())){
            payType = PayTypeEnum.DIRECT_PAY;
        }

        rpTradePaymentOrder.setPayTypeCode(payType.name());
        rpTradePaymentOrder.setPayTypeName(payType.getDesc());

        rpTradePaymentOrder.setPayWayCode(payWay.getPayWayCode());
        rpTradePaymentOrder.setPayWayName(payWay.getPayWayName());
        rpTradePaymentOrderDao.update(rpTradePaymentOrder);

        RpTradePaymentRecord rpTradePaymentRecord = sealRpTradePaymentRecord( rpTradePaymentOrder.getMerchantNo(),  rpTradePaymentOrder.getMerchantName() , rpTradePaymentOrder.getProductName(),  rpTradePaymentOrder.getMerchantOrderNo(),  rpTradePaymentOrder.getOrderAmount(), payWay.getPayWayCode(),  payWay.getPayWayName() , payType, rpTradePaymentOrder.getFundIntoType()  , BigDecimal.valueOf(payWay.getPayRate()) ,  rpTradePaymentOrder.getOrderIp(),  rpTradePaymentOrder.getReturnUrl(),  rpTradePaymentOrder.getNotifyUrl(),  rpTradePaymentOrder.getRemark(),  rpTradePaymentOrder.getField1(),  rpTradePaymentOrder.getField2(),  rpTradePaymentOrder.getField3(),  rpTradePaymentOrder.getField4(),  rpTradePaymentOrder.getField5());
        rpTradePaymentRecordDao.insert(rpTradePaymentRecord);

        if (PayWayEnum.WEIXIN.name().equals(payWayCode)){//微信支付
            String appid = "";
            String mch_id = "";
            String partnerKey = "";
            if (FundInfoTypeEnum.MERCHANT_RECEIVES.name().equals(rpTradePaymentOrder.getFundIntoType())){//商户收款
                //根据资金流向获取配置信息
                RpUserPayInfo rpUserPayInfo = rpUserPayInfoService.getByUserNo(rpTradePaymentOrder.getMerchantNo(),payWayCode);
                appid = rpUserPayInfo.getAppId();
                mch_id = rpUserPayInfo.getMerchantId();
                partnerKey = rpUserPayInfo.getPartnerKey();
            }else if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(rpTradePaymentOrder.getFundIntoType())){//平台收款
                appid = WeixinConfigUtil.readConfig("appId");
                mch_id = WeixinConfigUtil.readConfig("mch_id");
                partnerKey = WeixinConfigUtil.readConfig("partnerKey");
            }

            WeiXinPrePay weiXinPrePay = sealWeixinPerPay(appid , mch_id , rpTradePaymentOrder.getProductName() ,rpTradePaymentOrder.getRemark() , rpTradePaymentRecord.getBankOrderNo() , rpTradePaymentOrder.getOrderAmount() ,  rpTradePaymentOrder.getOrderTime() ,  rpTradePaymentOrder.getOrderPeriod() , WeiXinTradeTypeEnum.NATIVE ,
                    rpTradePaymentRecord.getBankOrderNo() ,"" ,rpTradePaymentOrder.getOrderIp());
            String prePayXml = WeiXinPayUtils.getPrePayXml(weiXinPrePay, partnerKey);
            //调用微信支付的功能,获取微信支付code_url
            Map<String, Object> prePayRequest = WeiXinPayUtils.httpXmlRequest(WeixinConfigUtil.readConfig("prepay_url"), "POST", prePayXml);
            if (WeixinTradeStateEnum.SUCCESS.name().equals(prePayRequest.get("return_code")) && WeixinTradeStateEnum.SUCCESS.name().equals(prePayRequest.get("result_code"))) {
                String weiXinPrePaySign = WeiXinPayUtils.geWeiXintPrePaySign(appid, mch_id, weiXinPrePay.getDeviceInfo(), WeiXinTradeTypeEnum.NATIVE.name(), prePayRequest, partnerKey);
                String codeUrl = String.valueOf(prePayRequest.get("code_url"));
                LOG.info("预支付生成成功,{}",codeUrl);
                if (prePayRequest.get("sign").equals(weiXinPrePaySign)) {
                    rpTradePaymentRecord.setBankReturnMsg(prePayRequest.toString());
                    rpTradePaymentRecordDao.update(rpTradePaymentRecord);
                    scanPayResultVo.setCodeUrl(codeUrl);//设置微信跳转地址
                    scanPayResultVo.setPayWayCode(PayWayEnum.WEIXIN.name());
                    scanPayResultVo.setProductName(rpTradePaymentOrder.getProductName());
                    scanPayResultVo.setOrderAmount(rpTradePaymentOrder.getOrderAmount());
                }else{
                    throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR,"微信返回结果签名异常");
                }
            }else{
                throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR,"请求微信异常");
            }
        }else if (PayWayEnum.ALIPAY.name().equals(payWayCode)){//支付宝支付

            //把请求参数打包成数组
            Map<String, String> sParaTemp = new HashMap<String, String>();
            sParaTemp.put("service", AlipayConfigUtil.service);
            sParaTemp.put("partner", AlipayConfigUtil.partner);
            sParaTemp.put("seller_id", AlipayConfigUtil.seller_id);
            sParaTemp.put("_input_charset", AlipayConfigUtil.input_charset);
            sParaTemp.put("payment_type", AlipayConfigUtil.payment_type);
            sParaTemp.put("notify_url", AlipayConfigUtil.notify_url);
            sParaTemp.put("return_url", AlipayConfigUtil.return_url);
            sParaTemp.put("anti_phishing_key", AlipayConfigUtil.anti_phishing_key);
            sParaTemp.put("exter_invoke_ip", AlipayConfigUtil.exter_invoke_ip);
            sParaTemp.put("out_trade_no", rpTradePaymentRecord.getBankOrderNo());
            sParaTemp.put("subject", rpTradePaymentOrder.getProductName());
            sParaTemp.put("total_fee", String.valueOf(rpTradePaymentOrder.getOrderAmount().setScale(2,BigDecimal.ROUND_HALF_UP)));//小数点后两位
            sParaTemp.put("body", "");
            //获取请求页面数据
            String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");

            rpTradePaymentRecord.setBankReturnMsg(sHtmlText);
            rpTradePaymentRecordDao.update(rpTradePaymentRecord);
            scanPayResultVo.setCodeUrl(sHtmlText);//设置微信跳转地址
            scanPayResultVo.setPayWayCode(PayWayEnum.ALIPAY.name());
            scanPayResultVo.setProductName(rpTradePaymentOrder.getProductName());
            scanPayResultVo.setOrderAmount(rpTradePaymentOrder.getOrderAmount());

        }else{
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR,"错误的支付方式");
        }

        return scanPayResultVo;
    }

    /**
     * 完成扫码支付(支付宝即时到账支付)
     * @param payWayCode
     * @param notifyMap
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String completeScanPay(String payWayCode ,Map<String, String> notifyMap) {
        LOG.info("接收到{}支付结果{}",payWayCode,notifyMap);

        String returnStr = null;
        String bankOrderNo = notifyMap.get("out_trade_no");
        //根据银行订单号获取支付信息
        RpTradePaymentRecord rpTradePaymentRecord = rpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
        if (rpTradePaymentRecord == null){
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,",非法订单,订单不存在");
        }

        if (TradeStatusEnum.SUCCESS.name().equals(rpTradePaymentRecord.getStatus())){
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单为成功状态");
        }
        String merchantNo = rpTradePaymentRecord.getMerchantNo();//商户编号

        //根据支付订单获取配置信息
        String fundIntoType = rpTradePaymentRecord.getFundIntoType();//获取资金流入类型
        String partnerKey = "";

        if (FundInfoTypeEnum.MERCHANT_RECEIVES.name().equals(fundIntoType)){//商户收款
            //根据资金流向获取配置信息
            RpUserPayInfo rpUserPayInfo = rpUserPayInfoService.getByUserNo(merchantNo,PayWayEnum.WEIXIN.name());
            partnerKey = rpUserPayInfo.getPartnerKey();

        }else if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(fundIntoType)){//平台收款
            partnerKey = WeixinConfigUtil.readConfig("partnerKey");

            RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByUserNo(merchantNo);
            if (rpUserPayConfig == null){
                throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
            }
            //根据支付产品及支付方式获取费率
            RpPayWay payWay = rpPayWayService.getByPayWayTypeCode(rpUserPayConfig.getProductCode(), rpTradePaymentRecord.getPayWayCode(), rpTradePaymentRecord.getPayTypeCode());
            if(payWay == null){
                throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
            }
        }


        if(PayWayEnum.WEIXIN.name().equals(payWayCode)){
            String sign = notifyMap.remove("sign");
            if (WeiXinPayUtils.notifySign(notifyMap, sign, partnerKey)){//根据配置信息验证签名
                if (WeixinTradeStateEnum.SUCCESS.name().equals(notifyMap.get("result_code"))){//业务结果 成功
                    String timeEndStr = notifyMap.get("time_end");
                    Date timeEnd = null;
                    if (!StringUtil.isEmpty(timeEndStr)){
                        timeEnd =  DateUtils.getDateFromString(timeEndStr,"yyyyMMddHHmmss");//订单支付完成时间
                    }
                    completeSuccessOrder(rpTradePaymentRecord, notifyMap.get("transaction_id"),timeEnd, notifyMap.toString());
                    returnStr = "<xml>\n" +
                            "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                            "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                            "</xml>";
                }else{
                    completeFailOrder(rpTradePaymentRecord,notifyMap.toString());
                }
            }else{
                throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR,"微信签名失败");
            }

        }else if (PayWayEnum.ALIPAY.name().equals(payWayCode)){
            if(AlipayNotify.verify(notifyMap)){//验证成功
                String tradeStatus = notifyMap.get("trade_status");
                if(AliPayTradeStateEnum.TRADE_FINISHED.name().equals(tradeStatus)){
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                } else if (AliPayTradeStateEnum.TRADE_SUCCESS.name().equals(tradeStatus)){

                    String gmtPaymentStr = notifyMap.get("gmt_payment");//付款时间
                    Date timeEnd = null;
                    if(!StringUtil.isEmpty(gmtPaymentStr)){
                        timeEnd = DateUtils.getDateFromString(gmtPaymentStr,"yyyy-MM-dd HH:mm:ss");
                    }
                    completeSuccessOrder(rpTradePaymentRecord, notifyMap.get("trade_no"), timeEnd ,notifyMap.toString());
                    returnStr = "success";
                }else{
                    completeFailOrder(rpTradePaymentRecord,notifyMap.toString());
                    returnStr = "fail";
                }
            }else{//验证失败
                throw new TradeBizException(TradeBizException.TRADE_ALIPAY_ERROR,"支付宝签名异常");
            }
        }else{
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR,"错误的支付方式");
        }

        LOG.info("返回支付通道{}信息{}",payWayCode,returnStr);
        return returnStr;
    }

    /**
     * 支付成功后,又是会出现页面通知早与后台通知
     * 现页面通知,暂时不做数据处理功能,只生成页面通知URL
     * @param payWayCode
     * @param resultMap
     * @return
     */
    @Override
    public OrderPayResultVo completeScanPayByResult(String payWayCode, Map<String, String> resultMap) {

        OrderPayResultVo orderPayResultVo = new OrderPayResultVo();

        String bankOrderNo = resultMap.get("out_trade_no");
        //根据银行订单号获取支付信息
        RpTradePaymentRecord rpTradePaymentRecord = rpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
        if (rpTradePaymentRecord == null){
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,",非法订单,订单不存在");
        }

        orderPayResultVo.setOrderPrice(rpTradePaymentRecord.getOrderAmount());//订单金额
        orderPayResultVo.setProductName(rpTradePaymentRecord.getProductName());//产品名称

        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(rpTradePaymentRecord.getMerchantNo(), rpTradePaymentRecord.getMerchantOrderNo());

        String trade_status = resultMap.get("trade_status");
        //计算得出通知验证结果
        boolean verify_result = AlipayNotify.verify(resultMap);
        if(verify_result){//验证成功
            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                String resultUrl = getMerchantNotifyUrl(rpTradePaymentRecord, rpTradePaymentOrder, rpTradePaymentRecord.getReturnUrl(), TradeStatusEnum.SUCCESS);
                orderPayResultVo.setReturnUrl(resultUrl);
                orderPayResultVo.setStatus(TradeStatusEnum.SUCCESS.name());
            }else{
                String resultUrl = getMerchantNotifyUrl(rpTradePaymentRecord, rpTradePaymentOrder, rpTradePaymentRecord.getReturnUrl(), TradeStatusEnum.FAILED);
                orderPayResultVo.setReturnUrl(resultUrl);
                orderPayResultVo.setStatus(TradeStatusEnum.FAILED.name());
            }
        }else{
            throw new TradeBizException(TradeBizException.TRADE_ALIPAY_ERROR,"支付宝签名异常");
        }
        return orderPayResultVo;
    }


    /**
     * 支付订单实体封装
     * @param merchantNo    商户编号
     * @param merchantName  商户名称
     * @param productName   产品名称
     * @param orderNo   商户订单号
     * @param orderDate 下单日期
     * @param orderTime 下单时间
     * @param orderPrice    订单金额
     * @param payWay    支付方式
     * @param payWayName    支付方式名称
     * @param payType   支付类型
     * @param fundIntoType  资金流入类型
     * @param orderIp   下单IP
     * @param orderPeriod   订单有效期
     * @param returnUrl 页面通知地址
     * @param notifyUrl 后台通知地址
     * @param remark    支付备注
     * @param field1    扩展字段1
     * @param field2    扩展字段2
     * @param field3    扩展字段3
     * @param field4    扩展字段4
     * @param field5    扩展字段5
     * @return
     */
    private RpTradePaymentOrder sealRpTradePaymentOrder(String merchantNo, String merchantName ,String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice,
                                                        String payWay,String payWayName , PayTypeEnum payType , String fundIntoType , String orderIp, Integer orderPeriod, String returnUrl, String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5){

        RpTradePaymentOrder rpTradePaymentOrder = new RpTradePaymentOrder();
        rpTradePaymentOrder.setProductName(productName);//商品名称
        if (StringUtil.isEmpty(orderNo)){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"订单号错误");
        }

        rpTradePaymentOrder.setMerchantOrderNo(orderNo);//订单号

        if (orderPrice == null || orderPrice.doubleValue() <= 0){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"订单金额错误");
        }

        rpTradePaymentOrder.setOrderAmount(orderPrice);//订单金额

        if (StringUtil.isEmpty(merchantName)){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"商户名称错误");
        }
        rpTradePaymentOrder.setMerchantName(merchantName);//商户名称

        if (StringUtil.isEmpty(merchantNo)){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"商户编号错误");
        }
        rpTradePaymentOrder.setMerchantNo(merchantNo);//商户编号

        if (orderDate == null){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"下单日期错误");
        }
        rpTradePaymentOrder.setOrderDate(orderDate);//下单日期

        if (orderTime == null){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"下单时间错误");
        }
        rpTradePaymentOrder.setOrderTime(orderTime);//下单时间
        rpTradePaymentOrder.setOrderIp(orderIp);//下单IP
        rpTradePaymentOrder.setOrderRefererUrl("");//下单前页面

        if (StringUtil.isEmpty(returnUrl)){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"页面通知地址错误");
        }
        rpTradePaymentOrder.setReturnUrl(returnUrl);//页面通知地址

        if (StringUtil.isEmpty(notifyUrl)){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"后台通知地址错误");
        }
        rpTradePaymentOrder.setNotifyUrl(notifyUrl);//后台通知地址

        if (orderPeriod == null || orderPeriod <= 0){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"订单有效期错误");
        }
        rpTradePaymentOrder.setOrderPeriod(orderPeriod);//订单有效期

        Date expireTime = DateUtils.addMinute(orderTime,orderPeriod);//订单过期时间
        rpTradePaymentOrder.setExpireTime(expireTime);//订单过期时间
        rpTradePaymentOrder.setPayWayCode(payWay);//支付通道编码
        rpTradePaymentOrder.setPayWayName(payWayName);//支付通道名称
        rpTradePaymentOrder.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());//订单状态 等待支付

        if (payType != null){
            rpTradePaymentOrder.setPayTypeCode(payType.name());//支付类型
            rpTradePaymentOrder.setPayTypeName(payType.getDesc());//支付方式
        }
        rpTradePaymentOrder.setFundIntoType(fundIntoType);//资金流入方向

        rpTradePaymentOrder.setRemark(remark);//支付备注
        rpTradePaymentOrder.setField1(field1);//扩展字段1
        rpTradePaymentOrder.setField2(field2);//扩展字段2
        rpTradePaymentOrder.setField3(field3);//扩展字段3
        rpTradePaymentOrder.setField4(field4);//扩展字段4
        rpTradePaymentOrder.setField5(field5);//扩展字段5

        return rpTradePaymentOrder;
    }


    /**
     * 封装支付流水记录实体
     * @param merchantNo    商户编号
     * @param merchantName  商户名称
     * @param productName   产品名称
     * @param orderNo   商户订单号
     * @param orderPrice    订单金额
     * @param payWay    支付方式编码
     * @param payWayName    支付方式名称
     * @param payType   支付类型
     * @param fundIntoType  资金流入方向
     * @param feeRate   支付费率
     * @param orderIp   订单IP
     * @param returnUrl 页面通知地址
     * @param notifyUrl 后台通知地址
     * @param remark    备注
     * @param field1    扩展字段1
     * @param field2    扩展字段2
     * @param field3    扩展字段3
     * @param field4    扩展字段4
     * @param field5    扩展字段5
     * @return
     */
    private RpTradePaymentRecord sealRpTradePaymentRecord(String merchantNo, String merchantName ,String productName, String orderNo, BigDecimal orderPrice , String payWay , String payWayName , PayTypeEnum payType , String fundIntoType , BigDecimal feeRate ,
                                                          String orderIp , String returnUrl, String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5){
        RpTradePaymentRecord rpTradePaymentRecord = new RpTradePaymentRecord();
        rpTradePaymentRecord.setProductName(productName);//产品名称
        rpTradePaymentRecord.setMerchantOrderNo(orderNo);//产品编号

        String trxNo = buildNoService.buildTrxNo();
        rpTradePaymentRecord.setTrxNo(trxNo);//支付流水号

        String bankOrderNo = buildNoService.buildBankOrderNo();
        rpTradePaymentRecord.setBankOrderNo(bankOrderNo);//银行订单号
        rpTradePaymentRecord.setMerchantName(merchantName);
        rpTradePaymentRecord.setMerchantNo(merchantNo);//商户编号
        rpTradePaymentRecord.setOrderIp(orderIp);//下单IP
        rpTradePaymentRecord.setOrderRefererUrl("");//下单前页面
        rpTradePaymentRecord.setReturnUrl(returnUrl);//页面通知地址
        rpTradePaymentRecord.setNotifyUrl(notifyUrl);//后台通知地址
        rpTradePaymentRecord.setPayWayCode(payWay);//支付通道编码
        rpTradePaymentRecord.setPayWayName(payWayName);//支付通道名称
        rpTradePaymentRecord.setTrxType(TrxTypeEnum.EXPENSE.name());//交易类型
        rpTradePaymentRecord.setOrderFrom(OrderFromEnum.USER_EXPENSE.name());//订单来源
        rpTradePaymentRecord.setOrderAmount(orderPrice);//订单金额
        rpTradePaymentRecord.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());//订单状态 等待支付

        rpTradePaymentRecord.setPayTypeCode(payType.name());//支付类型
        rpTradePaymentRecord.setPayTypeName(payType.getDesc());//支付方式
        rpTradePaymentRecord.setFundIntoType(fundIntoType);//资金流入方向

        if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(fundIntoType)){//平台收款 需要修改费率 成本 利润 收入 以及修改商户账户信息
            BigDecimal orderAmount = rpTradePaymentRecord.getOrderAmount();//订单金额
            BigDecimal platIncome = orderAmount.multiply(feeRate).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP);  //平台收入 = 订单金额 * 支付费率(设置的费率除以100为真实费率)
            BigDecimal platCost = orderAmount.multiply(BigDecimal.valueOf(Double.valueOf(WeixinConfigUtil.readConfig("pay_rate")))).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP);//平台成本 = 订单金额 * 微信费率(设置的费率除以100为真实费率)
            BigDecimal platProfit = platIncome.subtract(platCost);//平台利润 = 平台收入 - 平台成本

            rpTradePaymentRecord.setFeeRate(feeRate);//费率
            rpTradePaymentRecord.setPlatCost(platCost);//平台成本
            rpTradePaymentRecord.setPlatIncome(platIncome);//平台收入
            rpTradePaymentRecord.setPlatProfit(platProfit);//平台利润

        }

        rpTradePaymentRecord.setRemark(remark);//支付备注
        rpTradePaymentRecord.setField1(field1);//扩展字段1
        rpTradePaymentRecord.setField2(field2);//扩展字段2
        rpTradePaymentRecord.setField3(field3);//扩展字段3
        rpTradePaymentRecord.setField4(field4);//扩展字段4
        rpTradePaymentRecord.setField5(field5);//扩展字段5
        return rpTradePaymentRecord;
    }


    /**
     * 封装预支付实体
     * @param appId 公众号ID
     * @param mchId    商户号
     * @param productName   商品描述
     * @param remark  支付备注
     * @param bankOrderNo   银行订单号
     * @param orderPrice    订单价格
     * @param orderTime 订单下单时间
     * @param orderPeriod   订单有效期
     * @param weiXinTradeTypeEnum   微信支付方式
     * @param productId 商品ID
     * @param openId    用户标识
     * @param orderIp   下单IP
     * @return
     */
    private WeiXinPrePay sealWeixinPerPay(String appId ,String mchId ,String productName ,String remark ,String bankOrderNo ,BigDecimal orderPrice , Date orderTime , Integer orderPeriod ,WeiXinTradeTypeEnum weiXinTradeTypeEnum ,
                                            String productId ,String openId ,String orderIp){
        WeiXinPrePay weiXinPrePay = new WeiXinPrePay();

        weiXinPrePay.setAppid(appId);
        weiXinPrePay.setMchId(mchId);
        weiXinPrePay.setBody(productName);//商品描述
        weiXinPrePay.setAttach(remark);//支付备注
        weiXinPrePay.setOutTradeNo(bankOrderNo);//银行订单号

        Integer totalFee = orderPrice.multiply(BigDecimal.valueOf(100d)).intValue();
        weiXinPrePay.setTotalFee(totalFee);//订单金额
        weiXinPrePay.setTimeStart(DateUtils.formatDate(orderTime, "yyyyMMddHHmmss"));//订单开始时间
        weiXinPrePay.setTimeExpire(DateUtils.formatDate(DateUtils.addMinute(orderTime, orderPeriod), "yyyyMMddHHmmss"));//订单到期时间
        weiXinPrePay.setNotifyUrl(WeixinConfigUtil.readConfig("notify_url"));//通知地址
        weiXinPrePay.setTradeType(weiXinTradeTypeEnum);//交易类型
        weiXinPrePay.setProductId(productId);//商品ID
        weiXinPrePay.setOpenid(openId);//用户标识
        weiXinPrePay.setSpbillCreateIp(orderIp);//下单IP

        return weiXinPrePay;
    }
}
