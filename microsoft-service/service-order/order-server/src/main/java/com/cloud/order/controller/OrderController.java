package com.cloud.order.controller;


import com.cloud.common.utils.ResultVOUtil;
import com.cloud.common.vo.ResultVO;
import com.cloud.order.converter.OrderForm2OrderDTOConverter;
import com.cloud.order.dto.OrderDTO;
import com.cloud.order.enums.ResultEnum;
import com.cloud.order.exception.OrderException;
import com.cloud.order.form.OrderForm;
import com.cloud.order.service.OrderService;
import com.cloud.product.common.DecreaseStockInput;
import com.cloud.product.common.ProductInfoOutput;
import com.cloud.product.client.ProductClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xuweizhi
 * @since 2019-05-20
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductClient productClient;

    /**
     * 1. 参数校验
     * 2. 查询商品信息
     * 3. 计算总价
     * 4. 扣库存
     * 5. 订单入库
     *
     * 加入熔断后不会找不到服务
     */
    @PostMapping("/create")
    @HystrixCommand/*(commandProperties = @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "12000"))*/
    public ResultVO<Object> create(@Valid OrderForm orderForm, @NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车信息为空, orderForm={}", orderForm);
            throw new OrderException(ResultEnum.CART_EMPTY);
        }

        OrderDTO result = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());

        return ResultVOUtil.success(map);
    }

    @GetMapping("/productList")
    public List<ProductInfoOutput> getProductList() {
        List<ProductInfoOutput> productInfos = productClient.listForOrder(Arrays.asList("157875196366160022", "157875227953464068"));
        return productInfos;
    }

    @GetMapping("/decreaseStock")
    public void decreaseStock() {
        productClient.decreaseStock(Collections.singletonList(new DecreaseStockInput("157875196366160022", 2)));
    }

    @PostMapping("/finish")
    public ResultVO<OrderDTO> finish(@RequestParam("orderId") String orderId) {
        return ResultVOUtil.success(orderService.finish(orderId));
    }

}
