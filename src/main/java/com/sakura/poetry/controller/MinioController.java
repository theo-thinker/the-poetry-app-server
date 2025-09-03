package com.sakura.poetry.controller;

import com.sakura.poetry.common.result.Result;
import com.sakura.poetry.dto.FileUploadResponseDTO;
import com.sakura.poetry.service.FileValidationService;
import com.sakura.poetry.service.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Minio控制器
 * 
 * <p>提供文件上传、下载、删除等操作的HTTP接口。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
@RestController
@RequestMapping("/api/minio")
@Tag(name = "Minio文件管理", description = "Minio文件上传、下载、删除相关接口")
public class MinioController {

    @Autowired
    private MinioService minioService;
    
    @Autowired
    private FileValidationService fileValidationService;

    /**
     * 上传文件
     * 
     * @param file 文件
     * @param objectName 对象名称（可选）
     * @return 文件上传响应DTO
     */
    @PostMapping("/upload")
    @Operation(summary = "上传文件", description = "上传文件到Minio存储")
    public Result<FileUploadResponseDTO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "objectName", required = false) String objectName) {
        try {
            // 验证文件
            FileValidationService.ValidationResult validationResult = fileValidationService.validateFile(file);
            if (!validationResult.isValid()) {
                return Result.error(400, "文件验证失败: " + validationResult.getErrorMessage());
            }
            
            // 如果未指定对象名称，则使用原始文件名
            if (objectName == null || objectName.isEmpty()) {
                objectName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            }

            FileUploadResponseDTO response = minioService.uploadFile(file, objectName);
            log.info("文件上传成功: originalName={}, objectName={}", file.getOriginalFilename(), objectName);
            return Result.success(response);
        } catch (Exception e) {
            log.error("文件上传失败: originalName={}", file.getOriginalFilename(), e);
            return Result.error(500, "文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 下载文件
     * 
     * @param objectName 对象名称
     * @return 文件下载URL
     */
    @GetMapping("/download")
    @Operation(summary = "获取文件下载链接", description = "获取文件的临时下载链接")
    public Result<String> downloadFile(@RequestParam("objectName") String objectName) {
        try {
            if (!minioService.fileExists(objectName)) {
                return Result.error(404, "文件不存在");
            }

            String url = minioService.getFileUrl(objectName);
            log.info("获取文件下载链接成功: objectName={}", objectName);
            return Result.success(url);
        } catch (Exception e) {
            log.error("获取文件下载链接失败: objectName={}", objectName, e);
            return Result.error(500, "获取文件下载链接失败: " + e.getMessage());
        }
    }

    /**
     * 删除文件
     * 
     * @param objectName 对象名称
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除文件", description = "从Minio存储中删除文件")
    public Result<String> deleteFile(@RequestParam("objectName") String objectName) {
        try {
            if (!minioService.fileExists(objectName)) {
                return Result.error(404, "文件不存在");
            }

            minioService.deleteFile(objectName);
            log.info("文件删除成功: objectName={}", objectName);
            return Result.success("文件删除成功");
        } catch (Exception e) {
            log.error("文件删除失败: objectName={}", objectName, e);
            return Result.error(500, "文件删除失败: " + e.getMessage());
        }
    }

    /**
     * 检查文件是否存在
     * 
     * @param objectName 对象名称
     * @return 是否存在
     */
    @GetMapping("/exists")
    @Operation(summary = "检查文件是否存在", description = "检查指定文件是否存在于Minio存储中")
    public Result<Boolean> fileExists(@RequestParam("objectName") String objectName) {
        try {
            boolean exists = minioService.fileExists(objectName);
            log.info("检查文件是否存在: objectName={}, exists={}", objectName, exists);
            return Result.success(exists);
        } catch (Exception e) {
            log.error("检查文件是否存在失败: objectName={}", objectName, e);
            return Result.error(500, "检查文件是否存在失败: " + e.getMessage());
        }
    }

    /**
     * 获取文件信息
     * 
     * @param objectName 对象名称
     * @return 文件信息
     */
    @GetMapping("/info")
    @Operation(summary = "获取文件信息", description = "获取指定文件的详细信息")
    public Result<Object> getFileInfo(@RequestParam("objectName") String objectName) {
        try {
            if (!minioService.fileExists(objectName)) {
                return Result.error(404, "文件不存在");
            }

            Object info = minioService.getFileInfo(objectName);
            log.info("获取文件信息成功: objectName={}", objectName);
            return Result.success(info);
        } catch (Exception e) {
            log.error("获取文件信息失败: objectName={}", objectName, e);
            return Result.error(500, "获取文件信息失败: " + e.getMessage());
        }
    }
}