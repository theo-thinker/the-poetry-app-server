package com.sakura.poetry.controller;

import com.sakura.poetry.entity.SysConfig;
import com.sakura.poetry.service.SysConfigService;
import com.sakura.poetry.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统配置控制器
 * 
 * <p>系统配置相关的API接口控制器，处理配置相关的HTTP请求。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestController
@RequestMapping("/api/config")
@Tag(name = "系统配置管理", description = "系统配置相关的API接口")
public class SysConfigController {
    
    @Autowired
    private SysConfigService configService;
    
    /**
     * 根据配置键查询配置信息
     * 
     * @param configKey 配置键
     * @return 配置信息
     */
    @GetMapping("/key/{configKey}")
    @Operation(summary = "根据配置键查询配置信息")
    public Result<SysConfig> getConfigByKey(@PathVariable String configKey) {
        SysConfig config = configService.getConfigByKey(configKey);
        return Result.success(config);
    }
    
    /**
     * 根据配置分组查询配置列表
     * 
     * @param configGroup 配置分组
     * @return 配置列表
     */
    @GetMapping("/group/{configGroup}")
    @Operation(summary = "根据配置分组查询配置列表")
    public Result<List<SysConfig>> getConfigByGroup(@PathVariable String configGroup) {
        List<SysConfig> configList = configService.getConfigByGroup(configGroup);
        return Result.success(configList);
    }
    
    /**
     * 查询启用的配置列表
     * 
     * @return 配置列表
     */
    @GetMapping("/enabled")
    @Operation(summary = "查询启用的配置列表")
    public Result<List<SysConfig>> getEnabledConfigList() {
        List<SysConfig> configList = configService.getEnabledConfigList();
        return Result.success(configList);
    }
    
    /**
     * 批量更新配置值
     * 
     * @param configs 配置列表
     * @return 是否更新成功
     */
    @PutMapping("/batch")
    @Operation(summary = "批量更新配置值")
    public Result<Boolean> batchUpdateConfigValue(@RequestBody List<SysConfig> configs) {
        boolean result = configService.batchUpdateConfigValue(configs);
        return Result.success(result);
    }
    
    /**
     * 创建配置
     * 
     * @param sysConfig 配置信息
     * @return 是否创建成功
     */
    @PostMapping("/create")
    @Operation(summary = "创建配置")
    public Result<Boolean> createConfig(@RequestBody SysConfig sysConfig) {
        boolean result = configService.createConfig(sysConfig);
        return Result.success(result);
    }
    
    /**
     * 更新配置信息
     * 
     * @param sysConfig 配置信息
     * @return 是否更新成功
     */
    @PutMapping("/update")
    @Operation(summary = "更新配置信息")
    public Result<Boolean> updateConfig(@RequestBody SysConfig sysConfig) {
        boolean result = configService.updateConfig(sysConfig);
        return Result.success(result);
    }
    
    /**
     * 删除配置（逻辑删除）
     * 
     * @param configId 配置ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{configId}")
    @Operation(summary = "删除配置")
    public Result<Boolean> deleteConfig(@PathVariable Long configId) {
        boolean result = configService.deleteConfig(configId);
        return Result.success(result);
    }
}