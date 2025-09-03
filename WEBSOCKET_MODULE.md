# 企业级WebSocket聊天模块使用指南

## 概述

本模块提供了一个完整的企业级WebSocket聊天功能，支持一对一私聊和群聊，仿照微信和QQ的实现方式。该模块具有以下特性：

1. 基于JWT的身份认证
2. 支持私聊和群聊
3. 在线用户管理
4. 消息持久化存储
5. 心跳检测机制
6. 离线消息处理
7. 群组管理功能

## 技术架构

### 核心组件

1. **WebSocketConfig** - WebSocket配置类
2. **ChatWebSocketHandler** - WebSocket消息处理器
3. **ChatHandshakeInterceptor** - WebSocket握手拦截器
4. **WebSocketSessionManager** - WebSocket会话管理器
5. **ChatService** - 聊天业务服务类
6. **JwtUtil** - JWT工具类

### 数据模型

1. **ChatMessage** - 聊天消息实体
2. **OnlineUser** - 在线用户信息
3. **ChatGroup** - 聊天群组实体
4. **MessageType** - 消息类型枚举
5. **MessageStatus** - 消息状态枚举

## 使用指南

### 1. 建立WebSocket连接

客户端需要通过以下URL建立WebSocket连接：

```
ws://localhost:8080/ws/chat?token={JWT_TOKEN}
```

其中`{JWT_TOKEN}`是通过用户登录接口获取的JWT令牌。

### 2. 消息格式

所有通过WebSocket传输的消息都采用JSON格式，基本结构如下：

```json
{
  "messageId": "消息ID",
  "type": "消息类型",
  "senderId": "发送者ID",
  "senderName": "发送者昵称",
  "receiverId": "接收者ID（私聊时使用）",
  "groupId": "群组ID（群聊时使用）",
  "content": "消息内容",
  "timestamp": "发送时间",
  "status": "消息状态"
}
```

### 3. 消息类型

- `connect` - 连接消息
- `disconnect` - 断开连接消息
- `heartbeat` - 心跳消息
- `private_chat` - 私聊消息
- `group_chat` - 群聊消息
- `system` - 系统消息
- `error` - 错误消息

### 4. 私聊消息示例

发送私聊消息：

```json
{
  "type": "private_chat",
  "senderId": 1001,
  "receiverId": 1002,
  "content": "你好，这是一条私聊消息"
}
```

### 5. 群聊消息示例

发送群聊消息：

```json
{
  "type": "group_chat",
  "senderId": 1001,
  "groupId": 2001,
  "content": "大家好，这是一条群聊消息"
}
```

### 6. 心跳消息

客户端应定期发送心跳消息以保持连接：

```json
{
  "type": "heartbeat",
  "content": "ping"
}
```

服务器会回复：

```json
{
  "type": "heartbeat",
  "content": "pong"
}
```

## HTTP接口

### 1. WebSocket管理接口

#### 获取在线用户列表
```
GET /api/websocket/online-users
```

#### 获取在线用户数量
```
GET /api/websocket/online-count
```

### 2. 聊天群组管理接口

#### 创建群组
```
POST /api/chat/groups
参数：
- groupName: 群组名称
- description: 群组描述（可选）
Body: 成员ID列表
```

#### 获取用户群组列表
```
GET /api/chat/groups/user/{userId}
```

#### 添加群组成员
```
POST /api/chat/groups/{groupId}/members/{userId}
```

#### 移除群组成员
```
DELETE /api/chat/groups/{groupId}/members/{userId}
```

#### 解散群组
```
DELETE /api/chat/groups/{groupId}
```

### 3. 聊天消息管理接口

#### 获取私聊历史消息
```
GET /api/chat/messages/private
参数：
- userId: 用户ID
- friendId: 好友ID
- limit: 限制数量（默认20）
```

#### 获取群聊历史消息
```
GET /api/chat/messages/group/{groupId}
参数：
- limit: 限制数量（默认20）
```

#### 发送私聊消息（HTTP方式）
```
POST /api/chat/messages/private
参数：
- senderId: 发送者ID
- receiverId: 接收者ID
- content: 消息内容
```

#### 发送群聊消息（HTTP方式）
```
POST /api/chat/messages/group
参数：
- senderId: 发送者ID
- groupId: 群组ID
- content: 消息内容
```

#### 标记消息为已读
```
PUT /api/chat/messages/{messageId}/read
```

### 4. WebSocket测试接口

#### 生成WebSocket连接令牌
```
GET /api/websocket/test/token
参数：
- userId: 用户ID
- username: 用户名
```

#### 获取WebSocket连接地址
```
GET /api/websocket/test/connection-url
参数：
- token: JWT令牌
```

## 安全机制

1. **JWT认证**：所有WebSocket连接都需要有效的JWT令牌
2. **跨域支持**：支持配置允许的跨域来源
3. **会话管理**：自动管理用户会话和在线状态
4. **消息验证**：对所有消息进行格式和权限验证

## 扩展功能

### 1. 离线消息处理
系统会自动存储用户离线期间的消息，用户上线后可以获取历史消息。

### 2. 消息状态跟踪
支持消息的已发送、已接收、已读等状态跟踪。

### 3. 群组权限管理
支持群主、管理员等角色，可以设置不同的群组权限。

### 4. 消息撤回
支持在一定时间内撤回已发送的消息。

## 部署配置

在`application.yml`中配置WebSocket相关参数：

```yaml
app:
  websocket:
    enabled: true
    endpoint: /ws/chat
    allowed-origins: "*"
```

## 性能优化建议

1. **连接池管理**：合理配置WebSocket连接池大小
2. **消息队列**：对于高并发场景，建议使用消息队列处理消息分发
3. **缓存策略**：使用Redis等缓存存储在线用户信息和群组信息
4. **数据库优化**：对消息表进行分表分库处理
5. **负载均衡**：在集群部署时使用粘性会话或WebSocket集群方案

## 故障排除

### 1. 连接失败
- 检查JWT令牌是否有效
- 确认WebSocket端点URL是否正确
- 检查网络连接和防火墙设置

### 2. 消息发送失败
- 检查消息格式是否正确
- 确认接收者是否在线
- 查看服务端日志获取错误详情

### 3. 认证失败
- 确认JWT密钥配置是否正确
- 检查令牌是否过期
- 验证用户权限设置

## 版本更新记录

### v1.0.0 (2025-09-03)
- 初始版本发布
- 支持私聊和群聊功能
- 实现JWT认证机制
- 提供完整的HTTP管理接口

---

*本文档由Sakura Huang编写，版本1.0.0，最后更新于2025-09-03*