package com.sakura.poetry.service;

import com.sakura.poetry.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统角色服务接口
 * 
 * <p>系统角色业务逻辑接口，定义角色相关的业务操作方法。
 * 继承IService接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface SysRoleService extends IService<SysRole> {
    
    /**
     * 根据角色编码查询角色信息
     * 
     * @param roleCode 角色编码
     * @return 角色信息
     */
    SysRole getRoleByRoleCode(String roleCode);
    
    /**
     * 查询角色列表（分页）
     * 
     * @param role 查询条件
     * @return 角色列表
     */
    List<SysRole> getRoleList(SysRole role);
    
    /**
     * 查询用户拥有的角色列表
     * 
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> getRolesByUserId(Long userId);
    
    /**
     * 创建角色
     * 
     * @param sysRole 角色信息
     * @return 是否创建成功
     */
    boolean createRole(SysRole sysRole);
    
    /**
     * 更新角色信息
     * 
     * @param sysRole 角色信息
     * @return 是否更新成功
     */
    boolean updateRole(SysRole sysRole);
    
    /**
     * 删除角色（逻辑删除）
     * 
     * @param roleId 角色ID
     * @return 是否删除成功
     */
    boolean deleteRole(Long roleId);
}