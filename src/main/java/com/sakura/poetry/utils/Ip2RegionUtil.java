package com.sakura.poetry.utils;

import com.sakura.poetry.entity.IpInfo;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * IP地址解析工具类
 * 基于ip2region数据库实现IP地址解析功能
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
@Component
public class Ip2RegionUtil {
    
    private Searcher searcher;
    
    /**
     * 初始化ip2region搜索器
     */
    @PostConstruct
    public void init() {
        try {
            // 从classpath获取ip2region.xdb文件
            ClassPathResource resource = new ClassPathResource("ip2region/ip2region.xdb");
            InputStream inputStream = resource.getInputStream();
            
            // 创建临时文件
            Path tempFile = Files.createTempFile("ip2region", ".xdb");
            FileCopyUtils.copy(inputStream, Files.newOutputStream(tempFile));
            
            // 创建searcher对象
            searcher = Searcher.newWithFileOnly(tempFile.toString());
            log.info("IP2Region searcher initialized successfully");
        } catch (IOException e) {
            log.error("Failed to initialize IP2Region searcher", e);
            throw new RuntimeException("Failed to initialize IP2Region searcher", e);
        }
    }
    
    /**
     * 解析IP地址为地理位置信息
     * 
     * @param ip IP地址
     * @return IP信息对象
     */
    public IpInfo parseIp(String ip) {
        if (searcher == null) {
            log.warn("IP2Region searcher is not initialized");
            return null;
        }
        
        if (ip == null || ip.isEmpty()) {
            log.warn("IP address is null or empty");
            return null;
        }
        
        try {
            // 搜索IP信息
            String region = searcher.search(ip);
            if (region == null || region.isEmpty()) {
                log.warn("No region found for IP: {}", ip);
                return null;
            }
            
            // 解析为IpInfo对象
            return IpInfo.fromRegion(region);
        } catch (Exception e) {
            log.error("Failed to parse IP address: {}", ip, e);
            return null;
        }
    }
    
    /**
     * 获取客户端真实IP地址
     * 
     * @param request HttpServletRequest对象
     * @return 客户端真实IP地址
     */
    /*
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
    */
}