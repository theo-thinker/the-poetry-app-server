package com.sakura.poetry.controller;

import com.sakura.poetry.dto.UserLoginDTO;
import com.sakura.poetry.dto.UserRegisterDTO;
import com.sakura.poetry.dto.JwtResponseDTO;
import com.sakura.poetry.entity.SysUser;
import com.sakura.poetry.service.UserService;
import com.sakura.poetry.security.JwtTokenUtil;
import com.sakura.poetry.common.result.Result;
import com.sakura.poetry.entity.enums.CommonStatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 * 
 * <p>处理用户认证相关的API接口，包括登录、注册、刷新令牌等操作。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证管理", description = "用户认证相关的API接口")
public class AuthController {
    
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Value("${app.jwt.expiration}")
    private Long jwtExpiration;
    
    /**
     * 用户登录
     * 
     * @param loginDTO 登录请求数据
     * @return JWT令牌信息
     */
    @PostMapping("/login")
    @Operation(
        summary = "用户登录",
        description = "用户登录接口，验证用户名和密码，返回JWT令牌",
        requestBody = @RequestBody(
            description = "用户登录信息",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserLoginDTO.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "登录成功",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = JwtResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "401", description = "用户名或密码错误")
        }
    )
    public Result<JwtResponseDTO> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        try {
            log.info("用户登录请求: username={}", loginDTO.getUsername());
            
            // 执行认证
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
                )
            );
            
            // 设置认证信息到安全上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // 获取用户信息
            SysUser user = userService.getUserByUsername(loginDTO.getUsername());
            if (user == null) {
                log.warn("用户不存在: username={}", loginDTO.getUsername());
                return Result.error(401, "用户不存在");
            }
            
            // 检查用户状态
            if (user.getStatus() == null || user.getStatus().getValue() != 1) {
                log.warn("用户账户已被禁用: username={}", loginDTO.getUsername());
                return Result.error(401, "账户已被禁用");
            }
            
            // 生成JWT令牌
            String token = jwtTokenUtil.generateToken(
                user.getId(), 
                user.getUsername(), 
                "USER" // 简化处理，实际项目中应从用户角色表中查询
            );
            
            // 更新最后登录信息
            String clientIp = getClientIpAddress();
            userService.updateLastLoginInfo(user.getId(), clientIp);
            
            // 构建响应数据
            JwtResponseDTO response = new JwtResponseDTO(
                token,
                jwtExpiration,
                user.getId(),
                user.getUsername(),
                user.getNickname()
            );
            
            log.info("用户登录成功: username={}, userId={}", user.getUsername(), user.getId());
            return Result.success(response);
        } catch (BadCredentialsException e) {
            log.warn("用户登录失败-密码错误: username={}", loginDTO.getUsername());
            return Result.error(401, "用户名或密码错误");
        } catch (Exception e) {
            log.error("用户登录异常: username={}, error={}", loginDTO.getUsername(), e.getMessage(), e);
            return Result.error(500, "登录失败，请稍后重试");
        }
    }
    
    /**
     * 用户注册
     * 
     * @param registerDTO 注册请求数据
     * @return 注册结果
     */
    @PostMapping("/register")
    @Operation(
        summary = "用户注册",
        description = "用户注册接口，创建新用户账户",
        requestBody = @RequestBody(
            description = "用户注册信息",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserRegisterDTO.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "注册成功"),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "409", description = "用户名或邮箱已存在")
        }
    )
    public Result<String> register(@Valid @RequestBody UserRegisterDTO registerDTO) {
        try {
            log.info("用户注册请求: username={}", registerDTO.getUsername());
            
            // 检查用户名是否已存在
            SysUser existingUser = userService.getUserByUsername(registerDTO.getUsername());
            if (existingUser != null) {
                log.warn("用户名已存在: username={}", registerDTO.getUsername());
                return Result.error(409, "用户名已存在");
            }
            
            // 检查邮箱是否已存在
            if (registerDTO.getEmail() != null && !registerDTO.getEmail().isEmpty()) {
                existingUser = userService.getUserByEmail(registerDTO.getEmail());
                if (existingUser != null) {
                    log.warn("邮箱已存在: email={}", registerDTO.getEmail());
                    return Result.error(409, "邮箱已存在");
                }
            }
            
            // 创建新用户
            SysUser user = new SysUser();
            user.setUsername(registerDTO.getUsername());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setNickname(registerDTO.getNickname());
            user.setEmail(registerDTO.getEmail());
            user.setPhone(registerDTO.getPhone());
            
            // 设置默认状态
            user.setStatus(CommonStatusEnum.ENABLED);
            
            // 保存用户
            boolean success = userService.createUser(user);
            if (success) {
                log.info("用户注册成功: username={}, userId={}", user.getUsername(), user.getId());
                return Result.success("注册成功");
            } else {
                log.error("用户注册失败: username={}", registerDTO.getUsername());
                return Result.error(500, "注册失败，请稍后重试");
            }
        } catch (Exception e) {
            log.error("用户注册异常: username={}, error={}", registerDTO.getUsername(), e.getMessage(), e);
            return Result.error(500, "注册失败，请稍后重试");
        }
    }
    
    /**
     * 刷新JWT令牌
     * 
     * @param request HTTP请求
     * @return 新的JWT令牌信息
     */
    @PostMapping("/refresh")
    @Operation(
        summary = "刷新JWT令牌",
        description = "使用现有令牌刷新获取新的JWT令牌",
        parameters = {
            @Parameter(
                name = "Authorization", 
                description = "Bearer Token", 
                required = true, 
                schema = @Schema(type = "string")
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "令牌刷新成功",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = JwtResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "401", description = "令牌无效或已过期")
        }
    )
    public Result<JwtResponseDTO> refreshToken(HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            if (token == null || token.isEmpty()) {
                return Result.error(401, "令牌无效");
            }
            
            // 验证令牌
            String username = jwtTokenUtil.getUsernameFromToken(token);
            if (username == null || jwtTokenUtil.isTokenExpired(token)) {
                return Result.error(401, "令牌已过期");
            }
            
            // 获取用户信息
            SysUser user = userService.getUserByUsername(username);
            if (user == null) {
                return Result.error(401, "用户不存在");
            }
            
            // 检查用户状态
            if (user.getStatus() == null || user.getStatus().getValue() != 1) {
                return Result.error(401, "账户已被禁用");
            }
            
            // 刷新令牌
            String newToken = jwtTokenUtil.refreshToken(token);
            if (newToken == null) {
                return Result.error(401, "令牌刷新失败");
            }
            
            // 构建响应数据
            JwtResponseDTO response = new JwtResponseDTO(
                newToken,
                jwtExpiration,
                user.getId(),
                user.getUsername(),
                user.getNickname()
            );
            
            log.info("令牌刷新成功: username={}, userId={}", user.getUsername(), user.getId());
            return Result.success(response);
        } catch (Exception e) {
            log.error("令牌刷新异常: error={}", e.getMessage(), e);
            return Result.error(500, "令牌刷新失败，请稍后重试");
        }
    }
    
    /**
     * 用户登出
     * 
     * @return 登出结果
     */
    @PostMapping("/logout")
    @Operation(
        summary = "用户登出",
        description = "用户登出接口，清除认证信息",
        responses = {
            @ApiResponse(responseCode = "200", description = "登出成功")
        }
    )
    public Result<String> logout() {
        try {
            // 清除安全上下文
            SecurityContextHolder.clearContext();
            
            log.info("用户登出成功");
            return Result.success("登出成功");
        } catch (Exception e) {
            log.error("用户登出异常: error={}", e.getMessage(), e);
            return Result.error(500, "登出失败，请稍后重试");
        }
    }
    
    /**
     * 从请求中获取JWT令牌
     * 
     * @param request HTTP请求
     * @return JWT令牌
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
    /**
     * 获取客户端IP地址
     * 
     * @return 客户端IP地址
     */
    private String getClientIpAddress() {
        // 简化处理，实际项目中可能需要考虑代理等情况
        return "127.0.0.1";
    }
}