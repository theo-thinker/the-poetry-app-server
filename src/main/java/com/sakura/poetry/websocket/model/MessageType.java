package com.sakura.poetry.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * WebSocket消息类型枚举
 * 
 * <p>定义WebSocket通信中支持的消息类型。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Getter
@AllArgsConstructor
public enum MessageType {
    
    /**
     * 连接消息
     */
    CONNECT("connect"),
    
    /**
     * 断开连接消息
     */
    DISCONNECT("disconnect"),
    
    /**
     * 心跳消息
     */
    HEARTBEAT("heartbeat"),
    
    /**
     * 私聊消息
     */
    PRIVATE_CHAT("private_chat"),
    
    /**
     * 群聊消息
     */
    GROUP_CHAT("group_chat"),
    
    /**
     * 系统消息
     */
    SYSTEM("system"),
    
    /**
     * 错误消息
     */
    ERROR("error");
    
    /**
     * 消息类型值
     */
    private final String value;
}