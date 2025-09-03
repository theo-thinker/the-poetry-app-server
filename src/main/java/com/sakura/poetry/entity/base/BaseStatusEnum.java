package com.sakura.poetry.entity.base;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

/**
 * 状态枚举基础接口
 * 
 * <p>定义所有状态枚举类的基础规范，确保状态值的一致性和规范性。
 * 所有表示状态的枚举类都应该实现此接口。</p>
 * 
 * <p>该接口继承了MyBatis-Plus的IEnum接口，支持枚举与数据库值的自动转换。</p>
 * 
 * @param <T> 状态值类型，通常为Integer
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface BaseStatusEnum<T extends Serializable> extends IEnum<T> {

    /**
     * 获取状态描述
     * 
     * @return 状态的中文描述
     */
    String getDescription();

    /**
     * 根据状态值获取对应的枚举对象
     * 
     * @param value 状态值
     * @param enumClass 枚举类
     * @param <E> 枚举类型
     * @return 对应的枚举对象，如果找不到则返回null
     */
    static <E extends BaseStatusEnum<?>> E getByValue(Object value, Class<E> enumClass) {
        if (value == null || enumClass == null) {
            return null;
        }
        
        E[] constants = enumClass.getEnumConstants();
        if (constants == null) {
            return null;
        }
        
        for (E constant : constants) {
            if (value.equals(constant.getValue())) {
                return constant;
            }
        }
        
        return null;
    }
    
    /**
     * 检查给定的值是否为有效的枚举值
     * 
     * @param value 要检查的值
     * @param enumClass 枚举类
     * @param <E> 枚举类型
     * @return 如果是有效值返回true，否则返回false
     */
    static <E extends BaseStatusEnum<?>> boolean isValidValue(Object value, Class<E> enumClass) {
        return getByValue(value, enumClass) != null;
    }
}