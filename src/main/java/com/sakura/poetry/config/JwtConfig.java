package com.sakura.poetry.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * JWT配置类
 * 
 * <p>用于配置JWT相关的设置。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Configuration
public class JwtConfig {

    /**
     * JWT密钥
     */
    @Value("${app.jwt.secret:your-secret-key-your-secret-key-your-secret-key}")
    private String jwtSecret;

    /**
     * JWT过期时间（毫秒）
     */
    @Value("${app.jwt.expiration:86400000}")
    private Long jwtExpiration;

    /**
     * 获取JWT密钥
     * 
     * @return JWT密钥
     */
    public String getJwtSecret() {
        return jwtSecret;
    }

    /**
     * 获取JWT过期时间
     * 
     * @return JWT过期时间（毫秒）
     */
    public Long getJwtExpiration() {
        return jwtExpiration;
    }
}