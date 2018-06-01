package com.imooc.sell.repositoy;

import com.imooc.sell.dataObject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    /**
     * 测试查询
     */
    @Test
    public void findOneTest(){
        ProductCategory productCategory = productCategoryRepository.findById(0).get();
        log.info("findByID：{}",productCategory);
    }

    /**
     * 测试新增
     */
    @Test
    public void addOneTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("五一套餐");
        productCategory.setCategoryType(8);
        ProductCategory resultP = productCategoryRepository.save(productCategory);
        log.info("新增返回值：{}" , resultP);
    }

    /**
     *测试更新
     */
    @Test
    @Transactional
    public void updateOneTest(){
        ProductCategory productCategory = productCategoryRepository.findById(2).get();
        productCategory.setCategoryType(6);
        productCategoryRepository.save(productCategory);
    }

    /**
     *通过类目查询
     */
    @Test
    public void findByCategoryInTest(){
        List<ProductCategory> result = productCategoryRepository.findByCategoryTypeIn(Arrays.asList(7,8));
       log.info("结果集：{}",result);
       Assert.assertNotEquals(0,result.size());
    }

}