package com.sakura.poetry.vo;

import com.sakura.poetry.entity.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户信息VO
 * 
 * <p>用于向前端返回用户信息的视图对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "用户信息VO")
public class UserInfoVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 头像
     */
    @Schema(description = "头像URL")
    private String avatar;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private GenderEnum gender;

    /**
     * 生日
     */
    @Schema(description = "生日")
    private LocalDate birthday;

    /**
     * 个性签名
     */
    @Schema(description = "个性签名")
    private String signature;

    /**
     * 注册时间
     */
    @Schema(description = "注册时间")
    private LocalDateTime createdTime;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;
}