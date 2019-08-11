package com.cloud.product.client;

import com.cloud.product.common.DecreaseStockInput;
import com.cloud.product.common.ProductInfoOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Feign 方式访问服务,name 属性表示访问的服务名称
 *
 * @author xuweizhi
 * @date 2019/05/22 11:33
 */
@FeignClient(name = "product",contextId = "user",fallback = ProductClient.ProductClientBack.class)
public interface ProductClient {

    /**
     * 获取 product 服务的接口数据
     *
     * @return 获取数据
     */
    @PostMapping("/msg")
    String productMsg();

    /**
     * 调用远程服务,@RequestBody 注解使用后必须用 post
     */
    @PostMapping("/product/listForOrder")
    List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList);

    /**
     * 哈哈
     */
    @PostMapping("/product/decreaseStock")
    void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList);

    /**
     * 如果产生服务降级，将会调用下面的方法，在引入该方法的内部一定要加包扫描
     */
    @Component
    class ProductClientBack implements ProductClient {

        @Override
        public String productMsg() {
            return "productMsg 服务降级 !";
        }

        @Override
        public List<ProductInfoOutput> listForOrder(List<String> productIdList) {
            return null;
        }

        @Override
        public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {

        }
    }

}
