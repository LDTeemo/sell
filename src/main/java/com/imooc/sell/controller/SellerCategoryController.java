package com.imooc.sell.controller;

import com.imooc.sell.dataObject.ProductCategory;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.form.CategoryForm;
import com.imooc.sell.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

import java.util.Map;

/**
 * @author lix
 * @date 2018-06-20 10:06
 */
@Slf4j
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView getCategoryList(@RequestParam(name = "page" , defaultValue = "1") int page,
                                        @RequestParam(name = "pageSize",defaultValue = "10") int pageSize,
                                        Map<String , Object> map){
        PageRequest pageRequest = PageRequest.of(page-1,pageSize);
        Page<ProductCategory> categoryPage = categoryService.findAllByPage(pageRequest);
        map.put("currentPage",page);
        map.put("totalPages",categoryPage.getTotalPages());
        map.put("pageSize",pageSize);
        map.put("categoryList",categoryPage.getContent());
        return new ModelAndView("/category/categories",map);
    }

    @GetMapping("/toAdd")
    public ModelAndView toAddOne(){
        return new ModelAndView("/category/addCategory");
    }

    @PostMapping("doAdd")
    public ModelAndView doAddOne(CategoryForm categoryForm,
                                 BindingResult bindingResult,
                                 Map<String, Object> map){
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(categoryForm,productCategory);
        categoryService.toSave(productCategory);
        map.put("url","/sell/seller/category/list");
        map.put("msg",ResultEnum.SUCCESS.getMsg());
        return new ModelAndView("/common/success");
    }
}
