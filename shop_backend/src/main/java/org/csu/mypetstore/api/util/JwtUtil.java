package org.csu.mypetstore.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

import java.util.Date;
import java.util.Map;

//jwt
public class JwtUtil {

    private static final String KEY = "csu";
	
	//接收业务数据,生成token并返回
    //{claims={"username":"j2ee"}, exp=1713162371} exp表示用毫秒的截止时间
    public static String getToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14))//14天的expire time
                .sign(Algorithm.HMAC256(KEY));
    }

	//接收token,验证token,并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

    public static String getUsername(String token)   {
        Claim claim = JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims");
        return claim.asMap().get("username").toString();
    }
}