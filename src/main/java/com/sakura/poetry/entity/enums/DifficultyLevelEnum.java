package com.sakura.poetry.entity.enums;

import com.sakura.poetry.entity.base.BaseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 诗词难度等级枚举
 * 
 * <p>用于表示诗词的阅读理解难度等级，帮助用户选择适合的诗词作品。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Getter
@AllArgsConstructor
public enum DifficultyLevelEnum implements BaseStatusEnum<Integer> {

    /**
     * 简单难度
     */
    EASY(1, "简单"),

    /**
     * 中等难度
     */
    MEDIUM(2, "中等"),

    /**
     * 困难难度
     */
    HARD(3, "困难");

    /**
     * 难度值
     */
    private final Integer value;

    /**
     * 难度描述
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