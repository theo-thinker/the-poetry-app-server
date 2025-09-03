package com.sakura.poetry.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户角色关联实体类
 * 
 * <p>用户和角色的多对多关联关系实体，实现用户权限的分配管理。
 * 该实体对应数据库表 sys_user_role，建立用户与角色之间的关联关系。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>用户角色关系管理（一个用户可以拥有多个角色）</li>
 *   <li>角色用户关系管理（一个角色可以分配给多个用户）</li>
 *   <li>权限继承（用户通过角色获得相应权限）</li>
 *   <li>关联关系审计（记录分配时间和操作人）</li>
 * </ul>
 * 
 * <p>设计说明：</p>
 * <ul>
 *   <li>使用复合唯一索引防止重复分配</li>
 *   <li>支持级联删除，用户或角色删除时自动清理关联关系</li>
 *   <li>记录创建信息，便于审计和追踪</li>
 *   <li>不包含软删除字段，删除即物理删除</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Accessors(chain = true)
@TableName("sys_user_role")
@Schema(description = "用户角色关联实体")
public class SysUserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     * 
     * <p>关联关系的唯一标识符，使用数据库自增策略。</p>
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键ID", example = "1")
    private Long id;

    /**
     * 用户ID
     * 
     * <p>关联的用户主键ID，外键关联到 sys_user 表。</p>
     */
    @TableField("user_id")
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "1", required = true)
    private Long userId;

    /**
     * 角色ID
     * 
     * <p>关联的角色主键ID，外键关联到 sys_role 表。</p>
     */
    @TableField("role_id")
    @NotNull(message = "角色ID不能为空")
    @Schema(description = "角色ID", example = "1", required = true)
    private Long roleId;

    /**
     * 创建人ID
     * 
     * <p>记录是谁为用户分配了该角色，便于权限分配的审计和追踪。</p>
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    @Schema(description = "创建人ID", example = "1")
    private Long createdBy;

    /**
     * 创建时间
     * 
     * <p>记录角色分配的时间，便于权限分配历史的追踪和审计。</p>
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间", example = "2025-09-03 10:30:00")
    private LocalDateTime createdTime;
}