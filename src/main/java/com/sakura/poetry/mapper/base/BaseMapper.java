package com.sakura.poetry.mapper.base;

import com.sakura.poetry.entity.base.BaseEntity;

/**
 * 基础Mapper接口
 * 
 * <p>所有业务Mapper接口的父接口，继承MyBatis-Plus的BaseMapper，
 * 提供基础的CRUD操作方法。</p>
 * 
 * <p>该接口定义了所有实体Mapper的通用方法，包括：
 * <ul>
 *   <li>插入操作</li>
 *   <li>删除操作</li>
 *   <li>更新操作</li>
 *   <li>查询操作</li>
 * </ul>
 * </p>
 * 
 * @param <T> 实体类型
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface BaseMapper<T extends BaseEntity> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {
    
    /**
     * 根据ID逻辑删除记录
     * 
     * @param id 主键ID
     * @return 影响行数
     */
    int logicDeleteById(Long id);
    
    /**
     * 根据条件逻辑删除记录
     * 
     * @param entity 实体对象（包含删除条件）
     * @return 影响行数
     */
    int logicDeleteByEntity(T entity);
}