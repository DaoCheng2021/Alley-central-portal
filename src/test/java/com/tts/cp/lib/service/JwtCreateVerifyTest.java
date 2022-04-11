package com.tts.cp.lib.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alley zhao created on 2022/3/24.
 */
//jwt生成和验证，包括jwt和jjwt生成验证(功能都一样，用的包不同)
@Slf4j
public class JwtCreateVerifyTest {
    /*
    https://blog.csdn.net/weixin_45070175/article/details/118559272?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522164258890916780255256179%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D&request_id=164258890916780255256179&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v1~rank_v31_ecpm-1-118559272.pc_search_result_cache&utm_term=jwt&spm=1018.2226.3001.4187
    https://blog.csdn.net/qq_45076180/article/details/107243172
    以上两篇文章写的比较清楚
    jwt(Json Web Token) 是一种字符串token，三块部分组成Header，payload，签名组成。
    作用：只需要用户登录一次，之后客户端每次携带jwt，就可以在不同的服务器中验证通过后允许访问，服务器不用保存用户的token。解决了单点登录分布式项目登录问题。支持跨平台
    流程：
    生成：前端接收用户名和密码，后端验证成功后就根据用户信息加服务器的秘钥生成jwt。Header保存加密方式对称加密HS256.
    payload保存用户名和用户id等一些用户信息。这两部分用base64加密，可以反向解密，所以不能存储重要信息。签名是Header和payload用 . 相连，
    再加上服务器的秘钥（盐）用HS256进行生成，再用base64加密。
    解密：把jwt分成三段，Header和payload用逆向解密获取签名加密方式和jwt的过期时间，如果过期直接返回false。没过期就用Header.payload+秘钥
    用规定的加密算法再生成签名，生成的签名跟jwt的签名如果相同就返回true。因为HS256是不可逆的，所以对比验证。
    整个jwt过程最重要的是秘钥的不能泄露。
    * */
    private static final String SECRET = "ConcurrentHashMap"; // 签名秘钥。private只能此类获取，static所有线程共享一个对象，final不能被修改

    private SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");

    // 生成jwt工具（jwt包对称加密）
    public String createJWT(String userId, String userName) {
        Map<String, String> map = new HashMap();
        map.put("userId", userId);
        map.put("userName", userName);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60); //指定jwt过期时间，一个小时
        JWTCreator.Builder builder = JWT.create();
        // 构建 payload
        map.forEach((k, y) -> builder.withClaim(k, y));
        String finalJWT = builder.withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(SECRET));
        return finalJWT;
    }

    // 生成JWT工具 2 （jwt包对称加密）
    public String createJWTTWO(int userId, String userName) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);

        String JWTToken = JWT.create()
                .withHeader(new HashMap<>())
                .withClaim("userId", userId)
                .withClaim("userName", userName)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(SECRET));
        return JWTToken;
    }

    // 验证JWT对称加密
    public boolean verifyJWT(String JWTToken) {
        try {
            // 创建解析对象，使用的算法和签名的秘钥要和生成jwt时候的一致
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(JWTToken);
            // 获取解析后的token中的payload信息
            return true;
        } catch (IllegalArgumentException e) {
            // 非法的jwt异常
            log.error(e.getMessage());
            return false;
        } catch (JWTVerificationException e) {
            // 令牌过期异常
            log.error(e.getMessage());
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Test
    public void createJWT() {
        System.out.println(createJWTTWO(1, "dog"));
        System.out.println(verifyJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImRvZyIsImV4cCI6MTY0ODEyMzM1MiwidXNlcklkIjoxfQ.JmmbzoYeAdZH4l2j6eiRm0YFpbOTrWGA0PqGShAfUdI"));
    }

    // --------------------------

    // jjwt生成jwt
    public String createJJWT(int userId, String userName) {
        Map map = new HashMap<>();
        map.put("userId", userId);
        map.put("userName", userName);
        Date date = new Date();
        Date expireTime = new Date(date.getTime() + 60 * 1000 * 60 * 24); // 当前时间加一天
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(map)    // payload
                .setIssuedAt(date) // 生成时间
                .setExpiration(expireTime)    //过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET) //秘钥
                .compact();
        return token;
    }

    // jjwt 验证jwt
    public boolean verifyJJWT(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET) // 秘钥
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (ExpiredJwtException e) {
            // jwt过期
            e.printStackTrace();
            return false;
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
            return false;
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            return false;
        } catch (SignatureException e) {
            // jwt 不正确
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Test
    public void createJJWT() {
        System.out.println(createJJWT(1, "dog"));
        System.out.println(verifyJJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImRvZyIsImV4cCI6MTY0ODEyMDU0OCwidXNlcklkIjoxLCJpYXQiOjE2NDgxMjA1NDd9.Ig9L5irrbL-tbI-wG5xklQ9MZSEPx3a_C3f9OVGgzQ8"));
    }

}
