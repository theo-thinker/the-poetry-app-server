package com.sakura.poetry.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sakura.poetry.entity.base.BaseEntity;
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
 * 收藏夹实体类
 * 
 * <p>用户收藏夹信息实体，用于组织和管理用户收藏的诗词作品。
 * 该实体对应数据库表 collection_folder，支持用户创建多个收藏夹。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>收藏夹信息管理（名称、描述、封面等）</li>
 *   <li>收藏夹权限管理（公开/私有设置）</li>
 *   <li>收藏夹统计管理（诗词数量统计）</li>
 *   <li>收藏夹排序管理（用户自定义排序）</li>
 *   <li>收藏夹分享功能（公开收藏夹可被其他用户浏览）</li>
 * </ul>
 * 
 * <p>业务规则：</p>
 * <ul>
 *   <li>每个用户可以创建多个收藏夹</li>
 *   <li>收藏夹可以设置为公开或私有</li>
 *   <li>收藏夹支持封面图片和详细描述</li>
 *   <li>系统自动统计收藏夹中的诗词数量</li>
 *   <li>支持软删除，删除收藏夹时保留收藏记录</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("collection_folder")
@Schema(description = "收藏夹实体")
public class CollectionFolder extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     * 
     * <p>收藏夹所属用户的ID，外键关联到sys_user表。
     * 每个收藏夹都属于一个特定的用户。</p>
     */
    @TableField("user_id")
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "1", required = true)
    private Long userId;

    /**
     * 收藏夹名称
     * 
     * <p>收藏夹的显示名称，由用户自定义。
     * 如"我的最爱"、"唐诗精选"、"思乡诗词"等。</p>
     */
    @TableField("folder_name")
    @NotBlank(message = "收藏夹名称不能为空")
    @Size(min = 1, max = 100, message = "收藏夹名称长度必须在1-100个字符之间")
    @Schema(description = "收藏夹名称", example = "我的最爱", required = true)
    private String folderName;

    /**
     * 收藏夹描述
     * 
     * <p>收藏夹的详细描述信息，说明收藏夹的主题或收藏标准。
     * 用户可以在此处记录收藏夹的创建目的和内容特色。</p>
     */
    @TableField("description")
    @Size(max = 500, message = "收藏夹描述长度不能超过500个字符")
    @Schema(description = "收藏夹描述", example = "收藏我最喜欢的古典诗词作品")
    private String description;

    /**
     * 封面图片
     * 
     * <p>收藏夹的封面图片URL，用于美化收藏夹的展示效果。
     * 可以是用户上传的图片或系统提供的默认封面。</p>
     */
    @TableField("cover_image")
    @Size(max = 500, message = "封面图片URL长度不能超过500个字符")
    @Schema(description = "封面图片URL", example = "/images/folder/cover1.jpg")
    private String coverImage;

    /**
     * 是否公开
     * 
     * <p>收藏夹的公开状态设置。
     * 0-私有（仅自己可见），1-公开（其他用户也可浏览）。</p>
     */
    @TableField("is_public")
    @Schema(description = "是否公开", example = "0", allowableValues = {"0", "1"})
    private Integer isPublic;

    /**
     * 诗词数量
     * 
     * <p>收藏夹中包含的诗词数量统计。
     * 系统自动维护，当用户添加或删除收藏时更新。</p>
     */
    @TableField("poetry_count")
    @Schema(description = "诗词数量", example = "15")
    private Integer poetryCount;

    /**
     * 排序序号
     * 
     * <p>收藏夹在用户收藏夹列表中的排序位置。
     * 用户可以调整收藏夹的显示顺序，数值越小排序越靠前。</p>
     */
    @TableField("sort_order")
    @Schema(description = "排序序号", example = "1")
    private Integer sortOrder;

    /**
     * 更新时间
     * 
     * <p>收藏夹信息的最后更新时间。
     * 当收藏夹基本信息或内容发生变化时自动更新。</p>
     * 
     * <p>注意：这里重写父类的updatedTime字段，因为收藏夹表结构中
     * 没有created_by和updated_by字段，只有created_time和updated_time。</p>
     */
    @TableField("updated_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间", example = "2025-09-03 10:30:00")
    private LocalDateTime updatedTime;
}