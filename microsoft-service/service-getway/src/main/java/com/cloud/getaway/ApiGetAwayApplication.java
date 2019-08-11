package com.cloud.getaway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author xuweizhi
 * @date 2019/05/23 18:46
 */
@SpringBootApplication
@EnableZuulProxy
public class ApiGetAwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGetAwayApplication.class, args);
    }
    
}
