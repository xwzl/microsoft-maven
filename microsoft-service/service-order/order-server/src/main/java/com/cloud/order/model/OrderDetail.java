package com.cloud.order.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author xuweizhi
 * @since 2019-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_detail")
@ApiModel(value = "Detail对象", description = "")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String detailId;

    private String orderId;

    private String productId;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "当前价格,单位分")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "数量")
    private Integer productQuantity;

    @ApiModelProperty(value = "小图")
    private String productIcon;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


}
