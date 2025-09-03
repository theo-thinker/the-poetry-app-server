package com.sakura.poetry.service;

import com.sakura.poetry.entity.UserCollection;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户收藏服务接口
 * 
 * <p>用户收藏业务逻辑接口，定义收藏相关的业务操作方法。
 * 继承IService接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface UserCollectionService extends IService<UserCollection> {
    
    /**
     * 根据用户ID和诗词ID查询收藏信息
     * 
     * @param userId 用户ID
     * @param poetryId 诗词ID
     * @return 收藏信息
     */
    UserCollection getCollectionByUserAndPoetry(Long userId, Long poetryId);
    
    /**
     * 查询用户的收藏列表
     * 
     * @param userId 用户ID
     * @return 收藏列表
     */
    List<UserCollection> getCollectionByUserId(Long userId);
    
    /**
     * 查询用户在指定收藏夹中的收藏列表
     * 
     * @param userId 用户ID
     * @param folderId 收藏夹ID
     * @return 收藏列表
     */
    List<UserCollection> getCollectionByUserAndFolder(Long userId, Long folderId);
    
    /**
     * 创建收藏
     * 
     * @param userCollection 收藏信息
     * @return 是否创建成功
     */
    boolean createCollection(UserCollection userCollection);
    
    /**
     * 删除用户的收藏记录
     * 
     * @param userId 用户ID
     * @param poetryId 诗词ID
     * @return 是否删除成功
     */
    boolean deleteCollectionByUserAndPoetry(Long userId, Long poetryId);
    
    /**
     * 删除收藏（逻辑删除）
     * 
     * @param collectionId 收藏ID
     * @return 是否删除成功
     */
    boolean deleteCollection(Long collectionId);
}