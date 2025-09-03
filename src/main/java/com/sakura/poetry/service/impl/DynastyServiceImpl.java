package com.sakura.poetry.service.impl;

import com.sakura.poetry.entity.Dynasty;
import com.sakura.poetry.mapper.DynastyMapper;
import com.sakura.poetry.service.DynastyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 朝代服务实现类
 * 
 * <p>朝代业务逻辑实现类，实现朝代相关的业务操作方法。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Service
public class DynastyServiceImpl extends ServiceImpl<DynastyMapper, Dynasty> implements DynastyService {
    
    @Autowired
    private DynastyMapper dynastyMapper;
    
    @Override
    public Dynasty getDynastyByCode(String dynastyCode) {
        return dynastyMapper.selectByDynastyCode(dynastyCode);
    }
    
    @Override
    public List<Dynasty> getEnabledDynastyList() {
        return dynastyMapper.selectEnabledDynastyList();
    }
    
    @Override
    public List<Dynasty> getDynastyList(Dynasty dynasty) {
        return dynastyMapper.selectDynastyList(dynasty);
    }
    
    @Override
    public boolean createDynasty(Dynasty dynasty) {
        return this.save(dynasty);
    }
    
    @Override
    public boolean updateDynasty(Dynasty dynasty) {
        return this.updateById(dynasty);
    }
    
    @Override
    public boolean deleteDynasty(Long dynastyId) {
        return this.removeById(dynastyId);
    }
}