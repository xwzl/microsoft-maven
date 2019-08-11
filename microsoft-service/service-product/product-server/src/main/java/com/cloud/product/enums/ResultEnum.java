package com.cloud.product.enums;

import lombok.Getter;
import org.jetbrains.annotations.Contract;


/**
 * 商品再架状态
 *
 * @author xuweizhi
 * @since 2019-5-20
 */
@Getter
public enum ResultEnum {

    /**
     * 如题
     */
    PRODUCT_NOT_EXIST(1, "商品不存在"),
    /**
     * 如题
     */
    PRODUCT_STOCK_ERROR(2, "库存有误");

    private Integer code;

    private String message;

    @Contract(pure = true)
    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
