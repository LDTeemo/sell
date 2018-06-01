package com.imooc.sell.converter;

import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.dataObject.OrderMaster;
import com.imooc.sell.dto.DetailDTO;
import com.imooc.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTO {
    /**
     * OrderDTO中有List<OrderDetail>
     * @param orderMaster
     * @return OrderDTO
     */
    public static OrderDTO orderMaster2DTO(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }


    /**
     * 返回List<>
     */
    public static List<OrderDTO> orderMaster2DTO(List<OrderMaster> orderMasterList){
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderDTOList = orderMasterList.stream().map(e -> orderMaster2DTO(e)).collect(Collectors.toList());
        return orderDTOList;
    }

    public static OrderDTO orderMaster2DTO(OrderMaster orderMaster , List<OrderDetail> orderDetailList){
        OrderDTO orderDTO = orderMaster2DTO(orderMaster);
        orderDetailList.stream().map(e->getRidOfTime(e)).collect(Collectors.toList());
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
    private static OrderDetail getRidOfTime(OrderDetail orderDetail){
        orderDetail.setUpdateTime(null);
        orderDetail.setCreateTime(null);
        return orderDetail;
    }
}
