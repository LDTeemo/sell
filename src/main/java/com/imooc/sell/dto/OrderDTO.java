package com.imooc.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.utils.EnumUtil;
import lombok.Data;
import serializer.Date2LongSerializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data

public class OrderDTO {
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    /** 订单总金额  */
    private BigDecimal orderAmount;

    /** 订单状态. 默认新订单 0 */
    private Integer orderStatus;

    /** 支付状态. 未新订单 0 */
    private Integer payStatus;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getEnum(orderStatus,OrderStatusEnum.class);
    }


    // 方法修饰不能为私有，因为getEnum是传入的是父类引用对象，是访问不到子类的私有方法的；
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getEnum(payStatus,PayStatusEnum.class);
    }


}
