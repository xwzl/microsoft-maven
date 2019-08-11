package com.cloud.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.utils.ResultVOUtil;
import com.cloud.common.vo.ProductInfoVO;
import com.cloud.common.vo.ProductVO;
import com.cloud.common.vo.ResultVO;
import com.cloud.product.common.DecreaseStockInput;
import com.cloud.product.model.ProductCategory;
import com.cloud.product.model.ProductInfo;
import com.cloud.product.service.ProductCategoryService;
import com.cloud.product.service.ProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xuweizhi
 * @since 2019-05-20
 */
@RestController
@RequestMapping("/product")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 4. 构造数据，允许跨域访问,由服务网关统一处理
     */
    @GetMapping("/list")
    @CrossOrigin(allowCredentials = "true")
    public ResultVO getProductInfo(HttpServletRequest request) {

        // 1. 查询所有再架的商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        // 2. 查取类目type列表
        List<Integer> collect = productInfoList.stream().map(ProductInfo::getCategoryType).filter(integer -> integer == 1).collect(Collectors.toList());

        // 3. 查询类目
        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("category_type", collect);
        List<ProductCategory> productCategories = productCategoryService.list(queryWrapper);

        //4. 构造数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategories) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }

    /**
     * 获取商品列表
     */
    @PostMapping("/listForOrder")
    public List listForOrder(@RequestBody List<String> productIdList) {


        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id", productIdList);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return productInfoService.list(queryWrapper);
    }

    /**
     * 一定要注意啊，服务调用要用 Post ,RestTemplate 测试用 get
     */
    @GetMapping("/listFor")
    public List listFor() {

        // 延时模拟
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();

        queryWrapper.in("product_id", Collections.singletonList("157875227953464068"));

        return productInfoService.list(queryWrapper);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList) {
        productInfoService.decreaseStock(decreaseStockInputList);
    }

    @GetMapping("/zuul")
    public String getZuul() {
        return "如果被静止路由，zuul 无法访问，只能通过自身服务访问！";
    }


}
