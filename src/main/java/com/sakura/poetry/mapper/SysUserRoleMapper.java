package com.sakura.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.poetry.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色关联Mapper接口
 * 
 * <p>用户角色关联数据访问接口，提供用户角色关系相关的数据库操作方法。
 * 继承BaseMapper接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    
    /**
     * 根据用户ID查询用户角色关联列表
     * 
     * @param userId 用户ID
     * @return 用户角色关联列表
     */
    List<SysUserRole> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 根据角色ID查询用户角色关联列表
     * 
     * @param roleId 角色ID
     * @return 用户角色关联列表
     */
    List<SysUserRole> selectByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 删除用户的所有角色关联
     * 
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteByUserId(@Param("userId") Long userId);
    
    /**
     * 删除角色的所有用户关联
     * 
     * @param roleId 角色ID
     * @return 影响行数
     */
    int deleteByRoleId(@Param("roleId") Long roleId);
}