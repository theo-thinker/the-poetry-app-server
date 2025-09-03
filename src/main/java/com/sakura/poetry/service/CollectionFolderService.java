package com.sakura.poetry.service;

import com.sakura.poetry.entity.CollectionFolder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 收藏夹服务接口
 * 
 * <p>收藏夹业务逻辑接口，定义收藏夹相关的业务操作方法。
 * 继承IService接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface CollectionFolderService extends IService<CollectionFolder> {
    
    /**
     * 根据用户ID查询收藏夹列表
     * 
     * @param userId 用户ID
     * @return 收藏夹列表
     */
    List<CollectionFolder> getFolderByUserId(Long userId);
    
    /**
     * 查询用户的公开收藏夹列表
     * 
     * @param userId 用户ID
     * @return 公开收藏夹列表
     */
    List<CollectionFolder> getPublicFolderByUserId(Long userId);
    
    /**
     * 增加收藏夹中的诗词数量
     * 
     * @param folderId 收藏夹ID
     * @param count 增加的数量
     * @return 是否增加成功
     */
    boolean incrementPoetryCount(Long folderId, int count);
    
    /**
     * 创建收藏夹
     * 
     * @param collectionFolder 收藏夹信息
     * @return 是否创建成功
     */
    boolean createFolder(CollectionFolder collectionFolder);
    
    /**
     * 更新收藏夹信息
     * 
     * @param collectionFolder 收藏夹信息
     * @return 是否更新成功
     */
    boolean updateFolder(CollectionFolder collectionFolder);
    
    /**
     * 删除收藏夹（逻辑删除）
     * 
     * @param folderId 收藏夹ID
     * @return 是否删除成功
     */
    boolean deleteFolder(Long folderId);
}