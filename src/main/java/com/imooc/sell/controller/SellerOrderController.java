package com.imooc.sell.controller;

import com.imooc.sell.appException.SellException;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.service.OrderService;
import jdk.internal.org.objectweb.asm.commons.TryCatchBlockSorter;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/list")
    public ModelAndView getAllOrder(@RequestParam(name = "page" ,defaultValue = "1") int page,
                                    @RequestParam(name = "pageSize" ,defaultValue = "10") int pageSize,
                                    Map<String,Object> map){
        PageRequest pageRequest = PageRequest.of(page-1,pageSize);
        Page<OrderDTO> orderDTOPage = orderService.findAllOrder(pageRequest);
        //orderDTOPage.getContent().get(0).getOrderStatusEnum();
        map.put("orderDTOPage",orderDTOPage);
        map.put("totalPages",orderDTOPage.getTotalPages());
        map.put("currentPage",page);
        map.put("pageSize",pageSize);
        return new ModelAndView("/order/orderList",map);
    }

    @RequestMapping("/cancel")
    public ModelAndView toCancle(@RequestParam("orderId") String orderId,
                                 Map<String , Object> map){

        try {
            OrderDTO orderDTO = orderService.findOrder(orderId);
            orderService.cancelOrder(orderDTO);
        } catch (Exception e){
            log.info("【卖家取消订单操作失败】，原因{}",e.getMessage());
            map.put("url" , "/sell/seller/order/list");
            map.put("msg" , e.getMessage());
            return new ModelAndView("/common/error",map);
        }
        map.put("url" , "/sell/seller/order/list");
        map.put("msg" , ResultEnum.SUCCESS.getMsg());
        return new ModelAndView("/common/success",map);
    }

    @RequestMapping("/detail")
    public ModelAndView getOrderDetail(@RequestParam("orderId") String orderId,
                                       Map<String,Object> map){
        OrderDTO orderdto = orderService.findOrder(orderId);
        map.put("orderDto",orderdto);
        return new ModelAndView("/order/orderDetail");
    }
}
