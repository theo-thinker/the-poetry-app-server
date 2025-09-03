package com.sakura.poetry.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户收藏实体类
 * 
 * <p>用户收藏诗词的关联实体，记录用户与诗词的收藏关系。
 * 该实体对应数据库表 user_collection，建立用户和诗词的多对多关联。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>用户收藏关系管理（用户ID、诗词ID关联）</li>
 *   <li>收藏夹分类管理（可指定收藏到特定收藏夹）</li>
 *   <li>收藏备注管理（用户可为收藏添加个人备注）</li>
 *   <li>收藏时间记录（记录收藏的具体时间）</li>
 *   <li>收藏统计支持（支持各种收藏数据统计）</li>
 * </ul>
 * 
 * <p>业务规则：</p>
 * <ul>
 *   <li>同一用户不能重复收藏同一首诗词</li>
 *   <li>收藏可以不指定收藏夹（默认收藏）</li>
 *   <li>收藏可以添加个人备注和感想</li>
 *   <li>收藏记录支持时间排序和筛选</li>
 *   <li>删除诗词或用户时级联删除收藏记录</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Accessors(chain = true)
@TableName("user_collection")
@Schema(description = "用户收藏实体")
public class UserCollection implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 收藏ID
     * 
     * <p>收藏记录的唯一标识符，使用数据库自增策略。</p>
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "收藏ID", example = "1")
    private Long id;

    /**
     * 用户ID
     * 
     * <p>进行收藏操作的用户ID，外键关联到sys_user表。</p>
     */
    @TableField("user_id")
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "1", required = true)
    private Long userId;

    /**
     * 诗词ID
     * 
     * <p>被收藏的诗词ID，外键关联到poetry表。</p>
     */
    @TableField("poetry_id")
    @NotNull(message = "诗词ID不能为空")
    @Schema(description = "诗词ID", example = "1", required = true)
    private Long poetryId;

    /**
     * 收藏夹ID
     * 
     * <p>诗词被收藏到的收藏夹ID，外键关联到collection_folder表。
     * 如果为空，则表示收藏到默认收藏夹。</p>
     */
    @TableField("collection_folder_id")
    @Schema(description = "收藏夹ID", example = "1")
    private Long collectionFolderId;

    /**
     * 收藏备注
     * 
     * <p>用户对该收藏诗词的个人备注或感想。
     * 可以记录收藏原因、个人理解、心得体会等。</p>
     */
    @TableField("notes")
    @Size(max = 1000, message = "收藏备注长度不能超过1000个字符")
    @Schema(description = "收藏备注", example = "这首诗很好地表达了思乡之情，让我想起了家乡")
    private String notes;

    /**
     * 收藏时间
     * 
     * <p>用户收藏该诗词的具体时间。
     * 在插入收藏记录时自动填充当前时间。</p>
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "收藏时间", example = "2025-09-03 10:30:00")
    private LocalDateTime createdTime;
}