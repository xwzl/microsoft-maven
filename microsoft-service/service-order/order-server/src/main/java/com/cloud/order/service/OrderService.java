package com.cloud.order.service;

import com.cloud.order.model.OrderMaster;
import com.cloud.order.dto.OrderDTO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuweizhi
 * @since 2019-05-20
 */
public interface OrderService extends BaseService<OrderMaster> {

    /**
     * 创建订单对象
     *
     * @param orderDTO 订单对象
     * @return 订单对象
     */
    OrderDTO create(OrderDTO orderDTO);


    /**
     * 完结
     */
    OrderDTO finish(String openid);

}
