package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDTO;

/**
 * 买家行为业务层
 */
public interface BuyerService {
    OrderDTO findMyOrderOne(String openid,String orderId);
    void cancelMyOrder(String openid,String orderId);
    void checkOrderOwner(String openid,OrderDTO orderDTO);
}
