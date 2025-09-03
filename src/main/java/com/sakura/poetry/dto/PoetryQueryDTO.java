package com.sakura.poetry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 诗词查询DTO
 * 
 * <p>用于诗词查询条件的数据传输对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "诗词查询条件DTO")
public class PoetryQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 诗词标题
     */
    @Schema(description = "诗词标题")
    private String title;

    /**
     * 诗人ID
     */
    @Schema(description = "诗人ID")
    private Long poetId;

    /**
     * 朝代ID
     */
    @Schema(description = "朝代ID")
    private Long dynastyId;

    /**
     * 分类ID
     */
    @Schema(description = "分类ID")
    private Long categoryId;

    /**
     * 是否精选
     */
    @Schema(description = "是否精选")
    private Boolean isFeatured;

    /**
     * 是否热门
     */
    @Schema(description = "是否热门")
    private Boolean isHot;

    /**
     * 页码
     */
    @Schema(description = "页码", example = "1")
    private Integer page = 1;

    /**
     * 每页大小
     */
    @Schema(description = "每页大小", example = "10")
    private Integer size = 10;
}