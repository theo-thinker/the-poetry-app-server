package com.sakura.poetry.service.impl;

import com.sakura.poetry.entity.Poet;
import com.sakura.poetry.mapper.PoetMapper;
import com.sakura.poetry.service.PoetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 诗人服务实现类
 * 
 * <p>诗人业务逻辑实现类，实现诗人相关的业务操作方法。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Service
public class PoetServiceImpl extends ServiceImpl<PoetMapper, Poet> implements PoetService {
    
    @Autowired
    private PoetMapper poetMapper;
    
    @Override
    public List<Poet> getPoetByPoetName(String poetName) {
        return poetMapper.selectByPoetName(poetName);
    }
    
    @Override
    public List<Poet> getPoetList(Poet poet) {
        return poetMapper.selectPoetList(poet);
    }
    
    @Override
    public List<Poet> getPoetByDynastyId(Long dynastyId) {
        return poetMapper.selectByDynastyId(dynastyId);
    }
    
    @Override
    public boolean incrementViewCount(Long poetId) {
        return poetMapper.incrementViewCount(poetId) > 0;
    }
    
    @Override
    public boolean incrementLikeCount(Long poetId) {
        return poetMapper.incrementLikeCount(poetId) > 0;
    }
    
    @Override
    public boolean createPoet(Poet poet) {
        return this.save(poet);
    }
    
    @Override
    public boolean updatePoet(Poet poet) {
        return this.updateById(poet);
    }
    
    @Override
    public boolean deletePoet(Long poetId) {
        return this.removeById(poetId);
    }
}