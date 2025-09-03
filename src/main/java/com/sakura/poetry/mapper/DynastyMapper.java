package com.sakura.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.poetry.entity.Dynasty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 朝代Mapper接口
 * 
 * <p>朝代数据访问接口，提供朝代相关的数据库操作方法。
 * 继承BaseMapper接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface DynastyMapper extends BaseMapper<Dynasty> {
    
    /**
     * 根据编码查询朝代信息
     * 
     * @param dynastyCode 朝代编码
     * @return 朝代信息
     */
    Dynasty selectByDynastyCode(@Param("dynastyCode") String dynastyCode);
    
    /**
     * 查询启用的朝代列表
     * 
     * @return 朝代列表
     */
    List<Dynasty> selectEnabledDynastyList();
    
    /**
     * 查询朝代列表（分页）
     * 
     * @param dynasty 查询条件
     * @return 朝代列表
     */
    List<Dynasty> selectDynastyList(Dynasty dynasty);
}