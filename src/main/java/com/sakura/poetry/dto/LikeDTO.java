package com.sakura.poetry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 点赞DTO
 * 
 * <p>用于点赞操作的数据传输对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "点赞DTO")
public class LikeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 目标类型（1-诗词，2-诗人，3-评论）
     */
    @NotNull(message = "目标类型不能为空")
    @Schema(description = "目标类型（1-诗词，2-诗人，3-评论）")
    private Integer targetType;

    /**
     * 目标ID
     */
    @NotNull(message = "目标ID不能为空")
    @Schema(description = "目标ID")
    private Long targetId;
}