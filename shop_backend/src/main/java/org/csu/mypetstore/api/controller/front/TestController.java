package org.csu.mypetstore.api.controller.front;

import org.csu.mypetstore.api.common.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    //Authorization:
    //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsidXNlcm5hbWUiOiJqMmVlIn0sImV4cCI6MTcxMzE2MjM3MX0.IkI0mOJwSlQ0xNcM64YZyqVPhr7v7Vuj55UUi6aQUMo
    @GetMapping("/jwt")
    public CommonResponse<String> testForJwt(){
//    public CommonResponse<String> testForJwt(@RequestHeader(name="Authorization") String token){
        //使用登录拦截器时这段代码没用, 同时在参数表里也不同写
//        try{
//            Map<String,Object> claims = JwtUtil.parseToken(token);
//            return CommonResponse.createForSuccessMessage("good jwt");
//        }catch(Exception e){
//            return CommonResponse.createForError(ResponseCode.NEED_LOGIN.getCode(), "no login or bad jwt");
//        }
            return CommonResponse.createForSuccessMessage("good jwt");
    }
}
