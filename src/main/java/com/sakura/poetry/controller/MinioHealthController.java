package com.sakura.poetry.controller;

import com.sakura.poetry.common.result.Result;
import io.minio.MinioClient;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Minio健康检查控制器
 * 
 * <p>提供Minio服务健康检查的HTTP接口。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
@RestController
@RequestMapping("/api/minio/health")
@Tag(name = "Minio健康检查", description = "Minio服务健康检查相关接口")
public class MinioHealthController {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    /**
     * 检查Minio服务是否可用
     * 
     * @return 健康检查结果
     */
    @GetMapping
    @Operation(summary = "Minio健康检查", description = "检查Minio服务是否可用")
    public Result<String> healthCheck() {
        try {
            // 尝试获取存储桶信息
            minioClient.bucketExists(io.minio.BucketExistsArgs.builder().bucket(bucketName).build());
            
            log.info("Minio健康检查成功");
            return Result.success("Minio服务正常运行");
        } catch (Exception e) {
            log.error("Minio健康检查失败", e);
            return Result.error(500, "Minio服务不可用: " + e.getMessage());
        }
    }

    /**
     * 检查存储桶是否存在
     * 
     * @return 存储桶状态
     */
    @GetMapping("/bucket")
    @Operation(summary = "存储桶状态检查", description = "检查默认存储桶是否存在")
    public Result<String> bucketCheck() {
        try {
            boolean exists = minioClient.bucketExists(io.minio.BucketExistsArgs.builder().bucket(bucketName).build());
            
            if (exists) {
                log.info("存储桶 {} 存在", bucketName);
                return Result.success("存储桶 " + bucketName + " 存在");
            } else {
                log.warn("存储桶 {} 不存在", bucketName);
                return Result.success("存储桶 " + bucketName + " 不存在");
            }
        } catch (Exception e) {
            log.error("存储桶检查失败", e);
            return Result.error(500, "存储桶检查失败: " + e.getMessage());
        }
    }
}