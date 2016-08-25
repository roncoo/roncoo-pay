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

import com.roncoo.pay.trade.vo.F2FPayResultVo;
import com.roncoo.pay.trade.vo.OrderPayResultVo;
import com.roncoo.pay.trade.vo.RpPayGateWayPageShowVo;
import com.roncoo.pay.trade.vo.ScanPayResultVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * <b>功能说明:交易模块管理接口</b>
 * @author  Peter
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
     * @param payKey    商户支付Key
     * @param productName   产品名称
     * @param orderNo   商户订单号
     * @param orderDate 下单日期
     * @param orderTime 下单时间
     * @param orderPrice    订单金额(元)
     * @param payWayCode    支付方式
     * @param orderIp   下单IP
     * @param orderPeriod   订单有效期(分钟)
     * @param returnUrl 支付结果页面通知地址
     * @param notifyUrl 支付结果后台通知地址
     * @param remark    支付备注
     * @param field1    扩展字段1
     * @param field2    扩展字段2
     * @param field3    扩展字段3
     * @param field4    扩展字段4
     * @param field5    扩展字段5
     */
    public ScanPayResultVo initDirectScanPay(String payKey, String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String payWayCode, String orderIp, Integer orderPeriod, String returnUrl
            , String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5);


    /**
     * 条码支付,对应的是支付宝的条码支付或者微信的刷卡支付
     * @param payKey    商户支付key
     * @param authCode  支付授权码
     * @param productName   产品名称
     * @param orderNo   商户订单号
     * @param orderDate 下单日期
     * @param orderTime 下单时间
     * @param orderPrice    订单金额(元)
     * @param payWayCode    支付方式
     * @param orderIp   下单IP
     * @param remark    支付备注
     * @param field1    扩展字段1
     * @param field2    扩展字段2
     * @param field3    扩展字段3
     * @param field4    扩展字段4
     * @param field5    扩展字段5
     * @return
     */
    public F2FPayResultVo f2fPay(String payKey,String authCode ,  String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String payWayCode, String orderIp,  String remark, String field1, String field2, String field3, String field4, String field5);
    /**
     * 完成扫码支付(支付宝即时到账支付)
     * @param payWayCode
     * @param notifyMap
     * @return
     */
    public String completeScanPay(String payWayCode ,Map<String, String> notifyMap);

    /**
     *
     * @param payWayCode
     * @param resultMap
     * @return
     */
    public OrderPayResultVo completeScanPayByResult(String payWayCode , Map<String , String> resultMap);

    /**
     * 初始化非直连扫码支付数据,非直连扫码支付初始化方法规则
     * 1:根据(商户编号 + 商户订单号)确定订单是否存在
     * 1.1:如果订单不存在,创建支付订单
     * 2:获取商户支付配置,跳转到支付网关,选择支付方式
     * @param payKey  商户支付KEY
     * @param productName   产品名称
     * @param orderNo   商户订单号
     * @param orderDate 下单日期
     * @param orderTime 下单时间
     * @param orderPrice    订单金额(元)
     * @param orderIp   下单IP
     * @param orderPeriod   订单有效期(分钟)
     * @param returnUrl 支付结果页面通知地址
     * @param notifyUrl 支付结果后台通知地址
     * @param remark    支付备注
     * @param field1    扩展字段1
     * @param field2    扩展字段2
     * @param field3    扩展字段3
     * @param field4    扩展字段4
     * @param field5    扩展字段5
     */
    public RpPayGateWayPageShowVo initNonDirectScanPay(String payKey, String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String orderIp, Integer orderPeriod, String returnUrl
            , String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5);


    /**
     * 非直连扫码支付,选择支付方式后,去支付
     * @param payKey
     * @param orderNo
     * @param payWayCode
     * @return
     */
    public ScanPayResultVo toNonDirectScanPay(String payKey , String orderNo , String payWayCode);
}
