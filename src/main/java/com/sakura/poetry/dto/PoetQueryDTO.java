package com.sakura.poetry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 诗人查询DTO
 * 
 * <p>用于诗人查询条件的数据传输对象。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "诗人查询条件DTO")
public class PoetQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 诗人姓名
     */
    @Schema(description = "诗人姓名")
    private String poetName;

    /**
     * 别名
     */
    @Schema(description = "别名")
    private String alias;

    /**
     * 朝代ID
     */
    @Schema(description = "朝代ID")
    private Long dynastyId;

    /**
     * 出生地
     */
    @Schema(description = "出生地")
    private String birthplace;

    /**
     * 页码
     */
    @Schema(description = "页码", example = "1")
    private Integer page = 1;

    /**
     * 每页大小
     */
    @Schema(description = "每页大小", example = "10")
    private Integer size = 10;
}