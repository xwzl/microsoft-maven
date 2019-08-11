package com.cloud.user.until;

import com.cloud.common.vo.ResultVO;
import com.cloud.user.enums.ResultEnum;

/**
 * @author xuweizhi
 * @date 2019/05/23 22:52
 */
public class ResultVoUtil {

    public static ResultVO success() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO error(ResultEnum resultEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMessage());
        return resultVO;
    }

}
