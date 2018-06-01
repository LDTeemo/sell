package com.imooc.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


/**
 * 请求提交的数据映射的对象
 * 主要做一些表单验证
 */
@Data
public class OrderForm {
    @NotEmpty(message = "名字不能为空")
    private String name;

    @NotEmpty(message = "电话不能为空")
    private String phone;

    @NotEmpty(message = "地址不能为空")
    private String address;

    @NotEmpty(message = "微信账号不能为空")
    private String openid;

    @NotEmpty(message = "购物车不能为空")
    private String items;
}
