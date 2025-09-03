package com.sakura.poetry.websocket.manager;

import com.sakura.poetry.websocket.model.OnlineUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket会话管理器
 * 
 * <p>用于管理所有WebSocket连接的会话和在线用户信息。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
@Component
public class WebSocketSessionManager {

    /**
     * 存储WebSocket会话的映射表
     * key: sessionId, value: WebSocketSession
     */
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    /**
     * 存储在线用户的映射表
     * key: userId, value: OnlineUser
     */
    private final Map<Long, OnlineUser> onlineUsers = new ConcurrentHashMap<>();

    /**
     * 存储用户与会话ID的映射表
     * key: userId, value: sessionId
     */
    private final Map<Long, String> userSessionMap = new ConcurrentHashMap<>();
    
    /**
     * 存储群组成员的映射表
     * key: groupId, value: List<userId>
     */
    private final Map<Long, List<Long>> groupMembers = new ConcurrentHashMap<>();

    /**
     * 添加WebSocket会话
     * 
     * @param session WebSocket会话
     * @param userId 用户ID
     * @param username 用户名
     * @param nickname 用户昵称
     */
    public void addSession(WebSocketSession session, Long userId, String username, String nickname) {
        sessions.put(session.getId(), session);
        
        OnlineUser onlineUser = new OnlineUser(userId, username, nickname);
        onlineUser.setSessionId(session.getId());
        onlineUsers.put(userId, onlineUser);
        userSessionMap.put(userId, session.getId());
        
        log.info("用户 {} 已连接，会话ID: {}", userId, session.getId());
    }

    /**
     * 移除WebSocket会话
     * 
     * @param sessionId 会话ID
     */
    public void removeSession(String sessionId) {
        WebSocketSession session = sessions.get(sessionId);
        if (session != null) {
            Long userId = getUserIdBySessionId(sessionId);
            if (userId != null) {
                onlineUsers.remove(userId);
                userSessionMap.remove(userId);
            }
            sessions.remove(sessionId);
            log.info("会话 {} 已断开连接", sessionId);
        }
    }

    /**
     * 根据会话ID获取WebSocket会话
     * 
     * @param sessionId 会话ID
     * @return WebSocket会话
     */
    public WebSocketSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    /**
     * 根据用户ID获取WebSocket会话
     * 
     * @param userId 用户ID
     * @return WebSocket会话
     */
    public WebSocketSession getSessionByUserId(Long userId) {
        String sessionId = userSessionMap.get(userId);
        if (sessionId != null) {
            return sessions.get(sessionId);
        }
        return null;
    }

    /**
     * 根据会话ID获取用户ID
     * 
     * @param sessionId 会话ID
     * @return 用户ID
     */
    public Long getUserIdBySessionId(String sessionId) {
        WebSocketSession session = sessions.get(sessionId);
        if (session != null) {
            for (Map.Entry<Long, String> entry : userSessionMap.entrySet()) {
                if (entry.getValue().equals(sessionId)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    /**
     * 获取在线用户数量
     * 
     * @return 在线用户数量
     */
    public int getOnlineUserCount() {
        return onlineUsers.size();
    }

    /**
     * 获取所有在线用户
     * 
     * @return 在线用户映射表
     */
    public Map<Long, OnlineUser> getOnlineUsers() {
        return onlineUsers;
    }

    /**
     * 发送消息给指定会话
     * 
     * @param sessionId 会话ID
     * @param message 消息内容
     */
    public void sendMessageToSession(String sessionId, String message) {
        WebSocketSession session = sessions.get(sessionId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.error("发送消息失败，会话ID: {}", sessionId, e);
            }
        }
    }

    /**
     * 发送消息给指定用户
     * 
     * @param userId 用户ID
     * @param message 消息内容
     */
    public void sendMessageToUser(Long userId, String message) {
        String sessionId = userSessionMap.get(userId);
        if (sessionId != null) {
            sendMessageToSession(sessionId, message);
        }
    }

    /**
     * 广播消息给所有在线用户
     * 
     * @param message 消息内容
     */
    public void broadcastMessage(String message) {
        for (WebSocketSession session : sessions.values()) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    log.error("广播消息失败，会话ID: {}", session.getId(), e);
                }
            }
        }
    }
    
    /**
     * 发送消息给群组成员
     * 
     * @param groupId 群组ID
     * @param message 消息内容
     */
    public void sendMessageToGroup(Long groupId, String message) {
        List<Long> members = groupMembers.get(groupId);
        if (members != null) {
            for (Long userId : members) {
                sendMessageToUser(userId, message);
            }
        }
    }
    
    /**
     * 添加群组成员
     * 
     * @param groupId 群组ID
     * @param userId 用户ID
     */
    public void addGroupMember(Long groupId, Long userId) {
        groupMembers.computeIfAbsent(groupId, k -> new java.util.ArrayList<>()).add(userId);
    }
    
    /**
     * 移除群组成员
     * 
     * @param groupId 群组ID
     * @param userId 用户ID
     */
    public void removeGroupMember(Long groupId, Long userId) {
        List<Long> members = groupMembers.get(groupId);
        if (members != null) {
            members.remove(userId);
        }
    }

    /**
     * 更新用户最后活跃时间
     * 
     * @param userId 用户ID
     */
    public void updateLastActiveTime(Long userId) {
        OnlineUser onlineUser = onlineUsers.get(userId);
        if (onlineUser != null) {
            onlineUser.setLastActiveTime(java.time.LocalDateTime.now());
        }
    }
}