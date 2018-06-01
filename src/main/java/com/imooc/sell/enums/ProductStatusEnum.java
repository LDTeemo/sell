package com.imooc.sell.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {
    UP(1,"在售"),
    DOWN(0,"下架")
    ;

    private Integer code;
    private String msg;

    ProductStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
