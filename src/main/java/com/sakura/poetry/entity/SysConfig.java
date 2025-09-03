package com.sakura.poetry.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sakura.poetry.entity.base.BaseEntity;
import com.sakura.poetry.entity.enums.CommonStatusEnum;
import com.sakura.poetry.entity.enums.ConfigTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 系统配置实体类
 * 
 * <p>系统配置参数实体，用于存储系统运行时的各种配置信息。
 * 该实体对应数据库表 sys_config，支持动态配置管理。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>系统参数配置（应用配置、业务规则配置等）</li>
 *   <li>动态配置管理（运行时修改配置，无需重启）</li>
 *   <li>配置分组管理（按功能模块分组组织）</li>
 *   <li>配置类型管理（字符串、数字、布尔值、JSON等）</li>
 *   <li>配置权限管理（系统配置、用户配置区分）</li>
 * </ul>
 * 
 * <p>配置设计原则：</p>
 * <ul>
 *   <li>配置键使用点分层级结构，如system.name、upload.max.size</li>
 *   <li>支持多种数据类型，便于程序解析和使用</li>
 *   <li>提供详细的配置说明，便于管理员理解</li>
 *   <li>区分系统配置和业务配置，控制修改权限</li>
 *   <li>支持配置排序，便于界面展示</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_config")
@Schema(description = "系统配置实体")
public class SysConfig extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 配置键
     * 
     * <p>配置项的唯一标识符，使用点分层级结构。
     * 如"system.name"、"upload.max.size"、"jwt.expiration"等。
     * 配置键在整个系统中必须唯一。</p>
     */
    @TableField("config_key")
    @NotBlank(message = "配置键不能为空")
    @Size(min = 1, max = 100, message = "配置键长度必须在1-100个字符之间")
    @Schema(description = "配置键", example = "system.name", required = true)
    private String configKey;

    /**
     * 配置值
     * 
     * <p>配置项的具体值，根据configType字段确定数据类型。
     * 支持字符串、数字、布尔值、JSON对象等多种格式。</p>
     */
    @TableField("config_value")
    @Schema(description = "配置值", example = "企业级诗词APP后端服务")
    private String configValue;

    /**
     * 配置名称
     * 
     * <p>配置项的显示名称，面向管理员展示。
     * 应该使用简洁明了的中文描述。</p>
     */
    @TableField("config_name")
    @NotBlank(message = "配置名称不能为空")
    @Size(min = 1, max = 200, message = "配置名称长度必须在1-200个字符之间")
    @Schema(description = "配置名称", example = "系统名称", required = true)
    private String configName;

    /**
     * 配置类型
     * 
     * <p>配置值的数据类型，决定前端如何解析和展示配置值：
     * string-字符串，number-数字，boolean-布尔值，json-JSON对象。</p>
     */
    @TableField("config_type")
    @Schema(description = "配置类型", example = "string", allowableValues = {"string", "number", "boolean", "json"})
    private ConfigTypeEnum configType;

    /**
     * 配置分组
     * 
     * <p>配置项所属的功能分组，用于组织和管理相关配置。
     * 如"system"、"upload"、"security"、"poetry"等。</p>
     */
    @TableField("config_group")
    @Size(max = 50, message = "配置分组长度不能超过50个字符")
    @Schema(description = "配置分组", example = "system")
    private String configGroup;

    /**
     * 配置描述
     * 
     * <p>配置项的详细描述信息，说明配置的作用、取值范围、注意事项等。
     * 帮助管理员正确理解和设置配置。</p>
     */
    @TableField("description")
    @Size(max = 500, message = "配置描述长度不能超过500个字符")
    @Schema(description = "配置描述", example = "系统显示名称，用于前端页面标题")
    private String description;

    /**
     * 是否系统配置
     * 
     * <p>标识该配置是否为系统核心配置：
     * 0-普通配置（管理员可修改），1-系统配置（只有超级管理员可修改）。</p>
     */
    @TableField("is_system")
    @Schema(description = "是否系统配置", example = "0", allowableValues = {"0", "1"})
    private Integer isSystem;

    /**
     * 排序序号
     * 
     * <p>配置项在同一分组内的显示顺序。
     * 数值越小排序越靠前，便于管理界面的有序展示。</p>
     */
    @TableField("sort_order")
    @Schema(description = "排序序号", example = "1")
    private Integer sortOrder;

    /**
     * 配置状态
     * 
     * <p>配置项的启用状态：
     * 0-禁用（不生效），1-启用（正常使用）。</p>
     */
    @TableField("status")
    @Schema(description = "配置状态", example = "1", allowableValues = {"0", "1"})
    private CommonStatusEnum status;
}