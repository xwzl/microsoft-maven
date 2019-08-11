package com.cloud.product.start.handler;

import com.cloud.common.exception.ApiException;
import com.cloud.common.exception.ServiceException;
import com.cloud.product.start.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xuweizhi
 * @since 2019-08-09
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ApiResult handleThrowable(Throwable e) {
        log.error("meet exception: ", e);
        return new ApiResult(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @ExceptionHandler(ApiException.class)
    public void handleApiException(ApiException e) throws IOException {
        log.error("meet ApiException: " + e.getCode() + " " + e.getMessage());
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        response.sendError(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public ApiResult<?> handleServiceException(ServiceException e) {
        log.error("meet ServiceException: " + e.getCode() + " " + e.getMessage());
        return new ApiResult(e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResult<?> illegalArgumentException(IllegalArgumentException e) {
        log.error("meet ServiceException: " + e.getMessage());
        return new ApiResult<>(-1, e.getMessage());
    }
}
