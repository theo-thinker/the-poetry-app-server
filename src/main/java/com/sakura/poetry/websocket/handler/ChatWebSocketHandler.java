package com.sakura.poetry.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakura.poetry.websocket.manager.WebSocketSessionManager;
import com.sakura.poetry.websocket.model.ChatMessage;
import com.sakura.poetry.websocket.model.MessageType;
import com.sakura.poetry.websocket.service.ChatService;
import com.sakura.poetry.websocket.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 聊天WebSocket处理器
 * 
 * <p>处理WebSocket连接、消息接收和断开连接等事件。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager sessionManager = new WebSocketSessionManager();
    private final ChatService chatService = new ChatService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 存储会话认证信息的映射表
     * key: sessionId, value: userId
     */
    private final Map<String, Long> sessionAuthMap = new ConcurrentHashMap<>();

    /**
     * 建立WebSocket连接后触发
     * 
     * @param session WebSocket会话
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("WebSocket连接已建立，会话ID: {}", session.getId());
        
        // 从握手属性中获取用户信息
        Long userId = (Long) session.getAttributes().get("userId");
        String username = (String) session.getAttributes().get("username");
        
        if (userId != null && username != null) {
            // 添加会话到管理器
            sessionManager.addSession(session, userId, username, username);
            
            // 将认证信息存储到会话映射中
            sessionAuthMap.put(session.getId(), userId);
            
            // 发送连接成功消息
            ChatMessage connectMessage = new ChatMessage();
            connectMessage.setType(MessageType.CONNECT);
            connectMessage.setContent("连接成功，认证信息已验证");
            connectMessage.setTimestamp(LocalDateTime.now());
            
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(connectMessage)));
            
            log.info("WebSocket连接建立成功，用户ID: {}, 用户名: {}", userId, username);
        } else {
            // 发送认证失败消息
            ChatMessage errorMessage = new ChatMessage();
            errorMessage.setType(MessageType.ERROR);
            errorMessage.setContent("认证失败，请提供有效的JWT令牌");
            errorMessage.setTimestamp(LocalDateTime.now());
            
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(errorMessage)));
            
            // 关闭连接
            session.close();
            
            log.warn("WebSocket连接建立失败，缺少认证信息");
        }
    }

    /**
     * 接收WebSocket消息后触发
     * 
     * @param session WebSocket会话
     * @param message 文本消息
     * @throws Exception 异常
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("接收到消息: {}", payload);
        
        try {
            // 解析消息
            ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
            
            // 检查会话是否已认证
            if (!sessionAuthMap.containsKey(session.getId())) {
                // 发送未认证错误消息
                ChatMessage errorMessage = new ChatMessage();
                errorMessage.setType(MessageType.ERROR);
                errorMessage.setContent("会话未认证，请重新连接");
                errorMessage.setTimestamp(LocalDateTime.now());
                
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(errorMessage)));
                return;
            }
            
            // 根据消息类型处理
            switch (chatMessage.getType()) {
                case HEARTBEAT:
                    handleHeartbeat(session, chatMessage);
                    break;
                case PRIVATE_CHAT:
                    handlePrivateChat(session, chatMessage);
                    break;
                case GROUP_CHAT:
                    handleGroupChat(session, chatMessage);
                    break;
                default:
                    log.warn("未知的消息类型: {}", chatMessage.getType());
                    break;
            }
        } catch (Exception e) {
            log.error("处理消息时发生错误: {}", e.getMessage(), e);
            
            // 发送错误消息
            ChatMessage errorMessage = new ChatMessage();
            errorMessage.setType(MessageType.ERROR);
            errorMessage.setContent("消息处理失败: " + e.getMessage());
            errorMessage.setTimestamp(LocalDateTime.now());
            
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(errorMessage)));
        }
    }

    /**
     * 处理心跳消息
     * 
     * @param session WebSocket会话
     * @param message 聊天消息
     */
    /**
     * 处理心跳消息
     * 
     * @param session WebSocket会话
     * @param message 聊天消息
     */
    private void handleHeartbeat(WebSocketSession session, ChatMessage message) {
        try {
            // 回复心跳消息
            ChatMessage heartbeatReply = new ChatMessage();
            heartbeatReply.setType(MessageType.HEARTBEAT);
            heartbeatReply.setContent("pong");
            heartbeatReply.setTimestamp(LocalDateTime.now());
            
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(heartbeatReply)));
            
            // 更新用户最后活跃时间
            Long userId = sessionManager.getUserIdBySessionId(session.getId());
            if (userId != null) {
                sessionManager.updateLastActiveTime(userId);
            }
        } catch (Exception e) {
            log.error("处理心跳消息时发生错误: {}", e.getMessage(), e);
        }
    }

    /**
     * 处理私聊消息
     * 
     * @param session WebSocket会话
     * @param message 聊天消息
     */
    private void handlePrivateChat(WebSocketSession session, ChatMessage message) {
        try {
            // 保存消息到数据库
            chatService.savePrivateMessage(message);
            
            // 发送给接收者
            WebSocketSession receiverSession = sessionManager.getSessionByUserId(message.getReceiverId());
            if (receiverSession != null && receiverSession.isOpen()) {
                receiverSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            } else {
                log.warn("接收者不在线，用户ID: {}", message.getReceiverId());
                // 可以在这里实现离线消息存储逻辑
            }
        } catch (Exception e) {
            log.error("处理私聊消息时发生错误: {}", e.getMessage(), e);
        }
    }

    /**
     * 处理群聊消息
     * 
     * @param session WebSocket会话
     * @param message 聊天消息
     */
    private void handleGroupChat(WebSocketSession session, ChatMessage message) {
        try {
            // 保存消息到数据库
            chatService.saveGroupMessage(message);
            
            // 发送给群组成员
            sessionManager.sendMessageToGroup(message.getGroupId(), objectMapper.writeValueAsString(message));
        } catch (Exception e) {
            log.error("处理群聊消息时发生错误: {}", e.getMessage(), e);
        }
    }

    /**
     * WebSocket连接关闭后触发
     * 
     * @param session WebSocket会话
     * @param status 关闭状态
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("WebSocket连接已关闭，会话ID: {}, 状态: {}", session.getId(), status);
        
        // 从会话管理器中移除会话
        sessionManager.removeSession(session.getId());
        
        // 从认证映射中移除会话
        sessionAuthMap.remove(session.getId());
    }

    /**
     * WebSocket传输错误时触发
     * 
     * @param session WebSocket会话
     * @param exception 异常
     * @throws Exception 异常
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket传输错误，会话ID: {}", session.getId(), exception);
        
        // 从会话管理器中移除会话
        sessionManager.removeSession(session.getId());
        
        // 从认证映射中移除会话
        sessionAuthMap.remove(session.getId());
    }
}