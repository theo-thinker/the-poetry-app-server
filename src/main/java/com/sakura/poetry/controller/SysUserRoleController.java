package com.sakura.poetry.controller;

import com.sakura.poetry.entity.SysUserRole;
import com.sakura.poetry.service.SysUserRoleService;
import com.sakura.poetry.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户角色关联控制器
 * 
 * <p>用户角色关联相关的API接口控制器，处理用户角色关系相关的HTTP请求。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestController
@RequestMapping("/api/user-role")
@Tag(name = "用户角色关联管理", description = "用户角色关联相关的API接口")
public class SysUserRoleController {
    
    @Autowired
    private SysUserRoleService userRoleService;
    
    /**
     * 根据用户ID查询用户角色关联列表
     * 
     * @param userId 用户ID
     * @return 用户角色关联列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "根据用户ID查询用户角色关联列表")
    public Result<List<SysUserRole>> getUserRoleByUserId(@PathVariable Long userId) {
        List<SysUserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        return Result.success(userRoleList);
    }
    
    /**
     * 根据角色ID查询用户角色关联列表
     * 
     * @param roleId 角色ID
     * @return 用户角色关联列表
     */
    @GetMapping("/role/{roleId}")
    @Operation(summary = "根据角色ID查询用户角色关联列表")
    public Result<List<SysUserRole>> getUserRoleByRoleId(@PathVariable Long roleId) {
        List<SysUserRole> userRoleList = userRoleService.getUserRoleByRoleId(roleId);
        return Result.success(userRoleList);
    }
    
    /**
     * 删除用户的所有角色关联
     * 
     * @param userId 用户ID
     * @return 是否删除成功
     */
    @DeleteMapping("/user/{userId}")
    @Operation(summary = "删除用户的所有角色关联")
    public Result<Boolean> deleteByUserId(@PathVariable Long userId) {
        boolean result = userRoleService.deleteByUserId(userId);
        return Result.success(result);
    }
    
    /**
     * 删除角色的所有用户关联
     * 
     * @param roleId 角色ID
     * @return 是否删除成功
     */
    @DeleteMapping("/role/{roleId}")
    @Operation(summary = "删除角色的所有用户关联")
    public Result<Boolean> deleteByRoleId(@PathVariable Long roleId) {
        boolean result = userRoleService.deleteByRoleId(roleId);
        return Result.success(result);
    }
    
    /**
     * 创建用户角色关联
     * 
     * @param sysUserRole 用户角色关联信息
     * @return 是否创建成功
     */
    @PostMapping("/create")
    @Operation(summary = "创建用户角色关联")
    public Result<Boolean> createUserRole(@RequestBody SysUserRole sysUserRole) {
        boolean result = userRoleService.createUserRole(sysUserRole);
        return Result.success(result);
    }
    
    /**
     * 删除用户角色关联
     * 
     * @param userRoleId 用户角色关联ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{userRoleId}")
    @Operation(summary = "删除用户角色关联")
    public Result<Boolean> deleteUserRole(@PathVariable Long userRoleId) {
        boolean result = userRoleService.deleteUserRole(userRoleId);
        return Result.success(result);
    }
}