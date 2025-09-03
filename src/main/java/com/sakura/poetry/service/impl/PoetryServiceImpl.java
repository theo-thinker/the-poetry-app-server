package com.sakura.poetry.service.impl;

import com.sakura.poetry.entity.Poetry;
import com.sakura.poetry.mapper.PoetryMapper;
import com.sakura.poetry.service.PoetryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 诗词服务实现类
 * 
 * <p>诗词业务逻辑实现类，实现诗词相关的业务操作方法。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Service
public class PoetryServiceImpl extends ServiceImpl<PoetryMapper, Poetry> implements PoetryService {
    
    @Autowired
    private PoetryMapper poetryMapper;
    
    @Override
    public List<Poetry> getPoetryByTitle(String title) {
        return poetryMapper.selectByTitle(title);
    }
    
    @Override
    public List<Poetry> getPoetryList(Poetry poetry) {
        return poetryMapper.selectPoetryList(poetry);
    }
    
    @Override
    public List<Poetry> getHotPoetryList(int limit) {
        return poetryMapper.selectHotPoetryList(limit);
    }
    
    @Override
    public List<Poetry> getFeaturedPoetryList(int limit) {
        return poetryMapper.selectFeaturedPoetryList(limit);
    }
    
    @Override
    public boolean incrementViewCount(Long poetryId) {
        return poetryMapper.incrementViewCount(poetryId) > 0;
    }
    
    @Override
    public boolean incrementLikeCount(Long poetryId) {
        return poetryMapper.incrementLikeCount(poetryId) > 0;
    }
    
    @Override
    public boolean incrementCollectCount(Long poetryId) {
        return poetryMapper.incrementCollectCount(poetryId) > 0;
    }
    
    @Override
    public boolean createPoetry(Poetry poetry) {
        return this.save(poetry);
    }
    
    @Override
    public boolean updatePoetry(Poetry poetry) {
        return this.updateById(poetry);
    }
    
    @Override
    public boolean deletePoetry(Long poetryId) {
        return this.removeById(poetryId);
    }
}