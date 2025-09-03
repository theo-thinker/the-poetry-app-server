package com.sakura.poetry.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息状态枚举
 * 
 * <p>定义聊天消息的状态。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Getter
@AllArgsConstructor
public enum MessageStatus {
    
    /**
     * 已发送
     */
    SENT("sent"),
    
    /**
     * 已接收
     */
    RECEIVED("received"),
    
    /**
     * 已读
     */
    READ("read"),
    
    /**
     * 发送失败
     */
    FAILED("failed");
    
    /**
     * 状态值
     */
    private final String value;
}