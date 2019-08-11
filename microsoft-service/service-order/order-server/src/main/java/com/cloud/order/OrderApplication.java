package com.cloud.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 服务端注册中心，@EnableDiscoveryClient
 * <p>
 * Hystrix 服务： @EnableCircuitBreaker
 *
 * SpringCloudApplication 效果和以下注解相同
 *
 * <ul>
 *      <li>@SpringBootApplication</li>
 *      <li>@EnableDiscoveryClient</li>
 *      <li>@EnableCircuitBreaker</li>
 * </ul>
 *
 * 仪表盘
 * http://localhost:8081/hystrix，然后配置监控路径 http://localhost:8081/actuator/hystrix.stream 监控的服务是 order ,时间是 1000 ms
 *
 * @author xuweizhi
 * @since  2019/05/20 0:25
 */
@SpringCloudApplication
@EnableSwagger2
@EnableScheduling
@EnableCaching
@EnableHystrixDashboard
@EnableFeignClients(basePackages = "com.cloud.product.client")
@ComponentScan(basePackages = {"com.cloud.product.client","com.cloud.order","com.cloud.common"})
@MapperScan("com.cloud.order.mapper")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
