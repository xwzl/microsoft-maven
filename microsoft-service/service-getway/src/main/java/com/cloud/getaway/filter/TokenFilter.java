package com.cloud.getaway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * {@link FilterConstants} 查看常用常量信息，这个相当于一个简单的权限验证。
 *
 * @author xuweizhi
 * @date 2019/05/23 21:30
 */
//@Component
public class TokenFilter extends ZuulFilter {

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
     * 拦截逻辑
     */
    @Override
    public Object run() throws ZuulException {

        // 从当前线程中请求上下文信息
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        String token = request.getParameter("token");
        //if (StringUtils.isEmpty(token)) {
        //    // 终端路由操作
        //    requestContext.setSendZuulResponse(false);
        //    requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        //}

        return null;
    }
}
