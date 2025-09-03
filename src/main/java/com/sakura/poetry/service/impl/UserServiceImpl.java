package com.sakura.poetry.service.impl;

import com.sakura.poetry.entity.SysUser;
import com.sakura.poetry.mapper.SysUserMapper;
import com.sakura.poetry.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务实现类
 * 
 * <p>用户业务逻辑实现类，实现用户相关的业务操作方法。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {
    
    @Autowired
    private SysUserMapper userMapper;
    
    @Override
    public SysUser getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
    
    @Override
    public SysUser getUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }
    
    @Override
    public SysUser getUserByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }
    
    @Override
    public List<SysUser> getUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }
    
    @Override
    public boolean updateLastLoginInfo(Long userId, String lastLoginIp) {
        return userMapper.updateLastLoginInfo(userId, lastLoginIp) > 0;
    }
    
    @Override
    public boolean createUser(SysUser user) {
        return this.save(user);
    }
    
    @Override
    public boolean updateUser(SysUser user) {
        return this.updateById(user);
    }
    
    @Override
    public boolean deleteUser(Long userId) {
        return this.removeById(userId);
    }
}