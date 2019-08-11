package com.cloud.order.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * SpringCloud starter stream rabbit
 *
 * @author xuweizhi
 * @date 2019/05/23 14:47
 */
@Component
public interface StreamClient {

    /**
     * 接受消息方
     */
    String INPUT = "input";

    /**
     * 发送消息方
     */
    String OUTPUT = "output";

    /**
     * 定义消息的输出
     */
    @Input(INPUT)
    SubscribableChannel input();

    /**
     * 输出通道
     */
    @Output(OUTPUT)
    MessageChannel output();

}
