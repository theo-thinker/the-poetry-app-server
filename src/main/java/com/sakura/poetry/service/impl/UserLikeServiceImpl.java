package com.sakura.poetry.service.impl;

import com.sakura.poetry.entity.UserLike;
import com.sakura.poetry.mapper.UserLikeMapper;
import com.sakura.poetry.service.UserLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户点赞服务实现类
 * 
 * <p>用户点赞业务逻辑实现类，实现点赞相关的业务操作方法。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Service
public class UserLikeServiceImpl extends ServiceImpl<UserLikeMapper, UserLike> implements UserLikeService {
    
    @Autowired
    private UserLikeMapper likeMapper;
    
    @Override
    public UserLike getLikeByUserAndTarget(Long userId, Integer targetType, Long targetId) {
        return likeMapper.selectByUserAndTarget(userId, targetType, targetId);
    }
    
    @Override
    public List<UserLike> getLikeByUserId(Long userId) {
        return likeMapper.selectByUserId(userId);
    }
    
    @Override
    public int countLikeByTarget(Integer targetType, Long targetId) {
        return likeMapper.countByTarget(targetType, targetId);
    }
    
    @Override
    public boolean createLike(UserLike userLike) {
        return this.save(userLike);
    }
    
    @Override
    public boolean deleteLikeByUserAndTarget(Long userId, Integer targetType, Long targetId) {
        return likeMapper.deleteByUserAndTarget(userId, targetType, targetId) > 0;
    }
    
    @Override
    public boolean deleteLike(Long likeId) {
        return this.removeById(likeId);
    }
}