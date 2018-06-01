package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO findOrder(String orderId);

    Page<OrderDTO> findOrderList(String openid , Pageable pageable);

    OrderDTO cancelOrder(OrderDTO orderDTO);

    OrderDTO finishOrder(OrderDTO orderDTO);

    OrderDTO paidOrder(OrderDTO orderDTO);

    Page<OrderDTO> findAllOrder(Pageable pageable);

}
