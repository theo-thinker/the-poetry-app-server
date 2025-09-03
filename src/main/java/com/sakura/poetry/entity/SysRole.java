package com.sakura.poetry.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sakura.poetry.entity.base.BaseEntity;
import com.sakura.poetry.entity.enums.CommonStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 系统角色实体类
 * 
 * <p>系统角色信息实体，用于权限管理和用户分类。
 * 该实体对应数据库表 sys_role，定义了系统中的各种角色类型。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>角色基础信息管理（角色名称、编码、描述等）</li>
 *   <li>角色权限分配（通过角色权限关联表）</li>
 *   <li>用户角色关联（通过用户角色关联表）</li>
 *   <li>角色状态管理（启用/禁用）</li>
 *   <li>角色排序和层级管理</li>
 * </ul>
 * 
 * <p>角色设计原则：</p>
 * <ul>
 *   <li>角色编码必须唯一，用于系统内部识别</li>
 *   <li>角色名称面向用户，便于理解</li>
 *   <li>支持角色层级和排序，便于管理</li>
 *   <li>支持软删除，避免数据丢失</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role")
@Schema(description = "系统角色实体")
public class SysRole extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     * 
     * <p>角色的显示名称，面向用户展示，应该具有良好的可读性。
     * 例如：超级管理员、系统管理员、内容管理员、普通用户等。</p>
     */
    @TableField("role_name")
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 2, max = 50, message = "角色名称长度必须在2-50个字符之间")
    @Schema(description = "角色名称", example = "系统管理员", required = true)
    private String roleName;

    /**
     * 角色编码
     * 
     * <p>角色的唯一标识符，用于系统内部识别和权限判断。
     * 建议使用英文大写字母和下划线组合，例如：ADMIN、USER、CONTENT_MANAGER等。</p>
     */
    @TableField("role_code")
    @NotBlank(message = "角色编码不能为空")
    @Size(min = 2, max = 50, message = "角色编码长度必须在2-50个字符之间")
    @Pattern(regexp = "^[A-Z][A-Z0-9_]*$", message = "角色编码只能包含大写字母、数字、下划线，且必须以字母开头")
    @Schema(description = "角色编码", example = "ADMIN", required = true)
    private String roleCode;

    /**
     * 角色描述
     * 
     * <p>详细描述该角色的职责和权限范围，便于管理员理解和分配。</p>
     */
    @TableField("description")
    @Size(max = 255, message = "角色描述长度不能超过255个字符")
    @Schema(description = "角色描述", example = "系统管理员，拥有大部分系统管理权限")
    private String description;

    /**
     * 排序序号
     * 
     * <p>用于角色列表的排序显示，数值越小排序越靠前。
     * 可以用于表示角色的重要性或层级关系。</p>
     */
    @TableField("sort_order")
    @Schema(description = "排序序号", example = "1")
    private Integer sortOrder;

    /**
     * 角色状态
     * 
     * <p>角色的启用/禁用状态，禁用的角色无法被分配给用户。
     * 0-禁用，1-启用。</p>
     */
    @TableField("status")
    @Schema(description = "角色状态", example = "1", allowableValues = {"0", "1"})
    private CommonStatusEnum status;
}