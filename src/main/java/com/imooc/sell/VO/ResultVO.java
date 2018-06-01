package com.imooc.sell.VO;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * 返回结果的最外层封装对象，根据api进行设计
 */
@Data
public class ResultVO<T> {
    /**错误代码 */
    private Integer code;
    /**信息 */
    private String msg;
    /**返回的数据格式 */
    private T data;
}
