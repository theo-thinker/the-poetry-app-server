package com.sakura.poetry.entity.enums;

import com.sakura.poetry.entity.base.BaseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 评论状态枚举
 * 
 * <p>用于表示用户评论的审核状态，支持内容审核流程。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Getter
@AllArgsConstructor
public enum CommentStatusEnum implements BaseStatusEnum<Integer> {

    /**
     * 待审核状态
     */
    PENDING(0, "待审核"),

    /**
     * 审核通过状态
     */
    APPROVED(1, "已通过"),

    /**
     * 审核拒绝状态
     */
    REJECTED(2, "已拒绝");

    /**
     * 状态值
     */
    private final Integer value;

    /**
     * 状态描述
     */
    private final String description;
    
    /**
     * 获取状态描述
     * 
     * @return 状态描述
     */
    @Override
    public String getDescription() {
        return description;
    }
    
    /**
     * 获取枚举值
     * 
     * @return 枚举值
     */
    @Override
    public Integer getValue() {
        return value;
    }
}