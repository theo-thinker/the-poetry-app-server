package com.sakura.poetry.entity.enums;

import com.sakura.poetry.entity.base.BaseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用状态枚举
 * 
 * <p>用于表示系统中各种实体的启用/禁用状态。
 * 适用于用户状态、角色状态、分类状态等场景。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Getter
@AllArgsConstructor
public enum CommonStatusEnum implements BaseStatusEnum<Integer> {

    /**
     * 禁用状态
     */
    DISABLED(0, "禁用"),

    /**
     * 启用状态
     */
    ENABLED(1, "启用");

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