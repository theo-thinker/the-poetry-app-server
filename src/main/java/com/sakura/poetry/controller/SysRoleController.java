package com.sakura.poetry.controller;

import com.sakura.poetry.entity.SysRole;
import com.sakura.poetry.service.SysRoleService;
import com.sakura.poetry.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统角色控制器
 * 
 * <p>系统角色相关的API接口控制器，处理角色相关的HTTP请求。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestController
@RequestMapping("/api/role")
@Tag(name = "系统角色管理", description = "系统角色相关的API接口")
public class SysRoleController {
    
    @Autowired
    private SysRoleService roleService;
    
    /**
     * 根据角色编码查询角色信息
     * 
     * @param roleCode 角色编码
     * @return 角色信息
     */
    @GetMapping("/code/{roleCode}")
    @Operation(summary = "根据角色编码查询角色信息")
    public Result<SysRole> getRoleByRoleCode(@PathVariable String roleCode) {
        SysRole role = roleService.getRoleByRoleCode(roleCode);
        return Result.success(role);
    }
    
    /**
     * 查询角色列表（分页）
     * 
     * @param role 查询条件
     * @return 角色列表
     */
    @PostMapping("/list")
    @Operation(summary = "查询角色列表")
    public Result<List<SysRole>> getRoleList(@RequestBody SysRole role) {
        List<SysRole> roleList = roleService.getRoleList(role);
        return Result.success(roleList);
    }
    
    /**
     * 查询用户拥有的角色列表
     * 
     * @param userId 用户ID
     * @return 角色列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "查询用户拥有的角色列表")
    public Result<List<SysRole>> getRolesByUserId(@PathVariable Long userId) {
        List<SysRole> roleList = roleService.getRolesByUserId(userId);
        return Result.success(roleList);
    }
    
    /**
     * 创建角色
     * 
     * @param sysRole 角色信息
     * @return 是否创建成功
     */
    @PostMapping("/create")
    @Operation(summary = "创建角色")
    public Result<Boolean> createRole(@RequestBody SysRole sysRole) {
        boolean result = roleService.createRole(sysRole);
        return Result.success(result);
    }
    
    /**
     * 更新角色信息
     * 
     * @param sysRole 角色信息
     * @return 是否更新成功
     */
    @PutMapping("/update")
    @Operation(summary = "更新角色信息")
    public Result<Boolean> updateRole(@RequestBody SysRole sysRole) {
        boolean result = roleService.updateRole(sysRole);
        return Result.success(result);
    }
    
    /**
     * 删除角色（逻辑删除）
     * 
     * @param roleId 角色ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{roleId}")
    @Operation(summary = "删除角色")
    public Result<Boolean> deleteRole(@PathVariable Long roleId) {
        boolean result = roleService.deleteRole(roleId);
        return Result.success(result);
    }
}