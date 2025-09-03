package com.sakura.poetry.service.impl;

import com.sakura.poetry.entity.IpInfo;
import com.sakura.poetry.service.IpService;
import com.sakura.poetry.utils.Ip2RegionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * IP服务实现类
 * 实现IP地址解析相关的业务逻辑
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IpServiceImpl implements IpService {
    
    private final Ip2RegionUtil ip2RegionUtil;
    
    /**
     * 解析IP地址为地理位置信息
     * 
     * @param ip IP地址
     * @return IP信息对象
     */
    @Override
    public IpInfo parseIp(String ip) {
        if (ip == null || ip.isEmpty()) {
            return null;
        }
        
        try {
            return ip2RegionUtil.parseIp(ip);
        } catch (Exception e) {
            log.error("Failed to parse IP address: {}", ip, e);
            return null;
        }
    }
    
    /**
     * 批量解析IP地址
     * 
     * @param ips IP地址列表
     * @return IP信息对象列表
     */
    @Override
    public List<IpInfo> parseIps(List<String> ips) {
        if (ips == null || ips.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<IpInfo> ipInfos = new ArrayList<>();
        for (String ip : ips) {
            IpInfo ipInfo = parseIp(ip);
            ipInfos.add(ipInfo);
        }
        
        return ipInfos;
    }
    
    /**
     * 获取客户端真实IP地址
     * 
     * @param request HTTP请求对象
     * @return 客户端真实IP地址
     */
    @Override
    public String getRealIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        
        return ipAddress;
    }
}