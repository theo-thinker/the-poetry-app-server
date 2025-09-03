package com.sakura.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.poetry.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色Mapper接口
 * 
 * <p>系统角色数据访问接口，提供角色相关的数据库操作方法。
 * 继承BaseMapper接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    
    /**
     * 根据角色编码查询角色信息
     * 
     * @param roleCode 角色编码
     * @return 角色信息
     */
    SysRole selectByRoleCode(@Param("roleCode") String roleCode);
    
    /**
     * 查询角色列表（分页）
     * 
     * @param role 查询条件
     * @return 角色列表
     */
    List<SysRole> selectRoleList(SysRole role);
    
    /**
     * 查询用户拥有的角色列表
     * 
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolesByUserId(@Param("userId") Long userId);
}