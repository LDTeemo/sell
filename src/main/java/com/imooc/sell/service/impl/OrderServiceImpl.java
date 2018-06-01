package com.imooc.sell.service.impl;

import com.bea.xml.stream.samples.AllocEventParser;
import com.imooc.sell.appException.SellException;
import com.imooc.sell.converter.OrderDetail2CartDTO;
import com.imooc.sell.converter.OrderMaster2OrderDTO;
import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.dataObject.OrderMaster;
import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.repositoy.OrderDetailRepository;
import com.imooc.sell.repositoy.OrderMasterRepository;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.utils.GenerateKeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        //根据api文档，传入数据中，订单信息有限，只传入了商品ID和购买数量
        //查询商品信息 orderDetail中包含商品ID
        List<OrderDetail> orderDetails = orderDTO.getOrderDetailList();
        BigDecimal orderAmount = new BigDecimal(0);

        String orderId = GenerateKeyUtil.generateKey();
        for (OrderDetail o: orderDetails
             ) {
            ProductInfo productInfo = productInfoService.getOne(o.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }
            //计算总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(o.getProductQuantity()))
                .add(orderAmount);
            //写订单详情表
            BeanUtils.copyProperties(productInfo,o);
            o.setOrderId(orderId);
            o.setDetailId(GenerateKeyUtil.generateKey());
            orderDetailRepository.save(o);
        }

        //写入订单主表
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO , orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        OrderMaster result = orderMasterRepository.save(orderMaster);
        //更新库存 订单请求里有本次的productID和数量，不需要直接把orderDTO全部传进去 新建cartDTO
        //从订单DTO中获取
        List<CartDTO> cartDTOS = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOS);

        //执行成功，回传订单ID
        orderDTO.setOrderId(orderId);
        return orderDTO;
    }

    @Override
    public OrderDTO findOrder(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if (orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        //orderMaster转OrderDTO  所以在里可以创建一个转换工具，专门用于转换obj2DTO
        OrderDTO orderDTO = OrderMaster2OrderDTO.orderMaster2DTO(orderMaster,orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findOrderList(String openid , Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(openid,pageable);
        List<OrderMaster> orderMasterList = orderMasterPage.getContent();
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.orderMaster2DTO(orderMasterList);
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    /**
     * 取消订单，api中只传入了openid 和 orderID
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO cancelOrder(OrderDTO orderDTO) {
        //检查订单的状态，只有新订单才能取消
        OrderMaster orderMaster = orderMasterRepository.findById(orderDTO.getOrderId()).get();
        if (orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }
        if (orderMaster.getOrderStatus() != 0){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //更新订单的状态为取消
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            throw new SellException(ResultEnum.ORDER_CANCEL_ERROR);
        }

        //归还库存 由于归还库存使用的是List<CartDTO>，包含产品ID和数量，可以写一个转换工具
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderDTO.getOrderId());
        if(orderDetailList == null || orderDetailList.size() == 0){
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }
        productInfoService.increaseStock(OrderDetail2CartDTO.oDetail2CartDTOList(orderDetailList));

        //TODO 如果已付款，涉及到退款

        //上述执行成功，把orderMaster对象返回给调用端，orderMaster.status的对象已经被更新了

        return OrderMaster2OrderDTO.orderMaster2DTO(orderMaster);
    }

    @Override
    public OrderDTO finishOrder(OrderDTO orderDTO) {
        //检查订单状态 只有新订单才能完结
        OrderMaster orderMaster = orderMasterRepository.findById(orderDTO.getOrderId()).get();
        if (orderMaster.getOrderStatus() != OrderStatusEnum.NEW.getCode()){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //检查支付状态 支付完成以后才能完结该订单
        if(orderMaster.getPayStatus() != PayStatusEnum.PAID.getCode()){
            throw new SellException(ResultEnum.ORDER_FINISH_PAYSTATUS_ERROR);
        }

        //更新订单状态为已完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);

        //更新结果验证
        if (updateResult == null){
            throw new SellException(ResultEnum.UPDATE_PAY_STATUS_ERROR);
        }
        //更新结果返回
        return OrderMaster2OrderDTO.orderMaster2DTO(updateResult);
    }

    @Override
    public OrderDTO paidOrder(OrderDTO orderDTO) {
        //检查订单状态，新订单才能进行支付
        OrderMaster orderMaster = orderMasterRepository.findById(orderDTO.getOrderId()).get();
        if (orderMaster.getOrderStatus() != OrderStatusEnum.NEW.getCode()){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //更新支付状态
        orderMaster.setPayStatus(PayStatusEnum.PAID.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        //更新结果验证
        if (updateResult == null){
            throw new SellException(ResultEnum.UPDATE_PAY_STATUS_ERROR);
        }
        orderDTO.setPayStatus(updateResult.getPayStatus());
        return orderDTO;
    }


    /**
     * 商户查询所有的订单
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findAllOrder(Pageable pageable) {
        Page<OrderMaster> allOrders = orderMasterRepository.findAll(pageable);
        // 返回对象需要转成数据传输对象，实体类不能暴露
        List<OrderMaster> list = allOrders.getContent();
        List<OrderDTO> orderDTOS = OrderMaster2OrderDTO.orderMaster2DTO(list);

        return new PageImpl<>(orderDTOS,pageable,allOrders.getTotalElements());
    }
}
