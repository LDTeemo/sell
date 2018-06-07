package com.imooc.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0,"操作成功"),

    PARAM_ERROR(1,"参数错误"),

    PRODUCT_NOT_EXISTS(10,"商品信息不存在"),

    PRODUCT_STOCK_NOT_ENOUGH(11,"商品库存不够"),

    ORDER_NOT_EXISTS(12,"订单不存在"),

    ORDER_STATUS_ERROR(13,"订单状态错误"),

    ORDER_CANCEL_ERROR(14,"取消订单错误"),

    UPDATE_PRODUCT_STOCK_ERROR(15,"更新库存出错"),

    ORDER_FINISH_PAYSTATUS_ERROR(16,"完结订单时，支付状态不正确"),

    UPDATE_PAY_STATUS_ERROR(17,"更新支付状态出错"),

    WECHAT_AUTHORIZE_ERROR(19,"微信相关异常"),

    ORDER_OWNER_ERROR(18,"订单所有者非当前买家"),
    ORDER_PAID_MONEY_NOT_RIGHT(19,"订单金额与支付金额不一致"),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
