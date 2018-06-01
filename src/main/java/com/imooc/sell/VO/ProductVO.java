package com.imooc.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 根据api设计的格式，这一层返回的是category的内容，部分属性
 */
@Data
public class ProductVO<T> {
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    private T foods;


}
