package com.sakura.poetry.websocket.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 在线用户信息实体类
 * 
 * <p>用于封装在线用户的信息。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "在线用户信息")
public class OnlineUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 连接时间
     */
    @Schema(description = "连接时间")
    private LocalDateTime connectTime;

    /**
     * 最后活跃时间
     */
    @Schema(description = "最后活跃时间")
    private LocalDateTime lastActiveTime;

    /**
     * 连接的WebSocket会话ID
     */
    @Schema(description = "连接的WebSocket会话ID")
    private String sessionId;

    /**
     * 构造函数
     */
    public OnlineUser() {
        this.connectTime = LocalDateTime.now();
        this.lastActiveTime = LocalDateTime.now();
    }

    /**
     * 构造函数
     * 
     * @param userId 用户ID
     * @param username 用户名
     * @param nickname 用户昵称
     */
    public OnlineUser(Long userId, String username, String nickname) {
        this();
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
    }
}