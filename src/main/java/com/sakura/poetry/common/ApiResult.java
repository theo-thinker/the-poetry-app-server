package com.sakura.poetry.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 统一API响应结果类
 * 
 * <p>定义系统所有API接口的统一响应格式，确保前后端数据交互的一致性。
 * 该类包装了响应状态码、消息、数据和时间戳等信息。</p>
 * 
 * <p>响应格式示例：</p>
 * <pre>
 * {
 *   "code": 200,
 *   "message": "操作成功",
 *   "data": {...},
 *   "timestamp": "2025-09-03T10:30:00"
 * }
 * </pre>
 * 
 * <p>主要特性：</p>
 * <ul>
 *   <li>统一的响应格式，便于前端解析</li>
 *   <li>丰富的状态码定义，覆盖各种业务场景</li>
 *   <li>泛型数据支持，适应不同数据类型</li>
 *   <li>便捷的静态方法，快速构建响应</li>
 *   <li>完整的API文档注解</li>
 * </ul>
 * 
 * @param <T> 响应数据类型
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@NoArgsConstructor
@Schema(description = "统一响应结果")
public class ApiResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应状态码
     * 
     * <p>使用HTTP状态码作为基础，扩展业务状态码。
     * 200表示成功，4xx表示客户端错误，5xx表示服务器错误。</p>
     */
    @Schema(description = "响应状态码", example = "200")
    private Integer code;

    /**
     * 响应消息
     * 
     * <p>对响应状态的文字描述，便于用户理解操作结果。</p>
     */
    @Schema(description = "响应消息", example = "操作成功")
    private String message;

    /**
     * 响应数据
     * 
     * <p>实际的业务数据，可以是任意类型的对象。
     * 成功时包含业务数据，失败时通常为null。</p>
     */
    @Schema(description = "响应数据")
    private T data;

    /**
     * 响应时间戳
     * 
     * <p>响应生成的时间，用于调试和日志记录。</p>
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "响应时间戳", example = "2025-09-03 10:30:00")
    private LocalDateTime timestamp;

    /**
     * 构造方法
     * 
     * @param code 状态码
     * @param message 消息
     * @param data 数据
     */
    public ApiResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * 成功响应（无数据）
     * 
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> ApiResult<T> success() {
        return new ApiResult<>(200, "操作成功", null);
    }

    /**
     * 成功响应（带数据）
     * 
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(200, "操作成功", data);
    }

    /**
     * 成功响应（自定义消息）
     * 
     * @param message 响应消息
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> ApiResult<T> success(String message) {
        return new ApiResult<>(200, message, null);
    }

    /**
     * 成功响应（自定义消息和数据）
     * 
     * @param message 响应消息
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> ApiResult<T> success(String message, T data) {
        return new ApiResult<>(200, message, data);
    }

    /**
     * 失败响应
     * 
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 失败响应
     */
    public static <T> ApiResult<T> error(String message) {
        return new ApiResult<>(500, message, null);
    }

    /**
     * 失败响应（自定义状态码）
     * 
     * @param code 状态码
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 失败响应
     */
    public static <T> ApiResult<T> error(Integer code, String message) {
        return new ApiResult<>(code, message, null);
    }

    /**
     * 失败响应（完整参数）
     * 
     * @param code 状态码
     * @param message 错误消息
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 失败响应
     */
    public static <T> ApiResult<T> error(Integer code, String message, T data) {
        return new ApiResult<>(code, message, data);
    }

    /**
     * 参数错误响应
     * 
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 参数错误响应
     */
    public static <T> ApiResult<T> badRequest(String message) {
        return new ApiResult<>(400, message, null);
    }

    /**
     * 未认证响应
     * 
     * @param <T> 数据类型
     * @return 未认证响应
     */
    public static <T> ApiResult<T> unauthorized() {
        return new ApiResult<>(401, "未认证，请先登录", null);
    }

    /**
     * 权限不足响应
     * 
     * @param <T> 数据类型
     * @return 权限不足响应
     */
    public static <T> ApiResult<T> forbidden() {
        return new ApiResult<>(403, "权限不足，拒绝访问", null);
    }

    /**
     * 资源不存在响应
     * 
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 资源不存在响应
     */
    public static <T> ApiResult<T> notFound(String message) {
        return new ApiResult<>(404, message, null);
    }

    /**
     * 判断是否成功响应
     * 
     * @return true-成功，false-失败
     */
    public boolean isSuccess() {
        return this.code != null && this.code == 200;
    }
}