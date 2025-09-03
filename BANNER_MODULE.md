# Banner模块说明文档

## 模块概述

Banner模块是一个企业级的自定义Banner管理模块，支持根据不同环境显示不同的Banner，实现Banner的配置化管理。该模块提供了灵活的配置选项，可以根据不同的运行环境（开发、测试、生产）显示相应的Banner信息。

## 功能特性

1. **环境感知** - 根据Spring Profile自动选择对应的Banner文件
2. **配置化管理** - 通过配置文件控制Banner的启用/禁用和文件路径
3. **多环境支持** - 支持开发、测试、生产等不同环境的Banner定制
4. **自动装配** - 基于Spring Boot自动配置机制，开箱即用
5. **兼容性** - 兼容Spring Boot原生Banner机制

## 技术实现

### 核心组件

1. **BannerConfig** - Banner配置属性类，用于读取配置文件中的Banner配置
2. **CustomBanner** - 自定义Banner实现类，负责根据环境加载和显示Banner
3. **BannerAutoConfiguration** - Banner自动配置类，负责注册自定义Banner组件
4. **BannerInitializer** - Banner初始化器，负责在应用启动时设置自定义Banner

### 配置选项

在`application.yml`中可以配置以下Banner选项：

```yaml
app:
  banner:
    # 是否启用自定义Banner
    enabled: true
    # 默认Banner文件路径
    default-banner: banner.txt
    # 开发环境Banner文件路径
    dev-banner: banner-dev.txt
    # 生产环境Banner文件路径
    prod-banner: banner-prod.txt
    # 测试环境Banner文件路径
    test-banner: banner-test.txt
```

## 使用说明

### 1. 配置文件

在`src/main/resources`目录下创建不同环境的Banner文件：

- `banner.txt` - 默认Banner文件
- `banner-dev.txt` - 开发环境Banner文件
- `banner-prod.txt` - 生产环境Banner文件
- `banner-test.txt` - 测试环境Banner文件

### 2. Banner文件格式

Banner文件支持ANSI颜色代码，可以创建彩色的Banner显示效果：

```
${AnsiColor.BRIGHT_BLUE}
████████╗██╗  ██╗███████╗    ██████╗  ██████╗  ██████╗████████╗    █████╗ ██████╗ ██████╗ 
╚══██╔══╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗██╔════╝╚══██╔══╝   ██╔══██╗██╔══██╗██╔══██╗
   ██║   ███████║█████╗      ██████╔╝██║   ██║██║        ██║      ███████║██████╔╝██████╔╝
${AnsiColor.BRIGHT_GREEN}
...
${AnsiColor.DEFAULT}
```

### 3. 环境切换

通过设置Spring Profile来切换不同的环境Banner：

```bash
# 开发环境
java -Dspring.profiles.active=dev -jar the-poetry-app-server-1.0.0.jar

# 生产环境
java -Dspring.profiles.active=prod -jar the-poetry-app-server-1.0.0.jar

# 测试环境
java -Dspring.profiles.active=test -jar the-poetry-app-server-1.0.0.jar
```

## 扩展功能

### 自定义Banner文件

可以根据需要创建自定义的Banner文件，并在配置中指定：

```yaml
app:
  banner:
    enabled: true
    default-banner: my-custom-banner.txt
    dev-banner: my-dev-banner.txt
    prod-banner: my-prod-banner.txt
    test-banner: my-test-banner.txt
```

### 禁用Banner

如果需要禁用Banner显示，可以将enabled设置为false：

```yaml
app:
  banner:
    enabled: false
```

## 注意事项

1. Banner文件必须放置在`src/main/resources`目录下
2. 如果指定环境的Banner文件不存在，系统会自动回退到默认Banner文件
3. Banner文件支持ANSI颜色代码，可以创建丰富的视觉效果
4. 在生产环境中建议使用简洁的Banner以减少启动时间
5. 可以通过配置`spring.main.banner-mode`来控制Banner的显示方式（console、log、off）

## 版本信息

- **模块版本**: 1.0.0
- **Spring Boot版本**: 3.5.5
- **最后更新**: 2025-09-03