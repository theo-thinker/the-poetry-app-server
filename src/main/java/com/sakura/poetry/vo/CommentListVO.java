package com.sakura.poetry.vo;

import com.sakura.poetry.entity.enums.CommentStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论列表VO
 * 
 * <p>用于向前端返回评论列表信息的视图对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "评论列表VO")
public class CommentListVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    @Schema(description = "评论ID")
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
     * 用户头像
     */
    @Schema(description = "用户头像URL")
    private String userAvatar;

    /**
     * 诗词ID
     */
    @Schema(description = "诗词ID")
    private Long poetryId;

    /**
     * 父评论ID
     */
    @Schema(description = "父评论ID")
    private Long parentId;

    /**
     * 回复用户ID
     */
    @Schema(description = "回复用户ID")
    private Long replyUserId;

    /**
     * 回复用户昵称
     */
    @Schema(description = "回复用户昵称")
    private String replyUserNickname;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    private String content;

    /**
     * 点赞次数
     */
    @Schema(description = "点赞次数")
    private Integer likeCount;

    /**
     * 回复次数
     */
    @Schema(description = "回复次数")
    private Integer replyCount;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private CommentStatusEnum status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 回复列表
     */
    @Schema(description = "回复列表")
    private List<CommentListVO> replies;
}