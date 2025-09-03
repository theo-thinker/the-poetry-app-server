package com.sakura.poetry.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sakura.poetry.entity.base.BaseEntity;
import com.sakura.poetry.entity.enums.CommonStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 朝代实体类
 * 
 * <p>中国历史朝代信息实体，用于诗词和诗人的朝代归属管理。
 * 该实体对应数据库表 dynasty，存储历史朝代的基本信息。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>朝代基础信息管理（名称、编码、时间范围等）</li>
 *   <li>诗人朝代归属（与诗人实体关联）</li>
 *   <li>诗词朝代分类（与诗词实体关联）</li>
 *   <li>历史时期检索和筛选</li>
 *   <li>朝代排序和展示</li>
 * </ul>
 * 
 * <p>数据设计说明：</p>
 * <ul>
 *   <li>朝代编码用于系统内部识别，应保持唯一性</li>
 *   <li>支持起止年份，便于时间范围查询</li>
 *   <li>包含详细描述，提供历史背景信息</li>
 *   <li>支持排序，按时间顺序展示</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("dynasty")
@Schema(description = "朝代实体")
public class Dynasty extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 朝代名称
     * 
     * <p>朝代的完整名称，如"唐朝"、"宋朝"、"明朝"等。
     * 用于前端展示和用户检索。</p>
     */
    @TableField("dynasty_name")
    @NotBlank(message = "朝代名称不能为空")
    @Size(min = 2, max = 50, message = "朝代名称长度必须在2-50个字符之间")
    @Schema(description = "朝代名称", example = "唐朝", required = true)
    private String dynastyName;

    /**
     * 朝代编码
     * 
     * <p>朝代的唯一标识符，用于系统内部识别和数据关联。
     * 建议使用英文大写字母，如"TANG"、"SONG"、"MING"等。</p>
     */
    @TableField("dynasty_code")
    @NotBlank(message = "朝代编码不能为空")
    @Size(min = 2, max = 20, message = "朝代编码长度必须在2-20个字符之间")
    @Pattern(regexp = "^[A-Z][A-Z0-9_]*$", message = "朝代编码只能包含大写字母、数字、下划线，且必须以字母开头")
    @Schema(description = "朝代编码", example = "TANG", required = true)
    private String dynastyCode;

    /**
     * 朝代开始年份
     * 
     * <p>朝代建立的年份，用于时间范围查询和历史排序。
     * 可以为负数（表示公元前）。</p>
     */
    @TableField("start_year")
    @Schema(description = "开始年份", example = "618")
    private Integer startYear;

    /**
     * 朝代结束年份
     * 
     * <p>朝代灭亡的年份，用于时间范围查询和历史排序。
     * 可以为负数（表示公元前）。</p>
     */
    @TableField("end_year")
    @Schema(description = "结束年份", example = "907")
    private Integer endYear;

    /**
     * 朝代描述
     * 
     * <p>朝代的详细描述信息，包括历史背景、重要特征、文化成就等。
     * 用于提供用户更多的历史文化信息。</p>
     */
    @TableField("description")
    @Schema(description = "朝代描述", example = "中国诗歌的黄金时代，涌现了李白、杜甫等伟大诗人")
    private String description;

    /**
     * 排序序号
     * 
     * <p>用于朝代列表的排序显示，一般按历史时间顺序排列。
     * 数值越小排序越靠前。</p>
     */
    @TableField("sort_order")
    @Schema(description = "排序序号", example = "7")
    private Integer sortOrder;

    /**
     * 朝代状态
     * 
     * <p>朝代信息的启用/禁用状态，禁用的朝代不会在前端显示。
     * 0-禁用，1-启用。</p>
     */
    @TableField("status")
    @Schema(description = "朝代状态", example = "1", allowableValues = {"0", "1"})
    private CommonStatusEnum status;
}