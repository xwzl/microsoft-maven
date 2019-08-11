package com.cloud.user.controller;

import com.cloud.common.dtos.AppleDTO;
import com.cloud.common.dtos.UserDTO;
import com.cloud.product.client.ParamsDemoClient;
import com.cloud.product.start.util.ApiResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xuweizhi
 * @since 2019-08-06
 */
@RestController
@RequestMapping("/feign")
@Slf4j
@Api("服务间调用参数测试")
public class FeignDemosController {

    @Autowired
    private ParamsDemoClient paramsDemoClient;

    @GetMapping("test1")
    public ApiResult<String> getTest1() {
        log.info(paramsDemoClient.test1Get());
        return new ApiResult<>("haha");
    }

    @GetMapping("test2")
    public void getTest2(String param1) {
        log.info(paramsDemoClient.test2Get(param1));
    }

    @GetMapping("test3")
    public void getTest3(String param1, String param2) {
        log.info(paramsDemoClient.test3Get(param1, param2));
    }

    @GetMapping("test4")
    public void getTest4(UserDTO userDTO, String param1, String param2) {
        log.info(paramsDemoClient.test4Get(userDTO, param1, param2));
    }

    @GetMapping("test5")
    public void getTest5(UserDTO userDTO, AppleDTO appleDTO) {
        log.info(paramsDemoClient.test5Get(userDTO, appleDTO));
    }

    @GetMapping("test6")
    public void getTest6(AppleDTO appleDTO) {
        List<AppleDTO> lists = new ArrayList();
        lists.add(appleDTO);
        log.info(paramsDemoClient.test6Get(lists));
    }

    @PostMapping("test7")
    public void postTest7(String param1, String param2) {
        log.info(paramsDemoClient.test7Post(param1, param2));
    }

    @PostMapping("test8")
    public void postTest8(AppleDTO appleDTO) {
        List<AppleDTO> lists = new ArrayList();
        lists.add(appleDTO);
        log.info(paramsDemoClient.test8Get(lists));
    }

    @GetMapping("test9")
    public void getTest9(UserDTO userDTO, AppleDTO appleDTO) {
        log.info(paramsDemoClient.test9Get(userDTO));
    }

    @GetMapping("test10")
    public void getTest10() {
        log.info(paramsDemoClient.returnDate(new Date()).toString());
    }

    @GetMapping("test11")
    public void getTest11() {
        log.info(paramsDemoClient.returnDate(LocalDateTime.now()).toString());
    }

    @GetMapping("test12")
    public void getTest12() {
        log.info(paramsDemoClient.returnDate(LocalDate.now()).toString());
    }
}
