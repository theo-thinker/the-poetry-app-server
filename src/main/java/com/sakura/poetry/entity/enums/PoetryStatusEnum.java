package com.sakura.poetry.entity.enums;

import com.sakura.poetry.entity.base.BaseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 诗词状态枚举
 * 
 * <p>用于表示诗词的发布状态，包括草稿、已发布、下线三种状态。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Getter
@AllArgsConstructor
public enum PoetryStatusEnum implements BaseStatusEnum<Integer> {

    /**
     * 草稿状态
     */
    DRAFT(0, "草稿"),

    /**
     * 已发布状态
     */
    PUBLISHED(1, "已发布"),

    /**
     * 下线状态
     */
    OFFLINE(2, "下线");

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