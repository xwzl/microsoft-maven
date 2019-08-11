package com.cloud.order.controller.client;

import com.cloud.product.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 服务间通信调用,从 server 端获取数据信息
 *
 * @author xuweizhi
 * @date 2019/05/22 10:08
 */
@RestController
@RequestMapping("/client")
@Slf4j
public class ClientController {

    /**
     * 框架内置对象
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 直接把 LoadBalancerClient 配置在 restTemplate
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * feign 方式调用方法
     */
    @Autowired
    private ProductClient productClient;


    /**
     * 1. 第一种通信方式,缺点是访问地址写死，部署项目时并不知道具体地址
     */
    @GetMapping("/http1")
    public String getProductMsg1() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("http://localhost:8080/server/msg?msg='How are you'", String.class);
        log.info("response={}", response);
        return response;
    }

    /**
     * 第二种调用方式，通过loadBalancerClient 获取注册中心的 application name 获取 url 参数
     */
    @GetMapping("/http2")
    public String getProductMsg2() {

        // 1. 选择 product 服务,构造 uri
        ServiceInstance product = loadBalancerClient.choose("PRODUCT");
        String uri = String.format("http://%s:%s", product.getHost(), product.getPort()) + "/server/msg";
        RestTemplate restTemplate = new RestTemplate();

        // 2. 获取数据
        String response = restTemplate.getForObject(uri, String.class);
        log.info("response={}", response);

        return response;
    }

    /**
     * 第三种种调用方式
     */
    @GetMapping("/http3")
    public String getProductMsg3() {
        return restTemplate.getForObject("http://PRODUCT/server/msg?msg='123456'", String.class);
    }

    /**
     * feign 方式调用方法
     */
    @PostMapping("/feign")
    public String feign() {
        String s = productClient.productMsg();
        return s;
    }


}
