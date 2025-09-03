package com.sakura.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.poetry.entity.Poet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 诗人Mapper接口
 * 
 * <p>诗人数据访问接口，提供诗人相关的数据库操作方法。
 * 继承BaseMapper接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface PoetMapper extends BaseMapper<Poet> {
    
    /**
     * 根据姓名查询诗人列表
     * 
     * @param poetName 诗人姓名
     * @return 诗人列表
     */
    List<Poet> selectByPoetName(@Param("poetName") String poetName);
    
    /**
     * 查询诗人列表（分页）
     * 
     * @param poet 查询条件
     * @return 诗人列表
     */
    List<Poet> selectPoetList(Poet poet);
    
    /**
     * 根据朝代ID查询诗人列表
     * 
     * @param dynastyId 朝代ID
     * @return 诗人列表
     */
    List<Poet> selectByDynastyId(@Param("dynastyId") Long dynastyId);
    
    /**
     * 增加诗人浏览次数
     * 
     * @param poetId 诗人ID
     * @return 影响行数
     */
    int incrementViewCount(@Param("poetId") Long poetId);
    
    /**
     * 增加诗人点赞次数
     * 
     * @param poetId 诗人ID
     * @return 影响行数
     */
    int incrementLikeCount(@Param("poetId") Long poetId);
}