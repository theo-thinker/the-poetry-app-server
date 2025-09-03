package com.sakura.poetry.service;

import com.sakura.poetry.entity.SysConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统配置服务接口
 * 
 * <p>系统配置业务逻辑接口，定义配置相关的业务操作方法。
 * 继承IService接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface SysConfigService extends IService<SysConfig> {
    
    /**
     * 根据配置键查询配置信息
     * 
     * @param configKey 配置键
     * @return 配置信息
     */
    SysConfig getConfigByKey(String configKey);
    
    /**
     * 根据配置分组查询配置列表
     * 
     * @param configGroup 配置分组
     * @return 配置列表
     */
    List<SysConfig> getConfigByGroup(String configGroup);
    
    /**
     * 查询启用的配置列表
     * 
     * @return 配置列表
     */
    List<SysConfig> getEnabledConfigList();
    
    /**
     * 批量更新配置值
     * 
     * @param configs 配置列表
     * @return 是否更新成功
     */
    boolean batchUpdateConfigValue(List<SysConfig> configs);
    
    /**
     * 创建配置
     * 
     * @param sysConfig 配置信息
     * @return 是否创建成功
     */
    boolean createConfig(SysConfig sysConfig);
    
    /**
     * 更新配置信息
     * 
     * @param sysConfig 配置信息
     * @return 是否更新成功
     */
    boolean updateConfig(SysConfig sysConfig);
    
    /**
     * 删除配置（逻辑删除）
     * 
     * @param configId 配置ID
     * @return 是否删除成功
     */
    boolean deleteConfig(Long configId);
}