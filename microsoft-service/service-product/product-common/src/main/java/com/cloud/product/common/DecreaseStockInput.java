package com.cloud.product.common;

import lombok.Data;

/**
 * @author xuweizhi
 * @date 2019/05/22 15:40
 */
@Data
public class DecreaseStockInput {

    private String productId;

    private Integer productQuantity;

    public DecreaseStockInput() {

    }

    public DecreaseStockInput(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
