package com.imooc.sell.utils;

import com.imooc.sell.VO.ResultVO;

public class ResultVOUtils {

    /**
     * 成功返回
     * @param o
     * @return
     */
    public static ResultVO success(Object o){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(o);
        return resultVO;
    }

    /**
     * 执行成功，但是不用返回任何数据对象
     * @return
     */
    public static  ResultVO success(){

        return  success(null);

    }

    public static ResultVO error (String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(-1);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
