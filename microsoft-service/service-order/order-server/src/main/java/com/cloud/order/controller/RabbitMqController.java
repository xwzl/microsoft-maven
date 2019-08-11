package com.cloud.order.controller;

import com.cloud.common.utils.ResultVOUtil;
import com.cloud.common.vo.JsonUtil;
import com.cloud.common.vo.ResultVO;
import com.cloud.order.message.test.AnnotationRabbitMq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 返送消息队列
 *
 * @author xuweizhi
 * @date 2019/05/23 10:12
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitMqController {

    private Logger log = LoggerFactory.getLogger(RabbitMqController.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    //@Autowired
    //private HelloSender1 helloSender;
    //
    //@Autowired
    //private TopicSender topicSender;
    //
    //@Autowired
    //private FanoutSender fanoutSender;
    //
    //@Autowired
    //private CallBackSender callBackSender;

    @Autowired
    private AnnotationRabbitMq annotationRabbitMq;

    @GetMapping("/death")
    public void testScheduledTask() {
        amqpTemplate.convertAndSend("deathExchange", "death", "这是定时任务测试！", message -> {
            message.getMessageProperties().setExpiration("3000");
            return message;
        });
    }

    @GetMapping("/test")
    public String helloTest() {
        amqpTemplate.convertAndSend("amqp_test", JsonUtil.toJson("这是一个好消息"));
        return "Amqp Test";
    }


    /**
     * 发送消息
     */
    @GetMapping("/sendMSG")
    public ResultVO<Object> sendMessage(String message) {
        amqpTemplate.convertAndSend("myQueue", message + LocalDateTime.now());
        log.info("Send message:{}", message);
        return ResultVOUtil.success(message);
    }

    //@GetMapping("/send")
    //public String sendMessage() {
    //    helloSender.send();
    //    return "发送成功";
    //}
    //
    //@GetMapping("/sends")
    //public String sendMessages() {
    //    for(int i=0;i<3;i++){
    //        helloSender.send("hellomsg:"+i);
    //    }
    //    return "发送成功ss";
    //}
    //
    //@GetMapping("/sendUser")
    //public String sendUser() {
    //    helloSender.sendUser();
    //    return "发送成功la";
    //}
    //
    //@GetMapping("/we")
    //public String we() {
    //    helloSender.sendY();
    //    return "发送成功la";
    //}
    //
    //@GetMapping("/topic")
    //public String topic() {
    //    topicSender.send();
    //    return "发送成功lasss";
    //}
    //
    ///**
    // * fanout exchange类型rabbitmq测试
    // */
    //@GetMapping("/fanoutTest")
    //public void fanoutTest() {
    //    System.out.println("xxx");
    //    fanoutSender.send();
    //}
    //
    //@GetMapping("/callback")
    //public void callbak() {
    //    callBackSender.send();
    //}
    //
    //@GetMapping("/anno")
    //public void anno() {
    //    annotationRabbitMq.send();
    //}
    //
    //@GetMapping("/order")
    //public void order() {
    //    annotationRabbitMq.sendOrder();
    //}
    //
    //@Scheduled(cron = "30 27 * * * *")
    //public void datePrint(){
    //    annotationRabbitMq.sendOrder();
    //    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    //}

    /*@RabbitListener(queues = "helloQueue")
    @RabbitHandler
    public void receiveMessage(String hello){
         log.info("This is my recivece message:{}",hello);
        System.out.println("Yeah!I have already received message !");
    }*/


}
