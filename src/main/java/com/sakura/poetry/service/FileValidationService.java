package com.sakura.poetry.service;

import com.sakura.poetry.config.MinioProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件验证服务类
 * 
 * <p>提供文件上传前的验证功能。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
@Service
public class FileValidationService {

    @Autowired
    private MinioProperties minioProperties;

    /**
     * 验证文件
     * 
     * @param file 文件
     * @return 验证结果
     */
    public ValidationResult validateFile(MultipartFile file) {
        ValidationResult result = new ValidationResult();

        // 检查文件是否为空
        if (file == null || file.isEmpty()) {
            result.setValid(false);
            result.setErrorMessage("文件不能为空");
            return result;
        }

        // 检查文件大小
        if (file.getSize() > minioProperties.getMaxFileSize()) {
            result.setValid(false);
            result.setErrorMessage("文件大小超过限制，最大允许" + formatFileSize(minioProperties.getMaxFileSize()));
            return result;
        }

        // 检查文件类型
        String contentType = file.getContentType();
        if (contentType == null || !isAllowedFileType(contentType)) {
            result.setValid(false);
            result.setErrorMessage("不支持的文件类型: " + contentType);
            return result;
        }

        result.setValid(true);
        return result;
    }

    /**
     * 检查文件类型是否被允许
     * 
     * @param contentType 内容类型
     * @return 是否被允许
     */
    private boolean isAllowedFileType(String contentType) {
        for (String allowedType : minioProperties.getAllowedFileTypes()) {
            if (allowedType.equals(contentType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 格式化文件大小
     * 
     * @param size 文件大小（字节）
     * @return 格式化后的文件大小
     */
    private String formatFileSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", size / (1024.0 * 1024));
        } else {
            return String.format("%.2f GB", size / (1024.0 * 1024 * 1024));
        }
    }

    /**
     * 验证结果类
     */
    public static class ValidationResult {
        private boolean valid;
        private String errorMessage;

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}