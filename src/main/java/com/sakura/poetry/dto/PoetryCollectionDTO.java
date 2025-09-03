package com.sakura.poetry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 诗词收藏DTO
 * 
 * <p>用于诗词收藏操作的数据传输对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "诗词收藏DTO")
public class PoetryCollectionDTO implements Serializable {

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
     * 收藏夹ID
     */
    @Schema(description = "收藏夹ID")
    private Long folderId;

    /**
     * 收藏备注
     */
    @Schema(description = "收藏备注")
    private String notes;
}