package com.cloud.product.messsage;

import com.cloud.common.vo.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xuweizhi
 * @since 2019/07/11 16:07
 */
@Component
@Slf4j
public class RabbitInfoMessage {

    @RabbitListener(queuesToDeclare = @Queue("amqp_test"))
    public void productInfo(String message) {
        String json = (String)JsonUtil.fromJson(message, String.class);
        log.info(json);
    }
}
