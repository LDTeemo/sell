package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.appException.SellException;
import com.imooc.sell.converter.OrderForm2OrderDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.form.OrderForm;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.utils.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    /**
     * 根据需求文档，post请求，需要将post请求转换成
     * 根据返回数据的格式，泛型应该是map
     * @return
     */
    @PostMapping("/create")
    public ResultVO<Map<String,String>> createOrder(@Valid OrderForm orderForm , BindingResult bindingResult){
        //表单验证
        if (bindingResult.hasErrors()){
            log.error("参数错误{}",bindingResult.getFieldError());
            throw  new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        //构建数据传输对象 调用service层
        OrderDTO orderDTO = OrderForm2OrderDTO.oForm2OrderDTO(orderForm);
        OrderDTO result = orderService.createOrder(orderDTO);
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("orderId",result.getOrderId());
        return ResultVOUtils.success(resultMap);
    }

    /**
     * 买家获取订单列表:
     * get请求，openid page size三个参数
     * 返回orderderMaster
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> getBuyerOrders(@RequestParam(value = "openid") String openid,
                                                   @RequestParam(value = "page",defaultValue = "0") Integer page,
                                                   @RequestParam(value = "size",defaultValue = "10") Integer size){
        //入参判断
        if(StringUtils.isEmpty(openid)){
            log.error("【买家获取订单列表错误，openID为空】{}",openid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findOrderList(openid,pageRequest);
        return ResultVOUtils.success(orderDTOPage.getContent());
    }

    /**
     * 买家查询订单详情
     * get请求，参数openid和订单ID
     * 分析返回结果对象的数据，orderdt带订单详情的
     */
    @GetMapping("/detail")
    public ResultVO<OrderDTO> findMyOrderDetail(@RequestParam(value = "openid") String openid,
                                                @RequestParam(value = "orderId") String orderId){
        if (StringUtils.isEmpty(openid)){
            log.error("openID参数为空：{}",openid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if (StringUtils.isEmpty(orderId)){
            log.error("orderId参数为空：{}",orderId);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = buyerService.findMyOrderOne(openid,orderId);
        return ResultVOUtils.success(orderDTO);
    }


    /**
     * 取消订单
     * post请求，传入openid和orderID
     * 返回执行成功即可
     */
    @PostMapping("/cancel")
    public ResultVO cancelOrder(@RequestParam(value = "openid") String openid,
                            @RequestParam(value = "orderId") String orderId ){
        if (StringUtils.isEmpty(openid)){
            log.error("openID参数为空：{}",openid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if (StringUtils.isEmpty(orderId)){
            log.error("orderId参数为空：{}",orderId);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.cancelMyOrder(openid,orderId);
        return ResultVOUtils.success();
    }

}
