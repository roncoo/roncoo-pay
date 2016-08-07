package com.roncoo.pay.trade.utils.alipay.f2fpay;

import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePayContentBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.service.AlipayMonitorService;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.alipay.demo.trade.utils.Utils;
import com.roncoo.pay.trade.entity.RoncooPayGoodsDetails;
import com.roncoo.pay.trade.enums.TradeStatusEnum;
import com.roncoo.pay.trade.vo.F2FPayResultVo;
import com.roncoo.pay.user.enums.FundInfoTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>功能说明:支付宝当面付功能提交类
 * </b>
 *
 * @author Peter
 *         <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
@Repository("aliF2FPaySubmit")
public class AliF2FPaySubmit {

    private static final Logger LOG = LoggerFactory.getLogger(AliF2FPaySubmit.class);

    private  AlipayTradeService tradeService;

    private  AlipayTradeService tradeWithHBService;

    private  AlipayMonitorService monitorService;

//    static {
//        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
//         *  Configs会读取classpath下的alipayrisk10.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
//         */
//        Configs.init("zfbinfo.properties");
//
//        /** 使用Configs提供的默认参数
//         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
//         */
//        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
//
//        /** 支付宝当面付2.0服务（集成了交易保障接口逻辑）**/
//        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();
//
//        /** 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数 否则使用代码中的默认设置 */
//        monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
//                .setCharset("GBK")
//                .setFormat("json")
//                .build();
//    }


    /**
     * 初始化当面付支付配置
     * @param appId
     * @param pid
     * @param privateKey
     * @param publicKey
     */
    public void initConfigs (String  fundInfoType,String appId , String pid , String privateKey , String publicKey){

        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的alipayrisk10.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        if (FundInfoTypeEnum.MERCHANT_RECEIVES.name().equals(fundInfoType)){//商户收款
            Configs.setAppid(appId);
            Configs.setPid(pid);
            Configs.setPrivateKey(privateKey);
            Configs.setPublicKey(publicKey);
        }

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        /** 支付宝当面付2.0服务（集成了交易保障接口逻辑）**/
        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();

        /** 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数 否则使用代码中的默认设置 */
        monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
                .setCharset("GBK")
                .setFormat("json")
                .build();
    }

    /**
     * 支付宝当面付
     * @param outTradeNo    商户请求支付订单号
     * @param subject   订单名称
     * @param body  订单描述
     * @param authCode  支付授权码
     * @param amount    支付金额
     * @param roncooPayGoodsDetailses
     */
    public Map<String , String > f2fPay(String outTradeNo , String subject , String body ,String authCode , BigDecimal amount , List<RoncooPayGoodsDetails> roncooPayGoodsDetailses) {
        Map<String , String > returnMap = new HashMap<String , String >();

        String totalAmount = amount.toString()  ;//订单金额
        String undiscountableAmount = "0.0";//默认折扣金额为0,建议由业务系统记录折扣金额,值传递给支付宝实际支付金额
        String storeId = "roncoo_pay_store_id"; // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        String providerId = "2088100200300400500";
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(providerId);
        String timeExpress = "5m";// 支付超时，线下扫码交易定义为5分钟

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        if (roncooPayGoodsDetailses != null && roncooPayGoodsDetailses.size() > 0){
            for (RoncooPayGoodsDetails roncooPayGoodsDetails : roncooPayGoodsDetailses){
                // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
                GoodsDetail goods = GoodsDetail.newInstance(roncooPayGoodsDetails.getGoodsId(), roncooPayGoodsDetails.getGoodsName(), roncooPayGoodsDetails.getSinglePrice(), roncooPayGoodsDetails.getNums());
                // 创建好一个商品后添加至商品明细列表
                goodsDetailList.add(goods);
            }
        }

        // 创建请求builder，设置请求参数
        AlipayTradePayContentBuilder builder = new AlipayTradePayContentBuilder()
                .setOutTradeNo(outTradeNo)
                .setSubject(subject)
                .setAuthCode(authCode)
                .setTotalAmount(totalAmount)
                .setStoreId(storeId)
                .setUndiscountableAmount(undiscountableAmount)
                .setBody(body)
                .setExtendParams(extendParams)
                .setGoodsDetailList(goodsDetailList)
                .setTimeExpress(timeExpress);

        // 调用tradePay方法获取当面付应答
        AlipayF2FPayResult result = tradeService.tradePay(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                LOG.info("支付宝支付成功: )");
                returnMap.put("status",TradeStatusEnum.SUCCESS.name());
                returnMap.put("bankTrxNo",result.getResponse().getTradeNo());
                break;

            case FAILED:
                LOG.error("支付宝支付失败!!!");
                returnMap.put("status",TradeStatusEnum.FAILED.name());
                break;

            case UNKNOWN:
                returnMap.put("status", TradeStatusEnum.WAITING_PAYMENT.name());//未知支付状态的订单,置为等待支付状态
                LOG.error("系统异常，订单状态未知!!!");
                break;

            default:
                returnMap.put("status",TradeStatusEnum.FAILED.name());
                LOG.error("不支持的交易状态，交易返回异常!!!");
                break;
        }

        returnMap.put("bankReturnMsg",result.getResponse() == null ? "" : result.getResponse().getBody());//银行返回信息
        return returnMap;
    }


    /**
     * 根据商户订单号查询商户
     * @param outTradeNo
     */
    public void f2fPayquery(String outTradeNo) {

        AlipayF2FQueryResult result = tradeService.queryTradeResult(outTradeNo);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                LOG.info("查询返回该订单支付成功: )");

                AlipayTradeQueryResponse response = result.getResponse();
                dumpResponse(response);

                LOG.info(response.getTradeStatus());
                if (Utils.isListNotEmpty(response.getFundBillList())) {
                    for (TradeFundBill bill : response.getFundBillList()) {
                        LOG.info(bill.getFundChannel() + ":" + bill.getAmount());
                    }
                }
                break;

            case FAILED:
                LOG.error("查询返回该订单支付失败或被关闭!!!");
                break;

            case UNKNOWN:
                LOG.error("系统异常，订单支付状态未知!!!");
                break;

            default:
                LOG.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
    }

    public void dumpResponse(AlipayResponse response) {
        if (response != null) {
            LOG.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                LOG.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(), response.getSubMsg()));
            }
            LOG.info("body:" + response.getBody());
        }
    }


}
