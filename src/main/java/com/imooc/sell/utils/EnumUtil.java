package com.imooc.sell.utils;

import com.imooc.sell.enums.CodeEnum;

/**
 * @author lix
 * @date 2018/6/3 11:43
 */
public class EnumUtil {
    /**
     * 注意这里的写法，返回类型定义为
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeEnum> T getEnum(Integer code , Class<T> enumClass){
        for (T each : enumClass.getEnumConstants()
             ) {
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
