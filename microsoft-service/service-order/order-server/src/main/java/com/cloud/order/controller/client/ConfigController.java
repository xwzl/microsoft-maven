package com.cloud.order.controller.client;

import com.cloud.order.config.properties.GirlProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RefreshScope 配置线刷配置
 *
 * @author xuweizhi
 * @date 2019/05/22 22:36
 */
@RestController
@RefreshScope
@Slf4j
public class ConfigController {

    @Value("${env}")
    public String env;

    @Autowired
    private GirlProperties girlProperties;

    @GetMapping("/env")
    public String getEnv() {
        return env;
    }

    @GetMapping("/env1")
    public String getGirl() {
        log.info(girlProperties.toString());
        return girlProperties.toString();
    }

}
