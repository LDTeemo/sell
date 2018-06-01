package com.imooc.sell.utils;

/**
 * @author lix
 * @date 2018-05-31 19:14
 */
public class MathUtil {
    private static final double DEVIATION = 0.01;
    public static boolean compareMoney(Double var1,Double var2){
        if(Math.abs(var1-var2) < DEVIATION){
            return true;
        }else{
            return false;
        }

    }
}
