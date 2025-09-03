package com.sakura.poetry.config;

import com.sakura.poetry.security.JwtAccessDeniedHandler;
import com.sakura.poetry.security.JwtAuthenticationEntryPoint;
import com.sakura.poetry.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * Spring Security安全配置类
 * 
 * <p>配置应用程序的安全策略，包括认证、授权、CORS、JWT等。
 * 基于Spring Security 6.5.3实现，使用最新的配置方式。</p>
 * 
 * <p>主要配置：</p>
 * <ul>
 *   <li>JWT无状态认证配置</li>
 *   <li>URL访问权限控制</li>
 *   <li>CORS跨域配置</li>
 *   <li>异常处理配置</li>
 *   <li>密码加密配置</li>
 * </ul>
 * 
 * <p>安全策略：</p>
 * <ul>
 *   <li>使用JWT令牌进行认证，无需Session</li>
 *   <li>公开接口无需认证</li>
 *   <li>管理接口需要相应权限</li>
 *   <li>支持方法级权限控制</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 密码编码器
     * 
     * <p>使用BCrypt算法对密码进行加密，提供强安全性。</p>
     * 
     * @return BCrypt密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器
     * 
     * <p>提供认证管理功能，用于用户登录验证。</p>
     * 
     * @param config 认证配置
     * @return 认证管理器
     * @throws Exception 配置异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * CORS配置
     * 
     * <p>配置跨域资源共享，允许前端应用访问后端API。</p>
     * 
     * @return CORS配置源
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 允许的源
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        
        // 允许的HTTP方法
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // 允许的请求头
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        
        // 允许发送认证信息
        configuration.setAllowCredentials(true);
        
        // 预检请求的缓存时间
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }

    /**
     * 安全过滤器链配置
     * 
     * <p>配置HTTP安全策略，包括URL权限、认证方式、异常处理等。</p>
     * 
     * @param http HTTP安全配置器
     * @return 安全过滤器链
     * @throws Exception 配置异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 禁用CSRF（使用JWT不需要CSRF保护）
            .csrf(AbstractHttpConfigurer::disable)
            
            // 配置CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 配置会话管理（无状态）
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 配置异常处理
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            )
            
            // 配置URL访问权限
            .authorizeHttpRequests(auth -> auth
                // 公开接口 - 无需认证
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                
                // API文档相关 - 开发环境公开
                .requestMatchers("/doc.html", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**").permitAll()
                
                // Druid监控 - 需要管理员权限
                .requestMatchers("/druid/**").hasRole("ADMIN")
                
                // 健康检查 - 公开
                .requestMatchers("/actuator/health").permitAll()
                .requestMatchers("/actuator/**").hasRole("ADMIN")
                
                // 诗词查看相关 - 允许匿名访问
                .requestMatchers(HttpMethod.GET, "/api/poetry/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/poet/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/dynasty/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/category/**").permitAll()
                
                // 用户相关 - 需要认证
                .requestMatchers("/api/user/**").authenticated()
                
                // 管理相关 - 需要管理员权限
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/poetry/**").hasAnyRole("ADMIN", "CONTENT_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/poetry/**").hasAnyRole("ADMIN", "CONTENT_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/poetry/**").hasAnyRole("ADMIN", "CONTENT_ADMIN")
                
                // 系统管理 - 需要超级管理员权限
                .requestMatchers("/api/system/**").hasRole("SUPER_ADMIN")
                
                // 其他所有请求都需要认证
                .anyRequest().authenticated()
            );

        // 添加JWT认证过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}