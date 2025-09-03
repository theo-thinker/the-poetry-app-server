package com.sakura.poetry.vo;

import com.sakura.poetry.entity.enums.CommonStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 朝代列表VO
 * 
 * <p>用于向前端返回朝代列表信息的视图对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "朝代列表VO")
public class DynastyListVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 朝代ID
     */
    @Schema(description = "朝代ID")
    private Long id;

    /**
     * 朝代名称
     */
    @Schema(description = "朝代名称")
    private String dynastyName;

    /**
     * 朝代编码
     */
    @Schema(description = "朝代编码")
    private String dynastyCode;

    /**
     * 开始年份
     */
    @Schema(description = "开始年份")
    private Integer startYear;

    /**
     * 结束年份
     */
    @Schema(description = "结束年份")
    private Integer endYear;

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
}