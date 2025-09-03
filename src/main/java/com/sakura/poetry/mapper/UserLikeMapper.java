package com.sakura.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.poetry.entity.UserLike;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户点赞Mapper接口
 * 
 * <p>用户点赞数据访问接口，提供点赞相关的数据库操作方法。
 * 继承BaseMapper接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface UserLikeMapper extends BaseMapper<UserLike> {
    
    /**
     * 根据用户ID、目标类型和目标ID查询点赞信息
     * 
     * @param userId 用户ID
     * @param targetType 目标类型
     * @param targetId 目标ID
     * @return 点赞信息
     */
    UserLike selectByUserAndTarget(@Param("userId") Long userId, @Param("targetType") Integer targetType, @Param("targetId") Long targetId);
    
    /**
     * 查询用户的所有点赞记录
     * 
     * @param userId 用户ID
     * @return 点赞记录列表
     */
    List<UserLike> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 查询指定目标的点赞数量
     * 
     * @param targetType 目标类型
     * @param targetId 目标ID
     * @return 点赞数量
     */
    int countByTarget(@Param("targetType") Integer targetType, @Param("targetId") Long targetId);
    
    /**
     * 删除用户的点赞记录
     * 
     * @param userId 用户ID
     * @param targetType 目标类型
     * @param targetId 目标ID
     * @return 影响行数
     */
    int deleteByUserAndTarget(@Param("userId") Long userId, @Param("targetType") Integer targetType, @Param("targetId") Long targetId);
}