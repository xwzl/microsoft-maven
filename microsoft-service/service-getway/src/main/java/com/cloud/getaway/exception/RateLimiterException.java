package com.cloud.getaway.exception;

/**
 * @author xuweizhi
 * @date 2019/05/23 22:03
 */
public class RateLimiterException extends RuntimeException {

    private static final long serialVersionUID = 712576320525660401L;

    public RateLimiterException() {
        super("请求次数过多，请摆正你的态度。");
    }

}