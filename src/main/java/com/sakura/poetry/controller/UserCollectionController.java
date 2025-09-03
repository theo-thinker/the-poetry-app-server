package com.sakura.poetry.controller;

import com.sakura.poetry.entity.UserCollection;
import com.sakura.poetry.service.UserCollectionService;
import com.sakura.poetry.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户收藏控制器
 * 
 * <p>用户收藏相关的API接口控制器，处理收藏相关的HTTP请求。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestController
@RequestMapping("/api/collection")
@Tag(name = "用户收藏管理", description = "用户收藏相关的API接口")
public class UserCollectionController {
    
    @Autowired
    private UserCollectionService collectionService;
    
    /**
     * 根据用户ID和诗词ID查询收藏信息
     * 
     * @param userId 用户ID
     * @param poetryId 诗词ID
     * @return 收藏信息
     */
    @GetMapping("/user/{userId}/poetry/{poetryId}")
    @Operation(summary = "根据用户ID和诗词ID查询收藏信息")
    public Result<UserCollection> getCollectionByUserAndPoetry(@PathVariable Long userId, @PathVariable Long poetryId) {
        UserCollection collection = collectionService.getCollectionByUserAndPoetry(userId, poetryId);
        return Result.success(collection);
    }
    
    /**
     * 查询用户的收藏列表
     * 
     * @param userId 用户ID
     * @return 收藏列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "查询用户的收藏列表")
    public Result<List<UserCollection>> getCollectionByUserId(@PathVariable Long userId) {
        List<UserCollection> collectionList = collectionService.getCollectionByUserId(userId);
        return Result.success(collectionList);
    }
    
    /**
     * 查询用户在指定收藏夹中的收藏列表
     * 
     * @param userId 用户ID
     * @param folderId 收藏夹ID
     * @return 收藏列表
     */
    @GetMapping("/user/{userId}/folder/{folderId}")
    @Operation(summary = "查询用户在指定收藏夹中的收藏列表")
    public Result<List<UserCollection>> getCollectionByUserAndFolder(@PathVariable Long userId, @PathVariable Long folderId) {
        List<UserCollection> collectionList = collectionService.getCollectionByUserAndFolder(userId, folderId);
        return Result.success(collectionList);
    }
    
    /**
     * 创建收藏
     * 
     * @param userCollection 收藏信息
     * @return 是否创建成功
     */
    @PostMapping("/create")
    @Operation(summary = "创建收藏")
    public Result<Boolean> createCollection(@RequestBody UserCollection userCollection) {
        boolean result = collectionService.createCollection(userCollection);
        return Result.success(result);
    }
    
    /**
     * 删除用户的收藏记录
     * 
     * @param userId 用户ID
     * @param poetryId 诗词ID
     * @return 是否删除成功
     */
    @DeleteMapping("/user/{userId}/poetry/{poetryId}")
    @Operation(summary = "删除用户的收藏记录")
    public Result<Boolean> deleteCollectionByUserAndPoetry(@PathVariable Long userId, @PathVariable Long poetryId) {
        boolean result = collectionService.deleteCollectionByUserAndPoetry(userId, poetryId);
        return Result.success(result);
    }
    
    /**
     * 删除收藏（逻辑删除）
     * 
     * @param collectionId 收藏ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{collectionId}")
    @Operation(summary = "删除收藏")
    public Result<Boolean> deleteCollection(@PathVariable Long collectionId) {
        boolean result = collectionService.deleteCollection(collectionId);
        return Result.success(result);
    }
}