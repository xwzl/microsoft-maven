package com.cloud.user.enums;

import lombok.Getter;

/**
 * @author xuweizhi
 * @since 2019-05-23
 */
@Getter
public enum ResultEnum {

    /**
     * 哈哈
     */
    LOGIN_FAIL(1, "登陆失败"),
    /**
     * 角色权限失误
     */
    ROLE_ERROR(2, "角色权限有误");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
