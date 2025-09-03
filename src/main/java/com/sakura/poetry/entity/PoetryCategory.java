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
 * 诗词分类实体类
 * 
 * <p>诗词分类信息实体，用于诗词的分类管理和检索。
 * 该实体对应数据库表 poetry_category，支持多级分类结构。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>诗词分类管理（按体裁、主题、风格等分类）</li>
 *   <li>多级分类结构（支持父子级分类关系）</li>
 *   <li>分类检索和筛选</li>
 *   <li>分类统计和展示</li>
 *   <li>分类排序和层级管理</li>
 * </ul>
 * 
 * <p>分类设计说明：</p>
 * <ul>
 *   <li>支持无限级分类，通过parentId实现层级关系</li>
 *   <li>分类编码用于系统识别，分类名称用于展示</li>
 *   <li>支持封面图片，增强视觉效果</li>
 *   <li>包含排序字段，便于控制显示顺序</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("poetry_category")
@Schema(description = "诗词分类实体")
public class PoetryCategory extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     * 
     * <p>分类的显示名称，面向用户展示。
     * 如"五言绝句"、"七言律诗"、"山水田园"、"边塞军旅"等。</p>
     */
    @TableField("category_name")
    @NotBlank(message = "分类名称不能为空")
    @Size(min = 2, max = 100, message = "分类名称长度必须在2-100个字符之间")
    @Schema(description = "分类名称", example = "五言绝句", required = true)
    private String categoryName;

    /**
     * 分类编码
     * 
     * <p>分类的唯一标识符，用于系统内部识别和程序逻辑处理。
     * 建议使用英文大写字母和下划线组合，如"WYJJ"（五言绝句）等。</p>
     */
    @TableField("category_code")
    @NotBlank(message = "分类编码不能为空")
    @Size(min = 2, max = 50, message = "分类编码长度必须在2-50个字符之间")
    @Pattern(regexp = "^[A-Z][A-Z0-9_]*$", message = "分类编码只能包含大写字母、数字、下划线，且必须以字母开头")
    @Schema(description = "分类编码", example = "WYJJ", required = true)
    private String categoryCode;

    /**
     * 父分类ID
     * 
     * <p>上级分类的ID，用于构建分类的层级关系。
     * 顶级分类的父ID为0。</p>
     */
    @TableField("parent_id")
    @Schema(description = "父分类ID", example = "1")
    private Long parentId;

    /**
     * 分类层级
     * 
     * <p>分类在分类树中的层级深度，顶级分类为1，依次递增。
     * 用于控制分类的显示层次和查询优化。</p>
     */
    @TableField("level")
    @Schema(description = "分类层级", example = "2")
    private Integer level;

    /**
     * 排序序号
     * 
     * <p>同级分类的排序序号，数值越小排序越靠前。
     * 用于控制分类在列表中的显示顺序。</p>
     */
    @TableField("sort_order")
    @Schema(description = "排序序号", example = "11")
    private Integer sortOrder;

    /**
     * 分类描述
     * 
     * <p>分类的详细描述信息，说明该分类的特点、范围、代表作品等。
     * 用于帮助用户理解分类的含义和内容。</p>
     */
    @TableField("description")
    @Size(max = 500, message = "分类描述长度不能超过500个字符")
    @Schema(description = "分类描述", example = "五言四句诗，平仄押韵，言简意赅")
    private String description;

    /**
     * 分类封面图
     * 
     * <p>分类的封面图片URL，用于增强分类的视觉展示效果。
     * 可以展示该分类的代表性图片或艺术设计。</p>
     */
    @TableField("cover_image")
    @Size(max = 500, message = "封面图URL长度不能超过500个字符")
    @Schema(description = "分类封面图", example = "/images/category/wyjj.jpg")
    private String coverImage;

    /**
     * 分类状态
     * 
     * <p>分类的启用/禁用状态，禁用的分类不会在前端显示。
     * 0-禁用，1-启用。</p>
     */
    @TableField("status")
    @Schema(description = "分类状态", example = "1", allowableValues = {"0", "1"})
    private CommonStatusEnum status;
}