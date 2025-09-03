package com.sakura.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.poetry.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户Mapper接口
 * 
 * <p>系统用户数据访问接口，提供用户相关的数据库操作方法。
 * 继承BaseMapper接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    
    /**
     * 根据用户名查询用户信息
     * 
     * @param username 用户名
     * @return 用户信息
     */
    SysUser selectByUsername(@Param("username") String username);
    
    /**
     * 根据邮箱查询用户信息
     * 
     * @param email 邮箱
     * @return 用户信息
     */
    SysUser selectByEmail(@Param("email") String email);
    
    /**
     * 根据手机号查询用户信息
     * 
     * @param phone 手机号
     * @return 用户信息
     */
    SysUser selectByPhone(@Param("phone") String phone);
    
    /**
     * 查询用户列表（分页）
     * 
     * @param user 查询条件
     * @return 用户列表
     */
    List<SysUser> selectUserList(SysUser user);
    
    /**
     * 更新用户最后登录信息
     * 
     * @param userId 用户ID
     * @param lastLoginIp 最后登录IP
     * @return 影响行数
     */
    int updateLastLoginInfo(@Param("userId") Long userId, @Param("lastLoginIp") String lastLoginIp);
}