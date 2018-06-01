package com.imooc.sell.repositoy;

import com.imooc.sell.dataObject.OrderDetail;
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

public class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId("20180507123456");
        Assert.assertNotEquals(0,orderDetails.size());
    }

    @Test
    public void saveOrderDetailTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("20180507D002");
        orderDetail.setOrderId("20180507123456");
        orderDetail.setProductId("mmgc002");
        orderDetail.setProductName("霉霉果茶");
        orderDetail.setProductPrice(new BigDecimal(15));
        orderDetail.setProductQuantity(1);
        OrderDetail o = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(o);

    }
}