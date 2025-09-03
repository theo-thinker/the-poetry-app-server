package com.sakura.poetry.vo;

import com.sakura.poetry.entity.enums.DifficultyLevelEnum;
import com.sakura.poetry.entity.enums.PoetryStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 诗词详情VO
 * 
 * <p>用于向前端返回诗词详情信息的视图对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "诗词详情VO")
public class PoetryDetailVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 诗词ID
     */
    @Schema(description = "诗词ID")
    private Long id;

    /**
     * 诗词标题
     */
    @Schema(description = "诗词标题")
    private String title;

    /**
     * 诗词副标题
     */
    @Schema(description = "诗词副标题")
    private String subtitle;

    /**
     * 诗人姓名
     */
    @Schema(description = "诗人姓名")
    private String poetName;

    /**
     * 朝代名称
     */
    @Schema(description = "朝代名称")
    private String dynastyName;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String categoryName;

    /**
     * 诗词内容
     */
    @Schema(description = "诗词内容")
    private String content;

    /**
     * 诗词格式化内容
     */
    @Schema(description = "诗词格式化内容")
    private String contentFormat;

    /**
     * 译文
     */
    @Schema(description = "译文")
    private String translation;

    /**
     * 注释
     */
    @Schema(description = "注释")
    private String annotation;

    /**
     * 赏析
     */
    @Schema(description = "赏析")
    private String appreciation;

    /**
     * 创作背景
     */
    @Schema(description = "创作背景")
    private String background;

    /**
     * 标签
     */
    @Schema(description = "标签")
    private String tags;

    /**
     * 难度等级
     */
    @Schema(description = "难度等级")
    private DifficultyLevelEnum difficultyLevel;

    /**
     * 字数
     */
    @Schema(description = "字数")
    private Integer wordCount;

    /**
     * 句数
     */
    @Schema(description = "句数")
    private Integer verseCount;

    /**
     * 韵律
     */
    @Schema(description = "韵律")
    private String rhythm;

    /**
     * 押韵方式
     */
    @Schema(description = "押韵方式")
    private String rhymeScheme;

    /**
     * 浏览次数
     */
    @Schema(description = "浏览次数")
    private Integer viewCount;

    /**
     * 点赞次数
     */
    @Schema(description = "点赞次数")
    private Integer likeCount;

    /**
     * 收藏次数
     */
    @Schema(description = "收藏次数")
    private Integer collectCount;

    /**
     * 分享次数
     */
    @Schema(description = "分享次数")
    private Integer shareCount;

    /**
     * 评论次数
     */
    @Schema(description = "评论次数")
    private Integer commentCount;

    /**
     * 是否精选
     */
    @Schema(description = "是否精选")
    private Boolean isFeatured;

    /**
     * 是否热门
     */
    @Schema(description = "是否热门")
    private Boolean isHot;

    /**
     * 来源
     */
    @Schema(description = "来源")
    private String source;

    /**
     * 版权信息
     */
    @Schema(description = "版权信息")
    private String copyrightInfo;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private PoetryStatusEnum status;

    /**
     * 发布时间
     */
    @Schema(description = "发布时间")
    private LocalDateTime publishTime;
}