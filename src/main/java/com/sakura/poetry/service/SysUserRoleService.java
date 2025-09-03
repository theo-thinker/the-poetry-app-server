package com.sakura.poetry.service;

import com.sakura.poetry.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户角色关联服务接口
 * 
 * <p>用户角色关联业务逻辑接口，定义用户角色关系相关的业务操作方法。
 * 继承IService接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface SysUserRoleService extends IService<SysUserRole> {
    
    /**
     * 根据用户ID查询用户角色关联列表
     * 
     * @param userId 用户ID
     * @return 用户角色关联列表
     */
    List<SysUserRole> getUserRoleByUserId(Long userId);
    
    /**
     * 根据角色ID查询用户角色关联列表
     * 
     * @param roleId 角色ID
     * @return 用户角色关联列表
     */
    List<SysUserRole> getUserRoleByRoleId(Long roleId);
    
    /**
     * 删除用户的所有角色关联
     * 
     * @param userId 用户ID
     * @return 是否删除成功
     */
    boolean deleteByUserId(Long userId);
    
    /**
     * 删除角色的所有用户关联
     * 
     * @param roleId 角色ID
     * @return 是否删除成功
     */
    boolean deleteByRoleId(Long roleId);
    
    /**
     * 创建用户角色关联
     * 
     * @param sysUserRole 用户角色关联信息
     * @return 是否创建成功
     */
    boolean createUserRole(SysUserRole sysUserRole);
    
    /**
     * 删除用户角色关联
     * 
     * @param userRoleId 用户角色关联ID
     * @return 是否删除成功
     */
    boolean deleteUserRole(Long userRoleId);
}