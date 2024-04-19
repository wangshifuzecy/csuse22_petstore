package org.csu.petstorecms.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

public class JWTUtils {
    public static final String secret="csupetstore";

    public static String getToken(String username){
        Calendar instance=Calendar.getInstance();
        instance.add(Calendar.DATE,14);
        return JWT.create().withClaim("username",username).withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(secret));
    }
    //之后jwt校验以及处理,成功即会返回对应用户的账号
    public static String verify(HttpServletRequest httpServletRequest){
        String token=httpServletRequest.getHeader("Authorization");
        DecodedJWT result=JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        return result.getClaim("username").asString();
    }

}
