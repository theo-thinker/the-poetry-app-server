package com.sakura.poetry.service.impl;

import com.sakura.poetry.entity.SysUserRole;
import com.sakura.poetry.mapper.SysUserRoleMapper;
import com.sakura.poetry.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色关联服务实现类
 * 
 * <p>用户角色关联业务逻辑实现类，实现用户角色关系相关的业务操作方法。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    
    @Override
    public List<SysUserRole> getUserRoleByUserId(Long userId) {
        return userRoleMapper.selectByUserId(userId);
    }
    
    @Override
    public List<SysUserRole> getUserRoleByRoleId(Long roleId) {
        return userRoleMapper.selectByRoleId(roleId);
    }
    
    @Override
    public boolean deleteByUserId(Long userId) {
        return userRoleMapper.deleteByUserId(userId) > 0;
    }
    
    @Override
    public boolean deleteByRoleId(Long roleId) {
        return userRoleMapper.deleteByRoleId(roleId) > 0;
    }
    
    @Override
    public boolean createUserRole(SysUserRole sysUserRole) {
        return this.save(sysUserRole);
    }
    
    @Override
    public boolean deleteUserRole(Long userRoleId) {
        return this.removeById(userRoleId);
    }
}