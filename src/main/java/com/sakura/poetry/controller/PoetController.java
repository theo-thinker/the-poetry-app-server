package com.sakura.poetry.controller;

import com.sakura.poetry.entity.Poet;
import com.sakura.poetry.service.PoetService;
import com.sakura.poetry.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 诗人控制器
 * 
 * <p>诗人相关的API接口控制器，处理诗人相关的HTTP请求。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestController
@RequestMapping("/api/poet")
@Tag(name = "诗人管理", description = "诗人相关的API接口")
public class PoetController {
    
    @Autowired
    private PoetService poetService;
    
    /**
     * 根据姓名查询诗人列表
     * 
     * @param poetName 诗人姓名
     * @return 诗人列表
     */
    @GetMapping("/name/{poetName}")
    @Operation(summary = "根据姓名查询诗人列表")
    public Result<List<Poet>> getPoetByPoetName(@PathVariable String poetName) {
        List<Poet> poetList = poetService.getPoetByPoetName(poetName);
        return Result.success(poetList);
    }
    
    /**
     * 查询诗人列表（分页）
     * 
     * @param poet 查询条件
     * @return 诗人列表
     */
    @PostMapping("/list")
    @Operation(summary = "查询诗人列表")
    public Result<List<Poet>> getPoetList(@RequestBody Poet poet) {
        List<Poet> poetList = poetService.getPoetList(poet);
        return Result.success(poetList);
    }
    
    /**
     * 根据朝代ID查询诗人列表
     * 
     * @param dynastyId 朝代ID
     * @return 诗人列表
     */
    @GetMapping("/dynasty/{dynastyId}")
    @Operation(summary = "根据朝代ID查询诗人列表")
    public Result<List<Poet>> getPoetByDynastyId(@PathVariable Long dynastyId) {
        List<Poet> poetList = poetService.getPoetByDynastyId(dynastyId);
        return Result.success(poetList);
    }
    
    /**
     * 增加诗人浏览次数
     * 
     * @param poetId 诗人ID
     * @return 是否增加成功
     */
    @PutMapping("/view/{poetId}")
    @Operation(summary = "增加诗人浏览次数")
    public Result<Boolean> incrementViewCount(@PathVariable Long poetId) {
        boolean result = poetService.incrementViewCount(poetId);
        return Result.success(result);
    }
    
    /**
     * 增加诗人点赞次数
     * 
     * @param poetId 诗人ID
     * @return 是否增加成功
     */
    @PutMapping("/like/{poetId}")
    @Operation(summary = "增加诗人点赞次数")
    public Result<Boolean> incrementLikeCount(@PathVariable Long poetId) {
        boolean result = poetService.incrementLikeCount(poetId);
        return Result.success(result);
    }
    
    /**
     * 创建诗人
     * 
     * @param poet 诗人信息
     * @return 是否创建成功
     */
    @PostMapping("/create")
    @Operation(summary = "创建诗人")
    public Result<Boolean> createPoet(@RequestBody Poet poet) {
        boolean result = poetService.createPoet(poet);
        return Result.success(result);
    }
    
    /**
     * 更新诗人信息
     * 
     * @param poet 诗人信息
     * @return 是否更新成功
     */
    @PutMapping("/update")
    @Operation(summary = "更新诗人信息")
    public Result<Boolean> updatePoet(@RequestBody Poet poet) {
        boolean result = poetService.updatePoet(poet);
        return Result.success(result);
    }
    
    /**
     * 删除诗人（逻辑删除）
     * 
     * @param poetId 诗人ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{poetId}")
    @Operation(summary = "删除诗人")
    public Result<Boolean> deletePoet(@PathVariable Long poetId) {
        boolean result = poetService.deletePoet(poetId);
        return Result.success(result);
    }
}