package com.imooc.sell.dataObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import serializer.Date2LongSerializer;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 商品信息实体类
 */
@Entity
@Data
@Table(name = "product_info")
@DynamicUpdate
public class ProductInfo {
    /**
     * 指定自增策略，不然会报找不到sequence，Oracle就不会，奇怪，mysql中设计这个主键是varchar的，不能自增，
     * 这里写自增策略没有用
     */
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer categoryType;

    private Integer productStatus;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getEnum(this.productStatus,ProductStatusEnum.class);
    }


}
