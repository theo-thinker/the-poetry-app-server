package com.sakura.poetry.vo;

import com.sakura.poetry.entity.enums.CommonStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分类树形VO
 * 
 * <p>用于向前端返回树形分类信息的视图对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "分类树形VO")
public class CategoryTreeVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @Schema(description = "分类ID")
    private Long id;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String categoryName;

    /**
     * 分类编码
     */
    @Schema(description = "分类编码")
    private String categoryCode;

    /**
     * 父分类ID
     */
    @Schema(description = "父分类ID")
    private Long parentId;

    /**
     * 描述
     */
    @Schema(description = "分类描述")
    private String description;

    /**
     * 图标
     */
    @Schema(description = "图标URL")
    private String icon;

    /**
     * 排序序号
     */
    @Schema(description = "排序序号")
    private Integer sortOrder;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private CommonStatusEnum status;

    /**
     * 子分类列表
     */
    @Schema(description = "子分类列表")
    private List<CategoryTreeVO> children;
}