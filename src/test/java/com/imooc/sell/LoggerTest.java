package com.imooc.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

    //private static Logger logger = LoggerFactory.getLogger(LoggerTest.class);
    /*最原始的不添加注解的写法*/
    @Test
    public void testLogger(){

        log.info("info::::::");
        log.debug("debug::::::");
        log.error("error::::::");
        //如果要输出变量，则使用占位符。
        String u = "占位符";
        log.info("info:{}" , u);
    }
}
