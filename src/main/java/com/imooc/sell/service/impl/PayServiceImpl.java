package com.imooc.sell.service.impl;

import com.imooc.sell.appException.SellException;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.imooc.sell.utils.EnumUtil;
import com.imooc.sell.utils.JsonUtil;
import com.imooc.sell.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {


    private static final String ORDER_NAME = "微信点餐订单";
    // BestPayServiceImpl 中有属性 WxPayH5Config。spring在注入该对象的时候
    // 需要初始化属性WxPayH5Config，如果该属性不使用spring进行管理，是无法使用
    // 该属性，所以，要对WxPayH5Config进行@bean注解来实现，因此要写一个WechatPayConfig
    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        // 调用微信支付依赖中的方法
        PayRequest payRequest = new PayRequest();

        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信支付】request={}",JsonUtil.toJson(payRequest));

        PayResponse payResponse  = bestPayService.pay(payRequest);
        log.info("【微信支付】response={}",JsonUtil.toJson(payResponse));
        return payResponse;
    }

    /**
     * 支付成功微信医保回调通知
     * 做支付确认
     * @param String notifyData
     * @return PayResponse
     */
    @Override
    public PayResponse notify(String notifyData) {
        // 1. 验证签名
        // 2. 支付的状态
        // 3. 支付的金额
        // 4. 支付人（支付人==下单人？）
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        // 5. 获取订单数据
        OrderDTO orderDTO = orderService.findOrder(payResponse.getOrderId());
        if(orderDTO == null){
            log.error("【微信支付通知】订单不存在订单编号{}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }
        // 6. 判断金额与支付金额
        if (!MathUtil.compareMoney(orderDTO.getOrderAmount().doubleValue() ,
                payResponse.getOrderAmount())){
            log.error("【微信支付通知】订单金额与支付金额不一致，订单金额：{}，支付金额：{}",
                        orderDTO.getOrderAmount() , payResponse.getOrderAmount()
                        );
            throw new SellException(ResultEnum.ORDER_PAID_MONEY_NOT_RIGHT);
        }
        // 6. 修改系统的订单状态
        OrderDTO paidResult = orderService.paidOrder(orderDTO);
        log.info("支付成功后回调修改订单状态：{}",EnumUtil.getEnum(paidResult.getPayStatus(),PayStatusEnum.class).getMsg());
        return payResponse;
    }
}
