package com.sakura.poetry.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT认证过滤器
 * 
 * <p>处理HTTP请求中的JWT令牌，进行用户身份认证。
 * 该过滤器在每个请求中只执行一次，确保认证的一致性和性能。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>从请求头中提取JWT令牌</li>
 *   <li>验证令牌的有效性和完整性</li>
 *   <li>从令牌中解析用户信息</li>
 *   <li>设置Spring Security认证上下文</li>
 *   <li>处理认证失败的情况</li>
 * </ul>
 * 
 * <p>工作流程：</p>
 * <ol>
 *   <li>从请求头Authorization中获取JWT令牌</li>
 *   <li>验证令牌格式和签名</li>
 *   <li>从令牌中提取用户名</li>
 *   <li>加载用户详细信息</li>
 *   <li>验证令牌与用户信息的匹配性</li>
 *   <li>设置认证信息到SecurityContext</li>
 * </ol>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * JWT令牌头名称
     */
    @Value("${app.jwt.header-name}")
    private String tokenHeader;

    /**
     * JWT令牌前缀
     */
    @Value("${app.jwt.token-prefix}")
    private String tokenPrefix;

    /**
     * 执行过滤逻辑
     * 
     * @param request HTTP请求
     * @param response HTTP响应
     * @param filterChain 过滤器链
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                   @NonNull HttpServletResponse response,
                                   @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        // 从请求头中获取JWT令牌
        String authToken = getTokenFromRequest(request);
        
        if (StringUtils.hasText(authToken)) {
            try {
                // 从令牌中获取用户名
                String username = jwtTokenUtil.getUsernameFromToken(authToken);
                
                log.debug("检查用户认证: {}", username);
                
                // 如果用户名不为空且当前没有认证信息
                if (StringUtils.hasText(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    
                    // 加载用户详细信息
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    
                    // 验证令牌
                    if (jwtTokenUtil.validateToken(authToken, userDetails.getUsername())) {
                        // 创建认证令牌
                        UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        
                        // 设置认证详细信息
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        
                        // 设置认证信息到SecurityContext
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        
                        log.debug("认证用户 '{}' 成功，设置安全上下文", username);
                    } else {
                        log.warn("JWT令牌验证失败，用户: {}", username);
                    }
                }
            } catch (Exception e) {
                log.error("无法设置用户认证: {}", e.getMessage(), e);
            }
        }
        
        // 继续执行过滤器链
        filterChain.doFilter(request, response);
    }

    /**
     * 从请求中获取JWT令牌
     * 
     * @param request HTTP请求
     * @return JWT令牌，如果没有找到则返回null
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(tokenHeader);
        
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenPrefix + " ")) {
            // 移除令牌前缀
            return bearerToken.substring(tokenPrefix.length() + 1);
        }
        
        return null;
    }

    /**
     * 判断是否应该应用过滤器
     * 
     * <p>可以在此方法中定义哪些请求路径不需要进行JWT认证，
     * 例如登录接口、注册接口、公开的API文档等。</p>
     * 
     * @param request HTTP请求
     * @return true-应用过滤器，false-跳过过滤器
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        
        // 跳过认证的路径
        return path.startsWith("/api/auth/") ||
               path.startsWith("/doc.html") ||
               path.startsWith("/swagger-") ||
               path.startsWith("/webjars/") ||
               path.startsWith("/v3/api-docs") ||
               path.startsWith("/druid/") ||
               path.startsWith("/actuator/");
    }
}