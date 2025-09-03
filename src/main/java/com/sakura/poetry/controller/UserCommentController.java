package com.sakura.poetry.controller;

import com.sakura.poetry.entity.UserComment;
import com.sakura.poetry.service.UserCommentService;
import com.sakura.poetry.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户评论控制器
 * 
 * <p>用户评论相关的API接口控制器，处理评论相关的HTTP请求。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestController
@RequestMapping("/api/comment")
@Tag(name = "用户评论管理", description = "用户评论相关的API接口")
public class UserCommentController {
    
    @Autowired
    private UserCommentService commentService;
    
    /**
     * 根据诗词ID查询评论列表
     * 
     * @param poetryId 诗词ID
     * @return 评论列表
     */
    @GetMapping("/poetry/{poetryId}")
    @Operation(summary = "根据诗词ID查询评论列表")
    public Result<List<UserComment>> getCommentByPoetryId(@PathVariable Long poetryId) {
        List<UserComment> commentList = commentService.getCommentByPoetryId(poetryId);
        return Result.success(commentList);
    }
    
    /**
     * 查询顶级评论列表（分页）
     * 
     * @param poetryId 诗词ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 顶级评论列表
     */
    @GetMapping("/top/{poetryId}")
    @Operation(summary = "查询顶级评论列表")
    public Result<List<UserComment>> getTopLevelComments(@PathVariable Long poetryId, 
                                                        @RequestParam(defaultValue = "0") int offset, 
                                                        @RequestParam(defaultValue = "10") int limit) {
        List<UserComment> commentList = commentService.getTopLevelComments(poetryId, offset, limit);
        return Result.success(commentList);
    }
    
    /**
     * 根据父评论ID查询回复列表
     * 
     * @param parentId 父评论ID
     * @return 回复列表
     */
    @GetMapping("/reply/{parentId}")
    @Operation(summary = "根据父评论ID查询回复列表")
    public Result<List<UserComment>> getRepliesByParentId(@PathVariable Long parentId) {
        List<UserComment> replyList = commentService.getRepliesByParentId(parentId);
        return Result.success(replyList);
    }
    
    /**
     * 增加评论的点赞数
     * 
     * @param commentId 评论ID
     * @param count 增加的数量
     * @return 是否增加成功
     */
    @PutMapping("/like/{commentId}/{count}")
    @Operation(summary = "增加评论的点赞数")
    public Result<Boolean> incrementLikeCount(@PathVariable Long commentId, @PathVariable int count) {
        boolean result = commentService.incrementLikeCount(commentId, count);
        return Result.success(result);
    }
    
    /**
     * 增加评论的回复数
     * 
     * @param commentId 评论ID
     * @param count 增加的数量
     * @return 是否增加成功
     */
    @PutMapping("/reply/{commentId}/{count}")
    @Operation(summary = "增加评论的回复数")
    public Result<Boolean> incrementReplyCount(@PathVariable Long commentId, @PathVariable int count) {
        boolean result = commentService.incrementReplyCount(commentId, count);
        return Result.success(result);
    }
    
    /**
     * 创建评论
     * 
     * @param userComment 评论信息
     * @return 是否创建成功
     */
    @PostMapping("/create")
    @Operation(summary = "创建评论")
    public Result<Boolean> createComment(@RequestBody UserComment userComment) {
        boolean result = commentService.createComment(userComment);
        return Result.success(result);
    }
    
    /**
     * 更新评论信息
     * 
     * @param userComment 评论信息
     * @return 是否更新成功
     */
    @PutMapping("/update")
    @Operation(summary = "更新评论信息")
    public Result<Boolean> updateComment(@RequestBody UserComment userComment) {
        boolean result = commentService.updateComment(userComment);
        return Result.success(result);
    }
    
    /**
     * 删除评论（逻辑删除）
     * 
     * @param commentId 评论ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{commentId}")
    @Operation(summary = "删除评论")
    public Result<Boolean> deleteComment(@PathVariable Long commentId) {
        boolean result = commentService.deleteComment(commentId);
        return Result.success(result);
    }
}