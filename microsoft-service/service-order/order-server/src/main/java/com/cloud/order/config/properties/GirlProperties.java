package com.cloud.order.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author xuweizhi
 * @date 2019/05/22 22:54
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "girl")
public class GirlProperties {

    private String name;

    private Integer age;

    @Override
    public String toString() {
        return "GirlProperties{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}
