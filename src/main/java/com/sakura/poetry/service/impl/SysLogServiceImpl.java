package com.sakura.poetry.service.impl;

import com.sakura.poetry.entity.SysLog;
import com.sakura.poetry.mapper.SysLogMapper;
import com.sakura.poetry.service.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统日志服务实现类
 * 
 * <p>系统日志业务逻辑实现类，实现日志相关的业务操作方法。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
    
    @Autowired
    private SysLogMapper logMapper;
    
    @Override
    public List<SysLog> getLogList(SysLog log, int offset, int limit) {
        return logMapper.selectLogList(log, offset, limit);
    }
    
    @Override
    public List<SysLog> getLogByUserId(Long userId, int limit) {
        return logMapper.selectByUserId(userId, limit);
    }
    
    @Override
    public List<SysLog> getLogByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return logMapper.selectByTimeRange(startTime, endTime);
    }
    
    @Override
    public boolean createLog(SysLog sysLog) {
        return this.save(sysLog);
    }
    
    @Override
    public boolean deleteLog(Long logId) {
        return this.removeById(logId);
    }
}