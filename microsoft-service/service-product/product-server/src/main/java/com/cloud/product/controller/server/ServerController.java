package com.cloud.product.controller.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务端暴露接口
 *
 * @author xuweizhi
 * @date 2019/05/22 10:14
 */
@RestController
@Slf4j
public class ServerController {

    @GetMapping("/server/msg")
    public String productMsg(String msg) {
        log.info("测试服务间通信调用：{}", msg);
        return msg;
    }

    @PostMapping("/msg")
    public String productMsg1() {
        log.info("测试服务间通信调用：{}");
        return "nice";
    }
}
