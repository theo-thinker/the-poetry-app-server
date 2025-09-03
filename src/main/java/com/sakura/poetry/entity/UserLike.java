package com.sakura.poetry.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sakura.poetry.entity.enums.LikeTargetTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户点赞实体类
 * 
 * <p>用户点赞行为的记录实体，支持对多种类型对象的点赞操作。
 * 该实体对应数据库表 user_like，记录用户的点赞历史和偏好。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>点赞关系管理（用户、目标对象、点赞类型）</li>
 *   <li>多类型点赞支持（诗词、评论、诗人等）</li>
 *   <li>点赞时间记录（用于统计和分析）</li>
 *   <li>点赞统计支持（热门度计算、用户偏好分析）</li>
 *   <li>防重复点赞（同一用户对同一对象只能点赞一次）</li>
 * </ul>
 * 
 * <p>业务规则：</p>
 * <ul>
 *   <li>同一用户对同一目标对象只能点赞一次</li>
 *   <li>支持取消点赞（删除点赞记录）</li>
 *   <li>点赞操作实时更新目标对象的点赞计数</li>
 *   <li>点赞记录用于个性化推荐算法</li>
 *   <li>删除目标对象时级联删除相关点赞记录</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Accessors(chain = true)
@TableName("user_like")
@Schema(description = "用户点赞实体")
public class UserLike implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 点赞ID
     * 
     * <p>点赞记录的唯一标识符，使用数据库自增策略。</p>
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "点赞ID", example = "1")
    private Long id;

    /**
     * 用户ID
     * 
     * <p>进行点赞操作的用户ID，外键关联到sys_user表。</p>
     */
    @TableField("user_id")
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "1", required = true)
    private Long userId;

    /**
     * 点赞目标类型
     * 
     * <p>被点赞对象的类型，支持多种类型：
     * 1-诗词，2-评论，3-诗人。
     * 通过枚举类型确保类型值的有效性。</p>
     */
    @TableField("target_type")
    @NotNull(message = "点赞目标类型不能为空")
    @Schema(description = "点赞目标类型", example = "1", allowableValues = {"1", "2", "3"}, required = true)
    private LikeTargetTypeEnum targetType;

    /**
     * 目标ID
     * 
     * <p>被点赞对象的ID，根据targetType确定具体的目标表：
     * - targetType=1时，关联poetry表
     * - targetType=2时，关联user_comment表  
     * - targetType=3时，关联poet表</p>
     */
    @TableField("target_id")
    @NotNull(message = "目标ID不能为空")
    @Schema(description = "目标ID", example = "1", required = true)
    private Long targetId;

    /**
     * 点赞时间
     * 
     * <p>用户进行点赞操作的具体时间。
     * 在插入点赞记录时自动填充当前时间。</p>
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "点赞时间", example = "2025-09-03 10:30:00")
    private LocalDateTime createdTime;
}