package com.cloud.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 服务端注册中心，@EnableDiscoveryClient
 *
 * @author xuweizhi
 * @date 2019/05/20 0:25
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.cloud.product.mapper")
@EnableSwagger2
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}
