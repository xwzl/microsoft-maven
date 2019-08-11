package com.cloud.order.enums;

import lombok.Getter;

/**
 * @author xuweizhi
 * @since 2019-05-20
 */
@Getter
public enum ResultEnum {

    /**
     * 状态
     */
    PARAM_ERROR(1, "参数错误"),
    /**
     * 购物车为空
     */
    CART_EMPTY(2, "购物车为空"),
    /**
     * 订单不存在
     */
    ORDER_NOT_EXIST(3, "订单不存在"),
    /**
     * 订单状态错误
     */
    ORDER_STATUS_ERROR(4, "订单状态错误"),
    /**
     * 订单详情不存在
     */
    ORDER_DETAIL_NOT_EXIST(5, "订单详情不存在");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
