package com.sakura.poetry.service;

import com.sakura.poetry.entity.UserLike;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户点赞服务接口
 * 
 * <p>用户点赞业务逻辑接口，定义点赞相关的业务操作方法。
 * 继承IService接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface UserLikeService extends IService<UserLike> {
    
    /**
     * 根据用户ID、目标类型和目标ID查询点赞信息
     * 
     * @param userId 用户ID
     * @param targetType 目标类型
     * @param targetId 目标ID
     * @return 点赞信息
     */
    UserLike getLikeByUserAndTarget(Long userId, Integer targetType, Long targetId);
    
    /**
     * 查询用户的所有点赞记录
     * 
     * @param userId 用户ID
     * @return 点赞记录列表
     */
    List<UserLike> getLikeByUserId(Long userId);
    
    /**
     * 查询指定目标的点赞数量
     * 
     * @param targetType 目标类型
     * @param targetId 目标ID
     * @return 点赞数量
     */
    int countLikeByTarget(Integer targetType, Long targetId);
    
    /**
     * 创建点赞
     * 
     * @param userLike 点赞信息
     * @return 是否创建成功
     */
    boolean createLike(UserLike userLike);
    
    /**
     * 删除用户的点赞记录
     * 
     * @param userId 用户ID
     * @param targetType 目标类型
     * @param targetId 目标ID
     * @return 是否删除成功
     */
    boolean deleteLikeByUserAndTarget(Long userId, Integer targetType, Long targetId);
    
    /**
     * 删除点赞（逻辑删除）
     * 
     * @param likeId 点赞ID
     * @return 是否删除成功
     */
    boolean deleteLike(Long likeId);
}