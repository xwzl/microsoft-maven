package com.cloud.order.exception;


import com.cloud.order.enums.ResultEnum;


/**
 * @author xuweizhi
 * @since  2019-5-20
 */
public class OrderException extends RuntimeException {

    private static final long serialVersionUID = -7852696997515581482L;

    private Integer code;

    public OrderException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public OrderException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
