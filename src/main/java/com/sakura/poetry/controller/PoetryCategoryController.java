package com.sakura.poetry.controller;

import com.sakura.poetry.entity.PoetryCategory;
import com.sakura.poetry.service.PoetryCategoryService;
import com.sakura.poetry.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 诗词分类控制器
 * 
 * <p>诗词分类相关的API接口控制器，处理分类相关的HTTP请求。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestController
@RequestMapping("/api/category")
@Tag(name = "诗词分类管理", description = "诗词分类相关的API接口")
public class PoetryCategoryController {
    
    @Autowired
    private PoetryCategoryService categoryService;
    
    /**
     * 根据编码查询分类信息
     * 
     * @param categoryCode 分类编码
     * @return 分类信息
     */
    @GetMapping("/code/{categoryCode}")
    @Operation(summary = "根据编码查询分类信息")
    public Result<PoetryCategory> getCategoryByCode(@PathVariable String categoryCode) {
        PoetryCategory category = categoryService.getCategoryByCode(categoryCode);
        return Result.success(category);
    }
    
    /**
     * 查询启用的分类列表
     * 
     * @return 分类列表
     */
    @GetMapping("/enabled")
    @Operation(summary = "查询启用的分类列表")
    public Result<List<PoetryCategory>> getEnabledCategoryList() {
        List<PoetryCategory> categoryList = categoryService.getEnabledCategoryList();
        return Result.success(categoryList);
    }
    
    /**
     * 查询分类列表（分页）
     * 
     * @param category 查询条件
     * @return 分类列表
     */
    @PostMapping("/list")
    @Operation(summary = "查询分类列表")
    public Result<List<PoetryCategory>> getCategoryList(@RequestBody PoetryCategory category) {
        List<PoetryCategory> categoryList = categoryService.getCategoryList(category);
        return Result.success(categoryList);
    }
    
    /**
     * 根据父ID查询子分类列表
     * 
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    @GetMapping("/parent/{parentId}")
    @Operation(summary = "根据父ID查询子分类列表")
    public Result<List<PoetryCategory>> getCategoryByParentId(@PathVariable Long parentId) {
        List<PoetryCategory> categoryList = categoryService.getCategoryByParentId(parentId);
        return Result.success(categoryList);
    }
    
    /**
     * 创建分类
     * 
     * @param category 分类信息
     * @return 是否创建成功
     */
    @PostMapping("/create")
    @Operation(summary = "创建分类")
    public Result<Boolean> createCategory(@RequestBody PoetryCategory category) {
        boolean result = categoryService.createCategory(category);
        return Result.success(result);
    }
    
    /**
     * 更新分类信息
     * 
     * @param category 分类信息
     * @return 是否更新成功
     */
    @PutMapping("/update")
    @Operation(summary = "更新分类信息")
    public Result<Boolean> updateCategory(@RequestBody PoetryCategory category) {
        boolean result = categoryService.updateCategory(category);
        return Result.success(result);
    }
    
    /**
     * 删除分类（逻辑删除）
     * 
     * @param categoryId 分类ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{categoryId}")
    @Operation(summary = "删除分类")
    public Result<Boolean> deleteCategory(@PathVariable Long categoryId) {
        boolean result = categoryService.deleteCategory(categoryId);
        return Result.success(result);
    }
}