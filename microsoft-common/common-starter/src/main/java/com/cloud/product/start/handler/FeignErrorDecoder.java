package com.cloud.product.start.handler;

import com.alibaba.fastjson.JSON;
import com.cloud.common.exception.ServiceException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * @author 17847
 */
@Slf4j
public class FeignErrorDecoder {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserErrorDecoder();
    }

    /**
     * 自定义错误
     */
    public class UserErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = null;
            try {
                // 获取原始的返回内容
                String json = Util.toString(response.body().asReader());
                exception = new RuntimeException(json);
                // 将返回内容反序列化为Result，这里应根据自身项目作修改
                ExceptionResult result = JSON.parseObject(json, ExceptionResult.class);
                //// 业务异常抛出简单的 RuntimeException，保留原来错误信息
                if (!result.getStatus().equals("0")) {
                    //exception = new ApiException(Integer.parseInt(result.getStatus() ), result.getMessage());
                    exception = new ServiceException(Integer.parseInt(result.getStatus()), result.getMessage());
                }
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
            return exception;
        }
    }
}
