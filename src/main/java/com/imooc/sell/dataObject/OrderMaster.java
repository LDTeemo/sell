package com.imooc.sell.dataObject;

import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.OrderStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单主表
 * 根据请求的数据格式特点，传过来的订单数据有具体的详情，比如数量，需要一个订单详情list的属性
 * 但这里不能添加
 * 数据库没有的字段作为实体类的属性（@transient可以避免抛错，但是仍旧不能添加）
 * 需要专门设计数据传输对象
 */
@Entity
@Data
@Table(name = "order_master")
@DynamicUpdate
public class OrderMaster {
    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    /** 订单总金额  */
    private BigDecimal orderAmount;

    /** 订单状态. 默认新订单 0 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态. 未新订单 0 */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    private Date createTime;

    private Date updateTime;


}
