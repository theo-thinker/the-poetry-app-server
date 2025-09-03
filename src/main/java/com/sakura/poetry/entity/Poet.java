package com.sakura.poetry.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sakura.poetry.entity.base.BaseEntity;
import com.sakura.poetry.entity.enums.CommonStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 诗人实体类
 * 
 * <p>诗人信息实体，用于存储历史诗人的详细资料。
 * 该实体对应数据库表 poet，记录诗人的生平、成就、作品等信息。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>诗人基础信息管理（姓名、别名、朝代、生卒年等）</li>
 *   <li>诗人生平资料（出生地、传记、成就等）</li>
 *   <li>诗人作品关联（与诗词实体关联）</li>
 *   <li>诗人统计数据（查看次数、点赞次数等）</li>
 *   <li>诗人检索和分类展示</li>
 * </ul>
 * 
 * <p>数据设计说明：</p>
 * <ul>
 *   <li>支持诗人的完整生平信息记录</li>
 *   <li>关联朝代信息，便于历史分类</li>
 *   <li>包含统计字段，支持热门诗人排序</li>
 *   <li>支持富文本内容，便于详细介绍</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("poet")
@Schema(description = "诗人实体")
public class Poet extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 诗人姓名
     * 
     * <p>诗人的真实姓名或最广为人知的名字。
     * 如"李白"、"杜甫"、"苏轼"等。</p>
     */
    @TableField("poet_name")
    @NotBlank(message = "诗人姓名不能为空")
    @Size(min = 2, max = 100, message = "诗人姓名长度必须在2-100个字符之间")
    @Schema(description = "诗人姓名", example = "李白", required = true)
    private String poetName;

    /**
     * 诗人别名
     * 
     * <p>诗人的字、号、别称等。
     * 如"字太白，号青莲居士"、"字子美，号少陵野老"等。</p>
     */
    @TableField("poet_alias")
    @Size(max = 200, message = "诗人别名长度不能超过200个字符")
    @Schema(description = "诗人别名", example = "字太白，号青莲居士")
    private String poetAlias;

    /**
     * 朝代ID
     * 
     * <p>诗人所属朝代的ID，外键关联到dynasty表。
     * 用于按朝代分类和检索诗人。</p>
     */
    @TableField("dynasty_id")
    @Schema(description = "朝代ID", example = "7")
    private Long dynastyId;

    /**
     * 出生年份
     * 
     * <p>诗人的出生年份，可能不够精确。
     * 用于时间范围查询和年代排序。</p>
     */
    @TableField("birth_year")
    @Schema(description = "出生年份", example = "701")
    private Integer birthYear;

    /**
     * 逝世年份
     * 
     * <p>诗人的逝世年份，可能不够精确。
     * 用于时间范围查询和年代排序。</p>
     */
    @TableField("death_year")
    @Schema(description = "逝世年份", example = "762")
    private Integer deathYear;

    /**
     * 出生地
     * 
     * <p>诗人的出生地或籍贯。
     * 如"陇西成纪（今甘肃天水）"等。</p>
     */
    @TableField("birthplace")
    @Size(max = 200, message = "出生地长度不能超过200个字符")
    @Schema(description = "出生地", example = "陇西成纪（今甘肃天水）")
    private String birthplace;

    /**
     * 生平简介
     * 
     * <p>诗人的详细生平传记，包括生活经历、重要事件等。
     * 支持富文本格式，可以包含较长的文字内容。</p>
     */
    @TableField("biography")
    @Schema(description = "生平简介", example = "唐代伟大的浪漫主义诗人，被誉为\"诗仙\"...")
    private String biography;

    /**
     * 主要成就
     * 
     * <p>诗人的主要文学成就和历史贡献。
     * 如创作风格、文学地位、对后世的影响等。</p>
     */
    @TableField("achievements")
    @Schema(description = "主要成就", example = "创作了大量优秀诗歌，对后世影响深远...")
    private String achievements;

    /**
     * 代表作品
     * 
     * <p>诗人的代表性作品列表。
     * 可以是逗号分隔的作品名称，或者简要的作品介绍。</p>
     */
    @TableField("representative_works")
    @Schema(description = "代表作品", example = "《静夜思》《将进酒》《蜀道难》《早发白帝城》")
    private String representativeWorks;

    /**
     * 诗人头像
     * 
     * <p>诗人的头像图片URL，可以是画像、雕像或艺术形象。
     * 用于增强诗人信息的视觉展示效果。</p>
     */
    @TableField("avatar")
    @Size(max = 500, message = "头像URL长度不能超过500个字符")
    @Schema(description = "诗人头像", example = "/images/poet/libai.jpg")
    private String avatar;

    /**
     * 查看次数
     * 
     * <p>诗人信息页面的总访问次数统计。
     * 用于热门诗人排行和推荐算法。</p>
     */
    @TableField("view_count")
    @Schema(description = "查看次数", example = "12580")
    private Long viewCount;

    /**
     * 点赞次数
     * 
     * <p>用户对该诗人的点赞总数统计。
     * 用于热门诗人排行和用户喜好分析。</p>
     */
    @TableField("like_count")
    @Schema(description = "点赞次数", example = "3256")
    private Long likeCount;

    /**
     * 诗人状态
     * 
     * <p>诗人信息的启用/禁用状态。
     * 禁用的诗人不会在前端显示。
     * 0-禁用，1-启用。</p>
     */
    @TableField("status")
    @Schema(description = "诗人状态", example = "1", allowableValues = {"0", "1"})
    private CommonStatusEnum status;
}