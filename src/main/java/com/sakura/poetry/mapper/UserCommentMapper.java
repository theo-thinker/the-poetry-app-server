package com.sakura.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.poetry.entity.UserComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户评论Mapper接口
 * 
 * <p>用户评论数据访问接口，提供评论相关的数据库操作方法。
 * 继承BaseMapper接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface UserCommentMapper extends BaseMapper<UserComment> {
    
    /**
     * 根据诗词ID查询评论列表
     * 
     * @param poetryId 诗词ID
     * @return 评论列表
     */
    List<UserComment> selectByPoetryId(@Param("poetryId") Long poetryId);
    
    /**
     * 查询顶级评论列表（分页）
     * 
     * @param poetryId 诗词ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 顶级评论列表
     */
    List<UserComment> selectTopLevelComments(@Param("poetryId") Long poetryId, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 根据父评论ID查询回复列表
     * 
     * @param parentId 父评论ID
     * @return 回复列表
     */
    List<UserComment> selectRepliesByParentId(@Param("parentId") Long parentId);
    
    /**
     * 增加评论的点赞数
     * 
     * @param commentId 评论ID
     * @param count 增加的数量
     * @return 影响行数
     */
    int incrementLikeCount(@Param("commentId") Long commentId, @Param("count") int count);
    
    /**
     * 增加评论的回复数
     * 
     * @param commentId 评论ID
     * @param count 增加的数量
     * @return 影响行数
     */
    int incrementReplyCount(@Param("commentId") Long commentId, @Param("count") int count);
}