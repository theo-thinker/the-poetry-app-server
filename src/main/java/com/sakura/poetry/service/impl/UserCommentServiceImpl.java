package com.sakura.poetry.service.impl;

import com.sakura.poetry.entity.UserComment;
import com.sakura.poetry.mapper.UserCommentMapper;
import com.sakura.poetry.service.UserCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户评论服务实现类
 * 
 * <p>用户评论业务逻辑实现类，实现评论相关的业务操作方法。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Service
public class UserCommentServiceImpl extends ServiceImpl<UserCommentMapper, UserComment> implements UserCommentService {
    
    @Autowired
    private UserCommentMapper commentMapper;
    
    @Override
    public List<UserComment> getCommentByPoetryId(Long poetryId) {
        return commentMapper.selectByPoetryId(poetryId);
    }
    
    @Override
    public List<UserComment> getTopLevelComments(Long poetryId, int offset, int limit) {
        return commentMapper.selectTopLevelComments(poetryId, offset, limit);
    }
    
    @Override
    public List<UserComment> getRepliesByParentId(Long parentId) {
        return commentMapper.selectRepliesByParentId(parentId);
    }
    
    @Override
    public boolean incrementLikeCount(Long commentId, int count) {
        return commentMapper.incrementLikeCount(commentId, count) > 0;
    }
    
    @Override
    public boolean incrementReplyCount(Long commentId, int count) {
        return commentMapper.incrementReplyCount(commentId, count) > 0;
    }
    
    @Override
    public boolean createComment(UserComment userComment) {
        return this.save(userComment);
    }
    
    @Override
    public boolean updateComment(UserComment userComment) {
        return this.updateById(userComment);
    }
    
    @Override
    public boolean deleteComment(Long commentId) {
        return this.removeById(commentId);
    }
}