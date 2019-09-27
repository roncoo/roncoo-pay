package com.roncoo.pay.trade.enums;

public enum AuthStatusEnum {
    /**
     * 鉴权成功
     */
    SUCCESS("鉴权成功"),

    /**
     * 鉴权中
     */
    WAITING_AUTH("鉴权中"),

    /**
     * 鉴权失败
     */
    FAILED("鉴权失败");

    private AuthStatusEnum(String desc) {
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }
}
