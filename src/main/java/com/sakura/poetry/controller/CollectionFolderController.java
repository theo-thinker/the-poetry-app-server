package com.sakura.poetry.controller;

import com.sakura.poetry.entity.CollectionFolder;
import com.sakura.poetry.service.CollectionFolderService;
import com.sakura.poetry.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏夹控制器
 * 
 * <p>收藏夹相关的API接口控制器，处理收藏夹相关的HTTP请求。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestController
@RequestMapping("/api/folder")
@Tag(name = "收藏夹管理", description = "收藏夹相关的API接口")
public class CollectionFolderController {
    
    @Autowired
    private CollectionFolderService folderService;
    
    /**
     * 根据用户ID查询收藏夹列表
     * 
     * @param userId 用户ID
     * @return 收藏夹列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "根据用户ID查询收藏夹列表")
    public Result<List<CollectionFolder>> getFolderByUserId(@PathVariable Long userId) {
        List<CollectionFolder> folderList = folderService.getFolderByUserId(userId);
        return Result.success(folderList);
    }
    
    /**
     * 查询用户的公开收藏夹列表
     * 
     * @param userId 用户ID
     * @return 公开收藏夹列表
     */
    @GetMapping("/user/{userId}/public")
    @Operation(summary = "查询用户的公开收藏夹列表")
    public Result<List<CollectionFolder>> getPublicFolderByUserId(@PathVariable Long userId) {
        List<CollectionFolder> folderList = folderService.getPublicFolderByUserId(userId);
        return Result.success(folderList);
    }
    
    /**
     * 增加收藏夹中的诗词数量
     * 
     * @param folderId 收藏夹ID
     * @param count 增加的数量
     * @return 是否增加成功
     */
    @PutMapping("/poetry/{folderId}/{count}")
    @Operation(summary = "增加收藏夹中的诗词数量")
    public Result<Boolean> incrementPoetryCount(@PathVariable Long folderId, @PathVariable int count) {
        boolean result = folderService.incrementPoetryCount(folderId, count);
        return Result.success(result);
    }
    
    /**
     * 创建收藏夹
     * 
     * @param collectionFolder 收藏夹信息
     * @return 是否创建成功
     */
    @PostMapping("/create")
    @Operation(summary = "创建收藏夹")
    public Result<Boolean> createFolder(@RequestBody CollectionFolder collectionFolder) {
        boolean result = folderService.createFolder(collectionFolder);
        return Result.success(result);
    }
    
    /**
     * 更新收藏夹信息
     * 
     * @param collectionFolder 收藏夹信息
     * @return 是否更新成功
     */
    @PutMapping("/update")
    @Operation(summary = "更新收藏夹信息")
    public Result<Boolean> updateFolder(@RequestBody CollectionFolder collectionFolder) {
        boolean result = folderService.updateFolder(collectionFolder);
        return Result.success(result);
    }
    
    /**
     * 删除收藏夹（逻辑删除）
     * 
     * @param folderId 收藏夹ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{folderId}")
    @Operation(summary = "删除收藏夹")
    public Result<Boolean> deleteFolder(@PathVariable Long folderId) {
        boolean result = folderService.deleteFolder(folderId);
        return Result.success(result);
    }
}