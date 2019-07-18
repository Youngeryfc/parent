package com.yfc.common.base;

import com.yfc.common.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yfc
 * @date 2019-06-11
 * @description
 * @parmas
 */
@Component
public class BaseController {
    @Autowired
    protected BaseRedis baseRedis;

    //返回成功、传入obj
    public BasePojo setResultSuccess(Object obj){
        return  setResult(Constants.HTTP_RES_CODE_200,Constants.HTTP_RES_CODE_200_VALUE,obj);
    }

    //返回成功、传入obj
    public BasePojo setResultSuccess(){
        return  setResult(Constants.HTTP_RES_CODE_200,Constants.HTTP_RES_CODE_200_VALUE,null);
    }

    //返回错误、传msg
    public BasePojo setResultError(String msg){
        return setResult(Constants.HTTP_RES_CODE_500,msg,null);
    }
    /**
     * @Author yfc
     * @Date 2019-06-11
     * @Despration  通用结果
     * @Param
     */
    public BasePojo setResult(Integer code, String msg, Object obj){
        BasePojo responseBase = new BasePojo();
        responseBase.setCode(code);
        responseBase.setMsg(msg);
        if (obj != null)
            responseBase.setData(obj);
        return responseBase;
    }
}
