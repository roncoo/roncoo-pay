package com.roncoo.pay.trade.entity;

/**
 * <b>功能说明:龙果支付商品明细描述
 * </b>
 *
 * @author Peter
 *         <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public class RoncooPayGoodsDetails {

    /** 构造函数 传入所需参数 **/
    public RoncooPayGoodsDetails (String goodsId , String goodsName ,Long singlePrice , Integer nums){
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.singlePrice = singlePrice;
        this.nums = nums;
    }

    /** 商品ID **/
    private String goodsId;

    /** 名称 **/
    private String goodsName;

    /** 单价 **/
    private Long singlePrice;

    /** 数量 **/
    private Integer nums;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(Long singlePrice) {
        this.singlePrice = singlePrice;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }
}
