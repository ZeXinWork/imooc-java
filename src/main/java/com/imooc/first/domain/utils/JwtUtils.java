package com.imooc.first.domain.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    // 设置秘钥
    private static final String SECRET_KEY = "imooc_security";  // 这里可以从配置文件中获取秘钥
    private static final long EXPIRATION_TIME = 86400000; // 1天的过期时间

    // 生成JWT
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)  // 设置JWT的主题，一般是用户名
                .setIssuedAt(new Date())  // 设置生成时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 使用HS256算法和秘钥进行签名
                .compact();  // 生成token
    }

    // 从token中获取用户名
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();  // 获取JWT中的subject字段（即用户名）
    }

    // 判断token是否过期
    public boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }

    // 验证token是否有效
    public boolean validateToken(String token, String username) {
        return (username.equals(getUsernameFromToken(token)) && !isTokenExpired(token));
    }
}
