package com.sakura.poetry.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * IP信息实体类
 * 用于封装IP地址解析后的地理位置信息
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@NoArgsConstructor
@Schema(description = "IP信息实体")
public class IpInfo implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 国家
     */
    @Schema(description = "国家")
    private String country;
    
    /**
     * 区域
     */
    @Schema(description = "区域")
    private String region;
    
    /**
     * 省份
     */
    @Schema(description = "省份")
    private String province;
    
    /**
     * 城市
     */
    @Schema(description = "城市")
    private String city;
    
    /**
     * 运营商
     */
    @Schema(description = "运营商")
    private String isp;
    
    /**
     * 原始数据
     */
    @Schema(description = "原始数据")
    private String originalData;
    
    /**
     * 从ip2region格式化字符串解析IP信息
     * 
     * @param region ip2region格式化字符串，格式为"国家|区域|省份|城市|运营商"
     * @return IP信息对象
     */
    public static IpInfo fromRegion(String region) {
        if (region == null || region.isEmpty()) {
            return null;
        }
        
        IpInfo ipInfo = new IpInfo();
        ipInfo.setOriginalData(region);
        
        // ip2region格式: 国家|区域|省份|城市|运营商
        String[] parts = region.split("\\|");
        if (parts.length >= 1 && !"0".equals(parts[0])) {
            ipInfo.setCountry(parts[0]);
        }
        if (parts.length >= 2 && !"0".equals(parts[1])) {
            ipInfo.setRegion(parts[1]);
        }
        if (parts.length >= 3 && !"0".equals(parts[2])) {
            ipInfo.setProvince(parts[2]);
        }
        if (parts.length >= 4 && !"0".equals(parts[3])) {
            ipInfo.setCity(parts[3]);
        }
        if (parts.length >= 5 && !"0".equals(parts[4])) {
            ipInfo.setIsp(parts[4]);
        }
        
        return ipInfo;
    }
}