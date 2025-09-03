package com.sakura.poetry.controller;

import com.sakura.poetry.entity.UserLike;
import com.sakura.poetry.service.UserLikeService;
import com.sakura.poetry.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户点赞控制器
 * 
 * <p>用户点赞相关的API接口控制器，处理点赞相关的HTTP请求。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestController
@RequestMapping("/api/like")
@Tag(name = "用户点赞管理", description = "用户点赞相关的API接口")
public class UserLikeController {
    
    @Autowired
    private UserLikeService likeService;
    
    /**
     * 根据用户ID、目标类型和目标ID查询点赞信息
     * 
     * @param userId 用户ID
     * @param targetType 目标类型
     * @param targetId 目标ID
     * @return 点赞信息
     */
    @GetMapping("/user/{userId}/target/{targetType}/{targetId}")
    @Operation(summary = "根据用户ID、目标类型和目标ID查询点赞信息")
    public Result<UserLike> getLikeByUserAndTarget(@PathVariable Long userId, @PathVariable Integer targetType, @PathVariable Long targetId) {
        UserLike like = likeService.getLikeByUserAndTarget(userId, targetType, targetId);
        return Result.success(like);
    }
    
    /**
     * 查询用户的所有点赞记录
     * 
     * @param userId 用户ID
     * @return 点赞记录列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "查询用户的所有点赞记录")
    public Result<List<UserLike>> getLikeByUserId(@PathVariable Long userId) {
        List<UserLike> likeList = likeService.getLikeByUserId(userId);
        return Result.success(likeList);
    }
    
    /**
     * 查询指定目标的点赞数量
     * 
     * @param targetType 目标类型
     * @param targetId 目标ID
     * @return 点赞数量
     */
    @GetMapping("/count/target/{targetType}/{targetId}")
    @Operation(summary = "查询指定目标的点赞数量")
    public Result<Integer> countLikeByTarget(@PathVariable Integer targetType, @PathVariable Long targetId) {
        int count = likeService.countLikeByTarget(targetType, targetId);
        return Result.success(count);
    }
    
    /**
     * 创建点赞
     * 
     * @param userLike 点赞信息
     * @return 是否创建成功
     */
    @PostMapping("/create")
    @Operation(summary = "创建点赞")
    public Result<Boolean> createLike(@RequestBody UserLike userLike) {
        boolean result = likeService.createLike(userLike);
        return Result.success(result);
    }
    
    /**
     * 删除用户的点赞记录
     * 
     * @param userId 用户ID
     * @param targetType 目标类型
     * @param targetId 目标ID
     * @return 是否删除成功
     */
    @DeleteMapping("/user/{userId}/target/{targetType}/{targetId}")
    @Operation(summary = "删除用户的点赞记录")
    public Result<Boolean> deleteLikeByUserAndTarget(@PathVariable Long userId, @PathVariable Integer targetType, @PathVariable Long targetId) {
        boolean result = likeService.deleteLikeByUserAndTarget(userId, targetType, targetId);
        return Result.success(result);
    }
    
    /**
     * 删除点赞（逻辑删除）
     * 
     * @param likeId 点赞ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{likeId}")
    @Operation(summary = "删除点赞")
    public Result<Boolean> deleteLike(@PathVariable Long likeId) {
        boolean result = likeService.deleteLike(likeId);
        return Result.success(result);
    }
}