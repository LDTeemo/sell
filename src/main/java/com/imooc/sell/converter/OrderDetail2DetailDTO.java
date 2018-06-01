package com.imooc.sell.converter;

import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.DetailDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDetail2DetailDTO {
    public static DetailDTO oDetail2DetailDTO(OrderDetail orderDetail){
        DetailDTO detailDTO = new DetailDTO();
        BeanUtils.copyProperties(orderDetail,detailDTO);
        return detailDTO;
    }

    public static List<DetailDTO> oDetail2DetailDTOList(List<OrderDetail> list){
        return list.stream().map(e -> oDetail2DetailDTO(e)).collect(Collectors.toList());
    }
}
