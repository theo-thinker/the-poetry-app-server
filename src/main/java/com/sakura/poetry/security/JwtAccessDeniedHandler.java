package com.sakura.poetry.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT访问拒绝处理器
 * 
 * <p>处理用户已认证但权限不足的访问请求。
 * 当用户试图访问没有权限的资源时，会触发此处理器。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>拦截权限不足的请求</li>
 *   <li>返回统一格式的权限错误响应</li>
 *   <li>记录权限拒绝的日志</li>
 *   <li>设置适当的HTTP状态码</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>普通用户访问管理员接口</li>
 *   <li>用户访问其他用户的私有资源</li>
 *   <li>角色权限限制的接口访问</li>
 * </ul>
 * 
 * <p>响应格式：</p>
 * <pre>
 * {
 *   "code": 403,
 *   "message": "权限不足，拒绝访问",
 *   "data": null,
 *   "timestamp": "2025-09-03T10:30:00"
 * }
 * </pre>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    
    private static final Logger log = LoggerFactory.getLogger(JwtAccessDeniedHandler.class);

    /**
     * 处理访问拒绝异常
     * 
     * <p>当用户已认证但权限不足时调用此方法。
     * 返回403状态码和统一格式的权限错误信息。</p>
     * 
     * @param request HTTP请求
     * @param response HTTP响应
     * @param accessDeniedException 访问拒绝异常
     * @throws IOException IO异常
     */
    @Override
    public void handle(HttpServletRequest request,
                      HttpServletResponse response,
                      AccessDeniedException accessDeniedException) throws IOException {
        
        // 记录权限拒绝的请求
        log.warn("权限不足的访问请求: {} {}, 用户: {}, 异常: {}", 
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteUser(),
                accessDeniedException.getMessage());
        
        // 设置响应状态码和内容类型
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        // 构建错误响应
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", HttpServletResponse.SC_FORBIDDEN);
        errorResponse.put("message", "权限不足，拒绝访问");
        errorResponse.put("data", null);
        errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
        errorResponse.put("path", request.getRequestURI());
        
        // 写入响应
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}