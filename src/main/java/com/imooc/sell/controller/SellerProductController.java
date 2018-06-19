package com.imooc.sell.controller;

import com.imooc.sell.appException.SellException;
import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.form.ProductForm;
import com.imooc.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author lix
 * @date 2018-06-14 16:58
 */

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;
    /**
     * 买家端查询所有的产品
     */
    @GetMapping("/allProduct")
    public ModelAndView getAllProductInfo(@RequestParam(name = "page" , defaultValue = "1") int page,
                                          @RequestParam(name = "pageSize" , defaultValue = "10") int pageSize,
                                          Map<String , Object> map){
        PageRequest pageRequest = PageRequest.of(page-1,pageSize);
        Page<ProductInfo> productInfos =  productInfoService.getAllProduct(pageRequest);
        // 获取总页数用于前台分页控制
        map.put("totalPages",productInfos.getTotalPages());
        map.put("currentPage",page);
        map.put("pageSize",pageSize);
        // 产品信息
        map.put("productInfos",productInfos.getContent());
        return new ModelAndView("/product/products",map);
    }

    /**
     * 转发 参数自动传递(前提的转发参数跟前头的参数名字一模一样)
     * @param productId
     * @return
     */
    @GetMapping("/toUpdate")
    public String toUpdate(@RequestParam("productId") String productId){
        return "update";
    }

    @GetMapping("/update")
    public ModelAndView updateProduct( String productId,
                                       Map<String,Object> map){
        ProductInfo productInfo = productInfoService.getOne(productId);
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
        }
        map.put("productInfo",productInfo);
        return new ModelAndView("/product/updateProduct",map);
    }

    @PostMapping("/doUpdate")
    public ModelAndView doUpdate (@Valid ProductForm productForm,
                                  BindingResult bindingResult,
                                  Map<String , Object> map){

        return null;
    }





}
