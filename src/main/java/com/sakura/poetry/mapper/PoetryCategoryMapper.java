package com.sakura.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.poetry.entity.PoetryCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 诗词分类Mapper接口
 * 
 * <p>诗词分类数据访问接口，提供分类相关的数据库操作方法。
 * 继承BaseMapper接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface PoetryCategoryMapper extends BaseMapper<PoetryCategory> {
    
    /**
     * 根据编码查询分类信息
     * 
     * @param categoryCode 分类编码
     * @return 分类信息
     */
    PoetryCategory selectByCategoryCode(@Param("categoryCode") String categoryCode);
    
    /**
     * 查询启用的分类列表
     * 
     * @return 分类列表
     */
    List<PoetryCategory> selectEnabledCategoryList();
    
    /**
     * 查询分类列表（分页）
     * 
     * @param category 查询条件
     * @return 分类列表
     */
    List<PoetryCategory> selectCategoryList(PoetryCategory category);
    
    /**
     * 根据父ID查询子分类列表
     * 
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<PoetryCategory> selectByParentId(@Param("parentId") Long parentId);
}