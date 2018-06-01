package com.imooc.sell.controller;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    OrderService orderService ;

    @Autowired
    PayService payService;

    /**
     * 创建订单并支付
     * @param orderId
     * @param returnUrl
     * @param map
     * @return
     */
    @RequestMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String , Object> map){
        // 根据传入的订单编号，查询商品信息
        OrderDTO orderDTO = orderService.findOrder(orderId);
        // 支付逻辑
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);
        return new ModelAndView("/pay/create",map);

    }

    // 支付成功后的异步通知
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String noifyData){
        PayResponse payResponse = payService.notify(noifyData);
        return new ModelAndView("/pay/success");
    }
}
