package com.cloud.order.dto;

import lombok.Data;
import org.jetbrains.annotations.Contract;

/**
 * @author xuweizhi
 * @date 2019/05/22 14:02
 */
@Data
public class CartDTO {

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    @Contract(pure = true)
    public CartDTO() {
    }

    @Contract(pure = true)
    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "CartDTO{" +
                "productId='" + productId + '\'' +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
