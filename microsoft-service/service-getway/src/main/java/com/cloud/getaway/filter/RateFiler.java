package com.cloud.getaway.filter;

import com.cloud.getaway.exception.RateLimiterException;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * 令牌算法进行限流操作
 *
 * @author xuweizhi
 * @date 2019/05/23 21:59
 */
@Component
public class RateFiler extends ZuulFilter {

    /**
     * 每秒钟往桶中放多少个令牌
     */
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 如果没有获取到令牌桶，则放弃请求
        if (!RATE_LIMITER.tryAcquire()) {
            throw new RateLimiterException();
        }
        return null;
    }

}
