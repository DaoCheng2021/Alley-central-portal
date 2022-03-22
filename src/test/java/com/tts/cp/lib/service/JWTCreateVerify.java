package com.tts.cp.lib.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tts.cp.lib.common.StandardAlley;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alley zhao created on 2022/1/20.
 * 生成JWT方法 createJWT
 * 验证JWT是否过期或者是正确JWT verifyJWT
 */
@Slf4j
public class JWTCreateVerify {


    /*
        https://blog.csdn.net/weixin_45070175/article/details/118559272?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522164258890916780255256179%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D&request_id=164258890916780255256179&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v1~rank_v31_ecpm-1-118559272.pc_search_result_cache&utm_term=jwt&spm=1018.2226.3001.4187
        这个csdn的jwt写的比较清晰
        jwt作用流程
        前端验证用户名和密码，正确就给他生成一个jwt保存在Header里面，下次请求携带。
        生成jwt用秘钥加密，最重要的是秘钥只在服务器中，不要泄露
        后端拿到携带JWT的请求之后，通过秘钥解密jwt，如果成功根据解密的用户id来返回用户请求的数据
        如果失败就可能token过期，token是假的，让用户重新用户名密码登录，再生成jwt给用户
    */
    //       签名密钥
    private static final String SECRET = "!DAR$";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");

    //生成和Verify JWT的工具类，传过来的payload必须传过来之前转换成String类型数据
    public String createJWT(Map<String, String> payload) {
        //指定token过期时间为1分钟
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);

        JWTCreator.Builder builder = JWT.create();
        // 构建 payload
        payload.forEach((k, v) -> builder.withClaim(k, v));
        // 指定签名时间和算法
        String token = builder.withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    //生成jwt
    public String createJWT2() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);

        String JWTToken = JWT.create()
                .withHeader(new HashMap<>())
                .withClaim("userId", 1)
                .withClaim("userName", "userName")
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(SECRET));
        return JWTToken;
    }

    // 验证JWT
    public StandardAlley verifyJWT(String JWTToken) {
        StandardAlley sa = new StandardAlley();
        try {
            // 创建解析对象，使用的算法和签名的秘钥要和生成jwt时候的一致
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(JWTToken);
            // 获取解析后的token中的payload信息
            String userId = decodedJWT.getClaim("userId").asString();
            String userName = decodedJWT.getClaim("userName").asString();
            Date expiresAt = decodedJWT.getExpiresAt();
            String format = sdf.format(expiresAt);
            Map map = new HashMap();
            map.put("userId", Integer.parseInt(userId));
            map.put("userName", userName);
            map.put("dat", format); //此jwt过期时间
            sa.setSuccess(true);
            sa.setData(map);
            return sa;
        } catch (IllegalArgumentException e) {
            // 非法的jwt异常
            log.error(e.getMessage());
            sa.setStatusDesc(e.getMessage());
            sa.setSuccess(false);
            return sa;
        } catch (JWTVerificationException e) {
            // 令牌过期异常
            log.error(e.getMessage());
            sa.setStatusDesc(e.getMessage());
            sa.setSuccess(false);
            return sa;
        } catch (Exception e) {
            log.error(e.getMessage());
            sa.setStatusDesc(e.getMessage());
            sa.setSuccess(false);
            return sa;
        }
    }

    @Test
    public void TestVerifyJWT() {
        StandardAlley sa = verifyJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6Im5hbWUiLCJleHAiOjE2NDMxNjU4MjYsInVzZXJJZCI6IjEifQ.oGAm1cv__raoUcInmcFA-1NYFqNHzeDMBXxfzbRa6ZY");
        // 判断返回的Success是什么值，true正常返回，false让用户重新登录
        Boolean success = sa.getSuccess();
        System.out.println(success);

    }

    @Test
    public void TestCreateJwt() {
        Map map = new HashMap();
        map.put("userName", "name");
        map.put("userId", "1");
        String JWTToken = createJWT(map);
        //字符串转int类型方法，或者 int i = Integer.valueOf("").intValue();
        int userId = Integer.parseInt((String) map.get("userId"));
        System.out.println(JWTToken);
        System.out.println(userId);
    }

    @Test
    public void test01(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

    }
}
