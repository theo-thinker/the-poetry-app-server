package com.sakura.poetry.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 * 
 * <p>包含所有实体类的通用字段，包括主键、创建时间、更新时间、逻辑删除等。
 * 所有业务实体类都应该继承此类，以保证数据的一致性和完整性。</p>
 * 
 * <p>字段说明：</p>
 * <ul>
 *   <li>id - 主键ID，使用数据库自增</li>
 *   <li>createdBy - 创建人ID</li>
 *   <li>createdTime - 创建时间，插入时自动填充</li>
 *   <li>updatedBy - 更新人ID</li>
 *   <li>updatedTime - 更新时间，更新时自动填充</li>
 *   <li>isDeleted - 逻辑删除标志，0-未删除，1-已删除</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "基础实体类")
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     * 
     * <p>使用数据库自增策略生成主键，确保每条记录的唯一性。</p>
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键ID", example = "1")
    private Long id;

    /**
     * 创建人ID
     * 
     * <p>记录是谁创建了这条数据，便于数据追踪和审计。</p>
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    @Schema(description = "创建人ID", example = "1")
    private Long createdBy;

    /**
     * 创建时间
     * 
     * <p>记录数据的创建时间，在插入时自动填充当前时间。</p>
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间", example = "2025-09-03 10:30:00")
    private LocalDateTime createdTime;

    /**
     * 更新人ID
     * 
     * <p>记录是谁最后更新了这条数据，便于数据追踪和审计。</p>
     */
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新人ID", example = "1")
    private Long updatedBy;

    /**
     * 更新时间
     * 
     * <p>记录数据的最后更新时间，在插入和更新时自动填充当前时间。</p>
     */
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间", example = "2025-09-03 10:30:00")
    private LocalDateTime updatedTime;

    /**
     * 逻辑删除标志
     * 
     * <p>用于实现逻辑删除，避免物理删除数据导致的数据丢失。
     * 0表示正常状态，1表示已删除状态。</p>
     */
    @TableLogic
    @TableField(value = "is_deleted")
    @Schema(description = "是否删除", example = "0", allowableValues = {"0", "1"})
    private Integer isDeleted;
}