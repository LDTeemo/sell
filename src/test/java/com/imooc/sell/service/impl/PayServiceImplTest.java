package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author lix
 * @date 2018-05-29 20:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {
    @Autowired
    OrderService orderService;

    @Autowired
    PayService payService;


    @Test
    public void create() {
        OrderDTO orderDTO = orderService.findOrder("1525918441628114909");
        log.info("查询结果{}",orderDTO);
        payService.create(orderDTO);
    }
}