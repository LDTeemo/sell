package com.imooc.sell.service.impl;

import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;
    private static String BUYEROPENID = "luoqiang";

    @Test
    public void createOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerOpenid(BUYEROPENID);
        orderDTO.setBuyerAddress("武侯区神仙树北路11号");
        orderDTO.setBuyerName("阿翔");
        orderDTO.setBuyerPhone("18689542356");

        List<OrderDetail> list = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("zhwlng001");
        orderDetail.setProductQuantity(2);
        list.add(orderDetail);
        orderDTO.setOrderDetailList(list);
        OrderDTO oResult = orderService.createOrder(orderDTO);
        log.info("创建订单成，订单编号:{}",oResult.getOrderId());
        Assert.assertNotNull(oResult.getOrderId());
    }

    @Test
    public void findOrder() {
        OrderDTO o =orderService.findOrder("1525745025064180891");
        log.info("结果：{}",o);
        Assert.assertNotNull(o);
    }

    @Test
    public void findOrderList() {
        PageRequest pageRequest =PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findOrderList("luoqiang",pageRequest);
        List<OrderDTO> list = orderDTOPage.getContent();
        log.info("结果{}",list);
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void cancelOrder() {
        //取消订单请求只会传openid和订单ID
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("1525844636436269409");
        orderDTO.setBuyerOpenid("luoqiang");
        OrderDTO cancelResult = orderService.cancelOrder(orderDTO);
        Integer t = cancelResult.getOrderStatus();
        Assert.assertEquals(2,t.longValue());
    }

    @Test
    public void finishOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("1525844636436269409");
        orderDTO.setBuyerOpenid("luoqiang");
        OrderDTO result = orderService.finishOrder(orderDTO);
        Assert.assertEquals(1,result.getOrderStatus().longValue());
    }

    @Test
    public void paidOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("1525844636436269409");
        orderDTO.setBuyerOpenid("luoqiang");
        OrderDTO result = orderService.paidOrder(orderDTO);
        Assert.assertEquals(1,result.getPayStatus().longValue());
    }
}