package com.imooc.sell.handler;

import com.imooc.sell.appException.SellException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lix
 * @date 2018-06-07 20:50
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = SellException.class)
    public ModelAndView handle(SellException e){
        log.info("业务处理发生错误，错误信息：{}",e.getMessage());
        Map<String , Object> map = new HashMap<>();
        map.put("msg",e.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("/common/error",map);
    }
}
