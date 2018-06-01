package com.imooc.sell.converter;

import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.dto.CartDTO;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDetail2CartDTO {
    public static CartDTO oDetail2CartDTO(OrderDetail orderDetail){
        CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
        return cartDTO;
    }

    public static List<CartDTO> oDetail2CartDTOList(List<OrderDetail> list){
        return list.stream().map(e -> oDetail2CartDTO(e)).collect(Collectors.toList());
    }
}
