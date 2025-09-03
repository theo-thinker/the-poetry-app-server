package com.sakura.poetry.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Minio配置属性类
 * 
 * <p>用于封装Minio相关的配置属性。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    /**
     * Minio服务器地址
     */
    private String endpoint = "http://localhost:9000";

    /**
     * 访问密钥
     */
    private String accessKey = "minioadmin";

    /**
     * 秘密密钥
     */
    private String secretKey = "minioadmin";

    /**
     * 默认存储桶名称
     */
    private String bucketName = "poetry-app";

    /**
     * 连接超时时间（毫秒）
     */
    private int connectTimeout = 10000;

    /**
     * 读取超时时间（毫秒）
     */
    private int readTimeout = 30000;

    /**
     * 写入超时时间（毫秒）
     */
    private int writeTimeout = 30000;

    /**
     * 是否启用HTTPS
     */
    private boolean secure = false;

    /**
     * 文件上传的最大大小（字节）
     */
    private long maxFileSize = 104857600L; // 100MB

    /**
     * 允许的文件类型
     */
    private String[] allowedFileTypes = {
        "image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp",
        "application/pdf",
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "application/vnd.ms-powerpoint",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation",
        "text/plain",
        "application/zip",
        "application/x-rar-compressed"
    };

    /**
     * 临时URL的有效期（秒）
     */
    private int urlExpirySeconds = 604800; // 7天
}