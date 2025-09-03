package com.sakura.poetry.common.exception;

import com.sakura.poetry.common.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 
 * <p>统一处理系统中的各种异常，将异常转换为标准的API响应格式。
 * 提供完善的异常分类处理，确保系统的稳定性和用户体验。</p>
 * 
 * <p>处理的异常类型：</p>
 * <ul>
 *   <li>业务异常 - 自定义业务逻辑异常</li>
 *   <li>参数验证异常 - 请求参数验证失败</li>
 *   <li>安全异常 - 认证、授权相关异常</li>
 *   <li>数据库异常 - 数据完整性、约束违反等</li>
 *   <li>系统异常 - 未预期的运行时异常</li>
 * </ul>
 * 
 * <p>异常处理原则：</p>
 * <ul>
 *   <li>记录详细的异常日志，便于问题排查</li>
 *   <li>返回用户友好的错误信息</li>
 *   <li>隐藏系统内部实现细节</li>
 *   <li>根据异常类型设置合适的HTTP状态码</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常
     * 
     * @param e 业务异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResult<Void> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.warn("业务异常: {} - {}", request.getRequestURI(), e.getMessage());
        return ApiResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数验证异常（@Valid注解）
     * 
     * @param e 方法参数验证异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.warn("参数验证异常: {} - {}", request.getRequestURI(), e.getMessage());
        
        // 提取第一个验证错误信息
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "参数验证失败";
        
        return ApiResult.badRequest(errorMessage);
    }

    /**
     * 处理绑定异常
     * 
     * @param e 绑定异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<Void> handleBindException(BindException e, HttpServletRequest request) {
        log.warn("参数绑定异常: {} - {}", request.getRequestURI(), e.getMessage());
        
        // 提取第一个验证错误信息
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "参数绑定失败";
        
        return ApiResult.badRequest(errorMessage);
    }

    /**
     * 处理约束违反异常（@Validated注解）
     * 
     * @param e 约束违反异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<Void> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        log.warn("约束违反异常: {} - {}", request.getRequestURI(), e.getMessage());
        
        // 提取所有违反约束的信息
        String errorMessage = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        
        return ApiResult.badRequest(errorMessage.isEmpty() ? "参数验证失败" : errorMessage);
    }

    /**
     * 处理缺少请求参数异常
     * 
     * @param e 缺少请求参数异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        log.warn("缺少请求参数异常: {} - {}", request.getRequestURI(), e.getMessage());
        return ApiResult.badRequest("缺少必需的请求参数: " + e.getParameterName());
    }

    /**
     * 处理参数类型不匹配异常
     * 
     * @param e 参数类型不匹配异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.warn("参数类型不匹配异常: {} - {}", request.getRequestURI(), e.getMessage());
        return ApiResult.badRequest("参数类型错误: " + e.getName());
    }

    /**
     * 处理HTTP请求方法不支持异常
     * 
     * @param e HTTP请求方法不支持异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiResult<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.warn("HTTP方法不支持异常: {} - {}", request.getRequestURI(), e.getMessage());
        return ApiResult.error(405, "不支持的HTTP方法: " + e.getMethod());
    }

    /**
     * 处理404异常
     * 
     * @param e 找不到处理器异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResult<Void> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        log.warn("404异常: {} - {}", request.getRequestURI(), e.getMessage());
        return ApiResult.notFound("请求的资源不存在");
    }

    /**
     * 处理认证异常
     * 
     * @param e 认证异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResult<Void> handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
        log.warn("认证异常: {} - {}", request.getRequestURI(), e.getMessage());
        
        if (e instanceof BadCredentialsException) {
            return ApiResult.error(401, "用户名或密码错误");
        }
        
        return ApiResult.unauthorized();
    }

    /**
     * 处理授权异常
     * 
     * @param e 访问拒绝异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResult<Void> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        log.warn("授权异常: {} - {}", request.getRequestURI(), e.getMessage());
        return ApiResult.forbidden();
    }

    /**
     * 处理数据完整性违反异常
     * 
     * @param e 数据完整性违反异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<Void> handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        log.warn("数据完整性违反异常: {} - {}", request.getRequestURI(), e.getMessage());
        
        String message = "数据操作失败";
        if (e.getMessage() != null) {
            if (e.getMessage().contains("Duplicate entry")) {
                message = "数据重复，请检查唯一性约束";
            } else if (e.getMessage().contains("foreign key constraint")) {
                message = "数据关联约束违反，无法执行此操作";
            }
        }
        
        return ApiResult.badRequest(message);
    }

    /**
     * 处理所有未捕获的异常
     * 
     * @param e 异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult<Void> handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常: {} - {}", request.getRequestURI(), e.getMessage(), e);
        return ApiResult.error("系统内部错误，请稍后重试");
    }
}