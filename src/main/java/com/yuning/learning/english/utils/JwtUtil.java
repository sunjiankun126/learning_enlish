package com.yuning.learning.english.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "Huawei@123";  // 生成 Token 时的密钥

    public static String generateToken(String openid) {
        return Jwts.builder()
                .setSubject(openid)  // 用户的 openid
                .setIssuedAt(new Date())  // 当前时间
                .setExpiration(new Date(System.currentTimeMillis() + 36000 * 1000))  // 设置过期时间（这里设置为 10 小时）
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 使用 HS256 签名算法
                .compact();
    }

    // 验证 Token 是否有效
    public static boolean validateToken(String token, String openid) {
        String subject = Jwts.parser()
                .setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return subject.equals(openid);
    }

    // 获取 Token 中的 OpenID
    public static String getOpenidFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
