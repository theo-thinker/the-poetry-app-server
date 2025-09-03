package com.sakura.poetry.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sakura.poetry.entity.base.BaseEntity;
import com.sakura.poetry.entity.enums.CommonStatusEnum;
import com.sakura.poetry.entity.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 系统用户实体类
 * 
 * <p>系统用户基础信息实体，包含用户的基本资料、登录信息、状态等数据。
 * 该实体对应数据库表 sys_user，用于用户认证、授权和个人信息管理。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>用户基础信息存储（用户名、昵称、邮箱、手机号等）</li>
 *   <li>用户认证信息（密码等）</li>
 *   <li>用户个人资料（头像、性别、生日、个性签名等）</li>
 *   <li>用户状态管理（启用/禁用）</li>
 *   <li>登录记录跟踪（最后登录时间、IP等）</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
@Schema(description = "系统用户实体")
public class SysUser extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     * 
     * <p>用户登录系统时使用的唯一标识符，不允许重复。
     * 必须为3-50个字符，只能包含字母、数字、下划线。</p>
     */
    @TableField("username")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度必须在3-50个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字、下划线")
    @Schema(description = "用户名", example = "sakura", required = true)
    private String username;

    /**
     * 用户昵称
     * 
     * <p>用户的显示名称，用于前端展示，可以包含中文、英文、数字等字符。</p>
     */
    @TableField("nickname")
    @Size(max = 100, message = "昵称长度不能超过100个字符")
    @Schema(description = "用户昵称", example = "樱花诗人")
    private String nickname;

    /**
     * 用户邮箱
     * 
     * <p>用户的电子邮箱地址，用于登录、找回密码、接收通知等功能。
     * 需要符合邮箱格式规范，不允许重复。</p>
     */
    @TableField("email")
    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    @Schema(description = "用户邮箱", example = "sakura@poetry.com")
    private String email;

    /**
     * 用户手机号
     * 
     * <p>用户的手机号码，用于登录、验证、接收短信通知等功能。
     * 需要符合中国大陆手机号格式，不允许重复。</p>
     */
    @TableField("phone")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Schema(description = "用户手机号", example = "13800138000")
    private String phone;

    /**
     * 用户密码
     * 
     * <p>用户登录密码，需要进行加密存储。
     * 在JSON序列化时会被忽略，保证密码安全。</p>
     */
    @TableField("password")
    @NotBlank(message = "密码不能为空")
    @JsonIgnore
    @Schema(description = "用户密码", hidden = true)
    private String password;

    /**
     * 用户头像URL
     * 
     * <p>用户头像图片的访问地址，可以是相对路径或绝对URL。</p>
     */
    @TableField("avatar")
    @Size(max = 500, message = "头像URL长度不能超过500个字符")
    @Schema(description = "用户头像URL", example = "/avatar/default.jpg")
    private String avatar;

    /**
     * 用户性别
     * 
     * <p>用户性别信息，使用枚举类型确保数据一致性。
     * 0-未知，1-男性，2-女性。</p>
     */
    @TableField("gender")
    @Schema(description = "用户性别", example = "1", allowableValues = {"0", "1", "2"})
    private GenderEnum gender;

    /**
     * 用户生日
     * 
     * <p>用户的出生日期，用于计算年龄、生日提醒等功能。</p>
     */
    @TableField("birthday")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Schema(description = "用户生日", example = "1990-01-01")
    private LocalDate birthday;

    /**
     * 用户个性签名
     * 
     * <p>用户的个性签名或简介，在个人资料页面展示。</p>
     */
    @TableField("signature")
    @Size(max = 500, message = "个性签名长度不能超过500个字符")
    @Schema(description = "个性签名", example = "诗词爱好者，喜欢古典文学")
    private String signature;

    /**
     * 用户状态
     * 
     * <p>用户账户的启用/禁用状态，禁用的用户无法登录系统。
     * 0-禁用，1-启用。</p>
     */
    @TableField("status")
    @Schema(description = "用户状态", example = "1", allowableValues = {"0", "1"})
    private CommonStatusEnum status;

    /**
     * 最后登录时间
     * 
     * <p>记录用户最后一次成功登录系统的时间，用于安全审计和用户活跃度分析。</p>
     */
    @TableField("last_login_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "最后登录时间", example = "2025-09-03 10:30:00")
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP地址
     * 
     * <p>记录用户最后一次登录时的IP地址，用于安全审计和异常登录检测。</p>
     */
    @TableField("last_login_ip")
    @Size(max = 50, message = "IP地址长度不能超过50个字符")
    @Schema(description = "最后登录IP", example = "192.168.1.100")
    private String lastLoginIp;
}