package com.sakura.poetry.service.impl;

import com.sakura.poetry.entity.CollectionFolder;
import com.sakura.poetry.mapper.CollectionFolderMapper;
import com.sakura.poetry.service.CollectionFolderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收藏夹服务实现类
 * 
 * <p>收藏夹业务逻辑实现类，实现收藏夹相关的业务操作方法。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Service
public class CollectionFolderServiceImpl extends ServiceImpl<CollectionFolderMapper, CollectionFolder> implements CollectionFolderService {
    
    @Autowired
    private CollectionFolderMapper folderMapper;
    
    @Override
    public List<CollectionFolder> getFolderByUserId(Long userId) {
        return folderMapper.selectByUserId(userId);
    }
    
    @Override
    public List<CollectionFolder> getPublicFolderByUserId(Long userId) {
        return folderMapper.selectPublicByUserId(userId);
    }
    
    @Override
    public boolean incrementPoetryCount(Long folderId, int count) {
        return folderMapper.incrementPoetryCount(folderId, count) > 0;
    }
    
    @Override
    public boolean createFolder(CollectionFolder collectionFolder) {
        return this.save(collectionFolder);
    }
    
    @Override
    public boolean updateFolder(CollectionFolder collectionFolder) {
        return this.updateById(collectionFolder);
    }
    
    @Override
    public boolean deleteFolder(Long folderId) {
        return this.removeById(folderId);
    }
}