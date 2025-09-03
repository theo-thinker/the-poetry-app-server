package com.sakura.poetry.config;

import com.sakura.poetry.websocket.handler.ChatWebSocketHandler;
import com.sakura.poetry.websocket.interceptor.ChatHandshakeInterceptor;
import com.sakura.poetry.websocket.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import jakarta.annotation.PostConstruct;

/**
 * WebSocket配置类
 * 
 * <p>用于配置WebSocket相关的设置，包括端点注册、拦截器等。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private JwtConfig jwtConfig;
    
    /**
     * 初始化JWT配置
     */
    @PostConstruct
    public void init() {
        JwtUtil.setJwtConfig(jwtConfig.getJwtSecret(), jwtConfig.getJwtExpiration());
    }

    /**
     * 注册WebSocket处理器
     * 
     * @param registry WebSocket处理器注册器
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册聊天WebSocket处理器
        registry.addHandler(new ChatWebSocketHandler(), "/ws/chat")
                .addInterceptors(new ChatHandshakeInterceptor()) // 添加握手拦截器
                .setAllowedOrigins("*") // 允许跨域
                .withSockJS(); // 支持SockJS回退方案
    }
}