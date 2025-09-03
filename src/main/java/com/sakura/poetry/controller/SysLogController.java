package com.sakura.poetry.controller;

import com.sakura.poetry.entity.SysLog;
import com.sakura.poetry.service.SysLogService;
import com.sakura.poetry.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统日志控制器
 * 
 * <p>系统日志相关的API接口控制器，处理日志相关的HTTP请求。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestController
@RequestMapping("/api/log")
@Tag(name = "系统日志管理", description = "系统日志相关的API接口")
public class SysLogController {
    
    @Autowired
    private SysLogService logService;
    
    /**
     * 查询日志列表（分页）
     * 
     * @param log 查询条件
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 日志列表
     */
    @PostMapping("/list")
    @Operation(summary = "查询日志列表")
    public Result<List<SysLog>> getLogList(@RequestBody SysLog log, 
                                          @RequestParam(defaultValue = "0") int offset, 
                                          @RequestParam(defaultValue = "10") int limit) {
        List<SysLog> logList = logService.getLogList(log, offset, limit);
        return Result.success(logList);
    }
    
    /**
     * 根据用户ID查询操作日志
     * 
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 操作日志列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "根据用户ID查询操作日志")
    public Result<List<SysLog>> getLogByUserId(@PathVariable Long userId, 
                                              @RequestParam(defaultValue = "10") int limit) {
        List<SysLog> logList = logService.getLogByUserId(userId, limit);
        return Result.success(logList);
    }
    
    /**
     * 查询指定时间范围内的日志
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 日志列表
     */
    @GetMapping("/time")
    @Operation(summary = "查询指定时间范围内的日志")
    public Result<List<SysLog>> getLogByTimeRange(@RequestParam String startTime, 
                                                 @RequestParam String endTime) {
        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        List<SysLog> logList = logService.getLogByTimeRange(start, end);
        return Result.success(logList);
    }
    
    /**
     * 创建日志
     * 
     * @param sysLog 日志信息
     * @return 是否创建成功
     */
    @PostMapping("/create")
    @Operation(summary = "创建日志")
    public Result<Boolean> createLog(@RequestBody SysLog sysLog) {
        boolean result = logService.createLog(sysLog);
        return Result.success(result);
    }
    
    /**
     * 删除日志
     * 
     * @param logId 日志ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{logId}")
    @Operation(summary = "删除日志")
    public Result<Boolean> deleteLog(@PathVariable Long logId) {
        boolean result = logService.deleteLog(logId);
        return Result.success(result);
    }
}