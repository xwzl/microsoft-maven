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
public enum ProductStatusEnum {

    /**
     * 再架
     */
    UP(0, "在架"),
    /**
     * 下架
     */
    DOWN(1, "下架"),
    ;

    private Integer code;

    private String message;

    @Contract(pure = true)
    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
