package com.cloud.getaway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Component;

/**
 * 配置的动态注入
 *
 * @author xuweizhi
 * @date 2019/05/23 19:36
 */
@Component
public class ZuulConfig {

    /**
     * 配置动态注入
     */
    @ConfigurationProperties("zuul")
    @RefreshScope
    public ZuulProperties zuulProperties() {
        return new ZuulProperties();
    }

}
