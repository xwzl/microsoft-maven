package com.cloud.product.start;

import com.cloud.product.start.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuweizhi
 * @since 2019-08-09
 */
@Configuration
public class GlobalExceptionsAutoConfigure {

    @Bean
    public GlobalExceptionHandler globalExceptionHandlers() {
        return new GlobalExceptionHandler();
    }
}
