package com.cloud.order.message.test;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuweizhi
 * @date 2019/05/23 10:09
 */
@Component
@Log4j2
public class AnnotationRabbitMq {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public String send() {
        amqpTemplate.convertAndSend("fruitOrder", "我是一个大老粗");
        return "我发送消息成功了";
    }

    /**
     * 手动添加myQueueue队列，负责一直报错
     */
    //@RabbitListener(queues = "myQueue")
    //public void handlerReceiveMsg(String msg) {
    //    log.info(msg);
    //}

    /**
     * 自动添加myQueue队列
     */
    @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    public void autoReceiveMsg1(String msg) {
        log.info("msg1:{}", msg);
    }

    @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    public void autoReceiveMsg(String msg) {
        log.info("msg2:{}", msg);
    }

    /**
     * 模拟消息分组 发送方
     * 通道 交换机 路由键 队列
     * 队列-生产者:队列通过绑定的路由键规则，把消息传递给交换机
     * 队列-消费者:交换机把接受的消息，通过路由键规则（与生产者路由键规则可一样，可不一样）发送给队列
     */
    public void sendOrder() {
        String msg = "来自服务器爸爸对你的问候！";
        // 参数：交换机，路由key, 消息
        amqpTemplate.convertAndSend("myOrder", "computer", msg);
    }

    /**
     * 数码供应商服务  接收消息
     * 消息发到交换机，交换机根据不同的key 发送到不同的队列
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerOrder")
    ))
    public void receiveComputer(String msg) {
        log.info("服务器:{}", msg);
    }

    /**
     * 水果供应商服务  接收消息
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "fruit",
            value = @Queue("fruitOrder")
    ))
    public void receiveFruit(String msg) {
        log.info(" receiveFruit service = {}", msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "wowomenmen", type = ExchangeTypes.TOPIC),
            key = "fruit",
            value = @Queue("fruitOrder")
    ))
    public void receiveFruits(String msg) {
        log.info("women={}", msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "wowomenmen", type = ExchangeTypes.TOPIC),
            key = "frudit",
            value = @Queue("computerOrder")
    ))
    public void receiveFruitss(String msg) {
        log.info("womens={}", msg);
    }


    /**
     * 3. 自动创建，queue 和 exchange 绑定 模拟分组
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void receive(String msg) {
        log.info("mqReceive = {}", msg);
    }

}
