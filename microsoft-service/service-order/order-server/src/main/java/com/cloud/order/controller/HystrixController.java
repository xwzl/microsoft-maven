package com.cloud.order.controller;

import com.cloud.common.vo.ResultVO;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务降级默认触发的方法：@DefaultProperties {@link #defaultFallback}
 *
 * @author xuweizhi
 * @date 2019/05/24 11:31
 */
@Slf4j
@RestController
@RequestMapping("/hystrix")
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    private static AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 服务降级，方法体中服务服务出现问题，调用fallback 进行错误提示,可用于抛出异常,考虑更多的是时间的延迟，默认的延迟时间是一秒触发降级
     * HystrixCommandProperties default_executionTimeoutInMilliseconds 中设置超时时间为3000ms 3s内服务不降级处理 配置文件也可配
     * 置。
     * <p>
     * 此外方法出现异常也可触发降级服务,服务降级方法返回值与方法返回值一致
     */
    @HystrixCommand(fallbackMethod = "fallBack")
    @GetMapping("/fallback")
    public String getProductInfo() {
        ServiceInstance product = loadBalancerClient.choose("PRODUCT");
        String url = String.format("http://%s:%s/product/list", product.getHost(), product.getPort());
        RestTemplate restTemplate = new RestTemplate();
        if (Math.random() > 0.5) {
            throw new RuntimeException("服务内部出错也会引发降级!");
        }
        // 目标服务出现异常或者请求超时将会触发服务降级
        ResultVO forObject = restTemplate.getForObject(url, ResultVO.class);
        return forObject.toString();
    }

    /**
     * 记得加 HystrixCommand 注解
     */
    @HystrixCommand
    @GetMapping("/defaultFallback")
    public String defaultFault() {
        if (Math.random() > 0.5) {
            throw new RuntimeException("服务内部出错也会引发降级!");
        }
        return "服务局降级默认方法测试";
    }

    /**
     * 请求超时触发降级,点进 @HystrixProperty 包，进入 hystrix 包，找到 {@link HystrixCommandProperties} 获取其中想要设置的属性值
     * 比如超时时间设置：双击属性，找到键值对 execution.isolation.thread.timeoutInMilliseconds 。
     */
    @HystrixCommand(commandProperties = {
            // 设置超时时间为 3 s，默认 1 秒触发降级服务,也可以在配置文件中配置超时时间,会覆盖掉默认的超时配置
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "7000")
    })
    @GetMapping("/listFor")
    public String listFor() {
        RestTemplate restTemplate = new RestTemplate();
        // 这个是 get 请求，因此传递的方法也是 get 方法
        List result = restTemplate.getForObject("http://localhost:8080/product/listFor", List.class);
        assert result != null;
        return result.toString();
    }

    /**
     * 服务熔断：重试机制和断路器模式 一般是某个服务故障或异常引起，类似“保险丝”，当某个异常被触发，直接熔断整个服务，而不是等到此服务超时。
     * <p>
     * 熔断现象:当个接口的错误率到达 60，其正确的请求服务也会被拒绝
     * <p>
     * http://localhost:8081/hystrix/serviceBlow?number=1 短时间内请求多次，达到阈值，会进行熔断
     * <p>
     * http://localhost:8081/hystrix/serviceBlow?number=2 熔断之后，访问失效
     */
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),                    // 设置熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),       //滚动窗口最小请求数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //断路器打开时间窗口
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")      // 60 表示失败60%的次数，断路器会被打开
    })
    @GetMapping("/serviceBlow")
    public String serviceBlow(@RequestParam("number") Integer number) {
        if (number % 2 == 0) {
            return "success";
        }
        RestTemplate restTemplate = new RestTemplate();
        log.info("before" + LocalDateTime.now().toString() + "当前计数:" + atomicInteger.getAndIncrement());
        List result = restTemplate.getForObject("http://localhost:8080/product/listFor", List.class);
        log.info("after" + LocalDateTime.now().toString() + "当前计数：" + atomicInteger.get());
        assert result != null;
        return result.toString();
    }

    /**
     * 服务降级会触发此方法
     */
    @NotNull
    @Contract(pure = true)
    private String fallBack() {
        return "服务降级会触发此方法！";
    }

    @NotNull
    @Contract(pure = true)
    private String defaultFallback() {
        return "服务降级默认触发的方法";
    }

}
