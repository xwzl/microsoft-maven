package com.cloud.order.controller;

import com.cloud.order.dto.OrderDTO;
import com.cloud.order.message.stream.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * stream 有问题
 * @author xuweizhi
 * @date 2019/05/23 14:53
 */
@RestController
@RequestMapping("/stream")
public class StreamController {


    @Autowired
    private StreamClient streamClient;

    /**
     * 发送消息
     */
    @GetMapping("/stream")
    public void process() {
        // 特别注意 MessageBuilder 的包
        System.out.println("你好吧");
        streamClient.output().send(MessageBuilder.withPayload("now" + new Date()).build());
    }

    /**
     * 发送对象
     */
    @GetMapping("/orderDTO")
    public void stream() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("哈哈");
        // 特别注意 MessageBuilder 的包
        streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
    }


}
