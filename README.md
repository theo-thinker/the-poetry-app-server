# the-poetry-app-server
一个企业级的诗词app后端服务，用于测试Qoder的AI能力。

## 功能特性

- 诗词内容管理
- 用户权限管理
- WebSocket实时聊天（支持私聊和群聊）
- Minio文件存储
- RESTful API接口
- JWT身份认证
- 数据库持久化存储

## 技术栈

- Spring Boot 3.5.5
- Spring Security 6.5.3
- MyBatis-Plus 3.5.14
- MySQL 9.4.0
- JWT 0.12.7
- Knife4j 4.5.0
- WebSocket
- Minio 8.5.17

## WebSocket聊天模块

本项目包含一个完整的企业级WebSocket聊天模块，支持：

- 一对一私聊
- 群聊功能
- 在线用户管理
- 消息持久化
- JWT身份认证
- 心跳检测

详细使用说明请查看 [WebSocket模块使用指南](WEBSOCKET_MODULE.md)

## Minio文件存储模块

本项目包含一个完整的企业级Minio文件存储模块，支持：

- 文件上传、下载、删除
- 文件验证（大小、类型）
- 自动生成唯一文件名
- 临时URL访问
- 存储桶自动创建
- 健康检查功能

详细使用说明请查看 [Minio模块使用指南](MINIO_MODULE.md)

## 快速开始

### 环境要求

- Java 21
- MySQL 9.4.0
- Redis (可选)

### 构建项目

```bash
./gradlew build
```

### 运行项目

```bash
./gradlew bootRun
```

### 访问API文档

项目启动后，访问以下地址查看API文档：

- Knife4j UI: http://localhost:8080/doc.html
- Swagger UI: http://localhost:8080/swagger-ui.html

## 配置说明

### 数据库配置

在 `src/main/resources/application.yml` 中配置数据库连接：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/poetry_app_dev
    username: root
    password: your_password
```

### JWT配置

```yaml
app:
  jwt:
    secret: your_jwt_secret
    expiration: 604800
```

## 项目结构

```
src/main/java/com/sakura/poetry
├── common          # 通用工具类
├── config          # 配置类
├── controller      # 控制器
├── dto             # 数据传输对象
├── entity          # 实体类
├── mapper          # MyBatis映射器
├── security        # 安全配置
├── service         # 业务逻辑
├── utils           # 工具类
├── websocket       # WebSocket模块
└── ThePoetryAppServerApplication.java  # 启动类
```

## 贡献

作者: Sakura Huang

## 许可证

本项目仅供测试Qoder AI能力使用。
