package com.sakura.poetry.entity.enums;

import com.sakura.poetry.entity.base.BaseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 点赞目标类型枚举
 * 
 * <p>用于表示用户可以点赞的不同类型对象，包括诗词、评论、诗人等。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Getter
@AllArgsConstructor
public enum LikeTargetTypeEnum implements BaseStatusEnum<Integer> {

    /**
     * 诗词
     */
    POETRY(1, "诗词"),

    /**
     * 评论
     */
    COMMENT(2, "评论"),

    /**
     * 诗人
     */
    POET(3, "诗人");

    /**
     * 类型值
     */
    private final Integer value;

    /**
     * 类型描述
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