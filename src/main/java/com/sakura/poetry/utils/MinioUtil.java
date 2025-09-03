package com.sakura.poetry.utils;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Minio工具类
 * 
 * <p>提供Minio相关的通用工具方法。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
@Component
public class MinioUtil {

    @Autowired
    private MinioClient minioClient;

    /**
     * 生成唯一的对象名称
     * 
     * @param originalFilename 原始文件名
     * @return 唯一的对象名称
     */
    public static String generateUniqueObjectName(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.lastIndexOf(".") != -1) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return UUID.randomUUID().toString().replace("-", "") + extension;
    }

    /**
     * 根据文件扩展名获取内容类型
     * 
     * @param filename 文件名
     * @return 内容类型
     */
    public static String getContentType(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "application/octet-stream";
        }

        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "bmp":
                return "image/bmp";
            case "webp":
                return "image/webp";
            case "pdf":
                return "application/pdf";
            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "pptx":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "txt":
                return "text/plain";
            case "zip":
                return "application/zip";
            case "rar":
                return "application/x-rar-compressed";
            default:
                return "application/octet-stream";
        }
    }

    /**
     * 验证存储桶名称
     * 
     * @param bucketName 存储桶名称
     * @return 是否有效
     */
    public static boolean isValidBucketName(String bucketName) {
        if (bucketName == null || bucketName.isEmpty()) {
            return false;
        }

        // 存储桶名称必须介于3到63个字符之间
        if (bucketName.length() < 3 || bucketName.length() > 63) {
            return false;
        }

        // 存储桶名称只能包含小写字母、数字、点和连字符
        if (!bucketName.matches("^[a-z0-9.-]+$")) {
            return false;
        }

        // 存储桶名称不能以点或连字符开头或结尾
        if (bucketName.startsWith(".") || bucketName.endsWith(".") ||
            bucketName.startsWith("-") || bucketName.endsWith("-")) {
            return false;
        }

        // 存储桶名称不能包含连续的点
        if (bucketName.contains("..")) {
            return false;
        }

        // 存储桶名称不能是IP地址格式
        if (bucketName.matches("^\\d+\\.\\d+\\.\\d+\\.\\d+$")) {
            return false;
        }

        return true;
    }

    /**
     * 验证对象名称
     * 
     * @param objectName 对象名称
     * @return 是否有效
     */
    public static boolean isValidObjectName(String objectName) {
        if (objectName == null || objectName.isEmpty()) {
            return false;
        }

        // 对象名称不能超过1024个字符
        if (objectName.length() > 1024) {
            return false;
        }

        // 对象名称不能以斜杠开头
        if (objectName.startsWith("/")) {
            return false;
        }

        return true;
    }
}