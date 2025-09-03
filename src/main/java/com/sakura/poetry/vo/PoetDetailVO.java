package com.sakura.poetry.vo;

import com.sakura.poetry.entity.enums.GenderEnum;
import com.sakura.poetry.entity.enums.CommonStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 诗人详情VO
 * 
 * <p>用于向前端返回诗人详情信息的视图对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "诗人详情VO")
public class PoetDetailVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 诗人ID
     */
    @Schema(description = "诗人ID")
    private Long id;

    /**
     * 诗人姓名
     */
    @Schema(description = "诗人姓名")
    private String poetName;

    /**
     * 别名
     */
    @Schema(description = "别名")
    private String alias;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private GenderEnum gender;

    /**
     * 生日
     */
    @Schema(description = "生日")
    private LocalDate birthday;

    /**
     * 逝世日期
     */
    @Schema(description = "逝世日期")
    private LocalDate deathDay;

    /**
     * 朝代名称
     */
    @Schema(description = "朝代名称")
    private String dynastyName;

    /**
     * 出生地
     */
    @Schema(description = "出生地")
    private String birthplace;

    /**
     * 诗人描述
     */
    @Schema(description = "诗人描述")
    private String description;

    /**
     * 头像
     */
    @Schema(description = "头像URL")
    private String avatar;

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
     * 诗词数量
     */
    @Schema(description = "诗词数量")
    private Integer poetryCount;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private CommonStatusEnum status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;
}