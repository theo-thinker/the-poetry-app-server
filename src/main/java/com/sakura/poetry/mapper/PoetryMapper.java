package com.sakura.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.poetry.entity.Poetry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 诗词Mapper接口
 * 
 * <p>诗词数据访问接口，提供诗词相关的数据库操作方法。
 * 继承BaseMapper接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface PoetryMapper extends BaseMapper<Poetry> {
    
    /**
     * 根据标题查询诗词列表
     * 
     * @param title 诗词标题
     * @return 诗词列表
     */
    List<Poetry> selectByTitle(@Param("title") String title);
    
    /**
     * 查询诗词列表（分页）
     * 
     * @param poetry 查询条件
     * @return 诗词列表
     */
    List<Poetry> selectPoetryList(Poetry poetry);
    
    /**
     * 查询热门诗词列表
     * 
     * @param limit 返回记录数
     * @return 热门诗词列表
     */
    List<Poetry> selectHotPoetryList(@Param("limit") int limit);
    
    /**
     * 查询精选诗词列表
     * 
     * @param limit 返回记录数
     * @return 精选诗词列表
     */
    List<Poetry> selectFeaturedPoetryList(@Param("limit") int limit);
    
    /**
     * 增加诗词浏览次数
     * 
     * @param poetryId 诗词ID
     * @return 影响行数
     */
    int incrementViewCount(@Param("poetryId") Long poetryId);
    
    /**
     * 增加诗词点赞次数
     * 
     * @param poetryId 诗词ID
     * @return 影响行数
     */
    int incrementLikeCount(@Param("poetryId") Long poetryId);
    
    /**
     * 增加诗词收藏次数
     * 
     * @param poetryId 诗词ID
     * @return 影响行数
     */
    int incrementCollectCount(@Param("poetryId") Long poetryId);
}