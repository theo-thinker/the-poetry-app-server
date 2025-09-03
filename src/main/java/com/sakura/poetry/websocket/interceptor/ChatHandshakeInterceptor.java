package com.sakura.poetry.websocket.interceptor;

import com.sakura.poetry.websocket.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * WebSocket握手拦截器
 * 
 * <p>用于在WebSocket握手阶段进行身份验证和授权检查。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
public class ChatHandshakeInterceptor implements HandshakeInterceptor {

    /**
     * 在握手之前执行
     * 
     * @param request HTTP请求
     * @param response HTTP响应
     * @param wsHandler WebSocket处理器
     * @param attributes 属性映射
     * @return 是否继续握手
     * @throws Exception 异常
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, 
                                  WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info("WebSocket握手开始: {}", request.getURI());
        
        // 从请求参数中获取JWT令牌
        String token = null;
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            token = servletRequest.getServletRequest().getParameter("token");
        }
        
        // 验证令牌
        if (token != null && JwtUtil.validateToken(token) && !JwtUtil.isTokenExpired(token)) {
            Long userId = JwtUtil.getUserIdFromToken(token);
            String username = JwtUtil.getUsernameFromToken(token);
            
            // 将用户信息存储到属性中
            attributes.put("userId", userId);
            attributes.put("username", username);
            
            log.info("WebSocket握手认证成功: 用户ID={}, 用户名={}", userId, username);
            return true;
        } else {
            log.warn("WebSocket握手认证失败: 令牌无效或已过期");
            // 可以设置响应状态码
            response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return false;
        }
    }

    /**
     * 在握手之后执行
     * 
     * @param request HTTP请求
     * @param response HTTP响应
     * @param wsHandler WebSocket处理器
     * @param exception 异常
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, 
                              WebSocketHandler wsHandler, Exception exception) {
        log.info("WebSocket握手完成: {}", request.getURI());
    }
}