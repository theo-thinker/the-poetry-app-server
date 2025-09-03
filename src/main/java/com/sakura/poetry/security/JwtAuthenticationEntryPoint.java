package com.sakura.poetry.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT认证入口点
 * 
 * <p>处理未认证访问的请求，返回统一的错误响应。
 * 当用户访问需要认证的资源但没有提供有效的认证信息时，会触发此入口点。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>拦截未认证的请求</li>
 *   <li>返回统一格式的错误响应</li>
 *   <li>记录认证失败的日志</li>
 *   <li>设置适当的HTTP状态码</li>
 * </ul>
 * 
 * <p>响应格式：</p>
 * <pre>
 * {
 *   "code": 401,
 *   "message": "认证失败，请先登录",
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
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    /**
     * 处理认证异常
     * 
     * <p>当用户访问需要认证的资源但认证失败时调用此方法。
     * 返回401状态码和统一格式的错误信息。</p>
     * 
     * @param request HTTP请求
     * @param response HTTP响应
     * @param authException 认证异常
     * @throws IOException IO异常
     */
    @Override
    public void commence(HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException authException) throws IOException {
        
        // 记录认证失败的请求
        log.warn("未认证的访问请求: {} {}, 异常: {}", 
                request.getMethod(), 
                request.getRequestURI(), 
                authException.getMessage());
        
        // 设置响应状态码和内容类型
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        // 构建错误响应
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", HttpServletResponse.SC_UNAUTHORIZED);
        errorResponse.put("message", "认证失败，请先登录");
        errorResponse.put("data", null);
        errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
        errorResponse.put("path", request.getRequestURI());
        
        // 写入响应
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}