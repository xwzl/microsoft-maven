package com.cloud.product.client;

import com.cloud.common.dtos.AppleDTO;
import com.cloud.common.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author xuweizhi
 * @since 2019-08-06
 */
@FeignClient(name = "product")
@Component
public interface ParamsDemoClient {

    @GetMapping("/params/test1")
    String test1Get();

    /**
     * 单个参数传递需要加 @RequestParam 注解,暴露接口不需要加注解
     * <p>
     * 如果出现暴露接口加 @RequestParam 注解，服务调用时必须传入改参数
     *
     * @param param1
     * @return
     */
    @GetMapping("/params/test2")
    String test2Get(@RequestParam(value = "param1", required = false) String param1);

    /**
     * 包含 @Param 注解的参数，传输必须存在
     *
     * @param param1
     * @param param2
     * @return
     */
    @GetMapping("/params/test3")
    String test3Get(@RequestParam(value = "param1", required = false) String param1, @RequestParam("param2") String param2);

    /**
     * get 方法单个对象传递测试
     * <p>
     * UserDTO 中的参数必须存在，get 方法多参数请求时必须加 @RequestParam 参数
     * <p>
     * 单个对象传递加 @RequestBody 注解，抛异常 405 , 服务端控制台打印不支持 get 方法，支持 post 方法， put 和 delete 未测试
     *
     * @param userDTO
     * @param param1
     * @param param2
     * @return
     */
    @GetMapping("/params/test4")
    String test4Get(@SpringQueryMap UserDTO userDTO, @RequestParam("param1") String param1, @RequestParam("param2") String param2);

    @GetMapping("/params/test5")
    public String test5Get(@RequestParam("user") UserDTO user, @RequestParam("apple") AppleDTO apple);

    @GetMapping(value = "/params/test6"/*, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE*/)
    public String test6Get(@SpringQueryMap List<AppleDTO> lists);

    @PostMapping("/params/test7")
    public String test7Post(@RequestParam(value = "param1", required = false) String param1, @RequestParam("param2") String param2);

    @PostMapping("/params/test8")
    public String test8Get(@RequestBody List<AppleDTO> lists);

    @PostMapping("/params/test9")
    public String test9Get(@RequestBody UserDTO user);


    @GetMapping("/params/test10")
    public Date returnDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date);

    @GetMapping("/params/test11")
    public LocalDateTime returnDate(@RequestParam("localDateTime") LocalDateTime localDateTime);

    @GetMapping("/params/test12")
    public LocalDateTime returnDate(@RequestParam("localDate") LocalDate localDate);

}
