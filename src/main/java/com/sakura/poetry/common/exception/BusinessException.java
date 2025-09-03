package com.sakura.poetry.common.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * 业务异常类
 * 
 * <p>自定义业务异常，用于处理业务逻辑中的各种异常情况。
 * 该异常类携带错误码和错误消息，便于统一异常处理和前端错误展示。</p>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>业务规则验证失败</li>
 *   <li>数据状态异常</li>
 *   <li>权限校验失败</li>
 *   <li>资源不存在</li>
 *   <li>操作冲突</li>
 * </ul>
 * 
 * <p>使用示例：</p>
 * <pre>
 * // 抛出业务异常
 * throw new BusinessException("用户不存在");
 * throw new BusinessException(404, "诗词未找到");
 * </pre>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Getter
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误消息
     */
    private final String message;

    /**
     * 构造方法（默认错误码500）
     * 
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
    }

    /**
     * 构造方法（自定义错误码）
     * 
     * @param code 错误码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造方法（带原因异常）
     * 
     * @param message 错误消息
     * @param cause 原因异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
        this.message = message;
    }

    /**
     * 构造方法（完整参数）
     * 
     * @param code 错误码
     * @param message 错误消息
     * @param cause 原因异常
     */
    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
    
    /**
     * 获取错误码
     * 
     * @return 错误码
     */
    public Integer getCode() {
        return code;
    }
}