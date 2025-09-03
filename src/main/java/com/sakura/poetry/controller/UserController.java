package com.sakura.poetry.controller;

import com.sakura.poetry.entity.SysUser;
import com.sakura.poetry.service.UserService;
import com.sakura.poetry.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 * 
 * <p>用户相关的API接口控制器，处理用户相关的HTTP请求。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestController
@RequestMapping("/api/user")
@Tag(name = "用户管理", description = "用户相关的API接口")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 根据用户名查询用户信息
     * 
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/username/{username}")
    @Operation(summary = "根据用户名查询用户信息")
    public Result<SysUser> getUserByUsername(@PathVariable String username) {
        SysUser user = userService.getUserByUsername(username);
        return Result.success(user);
    }
    
    /**
     * 根据邮箱查询用户信息
     * 
     * @param email 邮箱
     * @return 用户信息
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "根据邮箱查询用户信息")
    public Result<SysUser> getUserByEmail(@PathVariable String email) {
        SysUser user = userService.getUserByEmail(email);
        return Result.success(user);
    }
    
    /**
     * 根据手机号查询用户信息
     * 
     * @param phone 手机号
     * @return 用户信息
     */
    @GetMapping("/phone/{phone}")
    @Operation(summary = "根据手机号查询用户信息")
    public Result<SysUser> getUserByPhone(@PathVariable String phone) {
        SysUser user = userService.getUserByPhone(phone);
        return Result.success(user);
    }
    
    /**
     * 查询用户列表（分页）
     * 
     * @param user 查询条件
     * @return 用户列表
     */
    @PostMapping("/list")
    @Operation(summary = "查询用户列表")
    public Result<List<SysUser>> getUserList(@RequestBody SysUser user) {
        List<SysUser> userList = userService.getUserList(user);
        return Result.success(userList);
    }
    
    /**
     * 创建用户
     * 
     * @param user 用户信息
     * @return 是否创建成功
     */
    @PostMapping("/create")
    @Operation(summary = "创建用户")
    public Result<Boolean> createUser(@RequestBody SysUser user) {
        boolean result = userService.createUser(user);
        return Result.success(result);
    }
    
    /**
     * 更新用户信息
     * 
     * @param user 用户信息
     * @return 是否更新成功
     */
    @PutMapping("/update")
    @Operation(summary = "更新用户信息")
    public Result<Boolean> updateUser(@RequestBody SysUser user) {
        boolean result = userService.updateUser(user);
        return Result.success(result);
    }
    
    /**
     * 删除用户（逻辑删除）
     * 
     * @param userId 用户ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{userId}")
    @Operation(summary = "删除用户")
    public Result<Boolean> deleteUser(@PathVariable Long userId) {
        boolean result = userService.deleteUser(userId);
        return Result.success(result);
    }
}