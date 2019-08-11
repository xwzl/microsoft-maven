package com.cloud.user;

import com.cloud.product.start.handler.FeignErrorDecoder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 服务端注册中心，@EnableDiscoveryClient
 *
 * @author xuweizhi
 * @date 2019/05/20 0:25
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@EnableCaching
@MapperScan("com.cloud.user.mapper")
@EnableHystrixDashboard
//@EnableFeignClients(basePackages = "com.cloud.product.client")
@EnableFeignClients(basePackages = "com.cloud.product.client",defaultConfiguration = FeignErrorDecoder.class)
@ComponentScan(basePackages = "com.cloud")
public class UserApplication {

    public static void main(String[] args) {

        SpringApplication.run(UserApplication.class, args);
    }

}
