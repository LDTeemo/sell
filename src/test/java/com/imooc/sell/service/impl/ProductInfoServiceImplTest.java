package com.imooc.sell.service.impl;

import com.imooc.sell.dataObject.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void getOne() {
        ProductInfo productInfo = productInfoService.getOne("zs4s001");
        log.info("查询结果：{}",productInfo);
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void getUpProduct() {
        List<ProductInfo> listUp = productInfoService.getUpProduct();
        Assert.assertNotEquals(0,listUp.size());
    }

    @Test
    public void getAllProduct() {
        PageRequest pageRequest = PageRequest.of(0,5);
        Page<ProductInfo> pages= productInfoService.getAllProduct(pageRequest);
        List<ProductInfo> lists = pages.getContent();
        log.info("分页查询结果:{}",lists);
        Assert.assertNotEquals(0,lists.size());
    }

    @Test
    public void toSave() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("mmgc001");
        productInfo.setCategoryType(5);
        productInfo.setProductDescription("各种莓");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductName("草莓缤纷乐");
        productInfo.setProductPrice(new BigDecimal(15));
        productInfo.setProductStatus(1);
        productInfo.setProductStock(99999);
        ProductInfo sResult = productInfoService.toSave(productInfo);
        Assert.assertNotNull(sResult);

    }
}