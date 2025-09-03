package com.sakura.poetry.vo;

import com.sakura.poetry.entity.enums.CommonStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 收藏夹详情VO
 * 
 * <p>用于向前端返回收藏夹详情信息的视图对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "收藏夹详情VO")
public class CollectionFolderDetailVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 收藏夹ID
     */
    @Schema(description = "收藏夹ID")
    private Long id;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String userNickname;

    /**
     * 收藏夹名称
     */
    @Schema(description = "收藏夹名称")
    private String folderName;

    /**
     * 描述
     */
    @Schema(description = "收藏夹描述")
    private String description;

    /**
     * 封面图片
     */
    @Schema(description = "封面图片URL")
    private String coverImage;

    /**
     * 诗词数量
     */
    @Schema(description = "诗词数量")
    private Integer poetryCount;

    /**
     * 是否公开
     */
    @Schema(description = "是否公开")
    private Boolean isPublic;

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
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;
}