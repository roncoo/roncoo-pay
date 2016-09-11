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
package com.roncoo.pay.trade.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.roncoo.pay.common.core.entity.BaseEntity;
import com.roncoo.pay.common.core.enums.PayTypeEnum;
import com.roncoo.pay.common.core.enums.PayWayEnum;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.trade.enums.TradeStatusEnum;
import com.roncoo.pay.trade.enums.TrxTypeEnum;
import com.roncoo.pay.user.enums.FundInfoTypeEnum;

/**
 * <b>功能说明:商户支付记录实体类</b>
 * @author  Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public class RpTradePaymentRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 商品名称 **/
    private String productName;

    /** 商户订单号 **/
    private String merchantOrderNo;

    /** 支付流水号 **/
    private String trxNo;

    /** 银行订单号 **/
    private String bankOrderNo;

    /** 银行流水号 **/
    private String bankTrxNo;

    /** 商户名称 **/
    private String merchantName;

    /** 商户编号 **/
    private String merchantNo;

    /** 付款方编号 **/
    private String payerUserNo;

    /** 付款方名称 **/
    private String payerName;

    /** 付款方支付金额 **/
    private BigDecimal payerPayAmount;

    /** 付款方手续费 **/
    private BigDecimal payerFee;

    /** 付款方账户类型 **/
    private String payerAccountType;

    /** 收款方编号 **/
    private String receiverUserNo;

    /** 收款方名称 **/
    private String receiverName;

    /** 收款方收款金额 **/
    private BigDecimal receiverPayAmount;

    /** 收款方手续费 **/
    private BigDecimal receiverFee;

    /** 收款方账户类型 **/
    private String receiverAccountType;

    /** 下单IP **/
    private String orderIp;

    /** 页面链接 **/
    private String orderRefererUrl;

    /** 订单金额 **/
    private BigDecimal orderAmount;

    /** 平台收入 初始创建默认为-**/
    private BigDecimal platIncome = BigDecimal.ZERO;

    /** 费率 **/
    private BigDecimal feeRate = BigDecimal.ZERO;

    /** 平台成本 **/
    private BigDecimal platCost = BigDecimal.ZERO;

    /** 平台利润 **/
    private BigDecimal platProfit = BigDecimal.ZERO;

    /** 支付结果页面通知地址 **/
    private String returnUrl;

    /** 支付结果后台通知地址 **/
    private String notifyUrl;

    /** 支付通道编码 **/
    private String payWayCode;

    /** 支付通道名称 **/
    private String payWayName;

    /** 成功支付时间 **/
    private Date paySuccessTime;

    /** 完成时间 **/
    private Date completeTime;

    /** 是否退款 **/
    private String isRefund;

    /** 退款次数 **/
    private Integer refundTimes;

    /** 成功退款金额 **/
    private BigDecimal successRefundAmount;

    /** 业务类型 **/
    private String trxType;

    /** 订单来源 **/
    private String orderFrom;

    /** 支付方式类型编码 **/
    private String payTypeCode;

    /** 支付方式类型名称 **/
    private String payTypeName;

    /** 资金流入类型 **/
    private String fundIntoType;

    /** 备注 **/
    private String remark;

    /** 银行返回信息 **/
    private String bankReturnMsg;

    /** 扩展字段1 **/
    private String field1;

    /** 扩展字段2 **/
    private String field2;

    /** 扩展字段3 **/
    private String field3;

    /** 扩展字段4 **/
    private String field4;

    /** 扩展字段5 **/
    private String field5;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
    }

    public String getTrxNo() {
        return trxNo;
    }

    public void setTrxNo(String trxNo) {
        this.trxNo = trxNo == null ? null : trxNo.trim();
    }

    public String getBankOrderNo() {
        return bankOrderNo;
    }

    public void setBankOrderNo(String bankOrderNo) {
        this.bankOrderNo = bankOrderNo == null ? null : bankOrderNo.trim();
    }

    public String getBankTrxNo() {
        return bankTrxNo;
    }

    public void setBankTrxNo(String bankTrxNo) {
        this.bankTrxNo = bankTrxNo == null ? null : bankTrxNo.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getPayerUserNo() {
        return payerUserNo;
    }

    public void setPayerUserNo(String payerUserNo) {
        this.payerUserNo = payerUserNo == null ? null : payerUserNo.trim();
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName == null ? null : payerName.trim();
    }

    public BigDecimal getPayerPayAmount() {
        return payerPayAmount;
    }

    public void setPayerPayAmount(BigDecimal payerPayAmount) {
        this.payerPayAmount = payerPayAmount;
    }

    public BigDecimal getPayerFee() {
        return payerFee;
    }

    public void setPayerFee(BigDecimal payerFee) {
        this.payerFee = payerFee;
    }

    public String getPayerAccountType() {
        return payerAccountType;
    }

    public void setPayerAccountType(String payerAccountType) {
        this.payerAccountType = payerAccountType == null ? null : payerAccountType.trim();
    }

    public String getReceiverUserNo() {
        return receiverUserNo;
    }

    public void setReceiverUserNo(String receiverUserNo) {
        this.receiverUserNo = receiverUserNo == null ? null : receiverUserNo.trim();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public BigDecimal getReceiverPayAmount() {
        return receiverPayAmount;
    }

    public void setReceiverPayAmount(BigDecimal receiverPayAmount) {
        this.receiverPayAmount = receiverPayAmount;
    }

    public BigDecimal getReceiverFee() {
        return receiverFee;
    }

    public void setReceiverFee(BigDecimal receiverFee) {
        this.receiverFee = receiverFee;
    }

    public String getReceiverAccountType() {
        return receiverAccountType;
    }

    public void setReceiverAccountType(String receiverAccountType) {
        this.receiverAccountType = receiverAccountType == null ? null : receiverAccountType.trim();
    }

    public String getOrderIp() {
        return orderIp;
    }

    public void setOrderIp(String orderIp) {
        this.orderIp = orderIp == null ? null : orderIp.trim();
    }

    public String getOrderRefererUrl() {
        return orderRefererUrl;
    }

    public void setOrderRefererUrl(String orderRefererUrl) {
        this.orderRefererUrl = orderRefererUrl == null ? null : orderRefererUrl.trim();
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getPlatIncome() {
        return platIncome;
    }

    public void setPlatIncome(BigDecimal platIncome) {
        this.platIncome = platIncome;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public BigDecimal getPlatCost() {
        return platCost;
    }

    public void setPlatCost(BigDecimal platCost) {
        this.platCost = platCost;
    }

    public BigDecimal getPlatProfit() {
        return platProfit;
    }

    public void setPlatProfit(BigDecimal platProfit) {
        this.platProfit = platProfit;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl == null ? null : returnUrl.trim();
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl == null ? null : notifyUrl.trim();
    }

    public String getPayWayCode() {
        return payWayCode;
    }

    public void setPayWayCode(String payWayCode) {
        this.payWayCode = payWayCode == null ? null : payWayCode.trim();
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName == null ? null : payWayName.trim();
    }

    public Date getPaySuccessTime() {
        return paySuccessTime;
    }

    public void setPaySuccessTime(Date paySuccessTime) {
        this.paySuccessTime = paySuccessTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(String isRefund) {
        this.isRefund = isRefund == null ? null : isRefund.trim();
    }


    public BigDecimal getSuccessRefundAmount() {
        return successRefundAmount;
    }

    public void setSuccessRefundAmount(BigDecimal successRefundAmount) {
        this.successRefundAmount = successRefundAmount;
    }


    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom == null ? null : orderFrom.trim();
    }

    public String getPayTypeCode() {
        return payTypeCode;
    }

    public void setPayTypeCode(String payTypeCode) {
        this.payTypeCode = payTypeCode == null ? null : payTypeCode.trim();
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName == null ? null : payTypeName.trim();
    }

    public String getFundIntoType() {
        return fundIntoType;
    }

    public void setFundIntoType(String fundIntoType) {
        this.fundIntoType = fundIntoType == null ? null : fundIntoType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4 == null ? null : field4.trim();
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5 == null ? null : field5.trim();
    }

    public Integer getRefundTimes() {
        return refundTimes;
    }

    public void setRefundTimes(Integer refundTimes) {
        this.refundTimes = refundTimes;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }

    public String getBankReturnMsg() {
        return bankReturnMsg;
    }

    public void setBankReturnMsg(String bankReturnMsg) {
        this.bankReturnMsg = bankReturnMsg;
    }
    
    public String getTrxTypeDesc() {
    	return TrxTypeEnum.getEnum(this.getTrxType()).getDesc();
    }
    
    public String getPayWayNameDesc() {
    	return PayWayEnum.getEnum(this.getPayWayCode()).getDesc()+"-"+PayTypeEnum.getEnum(this.getPayTypeCode()).getDesc();
    }
    
    public String getStatusDesc() {
    	return TradeStatusEnum.getEnum(this.getStatus()).getDesc();
    }
    
    public String getCreateTimeDesc() {
    	return DateUtils.formatDate(this.getCreateTime(), "yyyy-MM-dd HH:mm");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(super.getId());
        sb.append(", version=").append(super.getVersion());
        sb.append(", createTime=").append(super.getCreateTime());
        sb.append(", status=").append(super.getStatus());
        sb.append(", editor=").append(super.getEditor());
        sb.append(", creater=").append(super.getCreater());
        sb.append(", editTime=").append(super.getEditTime());
        sb.append(", productName=").append(productName);
        sb.append(", merchantOrderNo=").append(merchantOrderNo);
        sb.append(", trxNo=").append(trxNo);
        sb.append(", bankOrderNo=").append(bankOrderNo);
        sb.append(", bankTrxNo=").append(bankTrxNo);
        sb.append(", merchantName=").append(merchantName);
        sb.append(", merchantNo=").append(merchantNo);
        sb.append(", payerUserNo=").append(payerUserNo);
        sb.append(", payerName=").append(payerName);
        sb.append(", payerPayAmount=").append(payerPayAmount);
        sb.append(", payerFee=").append(payerFee);
        sb.append(", payerAccountType=").append(payerAccountType);
        sb.append(", receiverUserNo=").append(receiverUserNo);
        sb.append(", receiverName=").append(receiverName);
        sb.append(", receiverPayAmount=").append(receiverPayAmount);
        sb.append(", receiverFee=").append(receiverFee);
        sb.append(", receiverAccountType=").append(receiverAccountType);
        sb.append(", orderIp=").append(orderIp);
        sb.append(", orderRefererUrl=").append(orderRefererUrl);
        sb.append(", orderAmount=").append(orderAmount);
        sb.append(", platIncome=").append(platIncome);
        sb.append(", feeRate=").append(feeRate);
        sb.append(", platCost=").append(platCost);
        sb.append(", platProfit=").append(platProfit);
        sb.append(", returnUrl=").append(returnUrl);
        sb.append(", notifyUrl=").append(notifyUrl);
        sb.append(", payWayCode=").append(payWayCode);
        sb.append(", payWayName=").append(payWayName);
        sb.append(", paySuccessTime=").append(paySuccessTime);
        sb.append(", completeTime=").append(completeTime);
        sb.append(", isRefund=").append(isRefund);
        sb.append(", refundTimes=").append(refundTimes);
        sb.append(", successRefundAmount=").append(successRefundAmount);
        sb.append(", trxType=").append(trxType);
        sb.append(", orderFrom=").append(orderFrom);
        sb.append(", payTypeCode=").append(payTypeCode);
        sb.append(", payTypeName=").append(payTypeName);
        sb.append(", fundIntoType=").append(fundIntoType);
        sb.append(", remark=").append(remark);
        sb.append(", bankReturnMsg=").append(bankReturnMsg);
        sb.append(", field1=").append(field1);
        sb.append(", field2=").append(field2);
        sb.append(", field3=").append(field3);
        sb.append(", field4=").append(field4);
        sb.append(", field5=").append(field5);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}