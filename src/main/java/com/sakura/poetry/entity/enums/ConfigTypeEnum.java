package com.sakura.poetry.entity.enums;

import com.sakura.poetry.entity.base.BaseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统配置类型枚举
 * 
 * <p>用于表示系统配置项的数据类型，便于前端正确解析和显示配置值。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Getter
@AllArgsConstructor
public enum ConfigTypeEnum implements BaseStatusEnum<String> {

    /**
     * 字符串类型
     */
    STRING("string", "字符串"),

    /**
     * 数字类型
     */
    NUMBER("number", "数字"),

    /**
     * 布尔类型
     */
    BOOLEAN("boolean", "布尔值"),

    /**
     * JSON对象类型
     */
    JSON("json", "JSON对象");

    /**
     * 类型值
     */
    private final String value;

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
    public String getValue() {
        return value;
    }
}