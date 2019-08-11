package com.cloud.product.service.impl;

import com.cloud.common.vo.JsonUtil;
import com.cloud.product.common.DecreaseStockInput;
import com.cloud.product.common.ProductInfoOutput;
import com.cloud.product.enums.ResultEnum;
import com.cloud.product.exception.ProductException;
import com.cloud.product.mapper.ProductInfoMapper;
import com.cloud.product.model.ProductInfo;
import com.cloud.product.service.ProductInfoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuweizhi
 */
@Service
public class ProductInfoServiceImpl extends BaseServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoMapper.selectByMap(new HashMap<>());
    }

    @Override
    @Transactional
    public void decreaseStock(@NotNull List<DecreaseStockInput> decreaseStockInputs) {

        List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockInputs);

        //发送mq消息
        List<ProductInfoOutput> productInfoOutputList = productInfoList.stream().map(e -> {
            ProductInfoOutput output = new ProductInfoOutput();
            BeanUtils.copyProperties(e, output);
            return output;
        }).collect(Collectors.toList());

        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoOutputList));
    }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputs) {
        List<ProductInfo> productInfoList = new ArrayList<>();
        for (DecreaseStockInput stockInput : decreaseStockInputs) {
            ProductInfo productInfo = productInfoMapper.selectById(stockInput.getProductId());

            //判断商品是否存在
            if (productInfo == null) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //库存是否足够
            Integer result = productInfo.getProductStock() - stockInput.getProductQuantity();
            if (result < 0) {
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            productInfoMapper.updateById(productInfo);
            productInfoList.add(productInfo);
            //amqpTemplate.convertAndSend("product", JsonUtil.toJson(productInfo));
        }
        return productInfoList;
    }


}