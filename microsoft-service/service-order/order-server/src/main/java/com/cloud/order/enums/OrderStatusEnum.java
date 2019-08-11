package com.cloud.order.enums;

import lombok.Getter;
import org.jetbrains.annotations.Contract;

/**
 * @author xuweizhi
 * @since 2019-05-20
 */
@Getter
public enum OrderStatusEnum {
    /**
     * 新订单
     */
    NEW(0, "新订单"),
    /**
     * 完结
     */
    FINISHED(1, "完结"),
    /**
     * 取消
     */
    CANCEL(2, "取消");

    private Integer code;

    private String message;

    @Contract(pure = true)
    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
