package com.sakura.poetry.config;

import org.springframework.boot.SpringApplication;

import jakarta.annotation.PostConstruct;

/**
 * Banner初始化器
 * 
 * <p>用于在应用启动时设置自定义Banner。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public class BannerInitializer {
    
    private final SpringApplication springApplication;
    private final CustomBanner customBanner;
    
    public BannerInitializer(SpringApplication springApplication, CustomBanner customBanner) {
        this.springApplication = springApplication;
        this.customBanner = customBanner;
    }
    
    /**
     * 初始化Banner配置
     */
    @PostConstruct
    public void initBanner() {
        if (springApplication != null && customBanner != null) {
            springApplication.setBanner(customBanner);
        }
    }
}