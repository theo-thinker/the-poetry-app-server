package com.sakura.poetry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件上传响应DTO
 * 
 * <p>用于封装文件上传操作的响应数据。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "文件上传响应DTO")
public class FileUploadResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件对象名称
     */
    @Schema(description = "文件对象名称")
    private String objectName;

    /**
     * 文件访问URL
     */
    @Schema(description = "文件访问URL")
    private String url;

    /**
     * 文件大小（字节）
     */
    @Schema(description = "文件大小（字节）")
    private Long size;

    /**
     * 文件类型
     */
    @Schema(description = "文件类型")
    private String contentType;

    /**
     * 上传时间
     */
    @Schema(description = "上传时间")
    private String uploadTime;

    /**
     * 构造函数
     */
    public FileUploadResponseDTO() {
        this.uploadTime = java.time.LocalDateTime.now().toString();
    }

    /**
     * 构造函数
     * 
     * @param objectName 文件对象名称
     * @param url 文件访问URL
     * @param size 文件大小
     * @param contentType 文件类型
     */
    public FileUploadResponseDTO(String objectName, String url, Long size, String contentType) {
        this();
        this.objectName = objectName;
        this.url = url;
        this.size = size;
        this.contentType = contentType;
    }
}