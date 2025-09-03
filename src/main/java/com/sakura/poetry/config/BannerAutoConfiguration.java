package com.sakura.poetry.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * Banner自动配置类
 * 
 * <p>用于自动配置自定义Banner功能。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "app.banner", name = "enabled", havingValue = "true", matchIfMissing = true)
public class BannerAutoConfiguration {
    
    /**
     * 注册自定义Banner
     * 
     * @param bannerConfig Banner配置
     * @return 自定义Banner实例
     */
    @Bean
    public CustomBanner customBanner(BannerConfig bannerConfig) {
        return new CustomBanner(bannerConfig);
    }
    
    /**
     * 设置SpringApplication使用自定义Banner
     * 
     * @param springApplication Spring应用
     * @param customBanner 自定义Banner
     */
    @Bean
    public BannerInitializer bannerInitializer(SpringApplication springApplication, CustomBanner customBanner) {
        return new BannerInitializer(springApplication, customBanner);
    }
}