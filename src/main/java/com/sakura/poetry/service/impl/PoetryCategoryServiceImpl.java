package com.sakura.poetry.service.impl;

import com.sakura.poetry.entity.PoetryCategory;
import com.sakura.poetry.mapper.PoetryCategoryMapper;
import com.sakura.poetry.service.PoetryCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 诗词分类服务实现类
 * 
 * <p>诗词分类业务逻辑实现类，实现分类相关的业务操作方法。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Service
public class PoetryCategoryServiceImpl extends ServiceImpl<PoetryCategoryMapper, PoetryCategory> implements PoetryCategoryService {
    
    @Autowired
    private PoetryCategoryMapper categoryMapper;
    
    @Override
    public PoetryCategory getCategoryByCode(String categoryCode) {
        return categoryMapper.selectByCategoryCode(categoryCode);
    }
    
    @Override
    public List<PoetryCategory> getEnabledCategoryList() {
        return categoryMapper.selectEnabledCategoryList();
    }
    
    @Override
    public List<PoetryCategory> getCategoryList(PoetryCategory category) {
        return categoryMapper.selectCategoryList(category);
    }
    
    @Override
    public List<PoetryCategory> getCategoryByParentId(Long parentId) {
        return categoryMapper.selectByParentId(parentId);
    }
    
    @Override
    public boolean createCategory(PoetryCategory category) {
        return this.save(category);
    }
    
    @Override
    public boolean updateCategory(PoetryCategory category) {
        return this.updateById(category);
    }
    
    @Override
    public boolean deleteCategory(Long categoryId) {
        return this.removeById(categoryId);
    }
}