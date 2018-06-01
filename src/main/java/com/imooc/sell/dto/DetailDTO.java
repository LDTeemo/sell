package com.imooc.sell.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data

public class DetailDTO {
    private String detailId;

    private String orderId;

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productQuantity;

    private String productIcon;
}
