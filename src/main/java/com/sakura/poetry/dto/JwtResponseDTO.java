package com.sakura.poetry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * JWT响应DTO
 * 
 * <p>用于JWT认证成功后返回的数据传输对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "JWT认证响应DTO")
public class JwtResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * JWT令牌
     */
    @Schema(description = "JWT访问令牌")
    private String accessToken;

    /**
     * 令牌类型
     */
    @Schema(description = "令牌类型", example = "Bearer")
    private String tokenType = "Bearer";

    /**
     * 过期时间（毫秒）
     */
    @Schema(description = "令牌过期时间（毫秒）")
    private Long expiresIn;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 构造函数
     * 
     * @param accessToken JWT令牌
     * @param expiresIn 过期时间
     * @param userId 用户ID
     * @param username 用户名
     * @param nickname 昵称
     */
    public JwtResponseDTO(String accessToken, Long expiresIn, Long userId, String username, String nickname) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
    }
}