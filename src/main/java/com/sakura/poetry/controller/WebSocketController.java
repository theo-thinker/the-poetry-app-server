package com.sakura.poetry.controller;

import com.sakura.poetry.common.result.Result;
import com.sakura.poetry.websocket.manager.WebSocketSessionManager;
import com.sakura.poetry.websocket.model.OnlineUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * WebSocket控制器
 * 
 * <p>提供WebSocket相关的HTTP接口。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
@RestController
@RequestMapping("/api/websocket")
@Tag(name = "WebSocket管理", description = "WebSocket连接和在线用户管理相关接口")
public class WebSocketController {

    private final WebSocketSessionManager sessionManager = new WebSocketSessionManager();

    /**
     * 获取在线用户列表
     * 
     * @return 在线用户列表
     */
    @GetMapping("/online-users")
    @Operation(summary = "获取在线用户列表", description = "获取当前所有在线用户的信息")
    public Result<Collection<OnlineUser>> getOnlineUsers() {
        try {
            Collection<OnlineUser> onlineUsers = sessionManager.getOnlineUsers().values();
            log.info("获取在线用户列表，当前在线用户数: {}", onlineUsers.size());
            return Result.success(onlineUsers);
        } catch (Exception e) {
            log.error("获取在线用户列表失败: {}", e.getMessage(), e);
            return Result.error(500, "获取在线用户列表失败");
        }
    }

    /**
     * 获取在线用户数量
     * 
     * @return 在线用户数量
     */
    @GetMapping("/online-count")
    @Operation(summary = "获取在线用户数量", description = "获取当前在线用户的总数量")
    public Result<Integer> getOnlineUserCount() {
        try {
            int count = sessionManager.getOnlineUserCount();
            log.info("获取在线用户数量: {}", count);
            return Result.success(count);
        } catch (Exception e) {
            log.error("获取在线用户数量失败: {}", e.getMessage(), e);
            return Result.error(500, "获取在线用户数量失败");
        }
    }
}