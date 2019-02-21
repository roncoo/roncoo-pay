package com.roncoo.pay.trade.entity;

import com.roncoo.pay.common.core.entity.BaseEntity;

/**
 * 小微商户进件记录
 */
public class RpMicroSubmitRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 业务申请编号
     */
    private String businessCode;
    /**
     * 小微商户号
     */
    private String subMchId;
    /**
     * 身份证人像面照片，上传后的media_id
     */
    private String idCardCopy;
    /**
     * 身份证国徽面照片，上传后的media_id
     */
    private String idCardNational;
    /**
     * 身份证姓名(开户名称accountName、联系人姓名contact)
     */
    private String idCardName;
    /**
     * 身份证号码
     */
    private String idCardNumber;
    /**
     * 身份证有效期限，格式["1970-01-01","长期"]
     */
    private String idCardValidTime;
    /**
     * 开户银行(持卡人需同身份证名)
     */
    private String accountBank;
    /**
     * 开户银行省市编码（至少精确到市）
     */
    private String bankAddressCode;
    /**
     * 银行账号
     */
    private String accountNumber;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 门店省市编码
     */
    private String storeAddressCode;
    /**
     * 门店街道名称
     * 店铺详细地址，具体区/县及街道门牌号或大厦楼层，最长500个中文字符（无需填写省市信息）
     */
    private String storeStreet;
    /**
     * 门店门口照片，上传后的media_id
     */
    private String storeEntrancePic;
    /**
     * 店内环境照片，上传后的media_id
     */
    private String indoorPic;
    /**
     * 商户简称 2~30个字符
     * 将在支付完成页向买家展示，需与商家的实际经营场景相符
     */
    private String merchantShortname;
    /**
     * 客服电话
     * 将在交易记录中向买家展示，请确保电话畅通以便微信回拨确认
     */
    private String servicePhone;
    /**
     * 售卖商品/提供服务描述
     * 餐饮/线下零售/居民生活服务/休闲娱乐/交通出行/其他
     */
    private String productDesc;
    /**
     * 费率
     */
    private String rate;
    /**
     * 联系人手机
     */
    private String contactPhone;
    
    
    /*------*/
    /**
     * 身份证有效期
     */
    private String idCardValidTimeBegin;
    private String idCardValidTimeEnd;
    /*------*/

    public String getIdCardValidTimeBegin() {
        return idCardValidTimeBegin;
    }

    public void setIdCardValidTimeBegin(String idCardValidTimeBegin) {
        this.idCardValidTimeBegin = idCardValidTimeBegin;
    }

    public String getIdCardValidTimeEnd() {
        return idCardValidTimeEnd;
    }

    public void setIdCardValidTimeEnd(String idCardValidTimeEnd) {
        this.idCardValidTimeEnd = idCardValidTimeEnd;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getIdCardCopy() {
        return idCardCopy;
    }

    public void setIdCardCopy(String idCardCopy) {
        this.idCardCopy = idCardCopy;
    }

    public String getIdCardNational() {
        return idCardNational;
    }

    public void setIdCardNational(String idCardNational) {
        this.idCardNational = idCardNational;
    }

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getIdCardValidTime() {
        return idCardValidTime;
    }

    public void setIdCardValidTime(String idCardValidTime) {
        this.idCardValidTime = idCardValidTime;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getBankAddressCode() {
        return bankAddressCode;
    }

    public void setBankAddressCode(String bankAddressCode) {
        this.bankAddressCode = bankAddressCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddressCode() {
        return storeAddressCode;
    }

    public void setStoreAddressCode(String storeAddressCode) {
        this.storeAddressCode = storeAddressCode;
    }

    public String getStoreStreet() {
        return storeStreet;
    }

    public void setStoreStreet(String storeStreet) {
        this.storeStreet = storeStreet;
    }

    public String getStoreEntrancePic() {
        return storeEntrancePic;
    }

    public void setStoreEntrancePic(String storeEntrancePic) {
        this.storeEntrancePic = storeEntrancePic;
    }

    public String getIndoorPic() {
        return indoorPic;
    }

    public void setIndoorPic(String indoorPic) {
        this.indoorPic = indoorPic;
    }

    public String getMerchantShortname() {
        return merchantShortname;
    }

    public void setMerchantShortname(String merchantShortname) {
        this.merchantShortname = merchantShortname;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Override
    public String toString() {
        return "RpMicroSubmitRecord{" +
                "businessCode='" + businessCode + '\'' +
                ", subMchId='" + subMchId + '\'' +
                ", idCardCopy='" + idCardCopy + '\'' +
                ", idCardNational='" + idCardNational + '\'' +
                ", idCardName='" + idCardName + '\'' +
                ", idCardNumber='" + idCardNumber + '\'' +
                ", idCardValidTime='" + idCardValidTime + '\'' +
                ", accountBank='" + accountBank + '\'' +
                ", bankAddressCode='" + bankAddressCode + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", storeName='" + storeName + '\'' +
                ", storeAddressCode='" + storeAddressCode + '\'' +
                ", storeStreet='" + storeStreet + '\'' +
                ", storeEntrancePic='" + storeEntrancePic + '\'' +
                ", indoorPic='" + indoorPic + '\'' +
                ", merchantShortname='" + merchantShortname + '\'' +
                ", servicePhone='" + servicePhone + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", rate='" + rate + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", idCardValidTimeBegin='" + idCardValidTimeBegin + '\'' +
                ", idCardValidTimeEnd='" + idCardValidTimeEnd + '\'' +
                '}';
    }
}
