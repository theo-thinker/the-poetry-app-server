package com.sakura.poetry.websocket.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT工具类
 * 
 * <p>提供JWT令牌的生成、解析和验证功能。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
public class JwtUtil {

    /**
     * JWT密钥
     */
    private static String jwtSecret = "your-secret-key-your-secret-key-your-secret-key";

    /**
     * JWT过期时间（毫秒）
     */
    private static long jwtExpiration = 86400000; // 24小时

    /**
     * 设置JWT配置
     * 
     * @param secret JWT密钥
     * @param expiration JWT过期时间
     */
    public static void setJwtConfig(String secret, long expiration) {
        jwtSecret = secret;
        jwtExpiration = expiration;
    }
    
    /**
     * 生成JWT令牌
     * 
     * @param userId 用户ID
     * @param username 用户名
     * @return JWT令牌
     */
    public static String generateToken(Long userId, String username) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    /**
     * 解析JWT令牌
     * 
     * @param token JWT令牌
     * @return Claims对象
     */
    public static Claims parseToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 验证JWT令牌
     * 
     * @param token JWT令牌
     * @return 是否有效
     */
    public static boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            log.warn("JWT令牌验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 从JWT令牌中获取用户ID
     * 
     * @param token JWT令牌
     * @return 用户ID
     */
    public static Long getUserIdFromToken(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.get("userId", Long.class);
        } catch (Exception e) {
            log.warn("从JWT令牌中获取用户ID失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从JWT令牌中获取用户名
     * 
     * @param token JWT令牌
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getSubject();
        } catch (Exception e) {
            log.warn("从JWT令牌中获取用户名失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 检查JWT令牌是否过期
     * 
     * @param token JWT令牌
     * @return 是否过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            log.warn("检查JWT令牌是否过期失败: {}", e.getMessage());
            return true;
        }
    }
}