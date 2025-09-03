package com.sakura.poetry.service;

import com.sakura.poetry.entity.Dynasty;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 朝代服务接口
 * 
 * <p>朝代业务逻辑接口，定义朝代相关的业务操作方法。
 * 继承IService接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface DynastyService extends IService<Dynasty> {
    
    /**
     * 根据编码查询朝代信息
     * 
     * @param dynastyCode 朝代编码
     * @return 朝代信息
     */
    Dynasty getDynastyByCode(String dynastyCode);
    
    /**
     * 查询启用的朝代列表
     * 
     * @return 朝代列表
     */
    List<Dynasty> getEnabledDynastyList();
    
    /**
     * 查询朝代列表（分页）
     * 
     * @param dynasty 查询条件
     * @return 朝代列表
     */
    List<Dynasty> getDynastyList(Dynasty dynasty);
    
    /**
     * 创建朝代
     * 
     * @param dynasty 朝代信息
     * @return 是否创建成功
     */
    boolean createDynasty(Dynasty dynasty);
    
    /**
     * 更新朝代信息
     * 
     * @param dynasty 朝代信息
     * @return 是否更新成功
     */
    boolean updateDynasty(Dynasty dynasty);
    
    /**
     * 删除朝代（逻辑删除）
     * 
     * @param dynastyId 朝代ID
     * @return 是否删除成功
     */
    boolean deleteDynasty(Long dynastyId);
}