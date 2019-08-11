package com.cloud.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.vo.ResultVO;
import com.cloud.common.constant.CookieConstant;
import com.cloud.common.constant.RedisConstant;
import com.cloud.user.enums.ResultEnum;
import com.cloud.user.enums.RoleEnum;
import com.cloud.user.model.UserInfo;
import com.cloud.user.service.UserInfoService;
import com.cloud.common.vo.CookieUtils;
import com.cloud.user.until.ResultVoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xuweizhi
 * @since 2019-05-23
 */
@RestController
@RequestMapping("/user")
public class LoginController {


    @Autowired
    private UserInfoService userService;

    @Autowired
    private RedisTemplate<String, String> redisGenericTemplate;

    /**
     * 买家登录
     */
    @GetMapping("/buyer")
    private ResultVO buyer(@RequestParam("openid") String openid, HttpServletResponse response) {
        //1.openid和数据库里的数据进行匹配
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        UserInfo userInfo = userService.getOne(queryWrapper);
        if (userInfo == null) {
            return ResultVoUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2.判断角色
        if (!userInfo.getRole().equals(RoleEnum.BUYER.getCode())) {
            return ResultVoUtil.error(ResultEnum.ROLE_ERROR);
        }
        //3.cookie设置openid==abc
        CookieUtils.set(response, CookieConstant.OPENID, openid, CookieConstant.EXPIRE);
        return ResultVoUtil.success();
    }

    /**
     * 卖家登陆
     */
    @GetMapping("/seller")
    private ResultVO seller(@RequestParam("openid") String openid, HttpServletResponse response, HttpServletRequest request) {

        //1.openid和数据库里的数据进行匹配
        Cookie cookie = CookieUtils.get(request, CookieConstant.TOKEN);
        String result = null;
        if(cookie != null){
            result = redisGenericTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue()));
        }


        if (cookie != null && StringUtils.isNotEmpty(result)) {
            return ResultVoUtil.success();
        }

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        UserInfo userInfo = userService.getOne(queryWrapper);
        if (userInfo == null) {
            return ResultVoUtil.error(ResultEnum.LOGIN_FAIL);
        }

        //2.判断角色
        if (!userInfo.getRole().equals(RoleEnum.SELLER.getCode())) {
            return ResultVoUtil.error(ResultEnum.ROLE_ERROR);
        }

        //3.设置Cookie key=UUID value=xyz
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.EXPIRE;

        redisGenericTemplate.opsForValue().set(
                String.format(RedisConstant.TOKEN_TEMPLATE, token),
                openid,
                expire,
                TimeUnit.SECONDS);

        //4.cookie设置openid==abc
        CookieUtils.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);

        return ResultVoUtil.success();
    }


}
