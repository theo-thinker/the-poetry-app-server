package com.sakura.poetry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 收藏夹创建DTO
 * 
 * <p>用于创建收藏夹的数据传输对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "收藏夹创建DTO")
public class CollectionFolderCreateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 收藏夹名称
     */
    @NotBlank(message = "收藏夹名称不能为空")
    @Schema(description = "收藏夹名称", example = "唐诗精选")
    private String folderName;

    /**
     * 描述
     */
    @Schema(description = "收藏夹描述", example = "我收藏的唐诗精品")
    private String description;

    /**
     * 封面图片
     */
    @Schema(description = "封面图片URL")
    private String coverImage;

    /**
     * 是否公开
     */
    @Schema(description = "是否公开", example = "true")
    private Boolean isPublic = false;

    /**
     * 排序序号
     */
    @Schema(description = "排序序号", example = "1")
    private Integer sortOrder = 0;
}