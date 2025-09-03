package com.sakura.poetry.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Knife4j API文档配置类
 * 
 * <p>配置Knife4j（Swagger）API文档生成器，提供完善的API文档界面。
 * 基于OpenAPI 3.0规范，生成美观、交互式的API文档。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>自动生成API文档</li>
 *   <li>提供在线API测试功能</li>
 *   <li>支持JWT认证测试</li>
 *   <li>分组管理API接口</li>
 *   <li>自定义文档样式和信息</li>
 * </ul>
 * 
 * <p>访问地址：</p>
 * <ul>
 *   <li>Knife4j文档: http://localhost:8080/api/doc.html</li>
 *   <li>Swagger UI: http://localhost:8080/api/swagger-ui/index.html</li>
 *   <li>OpenAPI JSON: http://localhost:8080/api/v3/api-docs</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Configuration
public class Knife4jConfig {

    /**
     * 应用名称
     */
    @Value("${app.system.name}")
    private String applicationName;

    /**
     * 应用版本
     */
    @Value("${app.system.version}")
    private String applicationVersion;

    /**
     * 应用作者
     */
    @Value("${app.system.author}")
    private String applicationAuthor;

    /**
     * 应用描述
     */
    @Value("${app.system.description}")
    private String applicationDescription;

    /**
     * 配置OpenAPI文档信息
     * 
     * <p>定义API文档的基本信息，包括标题、描述、版本、联系人等。
     * 同时配置JWT认证方案，支持在文档界面进行认证测试。</p>
     * 
     * @return OpenAPI配置
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // API基本信息
                .info(new Info()
                        .title(applicationName + " API文档")
                        .description(buildDescription())
                        .version(applicationVersion)
                        .contact(new Contact()
                                .name(applicationAuthor)
                                .email("sakura@poetry.com")
                                .url("https://github.com/sakura"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                
                // 服务器信息
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080/api")
                                .description("本地开发服务器"),
                        new Server()
                                .url("https://api.poetry.example.com/api")
                                .description("生产环境服务器")))
                
                // 安全配置
                .components(new Components()
                        .addSecuritySchemes("Bearer", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                                .description("请输入JWT令牌，格式为：Bearer {token}")))
                
                // 全局安全要求
                .addSecurityItem(new SecurityRequirement().addList("Bearer"));
    }

    /**
     * 构建API文档描述信息
     * 
     * @return 格式化的描述文本
     */
    private String buildDescription() {
        return String.format("""
                %s
                
                ## 项目简介
                
                这是一个基于Spring Boot 3.5.5开发的企业级诗词应用后端服务，提供完整的诗词内容管理、用户管理、权限控制等功能。
                
                ## 技术栈
                
                - **核心框架**: Spring Boot 3.5.5
                - **安全框架**: Spring Security 6.5.3
                - **JWT处理**: JJWT 0.12.7
                - **ORM框架**: MyBatis-Plus 3.5.14
                - **数据库**: MySQL 9.4.0
                - **API文档**: Knife4j 4.5.0
                - **构建工具**: Gradle Kotlin DSL
                - **Java版本**: Java 21
                
                ## 主要功能
                
                ### 用户管理
                - 用户注册、登录、权限管理
                - 角色分配和权限控制
                - 用户信息管理和个人资料
                
                ### 诗词内容
                - 诗词、诗人、朝代、分类管理
                - 诗词检索和分类浏览
                - 诗词内容的增删改查
                
                ### 用户交互
                - 诗词收藏和收藏夹管理
                - 诗词点赞和评论功能
                - 用户行为数据统计
                
                ### 系统管理
                - 系统配置管理
                - 操作日志记录
                - 数据监控和统计
                
                ## 认证说明
                
                本API使用JWT（JSON Web Token）进行身份认证。请按以下步骤进行：
                
                1. 调用登录接口获取Token
                2. 在请求头中添加 `Authorization: Bearer {token}`
                3. 或在右上角🔒按钮中输入Token进行全局认证
                
                ## 联系信息
                
                - 作者: %s
                - 邮箱: sakura@poetry.com
                - 项目地址: https://github.com/sakura/poetry-app-server
                """, applicationDescription, applicationAuthor);
    }
}