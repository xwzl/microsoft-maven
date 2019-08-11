package com.cloud.order.message;

import com.cloud.order.utils.JsonUtil;
import com.cloud.product.common.ProductInfoOutput;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xuweizhi
 * @date 2019/05/23 10:09
 */
@Component
@Slf4j
public class ProductInfoMessage {

    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void productInfo(String message) {
        List<ProductInfoOutput> productInfoOutputList = (List<ProductInfoOutput>) JsonUtil.fromJson(message,
                new TypeReference<List<ProductInfoOutput>>() {
                });
        for (ProductInfoOutput productInfoOutput : productInfoOutputList) {
            String key = String.format(PRODUCT_STOCK_TEMPLATE, productInfoOutput.getProductId());
            String value = String.valueOf(productInfoOutput.getProductStock());
            stringRedisTemplate.opsForValue().set(key, value);
        }
    }
}
