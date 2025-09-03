package com.sakura.poetry.service;

import com.sakura.poetry.dto.FileUploadResponseDTO;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Minio服务类
 * 
 * <p>提供文件上传、下载、删除等操作的业务逻辑处理。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    /**
     * 上传文件
     * 
     * @param file 文件
     * @param objectName 对象名称
     * @return 文件上传响应DTO
     * @throws Exception 异常
     */
    public FileUploadResponseDTO uploadFile(MultipartFile file, String objectName) throws Exception {
        try {
            // 检查存储桶是否存在，不存在则创建
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            log.info("文件上传成功: bucket={}, object={}", bucketName, objectName);
            
            // 获取文件访问URL
            String url = getFileUrl(objectName);
            
            // 创建响应DTO
            FileUploadResponseDTO response = new FileUploadResponseDTO(
                    objectName,
                    url,
                    file.getSize(),
                    file.getContentType()
            );
            
            return response;
        } catch (Exception e) {
            log.error("文件上传失败: bucket={}, object={}", bucketName, objectName, e);
            throw e;
        }
    }

    /**
     * 上传文件（使用输入流）
     * 
     * @param inputStream 文件输入流
     * @param objectName 对象名称
     * @param contentType 内容类型
     * @return 文件上传响应DTO
     * @throws Exception 异常
     */
    public FileUploadResponseDTO uploadFile(InputStream inputStream, String objectName, String contentType) throws Exception {
        try {
            // 检查存储桶是否存在，不存在则创建
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // 获取输入流大小
            int available = inputStream.available();
            
            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, available, -1)
                            .contentType(contentType)
                            .build()
            );

            log.info("文件上传成功: bucket={}, object={}", bucketName, objectName);
            
            // 获取文件访问URL
            String url = getFileUrl(objectName);
            
            // 创建响应DTO
            FileUploadResponseDTO response = new FileUploadResponseDTO(
                    objectName,
                    url,
                    (long) available,
                    contentType
            );
            
            return response;
        } catch (Exception e) {
            log.error("文件上传失败: bucket={}, object={}", bucketName, objectName, e);
            throw e;
        }
    }

    /**
     * 下载文件
     * 
     * @param objectName 对象名称
     * @return 文件输入流
     * @throws Exception 异常
     */
    public InputStream downloadFile(String objectName) throws Exception {
        try {
            GetObjectResponse response = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );

            log.info("文件下载成功: bucket={}, object={}", bucketName, objectName);
            return response;
        } catch (Exception e) {
            log.error("文件下载失败: bucket={}, object={}", bucketName, objectName, e);
            throw e;
        }
    }

    /**
     * 删除文件
     * 
     * @param objectName 对象名称
     * @throws Exception 异常
     */
    public void deleteFile(String objectName) throws Exception {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );

            log.info("文件删除成功: bucket={}, object={}", bucketName, objectName);
        } catch (Exception e) {
            log.error("文件删除失败: bucket={}, object={}", bucketName, objectName, e);
            throw e;
        }
    }

    /**
     * 获取文件访问URL
     * 
     * @param objectName 对象名称
     * @return 文件访问URL
     * @throws Exception 异常
     */
    public String getFileUrl(String objectName) throws Exception {
        try {
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(7, TimeUnit.DAYS) // 7天有效期
                            .build()
            );

            log.info("获取文件URL成功: bucket={}, object={}", bucketName, objectName);
            return url;
        } catch (Exception e) {
            log.error("获取文件URL失败: bucket={}, object={}", bucketName, objectName, e);
            throw e;
        }
    }

    /**
     * 检查文件是否存在
     * 
     * @param objectName 对象名称
     * @return 是否存在
     */
    public boolean fileExists(String objectName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取文件信息
     * 
     * @param objectName 对象名称
     * @return 文件信息
     * @throws Exception 异常
     */
    public StatObjectResponse getFileInfo(String objectName) throws Exception {
        try {
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );

            log.info("获取文件信息成功: bucket={}, object={}", bucketName, objectName);
            return stat;
        } catch (Exception e) {
            log.error("获取文件信息失败: bucket={}, object={}", bucketName, objectName, e);
            throw e;
        }
    }
}