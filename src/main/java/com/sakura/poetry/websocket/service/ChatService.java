package com.sakura.poetry.websocket.service;

import com.sakura.poetry.websocket.model.ChatGroup;
import com.sakura.poetry.websocket.model.ChatMessage;
import com.sakura.poetry.websocket.model.MessageStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 聊天服务类
 * 
 * <p>提供聊天相关的业务逻辑处理。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Slf4j
public class ChatService {
    
    // 模拟数据库存储
    private final ConcurrentHashMap<String, ChatMessage> privateMessages = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ChatMessage> groupMessages = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, ChatGroup> groups = new ConcurrentHashMap<>();
    private final AtomicLong messageIdGenerator = new AtomicLong(System.currentTimeMillis());
    private final AtomicLong groupIdGenerator = new AtomicLong(System.currentTimeMillis());

    /**
     * 保存私聊消息
     * 
     * @param message 聊天消息
     */
    public void savePrivateMessage(ChatMessage message) {
        // 生成消息ID
        if (message.getMessageId() == null || message.getMessageId().isEmpty()) {
            message.setMessageId("private_" + messageIdGenerator.incrementAndGet());
        }
        
        // 设置时间戳
        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        }
        
        // 设置消息状态
        if (message.getStatus() == null) {
            message.setStatus(MessageStatus.SENT);
        }
        
        // 保存到存储中
        privateMessages.put(message.getMessageId(), message);
        
        log.info("保存私聊消息: from {} to {}, content: {}", 
                message.getSenderId(), message.getReceiverId(), message.getContent());
    }

    /**
     * 保存群聊消息
     * 
     * @param message 聊天消息
     */
    public void saveGroupMessage(ChatMessage message) {
        // 生成消息ID
        if (message.getMessageId() == null || message.getMessageId().isEmpty()) {
            message.setMessageId("group_" + messageIdGenerator.incrementAndGet());
        }
        
        // 设置时间戳
        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        }
        
        // 设置消息状态
        if (message.getStatus() == null) {
            message.setStatus(MessageStatus.SENT);
        }
        
        // 保存到存储中
        groupMessages.put(message.getMessageId(), message);
        
        log.info("保存群聊消息: from {}, group {}, content: {}", 
                message.getSenderId(), message.getGroupId(), message.getContent());
    }

    /**
     * 获取用户的历史私聊消息
     * 
     * @param userId 用户ID
     * @param friendId 好友ID
     * @param limit 限制数量
     * @return 聊天消息列表
     */
    public List<ChatMessage> getPrivateChatHistory(Long userId, Long friendId, int limit) {
        log.info("获取用户 {} 和用户 {} 的私聊历史消息，限制数量: {}", userId, friendId, limit);
        
        List<ChatMessage> messages = new ArrayList<>();
        
        // 筛选相关的私聊消息
        for (ChatMessage message : privateMessages.values()) {
            if ((message.getSenderId().equals(userId) && message.getReceiverId().equals(friendId)) ||
                (message.getSenderId().equals(friendId) && message.getReceiverId().equals(userId))) {
                messages.add(message);
            }
        }
        
        // 按时间排序并限制数量
        messages.sort((m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()));
        if (messages.size() > limit) {
            messages = messages.subList(0, limit);
        }
        
        // 反转列表以按时间顺序排列
        java.util.Collections.reverse(messages);
        
        return messages;
    }

    /**
     * 获取群组的历史消息
     * 
     * @param groupId 群组ID
     * @param limit 限制数量
     * @return 聊天消息列表
     */
    public List<ChatMessage> getGroupChatHistory(Long groupId, int limit) {
        log.info("获取群组 {} 的历史消息，限制数量: {}", groupId, limit);
        
        List<ChatMessage> messages = new ArrayList<>();
        
        // 筛选相关的群聊消息
        for (ChatMessage message : groupMessages.values()) {
            if (message.getGroupId() != null && message.getGroupId().equals(groupId)) {
                messages.add(message);
            }
        }
        
        // 按时间排序并限制数量
        messages.sort((m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()));
        if (messages.size() > limit) {
            messages = messages.subList(0, limit);
        }
        
        // 反转列表以按时间顺序排列
        java.util.Collections.reverse(messages);
        
        return messages;
    }

    /**
     * 创建群组
     * 
     * @param groupName 群组名称
     * @param creatorId 创建者ID
     * @param memberIds 成员ID列表
     * @return 群组信息
     */
    public ChatGroup createGroup(String groupName, Long creatorId, List<Long> memberIds) {
        log.info("创建群组: {}, 创建者: {}, 成员数量: {}", groupName, creatorId, memberIds.size());
        
        // 创建群组对象
        ChatGroup group = new ChatGroup();
        group.setGroupId(groupIdGenerator.incrementAndGet());
        group.setGroupName(groupName);
        group.setOwnerId(creatorId);
        group.setMemberIds(new ArrayList<>(memberIds));
        group.setMemberCount(memberIds.size());
        group.setCreatedTime(LocalDateTime.now());
        group.setUpdatedTime(LocalDateTime.now());
        
        // 保存到存储中
        groups.put(group.getGroupId(), group);
        
        return group;
    }
    
    /**
     * 获取群组信息
     * 
     * @param groupId 群组ID
     * @return 群组信息
     */
    public ChatGroup getGroup(Long groupId) {
        return groups.get(groupId);
    }

    /**
     * 获取用户加入的群组列表
     * 
     * @param userId 用户ID
     * @return 群组列表
     */
    public List<ChatGroup> getUserGroups(Long userId) {
        log.info("获取用户 {} 的群组列表", userId);
        
        List<ChatGroup> userGroups = new ArrayList<>();
        
        // 筛选用户加入的群组
        for (ChatGroup group : groups.values()) {
            if (group.getMemberIds().contains(userId)) {
                userGroups.add(group);
            }
        }
        
        return userGroups;
    }

    /**
     * 添加群组成员
     * 
     * @param groupId 群组ID
     * @param userId 用户ID
     */
    public void addGroupMember(Long groupId, Long userId) {
        log.info("添加用户 {} 到群组 {}", userId, groupId);
        
        ChatGroup group = groups.get(groupId);
        if (group != null && !group.getMemberIds().contains(userId)) {
            group.getMemberIds().add(userId);
            group.setMemberCount(group.getMemberIds().size());
            group.setUpdatedTime(LocalDateTime.now());
        }
    }

    /**
     * 移除群组成员
     * 
     * @param groupId 群组ID
     * @param userId 用户ID
     */
    public void removeGroupMember(Long groupId, Long userId) {
        log.info("从群组 {} 移除用户 {}", groupId, userId);
        
        ChatGroup group = groups.get(groupId);
        if (group != null && group.getMemberIds().contains(userId)) {
            group.getMemberIds().remove(userId);
            group.setMemberCount(group.getMemberIds().size());
            group.setUpdatedTime(LocalDateTime.now());
        }
    }

    /**
     * 解散群组
     * 
     * @param groupId 群组ID
     */
    public void dismissGroup(Long groupId) {
        log.info("解散群组 {}", groupId);
        
        groups.remove(groupId);
    }

    /**
     * 标记消息为已读
     * 
     * @param messageId 消息ID
     */
    public void markMessageAsRead(String messageId) {
        log.info("标记消息 {} 为已读", messageId);
        
        ChatMessage message = privateMessages.get(messageId);
        if (message != null) {
            message.setStatus(MessageStatus.READ);
            return;
        }
        
        message = groupMessages.get(messageId);
        if (message != null) {
            message.setStatus(MessageStatus.READ);
        }
    }
}