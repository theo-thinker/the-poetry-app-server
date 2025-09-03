package com.sakura.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.poetry.entity.UserCollection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户收藏Mapper接口
 * 
 * <p>用户收藏数据访问接口，提供收藏相关的数据库操作方法。
 * 继承BaseMapper接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface UserCollectionMapper extends BaseMapper<UserCollection> {
    
    /**
     * 根据用户ID和诗词ID查询收藏信息
     * 
     * @param userId 用户ID
     * @param poetryId 诗词ID
     * @return 收藏信息
     */
    UserCollection selectByUserAndPoetry(@Param("userId") Long userId, @Param("poetryId") Long poetryId);
    
    /**
     * 查询用户的收藏列表
     * 
     * @param userId 用户ID
     * @return 收藏列表
     */
    List<UserCollection> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 查询用户在指定收藏夹中的收藏列表
     * 
     * @param userId 用户ID
     * @param folderId 收藏夹ID
     * @return 收藏列表
     */
    List<UserCollection> selectByUserAndFolder(@Param("userId") Long userId, @Param("folderId") Long folderId);
    
    /**
     * 删除用户的收藏记录
     * 
     * @param userId 用户ID
     * @param poetryId 诗词ID
     * @return 影响行数
     */
    int deleteByUserAndPoetry(@Param("userId") Long userId, @Param("poetryId") Long poetryId);
}