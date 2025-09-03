package com.sakura.poetry.service;

import com.sakura.poetry.entity.IpInfo;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * IP服务接口
 * 提供IP地址解析相关的业务逻辑接口
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public interface IpService {
    
    /**
     * 解析IP地址为地理位置信息
     * 
     * @param ip IP地址
     * @return IP信息对象
     */
    IpInfo parseIp(String ip);
    
    /**
     * 批量解析IP地址
     * 
     * @param ips IP地址列表
     * @return IP信息对象列表
     */
    List<IpInfo> parseIps(List<String> ips);
    
    /**
     * 获取客户端真实IP地址
     * 
     * @param request HTTP请求对象
     * @return 客户端真实IP地址
     */
    String getRealIpAddress(HttpServletRequest request);
}