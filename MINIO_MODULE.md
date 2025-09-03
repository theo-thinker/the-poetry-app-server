# 企业级Minio文件存储模块使用指南

## 概述

本模块提供了一个完整的企业级Minio文件存储功能，支持文件上传、下载、删除等操作。该模块具有以下特性：

1. 基于Minio的对象存储
2. 文件上传验证（大小、类型）
3. 自动生成唯一文件名
4. 临时URL访问
5. 存储桶自动创建
6. 健康检查功能
7. 完整的API接口

## 技术架构

### 核心组件

1. **MinioConfig** - Minio配置类
2. **MinioProperties** - Minio配置属性类
3. **MinioService** - Minio业务服务类
4. **MinioController** - Minio控制器
5. **MinioHealthController** - Minio健康检查控制器
6. **FileValidationService** - 文件验证服务
7. **MinioUtil** - Minio工具类

### 数据模型

1. **FileUploadResponseDTO** - 文件上传响应DTO

## 配置说明

### application.yml配置

```yaml
minio:
  # Minio服务器地址
  endpoint: http://localhost:9000
  # 访问密钥
  access-key: minioadmin
  # 秘密密钥
  secret-key: minioadmin
  # 默认存储桶名称
  bucket-name: poetry-app
  # 连接超时时间（毫秒）
  connect-timeout: 10000
  # 读取超时时间（毫秒）
  read-timeout: 30000
  # 写入超时时间（毫秒）
  write-timeout: 30000
  # 是否启用HTTPS
  secure: false
  # 文件上传的最大大小（字节）
  max-file-size: 104857600
  # 允许的文件类型
  allowed-file-types:
    - image/jpeg
    - image/png
    - image/gif
    - image/bmp
    - image/webp
    - application/pdf
    - application/msword
    - application/vnd.openxmlformats-officedocument.wordprocessingml.document
    - application/vnd.ms-excel
    - application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
    - application/vnd.ms-powerpoint
    - application/vnd.openxmlformats-officedocument.presentationml.presentation
    - text/plain
    - application/zip
    - application/x-rar-compressed
  # 临时URL的有效期（秒）
  url-expiry-seconds: 604800
```

## HTTP接口

### 1. 文件管理接口

#### 上传文件
```
POST /api/minio/upload
参数：
- file: 文件（必需）
- objectName: 对象名称（可选，如不提供则自动生成）
```

#### 获取文件下载链接
```
GET /api/minio/download
参数：
- objectName: 对象名称（必需）
```

#### 删除文件
```
DELETE /api/minio/delete
参数：
- objectName: 对象名称（必需）
```

#### 检查文件是否存在
```
GET /api/minio/exists
参数：
- objectName: 对象名称（必需）
```

#### 获取文件信息
```
GET /api/minio/info
参数：
- objectName: 对象名称（必需）
```

### 2. 健康检查接口

#### Minio服务健康检查
```
GET /api/minio/health
```

#### 存储桶状态检查
```
GET /api/minio/health/bucket
```

## 使用示例

### 1. 上传文件

```bash
curl -X POST "http://localhost:8080/api/minio/upload" \
  -H "Content-Type: multipart/form-data" \
  -F "file=@/path/to/your/file.jpg" \
  -F "objectName=my-file.jpg"
```

### 2. 下载文件

```bash
curl -X GET "http://localhost:8080/api/minio/download?objectName=my-file.jpg"
```

### 3. 删除文件

```bash
curl -X DELETE "http://localhost:8080/api/minio/delete?objectName=my-file.jpg"
```

## 安全机制

1. **文件验证**：对上传文件的大小和类型进行验证
2. **存储桶隔离**：使用独立的存储桶存储文件
3. **临时URL**：文件访问使用有时效性的临时URL
4. **访问控制**：通过Spring Security控制API访问权限

## 扩展功能

### 1. 自定义存储桶
可以通过配置不同的存储桶名称来实现文件分类存储。

### 2. 文件版本控制
Minio支持文件版本控制，可通过配置启用。

### 3. 文件生命周期管理
可配置文件的自动删除策略。

### 4. 分布式部署
支持Minio集群部署以提高可用性。

## 性能优化建议

1. **连接池配置**：合理配置Minio客户端连接池
2. **缓存策略**：对频繁访问的文件信息进行缓存
3. **异步上传**：对于大文件可考虑实现异步上传
4. **CDN集成**：可与CDN服务集成提高文件访问速度

## 故障排除

### 1. 连接失败
- 检查Minio服务器地址和端口
- 确认访问密钥和秘密密钥正确
- 检查网络连接和防火墙设置

### 2. 上传失败
- 检查文件大小是否超过限制
- 确认文件类型是否被允许
- 查看服务端日志获取错误详情

### 3. 权限问题
- 确认Minio用户具有相应权限
- 检查存储桶策略配置

## 版本更新记录

### v1.0.0 (2025-09-03)
- 初始版本发布
- 支持基本文件上传、下载、删除功能
- 实现文件验证机制
- 提供健康检查接口

---

*本文档由Sakura Huang编写，版本1.0.0，最后更新于2025-09-03*