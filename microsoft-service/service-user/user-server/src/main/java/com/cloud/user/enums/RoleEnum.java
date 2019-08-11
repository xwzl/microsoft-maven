package com.cloud.user.enums;

import lombok.Getter;

/**
 * @author xuweizhi
 * @since 2019-05-23
 */
@Getter
public enum RoleEnum {

    /**
     * 哈哈
     */
    BUYER(1,"买家"),
    /**
     * 哈哈
     */
    SELLER(2,"卖家");

    private Integer code;

    private String message;

    RoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
