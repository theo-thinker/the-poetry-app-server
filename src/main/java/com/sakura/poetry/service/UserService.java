package com.sakura.poetry.service;

import com.sakura.poetry.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户服务接口
 * 
 * <p>用户业务逻辑接口，定义用户相关的业务操作方法。
 * 继承IService接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface UserService extends IService<SysUser> {
    
    /**
     * 根据用户名查询用户信息
     * 
     * @param username 用户名
     * @return 用户信息
     */
    SysUser getUserByUsername(String username);
    
    /**
     * 根据邮箱查询用户信息
     * 
     * @param email 邮箱
     * @return 用户信息
     */
    SysUser getUserByEmail(String email);
    
    /**
     * 根据手机号查询用户信息
     * 
     * @param phone 手机号
     * @return 用户信息
     */
    SysUser getUserByPhone(String phone);
    
    /**
     * 查询用户列表（分页）
     * 
     * @param user 查询条件
     * @return 用户列表
     */
    List<SysUser> getUserList(SysUser user);
    
    /**
     * 更新用户最后登录信息
     * 
     * @param userId 用户ID
     * @param lastLoginIp 最后登录IP
     * @return 是否更新成功
     */
    boolean updateLastLoginInfo(Long userId, String lastLoginIp);
    
    /**
     * 创建用户
     * 
     * @param user 用户信息
     * @return 是否创建成功
     */
    boolean createUser(SysUser user);
    
    /**
     * 更新用户信息
     * 
     * @param user 用户信息
     * @return 是否更新成功
     */
    boolean updateUser(SysUser user);
    
    /**
     * 删除用户（逻辑删除）
     * 
     * @param userId 用户ID
     * @return 是否删除成功
     */
    boolean deleteUser(Long userId);
}