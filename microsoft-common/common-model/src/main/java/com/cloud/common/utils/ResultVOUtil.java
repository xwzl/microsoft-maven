package com.cloud.common.utils;


import com.cloud.common.vo.ResultVO;

/**
 * @author xuweizhi
 * @date 2019/05/20 19:27
 */
public class ResultVOUtil {

    public static <T> ResultVO<T> success(T t) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setData(t);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }
}
