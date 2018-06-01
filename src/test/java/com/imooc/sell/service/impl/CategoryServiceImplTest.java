package com.imooc.sell.service.impl;

import com.imooc.sell.dataObject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CategoryServiceImplTest {
    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory productCategory = categoryService.findOne(2);
        Assert.assertEquals("周末特惠套餐",productCategory.getCategoryName());
    }

    @Test
    public void findAll() {
        List<ProductCategory> list = categoryService.findAll();
        log.info("结果集大小：{}",list.size());
        Assert.assertEquals(3,list.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> list = categoryService.findByCategoryTypeIn(Arrays.asList(1,7,8));
        log.info("结果集大小：{}",list.size());
        Assert.assertEquals(3,list.size());
    }

    @Test
    public void toSave() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryType(5);
        productCategory.setCategoryName("莓果恋人");
        ProductCategory saveResult = categoryService.toSave(productCategory);
        log.info("保存结果：{}",saveResult.getCategoryId());
        Assert .assertNotNull(saveResult);
    }
}