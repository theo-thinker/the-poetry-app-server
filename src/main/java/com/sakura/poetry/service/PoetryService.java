package com.sakura.poetry.service;

import com.sakura.poetry.entity.Poetry;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 诗词服务接口
 * 
 * <p>诗词业务逻辑接口，定义诗词相关的业务操作方法。
 * 继承IService接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface PoetryService extends IService<Poetry> {
    
    /**
     * 根据标题查询诗词列表
     * 
     * @param title 诗词标题
     * @return 诗词列表
     */
    List<Poetry> getPoetryByTitle(String title);
    
    /**
     * 查询诗词列表（分页）
     * 
     * @param poetry 查询条件
     * @return 诗词列表
     */
    List<Poetry> getPoetryList(Poetry poetry);
    
    /**
     * 查询热门诗词列表
     * 
     * @param limit 返回记录数
     * @return 热门诗词列表
     */
    List<Poetry> getHotPoetryList(int limit);
    
    /**
     * 查询精选诗词列表
     * 
     * @param limit 返回记录数
     * @return 精选诗词列表
     */
    List<Poetry> getFeaturedPoetryList(int limit);
    
    /**
     * 增加诗词浏览次数
     * 
     * @param poetryId 诗词ID
     * @return 是否增加成功
     */
    boolean incrementViewCount(Long poetryId);
    
    /**
     * 增加诗词点赞次数
     * 
     * @param poetryId 诗词ID
     * @return 是否增加成功
     */
    boolean incrementLikeCount(Long poetryId);
    
    /**
     * 增加诗词收藏次数
     * 
     * @param poetryId 诗词ID
     * @return 是否增加成功
     */
    boolean incrementCollectCount(Long poetryId);
    
    /**
     * 创建诗词
     * 
     * @param poetry 诗词信息
     * @return 是否创建成功
     */
    boolean createPoetry(Poetry poetry);
    
    /**
     * 更新诗词信息
     * 
     * @param poetry 诗词信息
     * @return 是否更新成功
     */
    boolean updatePoetry(Poetry poetry);
    
    /**
     * 删除诗词（逻辑删除）
     * 
     * @param poetryId 诗词ID
     * @return 是否删除成功
     */
    boolean deletePoetry(Long poetryId);
}