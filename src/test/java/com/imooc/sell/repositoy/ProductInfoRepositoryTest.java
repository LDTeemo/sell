package com.imooc.sell.repositoy;

import com.imooc.sell.dataObject.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void addOneTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("zs4s001");
        productInfo.setCategoryType(4);
        productInfo.setProductDescription("海盐芝士奶盖，台湾四季春茶");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductName("芝士四季春");
        productInfo.setProductPrice(new BigDecimal(12));
        productInfo.setProductStatus(1);
        productInfo.setProductStock(99999);
        ProductInfo saveResult = productInfoRepository.save(productInfo);
        log.info("新增的产品ID：{}",saveResult.getProductId());
        Assert.assertNotNull(saveResult.getProductId());
    }
    @Test
    public void findByProductStatus() {
        List<ProductInfo> list = productInfoRepository.findByProductStatus(1);
        Assert.assertNotEquals(0,list.size());
    }
}