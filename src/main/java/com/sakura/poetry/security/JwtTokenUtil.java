package com.sakura.poetry.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT工具类
 * 
 * <p>提供JWT令牌的生成、解析、验证等核心功能。
 * 基于JJWT 0.12.7库实现，支持HS256签名算法。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>生成访问令牌和刷新令牌</li>
 *   <li>从令牌中提取用户信息</li>
 *   <li>验证令牌的有效性和过期状态</li>
 *   <li>刷新令牌功能</li>
 * </ul>
 * 
 * <p>安全特性：</p>
 * <ul>
 *   <li>使用强密码签名，防止令牌伪造</li>
 *   <li>支持令牌过期时间控制</li>
 *   <li>提供令牌刷新机制</li>
 *   <li>完善的异常处理和日志记录</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Component
public class JwtTokenUtil {
    
    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    /**
     * JWT密钥
     */
    @Value("${app.jwt.secret}")
    private String secret;

    /**
     * JWT过期时间（秒）
     */
    @Value("${app.jwt.expiration}")
    private Long expiration;

    /**
     * 刷新令牌过期时间（秒）
     */
    @Value("${app.jwt.refresh-expiration}")
    private Long refreshExpiration;

    /**
     * JWT用户名声明键
     */
    public static final String CLAIM_KEY_USERNAME = "sub";

    /**
     * JWT用户ID声明键
     */
    public static final String CLAIM_KEY_USER_ID = "userId";

    /**
     * JWT角色声明键
     */
    public static final String CLAIM_KEY_ROLES = "roles";

    /**
     * JWT创建时间声明键
     */
    public static final String CLAIM_KEY_CREATED = "iat";

    /**
     * 从令牌中获取用户名
     * 
     * @param token JWT令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 从令牌中获取用户ID
     * 
     * @param token JWT令牌
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        return claims != null ? Long.valueOf(claims.get(CLAIM_KEY_USER_ID).toString()) : null;
    }

    /**
     * 从令牌中获取签发时间
     * 
     * @param token JWT令牌
     * @return 签发时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    /**
     * 从令牌中获取过期时间
     * 
     * @param token JWT令牌
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从令牌中获取特定声明
     * 
     * @param token JWT令牌
     * @param claimsResolver 声明解析器
     * @param <T> 声明类型
     * @return 声明值
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claims != null ? claimsResolver.apply(claims) : null;
    }

    /**
     * 从令牌中获取所有声明
     * 
     * @param token JWT令牌
     * @return 声明集合
     */
    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.warn("解析JWT令牌失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 检查令牌是否过期
     * 
     * @param token JWT令牌
     * @return true-已过期，false-未过期
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration != null && expiration.before(new Date());
    }

    /**
     * 检查令牌是否在最后密码重置时间之前创建
     * 
     * @param created 令牌创建时间
     * @param lastPasswordReset 最后密码重置时间
     * @return true-在密码重置之前创建，false-在密码重置之后创建
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /**
     * 生成访问令牌
     * 
     * @param userId 用户ID
     * @param username 用户名
     * @param roles 用户角色
     * @return JWT令牌
     */
    public String generateToken(Long userId, String username, String roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USER_ID, userId);
        claims.put(CLAIM_KEY_ROLES, roles);
        return generateToken(claims, username, expiration);
    }

    /**
     * 生成刷新令牌
     * 
     * @param userId 用户ID
     * @param username 用户名
     * @return 刷新令牌
     */
    public String generateRefreshToken(Long userId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USER_ID, userId);
        return generateToken(claims, username, refreshExpiration);
    }

    /**
     * 生成令牌
     * 
     * @param claims 声明
     * @param subject 主题（通常是用户名）
     * @param expiration 过期时间（秒）
     * @return JWT令牌
     */
    private String generateToken(Map<String, Object> claims, String subject, Long expiration) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(createdDate)
                .expiration(expirationDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 验证令牌
     * 
     * @param token JWT令牌
     * @param username 用户名
     * @return true-有效，false-无效
     */
    public Boolean validateToken(String token, String username) {
        final String tokenUsername = getUsernameFromToken(token);
        return (username.equals(tokenUsername) && !isTokenExpired(token));
    }

    /**
     * 刷新令牌
     * 
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            
            String username = claims.getSubject();
            Long userId = Long.valueOf(claims.get(CLAIM_KEY_USER_ID).toString());
            String roles = (String) claims.get(CLAIM_KEY_ROLES);
            
            return generateToken(userId, username, roles);
        } catch (Exception e) {
            log.warn("刷新JWT令牌失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取签名密钥
     * 
     * @return 密钥
     */
    private SecretKey getSignKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}