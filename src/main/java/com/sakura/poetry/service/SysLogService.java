package com.sakura.poetry.service;

import com.sakura.poetry.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统日志服务接口
 * 
 * <p>系统日志业务逻辑接口，定义日志相关的业务操作方法。
 * 继承IService接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface SysLogService extends IService<SysLog> {
    
    /**
     * 查询日志列表（分页）
     * 
     * @param log 查询条件
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 日志列表
     */
    List<SysLog> getLogList(SysLog log, int offset, int limit);
    
    /**
     * 根据用户ID查询操作日志
     * 
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 操作日志列表
     */
    List<SysLog> getLogByUserId(Long userId, int limit);
    
    /**
     * 查询指定时间范围内的日志
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 日志列表
     */
    List<SysLog> getLogByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 创建日志
     * 
     * @param sysLog 日志信息
     * @return 是否创建成功
     */
    boolean createLog(SysLog sysLog);
    
    /**
     * 删除日志
     * 
     * @param logId 日志ID
     * @return 是否删除成功
     */
    boolean deleteLog(Long logId);
}