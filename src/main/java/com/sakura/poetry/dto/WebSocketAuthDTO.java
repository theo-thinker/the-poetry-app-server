package com.sakura.poetry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * WebSocket连接认证DTO
 * 
 * <p>用于WebSocket连接时的身份认证。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "WebSocket连接认证请求DTO")
public class WebSocketAuthDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * JWT令牌
     */
    @NotBlank(message = "令牌不能为空")
    @Schema(description = "JWT访问令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}