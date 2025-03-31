package com.huangzizhu.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 * @Author huangzizhu
 * @Version 1.0
 */
public class JWTUtil {
    private static final String SECRET_KEY = "T0VBSEZXUUZVT1FQV0ZKUFFFSU9KR0hQUU9FSkZHUE9RRUhHRk9JUUhHRlBPRUtWW1BPV0VKVklPV0VSVkhJUE9XRVZHW1BXRVJJR1tPUFdFUkhWQk9KSVdSRU5IVkJQS1dFSkdWW09QV0VSSkdJT1dSRU5WSU9LRVJXVg=="; // 请确保密钥长度足够安全

    // 生成 JWT 令牌
    public static String generateToken(String username) {
        // 设置密钥
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        //claims
        Map<String,Object> claims = Map.of("username",username);
        // 构建 JWT
        return Jwts.builder()
                .signWith(key)
                .claims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+3600*10000))
                .compact();

    }

    // 解析 JWT 令牌
    public static Jws<Claims> parseToken(String token) throws Exception{
        // 设置密钥
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        // 解析 JWT
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
    }
}