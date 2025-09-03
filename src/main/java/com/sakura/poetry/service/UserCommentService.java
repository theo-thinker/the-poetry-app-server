package com.sakura.poetry.service;

import com.sakura.poetry.entity.UserComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户评论服务接口
 * 
 * <p>用户评论业务逻辑接口，定义评论相关的业务操作方法。
 * 继承IService接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface UserCommentService extends IService<UserComment> {
    
    /**
     * 根据诗词ID查询评论列表
     * 
     * @param poetryId 诗词ID
     * @return 评论列表
     */
    List<UserComment> getCommentByPoetryId(Long poetryId);
    
    /**
     * 查询顶级评论列表（分页）
     * 
     * @param poetryId 诗词ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 顶级评论列表
     */
    List<UserComment> getTopLevelComments(Long poetryId, int offset, int limit);
    
    /**
     * 根据父评论ID查询回复列表
     * 
     * @param parentId 父评论ID
     * @return 回复列表
     */
    List<UserComment> getRepliesByParentId(Long parentId);
    
    /**
     * 增加评论的点赞数
     * 
     * @param commentId 评论ID
     * @param count 增加的数量
     * @return 是否增加成功
     */
    boolean incrementLikeCount(Long commentId, int count);
    
    /**
     * 增加评论的回复数
     * 
     * @param commentId 评论ID
     * @param count 增加的数量
     * @return 是否增加成功
     */
    boolean incrementReplyCount(Long commentId, int count);
    
    /**
     * 创建评论
     * 
     * @param userComment 评论信息
     * @return 是否创建成功
     */
    boolean createComment(UserComment userComment);
    
    /**
     * 更新评论信息
     * 
     * @param userComment 评论信息
     * @return 是否更新成功
     */
    boolean updateComment(UserComment userComment);
    
    /**
     * 删除评论（逻辑删除）
     * 
     * @param commentId 评论ID
     * @return 是否删除成功
     */
    boolean deleteComment(Long commentId);
}