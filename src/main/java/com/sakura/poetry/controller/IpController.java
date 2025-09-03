package com.sakura.poetry.controller;

import com.sakura.poetry.common.result.Result;
import com.sakura.poetry.entity.IpInfo;
import com.sakura.poetry.service.IpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * IP控制器
 * 提供IP地址解析和地理位置查询相关的API接口
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/ip")
@RequiredArgsConstructor
@Tag(name = "IP管理", description = "IP地址解析和地理位置查询接口")
public class IpController {
    
    private final IpService ipService;
    
    /**
     * 解析IP地址
     * 
     * @param ip IP地址
     * @return IP信息
     */
    @GetMapping("/parse")
    @Operation(summary = "解析IP地址", description = "根据IP地址解析地理位置信息")
    public Result<IpInfo> parseIp(
            @Parameter(description = "IP地址") 
            @RequestParam @NotBlank(message = "IP地址不能为空") String ip) {
        try {
            IpInfo ipInfo = ipService.parseIp(ip);
            if (ipInfo == null) {
                return Result.notFound("未找到该IP地址的地理位置信息");
            }
            return Result.success("IP地址解析成功", ipInfo);
        } catch (Exception e) {
            log.error("Failed to parse IP address: {}", ip, e);
            return Result.error("IP地址解析失败");
        }
    }
    
    /**
     * 批量解析IP地址
     * 
     * @param ips IP地址列表
     * @return IP信息列表
     */
    @PostMapping("/batch-parse")
    @Operation(summary = "批量解析IP地址", description = "批量解析多个IP地址的地理位置信息")
    public Result<List<IpInfo>> batchParseIps(
            @Parameter(description = "IP地址列表") 
            @RequestBody List<String> ips) {
        try {
            if (ips == null || ips.isEmpty()) {
                return Result.badRequest("IP地址列表不能为空");
            }
            
            List<IpInfo> ipInfos = ipService.parseIps(ips);
            return Result.success("批量解析成功", ipInfos);
        } catch (Exception e) {
            log.error("Failed to batch parse IP addresses", e);
            return Result.error("批量解析失败");
        }
    }
    
    /**
     * 获取当前客户端IP信息
     * 
     * @param request HTTP请求对象
     * @return 当前客户端IP信息
     */
    @GetMapping("/current")
    @Operation(summary = "获取当前客户端IP信息", description = "获取当前客户端的真实IP地址和地理位置信息")
    public Result<IpInfo> getCurrentIpInfo(HttpServletRequest request) {
        try {
            String realIp = ipService.getRealIpAddress(request);
            if (realIp == null || realIp.isEmpty()) {
                return Result.notFound("无法获取客户端IP地址");
            }
            
            IpInfo ipInfo = ipService.parseIp(realIp);
            if (ipInfo == null) {
                // 即使无法解析地理位置，也返回IP地址
                ipInfo = new IpInfo();
                ipInfo.setOriginalData(realIp);
            }
            
            return Result.success("获取客户端IP信息成功", ipInfo);
        } catch (Exception e) {
            log.error("Failed to get current IP info", e);
            return Result.error("获取客户端IP信息失败");
        }
    }
}