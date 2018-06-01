package com.imooc.sell.repositoy;

import com.imooc.sell.dataObject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    private final String openid = "luoqiang";
    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<OrderMaster> page = orderMasterRepository.findByBuyerOpenid(openid,pageRequest);
        Assert.assertNotNull(page);
    }

    @Test
    public void saveOrderMasterTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("20180507123456");
        orderMaster.setBuyerAddress("内江市东兴区汉安大道136号社保大厦");
        orderMaster.setBuyerName("罗强");
        orderMaster.setBuyerOpenid(openid);
        orderMaster.setBuyerPhone("18628250065");
        orderMaster.setOrderAmount(new BigDecimal(29));
        OrderMaster orderMaster1 = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(orderMaster1);
    }
}