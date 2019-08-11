package com.cloud.common.enums;

import lombok.Getter;
import org.jetbrains.annotations.Contract;

/**
 * @author xuweizhi
 * @since 2019-02-09
 */
@Getter
public enum ServiceCodeEnum {

    /* -------------------------------------*/
    /*                                      */
    /*        1:成功    0:失败              */
    /*                                      */
    /* -------------------------------------*/

    /**
     * 成功
     */
    SUCCESS(0, "成功"),

    /**
     * 失败
     */
    FAIL(-1, "失败"),

    /* -------------------------------------*/
    /*                                      */
    /*        400-550 服务器相关错误        */
    /*                                      */
    /* -------------------------------------*/
    /**
     * 无效请求
     */
    BAD_REQUEST(400, "无效请求"),

    /**
     * 无效鉴权信息
     */
    UNAUTHORIZED(401, "无效授权信息"),

    /**
     * 无权访问此资源
     */
    FORBIDDEN(403, "无权访问此资源"),

    /**
     * 无效的访问地址
     */
    INVALID_URL(404, "无效的访问地址"),

    /**
     * 访问控制
     */
    LIMIT(406, "访问过于频繁，请稍后再试"),

    /**
     * 数据完整性异常 数据过长  过短
     */
    LENGTH_REQUIRED(411, "数据完整性异常"),
    /**
     * 参数错误
     */
    PARAM_ERROR(412, "参数错误，缺少必要参数"),
    /**
     * 不支持的登录方式
     */
    NOT_SUPPORT_LOGIN_CHANNEL(414, "不支持的登录方式"),
    /**
     * 不支持的请求方式
     */
    NOT_SUPPORTED(415, "不支持的请求方式"),
    /**
     * 数据格式错误
     */
    DATA_FORMAT_ERROR(416, "数据格式错误"),
    /* -------------------------------------*/
    /*                                      */
    /*        600-650 业务错误状态码        */
    /*                                      */
    /* -------------------------------------*/
    /**
     * 账户不存在
     */
    ACCOUNT_NOT_FOUND(601, "账户不存在"),
    /**
     * 密码错误
     */
    PASSWORD_ERROR(602, "密码错误"),
    /**
     * 手机号不存在
     */
    PHONE_NOT_FOUND(603, "手机号不存在"),
    /**
     * 验证码错误
     */
    VERIFICATION_CODE_ERROR(604, "验证码错误"),
    /**
     * 验证码失效
     */
    VERIFICATION_CODE_OUT_OF_TIME(605, "验证码失效"),
    /**
     * 登录类型错误
     */
    LOGIN_TYPE_ERROR(606, "登录类型错误"),
    /**
     * 登录来源错误
     */
    FROM_TYPE_ERROR(607, "登录来源错误"),
    /**
     * 账号已存在
     */
    ACCOUNT_REGISTERED(608, "账号已存在"),
    /**
     * 重复操作
     */
    REPEAT_OPERATION(609, "重复操作"),
    /* -------------------------------------*/
    /*                                      */
    /*        900-650 认证错误状态码        */
    /*                                      */
    /* -------------------------------------*/
    /**
     * 客户端不存在
     */
    AUTH_CLIENT_NOT_EXISTS(900, "客户端不存在"),
    /**
     * 客户端密码错误
     */
    AUTH_CLIENT_PASSWORD_ERROR(901, "客户端密码错误"),
    /**
     * 登录类型错误
     */
    AUTH_LOGIN_TYPE_ERROR(902, "不支持登录的类型"),
    /**
     * Token已过期
     */
    ACCESS_TOKEN_EXPIRED(903, "会话已过期"),
    /**
     * 该账号已在其他设备上登录
     */
    ACCESS_TOKEN_IN_OTHER_DEVICES(904, "该账号已在其他设备上登录"),
    /**
     * Token验证失败
     */
    ACCESS_TOKEN_VALIDATE_ERROR(905, "Token验证失败"),
    /**
     * Token验证失败
     */
    ACCESS_REFRESH_TOKEN_VALIDATE_ERROR(906, "Refresh Token验证失败"),
    /**
     * 未知错误，认证失败
     */
    AUTH_FAIL(906, "未知错误，认证失败");


    private final int code;
    private final String message;

    @Contract(pure = true)
    ServiceCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
