package com.sakura.poetry.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志实体类
 * 
 * <p>系统操作日志实体，用于记录用户在系统中的各种操作行为。
 * 该实体对应数据库表 sys_log，支持操作审计和安全监控。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>操作记录管理（用户操作、系统调用记录）</li>
 *   <li>安全审计支持（登录日志、敏感操作记录）</li>
 *   <li>性能监控支持（接口响应时间统计）</li>
 *   <li>异常监控支持（错误操作和异常情况记录）</li>
 *   <li>用户行为分析（操作习惯、使用模式分析）</li>
 * </ul>
 * 
 * <p>日志记录规则：</p>
 * <ul>
 *   <li>记录所有重要的业务操作（增删改操作）</li>
 *   <li>记录用户登录、登出等安全相关操作</li>
 *   <li>记录系统配置修改等管理操作</li>
 *   <li>记录接口调用的详细信息和响应时间</li>
 *   <li>记录操作失败的详细错误信息</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Accessors(chain = true)
@TableName("sys_log")
@Schema(description = "系统日志实体")
public class SysLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     * 
     * <p>日志记录的唯一标识符，使用数据库自增策略。</p>
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "日志ID", example = "1")
    private Long id;

    /**
     * 用户ID
     * 
     * <p>执行操作的用户ID，外键关联到sys_user表。
     * 如果为空，表示系统操作或匿名操作。</p>
     */
    @TableField("user_id")
    @Schema(description = "用户ID", example = "1")
    private Long userId;

    /**
     * 用户名
     * 
     * <p>执行操作的用户名，用于日志查询和展示。
     * 冗余存储用户名，避免关联查询。</p>
     */
    @TableField("username")
    @Size(max = 50, message = "用户名长度不能超过50个字符")
    @Schema(description = "用户名", example = "admin")
    private String username;

    /**
     * 操作内容
     * 
     * <p>用户执行的具体操作描述，使用中文描述。
     * 如"登录系统"、"添加诗词"、"修改用户信息"等。</p>
     */
    @TableField("operation")
    @Size(max = 200, message = "操作内容长度不能超过200个字符")
    @Schema(description = "操作内容", example = "用户登录")
    private String operation;

    /**
     * 请求方法
     * 
     * <p>HTTP请求的完整方法路径，包含类名和方法名。
     * 如"com.sakura.poetry.controller.UserController.login"。</p>
     */
    @TableField("method")
    @Size(max = 200, message = "请求方法长度不能超过200个字符")
    @Schema(description = "请求方法", example = "com.sakura.poetry.controller.UserController.login")
    private String method;

    /**
     * 请求参数
     * 
     * <p>HTTP请求的参数信息，通常为JSON格式。
     * 敏感信息（如密码）应该被过滤或脱敏处理。</p>
     */
    @TableField("params")
    @Schema(description = "请求参数", example = "{\"username\":\"admin\"}")
    private String params;

    /**
     * 执行时长
     * 
     * <p>操作执行的耗时，单位为毫秒。
     * 用于性能监控和接口响应时间分析。</p>
     */
    @TableField("time")
    @Schema(description = "执行时长(毫秒)", example = "125")
    private Long time;

    /**
     * IP地址
     * 
     * <p>用户操作时的客户端IP地址。
     * 用于安全监控和地域分析。</p>
     */
    @TableField("ip")
    @Size(max = 50, message = "IP地址长度不能超过50个字符")
    @Schema(description = "IP地址", example = "192.168.1.100")
    private String ip;

    /**
     * 操作地点
     * 
     * <p>根据IP地址解析出的地理位置信息。
     * 如"中国 北京 北京"等。</p>
     */
    @TableField("location")
    @Size(max = 200, message = "操作地点长度不能超过200个字符")
    @Schema(description = "操作地点", example = "中国 北京 北京")
    private String location;

    /**
     * 用户代理
     * 
     * <p>用户操作时的浏览器用户代理字符串。
     * 用于统计用户使用的浏览器和设备类型。</p>
     */
    @TableField("user_agent")
    @Size(max = 500, message = "用户代理长度不能超过500个字符")
    @Schema(description = "用户代理", example = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
    private String userAgent;

    /**
     * 操作状态
     * 
     * <p>操作执行的结果状态：
     * 0-失败，1-成功。</p>
     */
    @TableField("status")
    @Schema(description = "操作状态", example = "1", allowableValues = {"0", "1"})
    private Integer status;

    /**
     * 错误消息
     * 
     * <p>操作失败时的详细错误信息。
     * 只有当status为0（失败）时才有值。</p>
     */
    @TableField("error_msg")
    @Schema(description = "错误消息", example = "用户名或密码错误")
    private String errorMsg;

    /**
     * 创建时间
     * 
     * <p>日志记录的创建时间，即操作发生的时间。
     * 在插入日志记录时自动填充当前时间。</p>
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间", example = "2025-09-03 10:30:00")
    private LocalDateTime createdTime;
}