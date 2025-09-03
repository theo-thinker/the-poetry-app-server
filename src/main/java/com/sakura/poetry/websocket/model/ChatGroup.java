package com.sakura.poetry.websocket.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 聊天群组实体类
 * 
 * <p>用于封装聊天群组的信息。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "聊天群组实体")
public class ChatGroup implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 群组ID
     */
    @Schema(description = "群组ID")
    private Long groupId;

    /**
     * 群组名称
     */
    @Schema(description = "群组名称")
    private String groupName;

    /**
     * 群组描述
     */
    @Schema(description = "群组描述")
    private String description;

    /**
     * 群组头像URL
     */
    @Schema(description = "群组头像URL")
    private String avatar;

    /**
     * 群主ID
     */
    @Schema(description = "群主ID")
    private Long ownerId;

    /**
     * 群组成员ID列表
     */
    @Schema(description = "群组成员ID列表")
    private List<Long> memberIds;

    /**
     * 群组成员数量
     */
    @Schema(description = "群组成员数量")
    private Integer memberCount;

    /**
     * 是否公开群组
     */
    @Schema(description = "是否公开群组")
    private Boolean isPublic;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;

    /**
     * 构造函数
     */
    public ChatGroup() {
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
        this.memberCount = 0;
        this.isPublic = false;
    }
}