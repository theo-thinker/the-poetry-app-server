# IP模块说明文档

## 模块概述

IP模块是基于[ip2region](https://github.com/lionsoul2014/ip2region)数据库实现的IP地址解析功能模块。该模块可以将IP地址解析为详细的地理位置信息，包括国家、区域、省份、城市和运营商等信息。

## 功能特性

1. **IP地址解析** - 将IP地址解析为详细的地理位置信息
2. **批量解析** - 支持批量解析多个IP地址
3. **客户端IP获取** - 获取客户端真实IP地址（支持代理环境）
4. **高性能查询** - 基于ip2region数据库，查询速度快
5. **RESTful API** - 提供标准的RESTful接口

## 技术实现

### 核心组件

1. **Ip2RegionUtil** - IP解析工具类，封装了ip2region的核心功能
2. **IpInfo** - IP信息实体类，用于封装解析后的地理位置信息
3. **IpService** - IP服务接口，定义了业务逻辑接口
4. **IpServiceImpl** - IP服务实现类，实现了具体的业务逻辑
5. **IpController** - IP控制器，提供RESTful API接口

### 数据库

使用[ip2region](https://github.com/lionsoul2014/ip2region)数据库，该数据库具有以下特点：
- 数据格式: `国家|区域|省份|城市|运营商`
- 数据更新: 定期更新，数据准确性高
- 查询性能: 毫秒级查询速度
- 文件大小: 约10MB

## API接口

### 1. 解析IP地址

```
GET /api/v1/ip/parse?ip={ip地址}
```

**请求参数:**
- ip: 要解析的IP地址

**响应示例:**
```json
{
  "code": 200,
  "message": "IP地址解析成功",
  "data": {
    "country": "中国",
    "region": "华东",
    "province": "江苏省",
    "city": "南京市",
    "isp": "电信",
    "originalData": "中国|华东|江苏省|南京市|电信"
  },
  "timestamp": "2025-09-03 10:30:00"
}
```

### 2. 批量解析IP地址

```
POST /api/v1/ip/batch-parse
```

**请求体:**
```json
[
  "114.114.114.114",
  "8.8.8.8",
  "119.29.29.29"
]
```

**响应示例:**
```json
{
  "code": 200,
  "message": "批量解析成功",
  "data": [
    {
      "country": "中国",
      "region": "华东",
      "province": "江苏省",
      "city": "南京市",
      "isp": "电信",
      "originalData": "中国|华东|江苏省|南京市|电信"
    },
    {
      "country": "美国",
      "region": "加利福尼亚",
      "province": "加利福尼亚州",
      "city": "山景城",
      "isp": "Google",
      "originalData": "美国|加利福尼亚|加利福尼亚州|山景城|Google"
    },
    {
      "country": "中国",
      "region": "华南",
      "province": "广东省",
      "city": "深圳市",
      "isp": "DNSPod",
      "originalData": "中国|华南|广东省|深圳市|DNSPod"
    }
  ],
  "timestamp": "2025-09-03 10:30:00"
}
```

### 3. 获取当前客户端IP信息

```
GET /api/v1/ip/current
```

**响应示例:**
```json
{
  "code": 200,
  "message": "获取客户端IP信息成功",
  "data": {
    "country": "中国",
    "region": "华东",
    "province": "江苏省",
    "city": "南京市",
    "isp": "电信",
    "originalData": "中国|华东|江苏省|南京市|电信"
  },
  "timestamp": "2025-09-03 10:30:00"
}
```

## 使用示例

### Java代码使用示例

```java
@Service
@RequiredArgsConstructor
public class SomeService {
    
    private final IpService ipService;
    
    public void example() {
        // 解析单个IP
        IpInfo ipInfo = ipService.parseIp("114.114.114.114");
        
        // 批量解析IP
        List<String> ips = Arrays.asList("114.114.114.114", "8.8.8.8");
        List<IpInfo> ipInfos = ipService.parseIps(ips);
        
        // 获取客户端真实IP
        String realIp = ipService.getRealIpAddress(request);
    }
}
```

## 配置说明

IP模块无需额外配置，ip2region数据库文件已包含在项目资源目录中：
```
src/main/resources/ip2region/ip2region.xdb
```

## 性能优化

1. **数据库加载** - ip2region数据库在应用启动时加载到内存中，查询速度极快
2. **缓存机制** - 建议在业务层对频繁查询的IP地址添加缓存
3. **批量查询** - 对于大量IP地址解析需求，建议使用批量接口

## 注意事项

1. IP地址解析准确性依赖于ip2region数据库，数据可能不是100%准确
2. 对于内网IP地址，可能无法解析到具体的地理位置信息
3. 建议在生产环境中对IP解析功能添加适当的缓存机制以提高性能
4. 对于高并发场景，建议使用连接池或异步处理方式

## 版本信息

- **模块版本**: 1.0.0
- **ip2region版本**: 2.7.0
- **最后更新**: 2025-09-03