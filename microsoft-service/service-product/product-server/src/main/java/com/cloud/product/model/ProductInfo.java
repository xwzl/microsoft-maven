package com.cloud.product.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * <p>
 * 模型
 * </p>
 *
 * @author xuweizhi
 * @since 2019-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("product_info")
@ApiModel(value = "ProductInfo对象", description = "")
public class ProductInfo implements Serializable {

    @TableId(value = "product_id",type = IdType.AUTO)
    private String productId;

    @ApiModelProperty(value = "商品名称")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty(value = "单价")
    @TableField("product_price")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "库存")
    @TableField("product_stock")
    private Integer productStock;

    @ApiModelProperty(value = "描述")
    @TableField("product_description")
    private String productDescription;

    @ApiModelProperty(value = "小图")
    @TableField("product_icon")
    private String productIcon;

    @ApiModelProperty(value = "商品状态,0正常1下架")
    @TableField("product_status")
    private Integer productStatus;

    @ApiModelProperty(value = "类目编号")
    @TableField("category_type")
    private Integer categoryType;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

}
