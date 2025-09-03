package com.sakura.poetry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.poetry.entity.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统日志Mapper接口
 * 
 * <p>系统日志数据访问接口，提供日志相关的数据库操作方法。
 * 继承BaseMapper接口，拥有基础的CRUD操作能力。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface SysLogMapper extends BaseMapper<SysLog> {
    
    /**
     * 查询日志列表（分页）
     * 
     * @param log 查询条件
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 日志列表
     */
    List<SysLog> selectLogList(@Param("log") SysLog log, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 根据用户ID查询操作日志
     * 
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 操作日志列表
     */
    List<SysLog> selectByUserId(@Param("userId") Long userId, @Param("limit") int limit);
    
    /**
     * 查询指定时间范围内的日志
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 日志列表
     */
    List<SysLog> selectByTimeRange(@Param("startTime") java.time.LocalDateTime startTime, @Param("endTime") java.time.LocalDateTime endTime);
}