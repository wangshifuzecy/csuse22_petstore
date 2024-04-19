package org.csu.petstorecms.utils;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.csu.petstorecms.common.CommonResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalHandleException {
    //各种异常处理
    //自己要用的时候自己添加异常处理函数，返回commonresponse
    @ExceptionHandler(SignatureVerificationException.class)
    @ResponseBody
    public CommonResponse<Object> JWTException1(){
        return CommonResponse.createForFailure("登录凭证无效，请重新登录");
    }
    @ExceptionHandler(TokenExpiredException.class)
    @ResponseBody
    public CommonResponse<Object>JWTException2(){
        return CommonResponse.createForFailure("用户信息已过期，请重新登录");
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public CommonResponse<Object> ElseException(){
//        return CommonResponse.createForFailure("页面找不到啦");
//    }
}
