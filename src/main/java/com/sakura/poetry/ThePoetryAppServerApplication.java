package com.sakura.poetry;

import com.sakura.poetry.config.CustomBanner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * 企业级诗词APP后端服务主启动类
 * 
 * <p>本项目是一个基于Spring Boot 3.5.5的企业级诗词应用后端服务，
 * 集成了Spring Security、JWT认证、MyBatis-Plus、MySQL等技术栈，
 * 提供完整的诗词内容管理、用户管理、权限控制等功能。</p>
 * 
 * <p>技术栈：</p>
 * <ul>
 *   <li>Spring Boot 3.5.5 - 核心框架</li>
 *   <li>Spring Security 6.5.3 - 安全框架</li>
 *   <li>JJWT 0.12.7 - JWT处理</li>
 *   <li>MyBatis-Plus 3.5.14 - ORM框架</li>
 *   <li>MySQL 9.4.0 - 数据库</li>
 *   <li>Knife4j 4.5.0 - API文档</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableWebSocket
@MapperScan("com.sakura.poetry.mapper")
public class ThePoetryAppServerApplication {

    /**
     * 应用程序入口点
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        System.setProperty("spring.application.name", "the-poetry-app-server");
        System.setProperty("server.port", "8080");
        
        SpringApplication app = new SpringApplication(ThePoetryAppServerApplication.class);
        app.run(args);
    }
}