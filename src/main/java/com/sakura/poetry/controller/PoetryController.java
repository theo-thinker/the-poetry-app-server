package com.sakura.poetry.controller;

import com.sakura.poetry.entity.Poetry;
import com.sakura.poetry.service.PoetryService;
import com.sakura.poetry.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 诗词控制器
 * 
 * <p>诗词相关的API接口控制器，处理诗词相关的HTTP请求。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestController
@RequestMapping("/api/poetry")
@Tag(name = "诗词管理", description = "诗词相关的API接口")
public class PoetryController {
    
    @Autowired
    private PoetryService poetryService;
    
    /**
     * 根据标题查询诗词列表
     * 
     * @param title 诗词标题
     * @return 诗词列表
     */
    @GetMapping("/title/{title}")
    @Operation(summary = "根据标题查询诗词列表")
    public Result<List<Poetry>> getPoetryByTitle(@PathVariable String title) {
        List<Poetry> poetryList = poetryService.getPoetryByTitle(title);
        return Result.success(poetryList);
    }
    
    /**
     * 查询诗词列表（分页）
     * 
     * @param poetry 查询条件
     * @return 诗词列表
     */
    @PostMapping("/list")
    @Operation(summary = "查询诗词列表")
    public Result<List<Poetry>> getPoetryList(@RequestBody Poetry poetry) {
        List<Poetry> poetryList = poetryService.getPoetryList(poetry);
        return Result.success(poetryList);
    }
    
    /**
     * 查询热门诗词列表
     * 
     * @param limit 返回记录数
     * @return 热门诗词列表
     */
    @GetMapping("/hot/{limit}")
    @Operation(summary = "查询热门诗词列表")
    public Result<List<Poetry>> getHotPoetryList(@PathVariable int limit) {
        List<Poetry> poetryList = poetryService.getHotPoetryList(limit);
        return Result.success(poetryList);
    }
    
    /**
     * 查询精选诗词列表
     * 
     * @param limit 返回记录数
     * @return 精选诗词列表
     */
    @GetMapping("/featured/{limit}")
    @Operation(summary = "查询精选诗词列表")
    public Result<List<Poetry>> getFeaturedPoetryList(@PathVariable int limit) {
        List<Poetry> poetryList = poetryService.getFeaturedPoetryList(limit);
        return Result.success(poetryList);
    }
    
    /**
     * 增加诗词浏览次数
     * 
     * @param poetryId 诗词ID
     * @return 是否增加成功
     */
    @PutMapping("/view/{poetryId}")
    @Operation(summary = "增加诗词浏览次数")
    public Result<Boolean> incrementViewCount(@PathVariable Long poetryId) {
        boolean result = poetryService.incrementViewCount(poetryId);
        return Result.success(result);
    }
    
    /**
     * 增加诗词点赞次数
     * 
     * @param poetryId 诗词ID
     * @return 是否增加成功
     */
    @PutMapping("/like/{poetryId}")
    @Operation(summary = "增加诗词点赞次数")
    public Result<Boolean> incrementLikeCount(@PathVariable Long poetryId) {
        boolean result = poetryService.incrementLikeCount(poetryId);
        return Result.success(result);
    }
    
    /**
     * 增加诗词收藏次数
     * 
     * @param poetryId 诗词ID
     * @return 是否增加成功
     */
    @PutMapping("/collect/{poetryId}")
    @Operation(summary = "增加诗词收藏次数")
    public Result<Boolean> incrementCollectCount(@PathVariable Long poetryId) {
        boolean result = poetryService.incrementCollectCount(poetryId);
        return Result.success(result);
    }
    
    /**
     * 创建诗词
     * 
     * @param poetry 诗词信息
     * @return 是否创建成功
     */
    @PostMapping("/create")
    @Operation(summary = "创建诗词")
    public Result<Boolean> createPoetry(@RequestBody Poetry poetry) {
        boolean result = poetryService.createPoetry(poetry);
        return Result.success(result);
    }
    
    /**
     * 更新诗词信息
     * 
     * @param poetry 诗词信息
     * @return 是否更新成功
     */
    @PutMapping("/update")
    @Operation(summary = "更新诗词信息")
    public Result<Boolean> updatePoetry(@RequestBody Poetry poetry) {
        boolean result = poetryService.updatePoetry(poetry);
        return Result.success(result);
    }
    
    /**
     * 删除诗词（逻辑删除）
     * 
     * @param poetryId 诗词ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{poetryId}")
    @Operation(summary = "删除诗词")
    public Result<Boolean> deletePoetry(@PathVariable Long poetryId) {
        boolean result = poetryService.deletePoetry(poetryId);
        return Result.success(result);
    }
}