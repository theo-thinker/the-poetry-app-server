package com.sakura.poetry.service.impl;

import com.sakura.poetry.entity.SysConfig;
import com.sakura.poetry.mapper.SysConfigMapper;
import com.sakura.poetry.service.SysConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统配置服务实现类
 * 
 * <p>系统配置业务逻辑实现类，实现配置相关的业务操作方法。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {
    
    @Autowired
    private SysConfigMapper configMapper;
    
    @Override
    public SysConfig getConfigByKey(String configKey) {
        return configMapper.selectByConfigKey(configKey);
    }
    
    @Override
    public List<SysConfig> getConfigByGroup(String configGroup) {
        return configMapper.selectByConfigGroup(configGroup);
    }
    
    @Override
    public List<SysConfig> getEnabledConfigList() {
        return configMapper.selectEnabledConfigList();
    }
    
    @Override
    public boolean batchUpdateConfigValue(List<SysConfig> configs) {
        return configMapper.batchUpdateConfigValue(configs) > 0;
    }
    
    @Override
    public boolean createConfig(SysConfig sysConfig) {
        return this.save(sysConfig);
    }
    
    @Override
    public boolean updateConfig(SysConfig sysConfig) {
        return this.updateById(sysConfig);
    }
    
    @Override
    public boolean deleteConfig(Long configId) {
        return this.removeById(configId);
    }
}