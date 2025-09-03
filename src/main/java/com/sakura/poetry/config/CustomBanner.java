package com.sakura.poetry.config;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * 自定义Banner类
 * 
 * <p>支持根据不同环境显示不同的Banner，实现Banner的配置化管理。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
public class CustomBanner implements Banner {
    
    private final BannerConfig bannerConfig;
    
    public CustomBanner(BannerConfig bannerConfig) {
        this.bannerConfig = bannerConfig;
    }
    
    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        if (!bannerConfig.isEnabled()) {
            return;
        }
        
        String bannerPath = getBannerPath(environment);
        if (bannerPath != null) {
            printBannerFromFile(bannerPath, out);
        } else {
            // 如果没有找到特定环境的Banner，则使用默认Banner
            printBannerFromFile(bannerConfig.getDefaultBanner(), out);
        }
    }
    
    /**
     * 根据环境获取Banner文件路径
     * 
     * @param environment 环境信息
     * @return Banner文件路径
     */
    private String getBannerPath(Environment environment) {
        String[] activeProfiles = environment.getActiveProfiles();
        
        if (activeProfiles.length > 0) {
            String profile = activeProfiles[0];
            switch (profile.toLowerCase()) {
                case "dev":
                case "development":
                    return bannerConfig.getDevBanner();
                case "prod":
                case "production":
                    return bannerConfig.getProdBanner();
                case "test":
                case "testing":
                    return bannerConfig.getTestBanner();
                default:
                    return null;
            }
        }
        
        // 如果没有激活的profile，检查默认profile
        String[] defaultProfiles = environment.getDefaultProfiles();
        if (defaultProfiles.length > 0) {
            String profile = defaultProfiles[0];
            switch (profile.toLowerCase()) {
                case "dev":
                case "development":
                    return bannerConfig.getDevBanner();
                case "prod":
                case "production":
                    return bannerConfig.getProdBanner();
                case "test":
                case "testing":
                    return bannerConfig.getTestBanner();
                default:
                    return null;
            }
        }
        
        return null;
    }
    
    /**
     * 从文件打印Banner
     * 
     * @param bannerPath Banner文件路径
     * @param out 输出流
     */
    private void printBannerFromFile(String bannerPath, PrintStream out) {
        try {
            Resource resource = new ClassPathResource(bannerPath);
            if (resource.exists()) {
                try (InputStream inputStream = resource.getInputStream()) {
                    String bannerContent = new String(FileCopyUtils.copyToByteArray(inputStream));
                    out.println(bannerContent);
                }
            } else {
                // 如果指定的Banner文件不存在，使用默认Banner
                if (!bannerPath.equals(bannerConfig.getDefaultBanner())) {
                    printBannerFromFile(bannerConfig.getDefaultBanner(), out);
                }
            }
        } catch (IOException e) {
            // 如果读取文件出错，打印简单的Banner
            printSimpleBanner(out);
        }
    }
    
    /**
     * 打印简单的Banner
     * 
     * @param out 输出流
     */
    private void printSimpleBanner(PrintStream out) {
        out.println("==========================================");
        out.println("  企业级诗词APP后端服务");
        out.println("  The Poetry App Server");
        out.println("  Version: 1.0.0");
        out.println("  Author: Sakura Huang");
        out.println("==========================================");
        out.println();
    }
}