package com.sakura.poetry.vo;

import com.sakura.poetry.entity.enums.CommonStatusEnum;
import com.sakura.poetry.entity.enums.ConfigTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统配置VO
 * 
 * <p>用于向前端返回系统配置信息的视图对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "系统配置VO")
public class SysConfigVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 配置ID
     */
    @Schema(description = "配置ID")
    private Long id;

    /**
     * 配置键
     */
    @Schema(description = "配置键")
    private String configKey;

    /**
     * 配置值
     */
    @Schema(description = "配置值")
    private String configValue;

    /**
     * 配置名称
     */
    @Schema(description = "配置名称")
    private String configName;

    /**
     * 配置分组
     */
    @Schema(description = "配置分组")
    private String configGroup;

    /**
     * 配置类型
     */
    @Schema(description = "配置类型")
    private ConfigTypeEnum configType;

    /**
     * 描述
     */
    @Schema(description = "配置描述")
    private String description;

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