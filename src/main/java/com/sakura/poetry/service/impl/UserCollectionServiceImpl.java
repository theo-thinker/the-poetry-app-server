package com.sakura.poetry.service.impl;

import com.sakura.poetry.entity.UserCollection;
import com.sakura.poetry.mapper.UserCollectionMapper;
import com.sakura.poetry.service.UserCollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户收藏服务实现类
 * 
 * <p>用户收藏业务逻辑实现类，实现收藏相关的业务操作方法。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Service
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection> implements UserCollectionService {
    
    @Autowired
    private UserCollectionMapper collectionMapper;
    
    @Override
    public UserCollection getCollectionByUserAndPoetry(Long userId, Long poetryId) {
        return collectionMapper.selectByUserAndPoetry(userId, poetryId);
    }
    
    @Override
    public List<UserCollection> getCollectionByUserId(Long userId) {
        return collectionMapper.selectByUserId(userId);
    }
    
    @Override
    public List<UserCollection> getCollectionByUserAndFolder(Long userId, Long folderId) {
        return collectionMapper.selectByUserAndFolder(userId, folderId);
    }
    
    @Override
    public boolean createCollection(UserCollection userCollection) {
        return this.save(userCollection);
    }
    
    @Override
    public boolean deleteCollectionByUserAndPoetry(Long userId, Long poetryId) {
        return collectionMapper.deleteByUserAndPoetry(userId, poetryId) > 0;
    }
    
    @Override
    public boolean deleteCollection(Long collectionId) {
        return this.removeById(collectionId);
    }
}