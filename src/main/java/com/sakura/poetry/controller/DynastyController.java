package com.sakura.poetry.controller;

import com.sakura.poetry.entity.Dynasty;
import com.sakura.poetry.service.DynastyService;
import com.sakura.poetry.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 朝代控制器
 * 
 * <p>朝代相关的API接口控制器，处理朝代相关的HTTP请求。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@RestController
@RequestMapping("/api/dynasty")
@Tag(name = "朝代管理", description = "朝代相关的API接口")
public class DynastyController {
    
    @Autowired
    private DynastyService dynastyService;
    
    /**
     * 根据编码查询朝代信息
     * 
     * @param dynastyCode 朝代编码
     * @return 朝代信息
     */
    @GetMapping("/code/{dynastyCode}")
    @Operation(summary = "根据编码查询朝代信息")
    public Result<Dynasty> getDynastyByCode(@PathVariable String dynastyCode) {
        Dynasty dynasty = dynastyService.getDynastyByCode(dynastyCode);
        return Result.success(dynasty);
    }
    
    /**
     * 查询启用的朝代列表
     * 
     * @return 朝代列表
     */
    @GetMapping("/enabled")
    @Operation(summary = "查询启用的朝代列表")
    public Result<List<Dynasty>> getEnabledDynastyList() {
        List<Dynasty> dynastyList = dynastyService.getEnabledDynastyList();
        return Result.success(dynastyList);
    }
    
    /**
     * 查询朝代列表（分页）
     * 
     * @param dynasty 查询条件
     * @return 朝代列表
     */
    @PostMapping("/list")
    @Operation(summary = "查询朝代列表")
    public Result<List<Dynasty>> getDynastyList(@RequestBody Dynasty dynasty) {
        List<Dynasty> dynastyList = dynastyService.getDynastyList(dynasty);
        return Result.success(dynastyList);
    }
    
    /**
     * 创建朝代
     * 
     * @param dynasty 朝代信息
     * @return 是否创建成功
     */
    @PostMapping("/create")
    @Operation(summary = "创建朝代")
    public Result<Boolean> createDynasty(@RequestBody Dynasty dynasty) {
        boolean result = dynastyService.createDynasty(dynasty);
        return Result.success(result);
    }
    
    /**
     * 更新朝代信息
     * 
     * @param dynasty 朝代信息
     * @return 是否更新成功
     */
    @PutMapping("/update")
    @Operation(summary = "更新朝代信息")
    public Result<Boolean> updateDynasty(@RequestBody Dynasty dynasty) {
        boolean result = dynastyService.updateDynasty(dynasty);
        return Result.success(result);
    }
    
    /**
     * 删除朝代（逻辑删除）
     * 
     * @param dynastyId 朝代ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{dynastyId}")
    @Operation(summary = "删除朝代")
    public Result<Boolean> deleteDynasty(@PathVariable Long dynastyId) {
        boolean result = dynastyService.deleteDynasty(dynastyId);
        return Result.success(result);
    }
}