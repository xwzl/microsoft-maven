package com.cloud.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author xuweizhi
 * @date 2019/05/23 10:09
 */
@Slf4j
@Component
public class MyReceiver {

    /**
     * 监听消息队列的消息
     *
     * @param message 消息
     */
    @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    @RabbitHandler
    public void process(String message) {
        log.info("MqReceiver:{}", message);
    }

}
