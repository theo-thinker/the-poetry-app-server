package com.sakura.poetry.mapper;

import com.sakura.poetry.entity.CollectionFolder;
import com.sakura.poetry.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏夹Mapper接口
 * 
 * <p>收藏夹数据访问接口，提供收藏夹相关的数据库操作方法。
 * 继承BaseMapper接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface CollectionFolderMapper extends BaseMapper<CollectionFolder> {
    
    /**
     * 根据用户ID查询收藏夹列表
     * 
     * @param userId 用户ID
     * @return 收藏夹列表
     */
    List<CollectionFolder> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 查询用户的公开收藏夹列表
     * 
     * @param userId 用户ID
     * @return 公开收藏夹列表
     */
    List<CollectionFolder> selectPublicByUserId(@Param("userId") Long userId);
    
    /**
     * 增加收藏夹中的诗词数量
     * 
     * @param folderId 收藏夹ID
     * @param count 增加的数量
     * @return 影响行数
     */
    int incrementPoetryCount(@Param("folderId") Long folderId, @Param("count") int count);
}