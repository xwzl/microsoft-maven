package com.cloud.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务端注册中心，@EnableEurekaServer
 *
 * Java 11 版本与 tomcat 相关的 jar包好像被删除了，额外引入项目才能启动项目 .......
 *
 * @author xuweizhi
 * @date 2019/05/20 0:25
 */
@SpringBootApplication
@EnableEurekaServer
public class MicrosoftServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicrosoftServerApplication.class, args);
    }

}
