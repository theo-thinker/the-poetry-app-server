package com.sakura.poetry.websocket.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 聊天消息实体类
 * 
 * <p>用于封装WebSocket聊天消息的数据结构。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Data
@Schema(description = "聊天消息实体")
public class ChatMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    @Schema(description = "消息ID")
    private String messageId;

    /**
     * 消息类型
     */
    @Schema(description = "消息类型")
    private MessageType type;

    /**
     * 发送者ID
     */
    @Schema(description = "发送者ID")
    private Long senderId;

    /**
     * 发送者昵称
     */
    @Schema(description = "发送者昵称")
    private String senderName;

    /**
     * 接收者ID（私聊时使用）
     */
    @Schema(description = "接收者ID（私聊时使用）")
    private Long receiverId;

    /**
     * 群组ID（群聊时使用）
     */
    @Schema(description = "群组ID（群聊时使用）")
    private Long groupId;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容")
    private String content;

    /**
     * 发送时间
     */
    @Schema(description = "发送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime timestamp;

    /**
     * 消息状态
     */
    @Schema(description = "消息状态")
    private MessageStatus status;

    /**
     * 构造函数
     */
    public ChatMessage() {
        this.timestamp = LocalDateTime.now();
        this.status = MessageStatus.SENT;
    }

    /**
     * 构造函数
     * 
     * @param type 消息类型
     * @param senderId 发送者ID
     * @param content 消息内容
     */
    public ChatMessage(MessageType type, Long senderId, String content) {
        this();
        this.type = type;
        this.senderId = senderId;
        this.content = content;
    }
}