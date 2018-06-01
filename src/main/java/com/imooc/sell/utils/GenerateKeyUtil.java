package com.imooc.sell.utils;

import java.util.Random;

/**
 * 生成唯一的订单编号
 */
public class GenerateKeyUtil {
    public static synchronized String generateKey(){
        Random random = new Random();
        Integer integer = random.nextInt(900000)+100000;

        return System.currentTimeMillis()+String.valueOf(integer);
    }
}
