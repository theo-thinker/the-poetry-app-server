package com.sakura.poetry.service;

import com.sakura.poetry.entity.PoetryCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 诗词分类服务接口
 * 
 * <p>诗词分类业务逻辑接口，定义分类相关的业务操作方法。
 * 继承IService接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface PoetryCategoryService extends IService<PoetryCategory> {
    
    /**
     * 根据编码查询分类信息
     * 
     * @param categoryCode 分类编码
     * @return 分类信息
     */
    PoetryCategory getCategoryByCode(String categoryCode);
    
    /**
     * 查询启用的分类列表
     * 
     * @return 分类列表
     */
    List<PoetryCategory> getEnabledCategoryList();
    
    /**
     * 查询分类列表（分页）
     * 
     * @param category 查询条件
     * @return 分类列表
     */
    List<PoetryCategory> getCategoryList(PoetryCategory category);
    
    /**
     * 根据父ID查询子分类列表
     * 
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<PoetryCategory> getCategoryByParentId(Long parentId);
    
    /**
     * 创建分类
     * 
     * @param category 分类信息
     * @return 是否创建成功
     */
    boolean createCategory(PoetryCategory category);
    
    /**
     * 更新分类信息
     * 
     * @param category 分类信息
     * @return 是否更新成功
     */
    boolean updateCategory(PoetryCategory category);
    
    /**
     * 删除分类（逻辑删除）
     * 
     * @param categoryId 分类ID
     * @return 是否删除成功
     */
    boolean deleteCategory(Long categoryId);
}