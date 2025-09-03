package com.sakura.poetry.common.result;

import com.sakura.poetry.common.ApiResult;

/**
 * 统一响应结果别名类
 * 
 * <p>为了简化使用，提供ApiResult的别名类。</p>
 * 
 * @param <T> 响应数据类型
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public class Result<T> extends ApiResult<T> {
    
    /**
     * 构造方法
     * 
     * @param code 状态码
     * @param message 消息
     * @param data 数据
     */
    public Result(Integer code, String message, T data) {
        super(code, message, data);
    }

    /**
     * 成功响应（无数据）
     * 
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 成功响应（带数据）
     * 
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 成功响应（自定义消息）
     * 
     * @param message 响应消息
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> Result<T> success(String message) {
        return new Result<>(200, message, null);
    }

    /**
     * 成功响应（自定义消息和数据）
     * 
     * @param message 响应消息
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    /**
     * 失败响应
     * 
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 失败响应
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

    /**
     * 失败响应（自定义状态码）
     * 
     * @param code 状态码
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 失败响应
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
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
    public static <T> Result<T> error(Integer code, String message, T data) {
        return new Result<>(code, message, data);
    }

    /**
     * 参数错误响应
     * 
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 参数错误响应
     */
    public static <T> Result<T> badRequest(String message) {
        return new Result<>(400, message, null);
    }

    /**
     * 未认证响应
     * 
     * @param <T> 数据类型
     * @return 未认证响应
     */
    public static <T> Result<T> unauthorized() {
        return new Result<>(401, "未认证，请先登录", null);
    }

    /**
     * 权限不足响应
     * 
     * @param <T> 数据类型
     * @return 权限不足响应
     */
    public static <T> Result<T> forbidden() {
        return new Result<>(403, "权限不足，拒绝访问", null);
    }

    /**
     * 资源不存在响应
     * 
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 资源不存在响应
     */
    public static <T> Result<T> notFound(String message) {
        return new Result<>(404, message, null);
    }
}