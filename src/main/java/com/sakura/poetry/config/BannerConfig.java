package com.sakura.poetry.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Banner配置属性类
 * 
 * <p>用于配置自定义Banner的相关属性，支持不同环境使用不同的Banner。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app.banner")
public class BannerConfig {
    
    /**
     * 是否启用自定义Banner
     */
    private boolean enabled = true;
    
    /**
     * 默认Banner文件路径
     */
    private String defaultBanner = "banner.txt";
    
    /**
     * 开发环境Banner文件路径
     */
    private String devBanner = "banner-dev.txt";
    
    /**
     * 生产环境Banner文件路径
     */
    private String prodBanner = "banner-prod.txt";
    
    /**
     * 测试环境Banner文件路径
     */
    private String testBanner = "banner-test.txt";
    
    /**
     * Banner显示模式
     */
    private BannerMode mode = BannerMode.CONSOLE;
    
    /**
     * Banner模式枚举
     */
    public enum BannerMode {
        /**
         * 控制台显示
         */
        CONSOLE,
        
        /**
         * 日志显示
         */
        LOG,
        
        /**
         * both
         */
        BOTH
    }
}