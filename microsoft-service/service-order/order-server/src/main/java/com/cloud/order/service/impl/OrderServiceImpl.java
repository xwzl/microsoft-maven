package com.cloud.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.order.dto.OrderDTO;
import com.cloud.order.enums.OrderStatusEnum;
import com.cloud.order.enums.PayStatusEnum;
import com.cloud.order.enums.ResultEnum;
import com.cloud.order.exception.OrderException;
import com.cloud.order.mapper.OrderDetailMapper;
import com.cloud.order.mapper.OrderMasterMapper;
import com.cloud.order.model.OrderDetail;
import com.cloud.order.model.OrderMaster;
import com.cloud.order.service.OrderService;
import com.cloud.order.utils.KeyUtil;
import com.cloud.product.common.DecreaseStockInput;
import com.cloud.product.common.ProductInfoOutput;
import com.cloud.product.client.ProductClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 服务实现类
 *
 * @author xuweizhi
 * @since 2019-05-20
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderMasterMapper, OrderMaster> implements OrderService {

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductClient productClient;

    @Override
    @Transactional
    public OrderDTO create(@NotNull OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();

        //查询商品信息(调用商品服务)
        List<String> productIdList = orderDTO.getOrderDetailList().stream().map(OrderDetail::getProductId).collect(Collectors.toList());
        List<ProductInfoOutput> productInfoList = productClient.listForOrder(productIdList);

        //计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProductInfoOutput productInfo : productInfoList) {
                if (productInfo.getProductId().equals(orderDetail.getProductId())) {
                    //单价*数量
                    orderAmount = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    //订单详情入库
                    orderDetailMapper.insert(orderDetail);
                }
            }
        }

        //扣库存(调用商品服务)
        List<OrderDetail> detailList = orderDTO.getOrderDetailList();
        Stream<OrderDetail> detailStream = detailList.stream();
        DecreaseStockInput decreaseStockInput = new DecreaseStockInput();
        Stream<DecreaseStockInput> decreaseStockInputStream = detailStream.map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()));
        List<DecreaseStockInput> decreaseStockInputList = decreaseStockInputStream.collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);

        //订单入库
        OrderMaster orderMaster = new OrderMaster();

        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMaster.setCreateTime(LocalDateTime.now());
        orderMaster.setUpdateTime(LocalDateTime.now());

        orderMasterMapper.insert(orderMaster);
        return orderDTO;
    }


    @Override
    @Transactional
    public OrderDTO finish(String openid) {
        //1.查询订单
        QueryWrapper<OrderMaster> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",openid);
        OrderMaster order = orderMasterMapper.selectOne(queryWrapper);

        //订单不存在
        if (order == null) {
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.判断订单状态
        if (!OrderStatusEnum.NEW.getCode().equals(order.getOrderStatus())) {
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3.修改订单状态为完结
        order.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterMapper.updateById(order);
        //4.查询订单详情
        Map<String, Object> params = new HashMap<>();
        params.put("order_id", order.getOrderId());
        List<OrderDetail> orderDetails = orderDetailMapper.selectByMap(params);
        if (CollectionUtils.isEmpty(orderDetails)) {
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        orderDTO.setOrderDetailList(orderDetails);

        return orderDTO;
    }


}
