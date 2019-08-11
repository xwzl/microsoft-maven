package com.cloud.order.enums;

import lombok.Getter;

/**
 * 支付枚举类
 *
 * @author xuweizhi
 * @since 2019-05-20
 */
@Getter
public enum PayStatusEnum {

    /**
     * 等待支付
     */
    WAIT(0, "等待支付"),
    /**
     * 支付成功
     */
    SUCCESS(1, "支付成功");

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
