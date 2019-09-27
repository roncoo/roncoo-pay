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
package com.roncoo.pay.trade.service;

import com.roncoo.pay.trade.bo.F2FPayRequestBo;
import com.roncoo.pay.trade.bo.ProgramPayRequestBo;
import com.roncoo.pay.trade.bo.ScanPayRequestBo;
import com.roncoo.pay.trade.vo.*;
import com.roncoo.pay.user.entity.RpUserPayConfig;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * <b>功能说明:交易模块管理接口</b>
 *
 * @author Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public interface RpTradePaymentManagerService {

    /**
     * 初始化直连扫码支付数据,直连扫码支付初始化方法规则
     * 1:根据(商户编号 + 商户订单号)确定订单是否存在
     * 1.1:如果订单不存在,创建支付订单
     * 2:创建支付记录
     * 3:根据相应渠道方法
     * 4:调转到相应支付渠道扫码界面
     **/

    public ScanPayResultVo initDirectScanPay(RpUserPayConfig rpUserPayConfig , ScanPayRequestBo scanPayRequestBo);


    /**
     * 条码支付,对应的是支付宝的条码支付或者微信的刷卡支付
     *
     * @return
     */
    public F2FPayResultVo f2fPay(RpUserPayConfig rpUserPayConfig , F2FPayRequestBo f2FPayRequestBo);

    /**
     * 完成扫码支付(支付宝即时到账支付)
     *
     * @param payWayCode
     * @param notifyMap
     * @return
     */
    public String completeScanPay(String payWayCode, Map<String, String> notifyMap);

    /**
     * @param payWayCode
     * @param resultMap
     * @return
     */
    public OrderPayResultVo completeScanPayByResult(String payWayCode, Map<String, String> resultMap);


    /**
     * 初始化非直连扫码支付数据,非直连扫码支付初始化方法规则
     *      1:根据(商户编号 + 商户订单号)确定订单是否存在
     *       1.1:如果订单不存在,创建支付订单
     *      2:获取商户支付配置,跳转到支付网关,选择支付方式
     * @param rpUserPayConfig
     * @param scanPayRequestBo
     * @return
     */
    public RpPayGateWayPageShowVo initNonDirectScanPay(RpUserPayConfig rpUserPayConfig , ScanPayRequestBo scanPayRequestBo);

    /**
     * 非直连扫码支付,选择支付方式后,去支付
     *
     * @param payKey
     * @param orderNo
     * @param payType
     * @param numberOfStages
     * @return
     */
    public ScanPayResultVo toNonDirectScanPay(String payKey, String orderNo, String payType ,Integer numberOfStages);

    /**
     * 处理交易记录
     * 如果交易记录是成功或者本地未支付,查询上游已支付,返回TRUE
     * 如果上游支付结果为未支付,返回FALSE
     *
     * @param bankOrderNo 银行订单号
     * @return
     */
    public boolean processingTradeRecord(String bankOrderNo);


    /** 小程序支付
     * @return
     */
    ProgramPayResultVo programPay(RpUserPayConfig rpUserPayConfig , ProgramPayRequestBo programPayRequestBo);

    /**
     * 初始化鉴权
     *
     * @param productName
     * @param orderPrice
     * @param orderIp
     * @param paramVo
     * @return
     */
    AuthInitResultVo initDirectAuth(String productName, BigDecimal orderPrice, String orderIp, AuthParamVo paramVo, RpUserPayConfig userPayConfig);

    /**
     * 初始化小程序鉴权
     *
     * @param productName
     * @param orderPrice
     * @param orderIp
     * @param paramVo
     * @return
     */
    AuthProgramInitResultVo initProgramDirectAuth(String productName, BigDecimal orderPrice, String orderIp, AuthProgramInitParamVo paramVo, RpUserPayConfig userPayConfig);

    /**
     * 用户鉴权
     *
     * @param merchantNo
     * @param orderNo
     * @return
     */
    AuthResultVo userAuth(String merchantNo, String orderNo);
}
