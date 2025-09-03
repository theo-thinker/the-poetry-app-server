package com.sakura.poetry.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sakura.poetry.entity.base.BaseEntity;
import com.sakura.poetry.entity.enums.CommentStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 用户评论实体类
 * 
 * <p>用户对诗词的评论信息实体，支持多级评论和回复功能。
 * 该实体对应数据库表 user_comment，记录用户的评论内容和状态。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>评论内容管理（评论文本、HTML格式支持）</li>
 *   <li>多级评论支持（顶级评论、回复评论的层级结构）</li>
 *   <li>评论审核管理（待审核、已通过、已拒绝状态）</li>
 *   <li>评论统计管理（点赞数、回复数统计）</li>
 *   <li>评论安全管理（IP地址、用户代理记录）</li>
 * </ul>
 * 
 * <p>业务规则：</p>
 * <ul>
 *   <li>每条评论必须关联一首诗词</li>
 *   <li>支持对评论的回复（多级评论结构）</li>
 *   <li>评论内容需要经过审核才能显示</li>
 *   <li>系统记录评论者的IP和浏览器信息</li>
 *   <li>支持评论的点赞和举报功能</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_comment")
@Schema(description = "用户评论实体")
public class UserComment extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     * 
     * <p>发表评论的用户ID，外键关联到sys_user表。</p>
     */
    @TableField("user_id")
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "1", required = true)
    private Long userId;

    /**
     * 诗词ID
     * 
     * <p>被评论的诗词ID，外键关联到poetry表。
     * 每条评论都必须关联到一首具体的诗词。</p>
     */
    @TableField("poetry_id")
    @NotNull(message = "诗词ID不能为空")
    @Schema(description = "诗词ID", example = "1", required = true)
    private Long poetryId;

    /**
     * 父评论ID
     * 
     * <p>父评论的ID，用于构建评论的层级关系。
     * 如果为0或null，表示这是一条顶级评论；
     * 如果有值，表示这是对某条评论的回复。</p>
     */
    @TableField("parent_id")
    @Schema(description = "父评论ID", example = "0")
    private Long parentId;

    /**
     * 评论内容
     * 
     * <p>用户发表的评论文本内容。
     * 支持纯文本和简单的HTML格式（经过安全过滤）。</p>
     */
    @TableField("content")
    @NotBlank(message = "评论内容不能为空")
    @Size(min = 1, max = 1000, message = "评论内容长度必须在1-1000个字符之间")
    @Schema(description = "评论内容", example = "这首诗写得太好了，深深地打动了我", required = true)
    private String content;

    /**
     * 点赞数
     * 
     * <p>该评论获得的点赞总数。
     * 当有用户对评论点赞时，系统自动更新此字段。</p>
     */
    @TableField("like_count")
    @Schema(description = "点赞数", example = "5")
    private Integer likeCount;

    /**
     * 回复数
     * 
     * <p>对该评论的回复总数。
     * 当有用户回复该评论时，系统自动更新此字段。</p>
     */
    @TableField("reply_count")
    @Schema(description = "回复数", example = "2")
    private Integer replyCount;

    /**
     * 评论状态
     * 
     * <p>评论的审核状态：
     * 0-待审核，1-已通过，2-已拒绝。
     * 只有已通过的评论才会在前端显示。</p>
     */
    @TableField("status")
    @Schema(description = "评论状态", example = "1", allowableValues = {"0", "1", "2"})
    private CommentStatusEnum status;

    /**
     * IP地址
     * 
     * <p>用户发表评论时的IP地址。
     * 用于安全监控、地域分析和异常行为检测。</p>
     */
    @TableField("ip_address")
    @Size(max = 50, message = "IP地址长度不能超过50个字符")
    @Schema(description = "IP地址", example = "192.168.1.100")
    private String ipAddress;

    /**
     * 用户代理
     * 
     * <p>用户发表评论时的浏览器用户代理字符串。
     * 用于统计用户使用的浏览器和设备类型。</p>
     */
    @TableField("user_agent")
    @Size(max = 500, message = "用户代理长度不能超过500个字符")
    @Schema(description = "用户代理", example = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
    private String userAgent;

    /**
     * 更新时间
     * 
     * <p>评论信息的最后更新时间。
     * 当评论内容或状态发生变化时自动更新。</p>
     * 
     * <p>注意：这里重写父类的updatedTime字段，保持与数据库表结构一致。</p>
     */
    @TableField("updated_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间", example = "2025-09-03 10:30:00")
    private LocalDateTime updatedTime;
}