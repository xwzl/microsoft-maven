package com.cloud.getaway.filter;

import com.cloud.common.constant.RedisConstant;
import com.cloud.common.vo.CookieUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 权限校验,标注为 component 才会被加入缓存
 *
 * @author xuweizhi
 * @date 2019/05/23 21:30
 */
//@Component
public class AuthFilter extends ZuulFilter {

    @Autowired
    private RedisTemplate<String,String> redisGenericTemplate;

    /**
     * 过滤器的类型
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 拦截顺序越小，越先执行
     */
    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    /**
     * 是否被拦截
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * /order/create 只能买家访问
     * /order/finish 只能卖家访问
     * /product/list 都可访问
     */
    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        /*
          /order/create 只能买家访问(Cookie有openid)
          /order/finish 只能卖家访问(Cookie有token,并对应的redis)
          /product/list 都可  访问
         */
        if ("/order/order/create".equals(request.getRequestURI())) {
            Cookie cookie = CookieUtils.get(request, "openid");
            if (cookie == null || StringUtils.isEmpty(cookie.getValue())) {
                currentContext.setSendZuulResponse(false);
                currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }

        if ("/order/order/finish".equals(request.getRequestURI())) {
            Cookie cookie = CookieUtils.get(request, "token");
            if (cookie == null || StringUtils.isEmpty(cookie.getValue()) ||
                    StringUtils.isEmpty(redisGenericTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue())))) {
                currentContext.setSendZuulResponse(false);
                currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }

        return null;
    }
}
