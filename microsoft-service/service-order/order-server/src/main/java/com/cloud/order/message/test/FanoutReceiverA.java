package com.cloud.order.message.test;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xuweizhi
 * @date 2019/05/23 10:09
 */
//@Component
//@RabbitListener(queues = "fanout.A")
public class FanoutReceiverA {

    //@RabbitHandler
    //public void process(String msg) {
    //    System.out.println("FanoutReceiverA  : " + msg);
    //}

}