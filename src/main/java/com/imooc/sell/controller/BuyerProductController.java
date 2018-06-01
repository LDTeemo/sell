package com.imooc.sell.controller;

import com.imooc.sell.VO.ProductInfoVO;
import com.imooc.sell.VO.ProductVO;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.dataObject.ProductCategory;
import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.repositoy.ProductCategoryRepository;
import com.imooc.sell.service.impl.ProductInfoServiceImpl;
import com.imooc.sell.utils.ResultVOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    /**
     * 获取所有在售商品
     * 根据api，分析返回对象的数据特点，拼装返回数据；
     * @return
     */
    @GetMapping("/list")
    public ResultVO getOrderList(){
        //获取所有的商品
        List<ProductInfo> productInfoList = productInfoService.getUpProduct();
        //java8之前获取集合元素放
//        List<Integer > categpryTypeList = new ArrayList<>();
//        for (ProductInfo p: productInfoList
//             ) {
//            categpryTypeList.add(p.getCategoryType());
//        }
        //应该获取所有商品的 使用java8的lambda表达式
        List<Integer > categpryTypeList = productInfoList.stream().
                map(e -> e.getCategoryType()).collect(Collectors.toList());
        //根据类型获取所有的类目
        List<ProductCategory> categoryList = productCategoryRepository.findByCategoryTypeIn(categpryTypeList);
        //根据类型将产品进行封装成结果对象
        List<ProductVO> productVOList = new ArrayList<ProductVO>();
        for (ProductCategory e:categoryList
             ) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(e.getCategoryName());
            productVO.setCategoryType(e.getCategoryType());
            //产品信息中类型相同的找出来封装后封入productVO
            List<ProductInfoVO> productInfoVOList = new ArrayList<ProductInfoVO>();
            for (ProductInfo p: productInfoList
                 ) {
                if (e.getCategoryType() == p.getCategoryType()){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //BeanUtil的工具,直接复制属性
                    BeanUtils.copyProperties(p,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setFoods(productInfoVOList);
            productVOList.add(productVO);
        }
        //
        return ResultVOUtils.success(productVOList);
    }
}
