package com.imooc.sell.controller;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author lix
 * @date 2018/6/1 20:50
 */
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/list")
    public ModelAndView getAllOrder(@RequestParam(name = "page" ,defaultValue = "0") int page,
                                    @RequestParam(name = "pageSize" ,defaultValue = "0") int pageSize,
                                    Map<String,Object> map){
        PageRequest pageRequest = PageRequest.of(page,pageSize);
        Page<OrderDTO> orderDTOPage = orderService.findAllOrder(pageRequest);
        map.put("orderDTOPage",orderDTOPage);
        return new ModelAndView("/order/orderList",map);
    }
}
