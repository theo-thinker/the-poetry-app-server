package com.sakura.poetry.entity.enums;

import com.sakura.poetry.entity.base.BaseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 诗词内容格式枚举
 * 
 * <p>用于表示诗词内容的格式类型，区分古文原文和现代文翻译。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Getter
@AllArgsConstructor
public enum ContentFormatEnum implements BaseStatusEnum<Integer> {

    /**
     * 原文格式
     */
    ORIGINAL(1, "原文"),

    /**
     * 现代文格式
     */
    MODERN(2, "现代文");

    /**
     * 格式值
     */
    private final Integer value;

    /**
     * 格式描述
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