package org.csu.mypetstore.api.util;

import org.csu.mypetstore.api.common.CommonResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常处理
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public CommonResponse handleException(Exception e){
        e.printStackTrace();
        return CommonResponse.createForError(
                StringUtils.hasLength(e.getMessage()) ?
                        e.getMessage() : "exception happen"
        );
    }
}
