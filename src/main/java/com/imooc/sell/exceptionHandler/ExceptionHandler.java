package com.imooc.sell.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lix
 * @date 2018-06-07 20:50
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ModelAndView handle(Exception e){
        log.info("业务处理发生错误，错误信息：{}",e.getMessage());
        return new ModelAndView("/common/error");
    }
}
