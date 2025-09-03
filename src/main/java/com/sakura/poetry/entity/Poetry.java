package com.sakura.poetry.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sakura.poetry.entity.base.BaseEntity;
import com.sakura.poetry.entity.enums.ContentFormatEnum;
import com.sakura.poetry.entity.enums.DifficultyLevelEnum;
import com.sakura.poetry.entity.enums.PoetryStatusEnum;
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
 * 诗词实体类
 * 
 * <p>诗词作品的核心实体，存储诗词的完整信息和相关数据。
 * 该实体对应数据库表 poetry，是系统的核心业务实体。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>诗词内容管理（标题、内容、译文、注释等）</li>
 *   <li>诗词分类管理（作者、朝代、分类关联）</li>
 *   <li>诗词属性管理（难度、格式、韵律等）</li>
 *   <li>诗词统计管理（浏览量、点赞量、收藏量等）</li>
 *   <li>诗词状态管理（发布状态、精选、热门等）</li>
 * </ul>
 * 
 * <p>业务逻辑说明：</p>
 * <ul>
 *   <li>每首诗词必须关联一个作者（诗人）</li>
 *   <li>可以关联朝代和分类，用于分类检索</li>
 *   <li>支持多种内容格式（原文、现代文等）</li>
 *   <li>包含丰富的统计数据，支持个性化推荐</li>
 *   <li>支持发布流程管理，从草稿到发布</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("poetry")
@Schema(description = "诗词实体")
public class Poetry extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 诗词标题
     * 
     * <p>诗词的主标题，是诗词的主要标识。
     * 如"静夜思"、"春夜喜雨"、"水调歌头·明月几时有"等。</p>
     */
    @TableField("title")
    @NotBlank(message = "诗词标题不能为空")
    @Size(min = 1, max = 200, message = "诗词标题长度必须在1-200个字符之间")
    @Schema(description = "诗词标题", example = "静夜思", required = true)
    private String title;

    /**
     * 诗词副标题
     * 
     * <p>诗词的副标题或补充说明。
     * 如词牌名的补充、创作背景的简要说明等。</p>
     */
    @TableField("subtitle")
    @Size(max = 200, message = "副标题长度不能超过200个字符")
    @Schema(description = "副标题", example = "明月几时有")
    private String subtitle;

    /**
     * 作者ID
     * 
     * <p>诗词作者（诗人）的ID，外键关联到poet表。
     * 每首诗词必须有一个作者。</p>
     */
    @TableField("poet_id")
    @NotNull(message = "作者ID不能为空")
    @Schema(description = "作者ID", example = "1", required = true)
    private Long poetId;

    /**
     * 朝代ID
     * 
     * <p>诗词所属朝代的ID，外键关联到dynasty表。
     * 用于按朝代分类和检索诗词。</p>
     */
    @TableField("dynasty_id")
    @Schema(description = "朝代ID", example = "7")
    private Long dynastyId;

    /**
     * 分类ID
     * 
     * <p>诗词所属分类的ID，外键关联到poetry_category表。
     * 用于按分类筛选和组织诗词。</p>
     */
    @TableField("category_id")
    @Schema(description = "分类ID", example = "5")
    private Long categoryId;

    /**
     * 诗词内容
     * 
     * <p>诗词的主要内容，即诗词正文。
     * 支持古文原文和现代文格式，可以包含换行符。</p>
     */
    @TableField("content")
    @NotBlank(message = "诗词内容不能为空")
    @Schema(description = "诗词内容", example = "床前明月光，疑是地上霜。\\n举头望明月，低头思故乡。", required = true)
    private String content;

    /**
     * 内容格式
     * 
     * <p>标识诗词内容的格式类型。
     * 1-原文格式，2-现代文格式。</p>
     */
    @TableField("content_format")
    @Schema(description = "内容格式", example = "1", allowableValues = {"1", "2"})
    private ContentFormatEnum contentFormat;

    /**
     * 译文
     * 
     * <p>诗词的现代汉语翻译，帮助用户理解诗词含义。
     * 通常用白话文表达诗词的意思。</p>
     */
    @TableField("translation")
    @Schema(description = "译文", example = "明亮的月光洒在床前，怀疑是地上的白霜...")
    private String translation;

    /**
     * 注释
     * 
     * <p>诗词中重要词汇、典故、背景的详细解释。
     * 帮助用户理解诗词的深层含义和文化背景。</p>
     */
    @TableField("annotation")
    @Schema(description = "注释", example = "床：坐具，这里指坐卧的地方。疑：怀疑...")
    private String annotation;

    /**
     * 赏析
     * 
     * <p>对诗词的文学分析和艺术鉴赏。
     * 包括诗词的艺术特色、表现手法、思想情感等分析。</p>
     */
    @TableField("appreciation")
    @Schema(description = "赏析", example = "这首诗写的是诗人在寂静的月夜思念家乡的感受...")
    private String appreciation;

    /**
     * 创作背景
     * 
     * <p>诗词的创作背景和历史环境。
     * 包括创作时间、地点、历史事件、个人经历等。</p>
     */
    @TableField("background")
    @Schema(description = "创作背景", example = "这首诗作于诗人离乡之后的某个月夜...")
    private String background;

    /**
     * 标签
     * 
     * <p>诗词的关键词标签，用逗号分隔。
     * 用于搜索、分类和推荐算法。</p>
     */
    @TableField("tags")
    @Size(max = 500, message = "标签长度不能超过500个字符")
    @Schema(description = "标签", example = "思乡,月夜,游子")
    private String tags;

    /**
     * 难度等级
     * 
     * <p>诗词的阅读理解难度等级。
     * 1-简单，2-中等，3-困难。</p>
     */
    @TableField("difficulty_level")
    @Schema(description = "难度等级", example = "1", allowableValues = {"1", "2", "3"})
    private DifficultyLevelEnum difficultyLevel;

    /**
     * 字数
     * 
     * <p>诗词的总字数统计，不包括标点符号。
     * 用于诗词分类和检索筛选。</p>
     */
    @TableField("word_count")
    @Schema(description = "字数", example = "20")
    private Integer wordCount;

    /**
     * 句数
     * 
     * <p>诗词的总句数统计。
     * 用于诗词格律分析和分类。</p>
     */
    @TableField("verse_count")
    @Schema(description = "句数", example = "4")
    private Integer verseCount;

    /**
     * 韵律
     * 
     * <p>诗词的韵律信息，如平仄格式等。
     * 用于诗词格律分析和学习。</p>
     */
    @TableField("rhythm")
    @Size(max = 100, message = "韵律长度不能超过100个字符")
    @Schema(description = "韵律", example = "平平仄仄平")
    private String rhythm;

    /**
     * 押韵方式
     * 
     * <p>诗词的押韵规律和方式。
     * 如"一三句不押韵，二四句押韵"等。</p>
     */
    @TableField("rhyme_scheme")
    @Size(max = 100, message = "押韵方式长度不能超过100个字符")
    @Schema(description = "押韵方式", example = "二四句押韵")
    private String rhymeScheme;

    /**
     * 浏览次数
     * 
     * <p>诗词页面的总访问次数统计。
     * 用于热门诗词排行和推荐算法。</p>
     */
    @TableField("view_count")
    @Schema(description = "浏览次数", example = "1580")
    private Long viewCount;

    /**
     * 点赞次数
     * 
     * <p>用户对该诗词的点赞总数统计。
     * 用于评估诗词受欢迎程度。</p>
     */
    @TableField("like_count")
    @Schema(description = "点赞次数", example = "326")
    private Long likeCount;

    /**
     * 收藏次数
     * 
     * <p>用户收藏该诗词的总数统计。
     * 用于评估诗词的收藏价值。</p>
     */
    @TableField("collect_count")
    @Schema(description = "收藏次数", example = "188")
    private Long collectCount;

    /**
     * 分享次数
     * 
     * <p>用户分享该诗词的总数统计。
     * 用于评估诗词的传播价值。</p>
     */
    @TableField("share_count")
    @Schema(description = "分享次数", example = "95")
    private Long shareCount;

    /**
     * 评论次数
     * 
     * <p>用户对该诗词的评论总数统计。
     * 用于评估诗词的讨论热度。</p>
     */
    @TableField("comment_count")
    @Schema(description = "评论次数", example = "42")
    private Long commentCount;

    /**
     * 是否精选
     * 
     * <p>标记该诗词是否为编辑精选作品。
     * 0-否，1-是。精选作品会获得更多展示机会。</p>
     */
    @TableField("is_featured")
    @Schema(description = "是否精选", example = "0", allowableValues = {"0", "1"})
    private Integer isFeatured;

    /**
     * 是否热门
     * 
     * <p>标记该诗词是否为热门作品。
     * 0-否，1-是。通常根据浏览量、点赞量等数据自动计算。</p>
     */
    @TableField("is_hot")
    @Schema(description = "是否热门", example = "0", allowableValues = {"0", "1"})
    private Integer isHot;

    /**
     * 来源
     * 
     * <p>诗词的来源信息，如古籍名称、版本等。
     * 用于标注诗词的权威性和可靠性。</p>
     */
    @TableField("source")
    @Size(max = 200, message = "来源长度不能超过200个字符")
    @Schema(description = "来源", example = "《全唐诗》")
    private String source;

    /**
     * 版权信息
     * 
     * <p>诗词的版权声明和使用许可信息。
     * 用于规范诗词内容的使用和传播。</p>
     */
    @TableField("copyright_info")
    @Size(max = 500, message = "版权信息长度不能超过500个字符")
    @Schema(description = "版权信息", example = "本内容来源于公共领域古籍")
    private String copyrightInfo;

    /**
     * 诗词状态
     * 
     * <p>诗词的发布状态。
     * 0-草稿，1-已发布，2-下线。</p>
     */
    @TableField("status")
    @Schema(description = "诗词状态", example = "1", allowableValues = {"0", "1", "2"})
    private PoetryStatusEnum status;

    /**
     * 发布时间
     * 
     * <p>诗词正式发布的时间。
     * 只有已发布状态的诗词才有发布时间。</p>
     */
    @TableField("publish_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "发布时间", example = "2025-09-03 10:30:00")
    private LocalDateTime publishTime;
}