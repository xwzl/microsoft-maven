package com.cloud.product.start.util;

import com.cloud.common.enums.ServiceCodeEnum;
import com.cloud.common.exception.ServiceException;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xuweizhi
 * @since 2019-08-09
 */
@Data
public class ApiResult<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    public ApiResult() {
        this.code = ServiceCodeEnum.SUCCESS.getCode();
        this.message = ServiceCodeEnum.SUCCESS.getMessage();
        this.data = null;
    }

    public ApiResult(T data) {
        this.code = ServiceCodeEnum.SUCCESS.getCode();
        this.message = ServiceCodeEnum.SUCCESS.getMessage();
        this.data = data;
    }

    public ApiResult(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResult(ServiceCodeEnum sce) {
        this.code = sce.getCode();
        this.message = sce.getMessage();
        this.data = null;
    }

    public ApiResult(ServiceCodeEnum sce, T data) {
        this.code = sce.getCode();
        this.message = sce.getMessage();
        this.data = data;
    }

    public ApiResult(ServiceException e) {
        this.code = e.getCode();
        this.message = e.getMessage();
        this.data = null;
    }

    public ApiResult(ServiceException e, T data) {
        this.code = e.getCode();
        this.message = e.getMessage();
        this.data = data;
    }

    public boolean isSucceed() {
        return code == ServiceCodeEnum.SUCCESS.getCode();
    }

}

