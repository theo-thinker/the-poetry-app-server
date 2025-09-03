package com.sakura.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.poetry.entity.SysConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统配置Mapper接口
 * 
 * <p>系统配置数据访问接口，提供配置相关的数据库操作方法。
 * 继承BaseMapper接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {
    
    /**
     * 根据配置键查询配置信息
     * 
     * @param configKey 配置键
     * @return 配置信息
     */
    SysConfig selectByConfigKey(@Param("configKey") String configKey);
    
    /**
     * 根据配置分组查询配置列表
     * 
     * @param configGroup 配置分组
     * @return 配置列表
     */
    List<SysConfig> selectByConfigGroup(@Param("configGroup") String configGroup);
    
    /**
     * 查询启用的配置列表
     * 
     * @return 配置列表
     */
    List<SysConfig> selectEnabledConfigList();
    
    /**
     * 批量更新配置值
     * 
     * @param configs 配置列表
     * @return 影响行数
     */
    int batchUpdateConfigValue(List<SysConfig> configs);
}