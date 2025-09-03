package com.sakura.poetry.controller;

import com.sakura.poetry.common.result.Result;
import com.sakura.poetry.websocket.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * WebSocket测试控制器
 * 
 * <p>提供WebSocket功能测试的HTTP接口。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
@RestController
@RequestMapping("/api/websocket/test")
@Tag(name = "WebSocket测试", description = "WebSocket功能测试相关接口")
public class WebSocketTestController {

    /**
     * 生成WebSocket连接令牌
     * 
     * @param userId 用户ID
     * @param username 用户名
     * @return WebSocket连接令牌
     */
    @GetMapping("/token")
    @Operation(summary = "生成WebSocket连接令牌", description = "为指定用户生成用于WebSocket连接的JWT令牌")
    public Result<String> generateWebSocketToken(
            @RequestParam Long userId,
            @RequestParam String username) {
        try {
            // 生成JWT令牌
            String token = JwtUtil.generateToken(userId, username);
            
            log.info("为用户生成WebSocket连接令牌: 用户ID={}, 用户名={}", userId, username);
            return Result.success(token);
        } catch (Exception e) {
            log.error("生成WebSocket连接令牌失败: {}", e.getMessage(), e);
            return Result.error(500, "生成WebSocket连接令牌失败");
        }
    }

    /**
     * 获取WebSocket连接地址
     * 
     * @param token JWT令牌
     * @return WebSocket连接地址
     */
    @GetMapping("/connection-url")
    @Operation(summary = "获取WebSocket连接地址", description = "根据JWT令牌生成WebSocket连接地址")
    public Result<String> getWebSocketConnectionUrl(@RequestParam String token) {
        try {
            // 验证令牌
            if (!JwtUtil.validateToken(token)) {
                return Result.error(401, "令牌无效");
            }
            
            if (JwtUtil.isTokenExpired(token)) {
                return Result.error(401, "令牌已过期");
            }
            
            // 构造WebSocket连接地址
            String connectionUrl = String.format("ws://localhost:8080/ws/chat?token=%s", token);
            
            log.info("生成WebSocket连接地址: {}", connectionUrl);
            return Result.success(connectionUrl);
        } catch (Exception e) {
            log.error("生成WebSocket连接地址失败: {}", e.getMessage(), e);
            return Result.error(500, "生成WebSocket连接地址失败");
        }
    }
}