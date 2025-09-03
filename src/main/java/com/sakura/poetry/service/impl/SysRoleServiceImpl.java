package com.sakura.poetry.service.impl;

import com.sakura.poetry.entity.SysRole;
import com.sakura.poetry.mapper.SysRoleMapper;
import com.sakura.poetry.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统角色服务实现类
 * 
 * <p>系统角色业务逻辑实现类，实现角色相关的业务操作方法。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    
    @Autowired
    private SysRoleMapper roleMapper;
    
    @Override
    public SysRole getRoleByRoleCode(String roleCode) {
        return roleMapper.selectByRoleCode(roleCode);
    }
    
    @Override
    public List<SysRole> getRoleList(SysRole role) {
        return roleMapper.selectRoleList(role);
    }
    
    @Override
    public List<SysRole> getRolesByUserId(Long userId) {
        return roleMapper.selectRolesByUserId(userId);
    }
    
    @Override
    public boolean createRole(SysRole sysRole) {
        return this.save(sysRole);
    }
    
    @Override
    public boolean updateRole(SysRole sysRole) {
        return this.updateById(sysRole);
    }
    
    @Override
    public boolean deleteRole(Long roleId) {
        return this.removeById(roleId);
    }
}