package com.imooc.sell.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lix
 * @date 2018-06-14 22:30
 */
@Data
public class ProductForm {
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer categoryType;

    private Integer productStatus;
}
