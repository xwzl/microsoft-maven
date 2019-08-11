package com.cloud.order.message.stream;

import com.cloud.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * 注解@EnableBinding声明了这个应用程序绑定了2个通道：INPUT和OUTPUT。这2个通道是在接口StreamClient 中定义的
 * （Spring Cloud Stream默认设置）。所有通道都是配置在一个具体的消息中间件或绑定器中。
 *
 * @author xuweizhi
 * @date 2019/05/23 14:50
 */
@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

    /**
     * 监听输出通道,正常来说是个起作用
     */
    @StreamListener(value = StreamClient.OUTPUT)
    public void process(Object message) {
        log.info("StreamReceiver:{}", message);
    }


    /**
     * 接受消息，但是为什么这个会有消息
     */
    @StreamListener(value = StreamClient.OUTPUT)
    //@SendTo(StreamClient.INPUT2)//接收到消息后向 INPUT2 发送消息队列
    public void orderDTO(OrderDTO orderDTO) {
        log.info("StreamReceiver:{}", orderDTO);
    }

}
