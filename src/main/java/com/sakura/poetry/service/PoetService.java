package com.sakura.poetry.service;

import com.sakura.poetry.entity.Poet;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 诗人服务接口
 * 
 * <p>诗人业务逻辑接口，定义诗人相关的业务操作方法。
 * 继承IService接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface PoetService extends IService<Poet> {
    
    /**
     * 根据姓名查询诗人列表
     * 
     * @param poetName 诗人姓名
     * @return 诗人列表
     */
    List<Poet> getPoetByPoetName(String poetName);
    
    /**
     * 查询诗人列表（分页）
     * 
     * @param poet 查询条件
     * @return 诗人列表
     */
    List<Poet> getPoetList(Poet poet);
    
    /**
     * 根据朝代ID查询诗人列表
     * 
     * @param dynastyId 朝代ID
     * @return 诗人列表
     */
    List<Poet> getPoetByDynastyId(Long dynastyId);
    
    /**
     * 增加诗人浏览次数
     * 
     * @param poetId 诗人ID
     * @return 是否增加成功
     */
    boolean incrementViewCount(Long poetId);
    
    /**
     * 增加诗人点赞次数
     * 
     * @param poetId 诗人ID
     * @return 是否增加成功
     */
    boolean incrementLikeCount(Long poetId);
    
    /**
     * 创建诗人
     * 
     * @param poet 诗人信息
     * @return 是否创建成功
     */
    boolean createPoet(Poet poet);
    
    /**
     * 更新诗人信息
     * 
     * @param poet 诗人信息
     * @return 是否更新成功
     */
    boolean updatePoet(Poet poet);
    
    /**
     * 删除诗人（逻辑删除）
     * 
     * @param poetId 诗人ID
     * @return 是否删除成功
     */
    boolean deletePoet(Long poetId);
}