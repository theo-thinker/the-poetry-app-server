package com.sakura.poetry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 评论创建DTO
 * 
 * <p>用于创建评论的数据传输对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "评论创建DTO")
public class CommentCreateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 诗词ID
     */
    @NotNull(message = "诗词ID不能为空")
    @Schema(description = "诗词ID")
    private Long poetryId;

    /**
     * 父评论ID（回复评论时使用）
     */
    @Schema(description = "父评论ID")
    private Long parentId;

    /**
     * 回复用户ID（回复评论时使用）
     */
    @Schema(description = "回复用户ID")
    private Long replyUserId;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    @Schema(description = "评论内容")
    private String content;
}