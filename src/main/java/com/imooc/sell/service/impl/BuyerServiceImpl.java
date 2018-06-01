package com.imooc.sell.service.impl;

import com.imooc.sell.appException.SellException;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;
    @Override
    public OrderDTO findMyOrderOne(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOrder(orderId);
        if (orderDTO == null){
            log.error("查询订单失败，订单不存在，orderid:{}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }
        checkOrderOwner(openid , orderDTO);
        return orderDTO;
    }

    @Override
    public void checkOrderOwner(String openid, OrderDTO orderDTO) {
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("查询订单失败，订单不存在，orderid:{}",openid);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
    }

    @Override
    public void cancelMyOrder(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOrder(orderId);
        if (orderDTO == null){
            log.error("查询订单失败，订单不存在，orderid:{}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }
        checkOrderOwner(openid , orderDTO);
        orderService.cancelOrder(orderDTO);
    }
}
