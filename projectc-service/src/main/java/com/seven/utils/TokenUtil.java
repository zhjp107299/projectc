package com.seven.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * token工具类
 */
public class TokenUtil {
    //有效时长
    private static final long EXPIRE_TIME = 24*60*60*1000;

    //密钥
    private  static final String TOKEN_SECRET = "hello";

    /**
     * 签名生成
     * @return
     */
    public static String sign(Integer id){
        String token = null;
        Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        try {
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userID",id)
                    .withExpiresAt(expiresAt)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));     //使用HMAC256算法加密
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static boolean verify(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET))
                    .withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("认证通过");
            System.out.println("userID"+jwt.getClaim("userID").asString());
            System.out.println("过期时间："+jwt.getExpiresAt());
            return true;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }
}
