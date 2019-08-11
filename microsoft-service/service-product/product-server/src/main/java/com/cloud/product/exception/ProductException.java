package com.cloud.product.exception;

import com.cloud.product.enums.ResultEnum;
import org.jetbrains.annotations.NotNull;

/**
 * @author xuweizhi
 * @date 2019/05/22 14:12
 */
public class ProductException extends RuntimeException {

    private static final long serialVersionUID = 4871699362588531401L;

    private Integer code;

    public ProductException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ProductException(@NotNull ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
