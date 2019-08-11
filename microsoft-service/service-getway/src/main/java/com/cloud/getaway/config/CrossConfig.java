package com.cloud.getaway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 跨域配置统一处理
 *
 * @author xuweizhi
 * @date 2019/05/24 10:55
 */
@Configuration
public class CrossConfig {

    @Bean
    public CorsFilter corsFilter() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        // Cookies 允许跨域
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        // 允许所有域名
        config.setAllowedOrigins(Arrays.asList("*"));

        // 跨域时间
        config.setMaxAge(300L);

        // 对所有路径进行跨域设置
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }


}
