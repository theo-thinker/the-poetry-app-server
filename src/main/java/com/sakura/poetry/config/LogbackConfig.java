package com.sakura.poetry.config;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.PatternLayout;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * Logback配置类
 * 
 * <p>用于自定义Logback日志配置，实现色彩丰富的日志打印效果。</p>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Configuration
public class LogbackConfig {
    
    /**
     * 初始化Logback配置
     * 
     * <p>在应用启动时配置Logback的日志输出格式和颜色。</p>
     */
    @PostConstruct
    public void init() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        
        // 获取控制台appender
        ch.qos.logback.classic.Logger rootLogger = context.getLogger("ROOT");
        Appender<ILoggingEvent> consoleAppender = rootLogger.getAppender("CONSOLE");
        
        if (consoleAppender instanceof ConsoleAppender) {
            ConsoleAppender<ILoggingEvent> ca = (ConsoleAppender<ILoggingEvent>) consoleAppender;
            
            // 创建带颜色的编码器
            PatternLayoutEncoder encoder = new PatternLayoutEncoder();
            encoder.setContext(context);
            
            // 设置带颜色的日志格式
            String pattern = "%yellow(%d{yyyy-MM-dd HH:mm:ss.SSS}) %highlight(%-5level) %green([%thread]) %boldMagenta(%logger{36}) - %msg%n";
            encoder.setPattern(pattern);
            encoder.start();
            
            ca.setEncoder(encoder);
        }
    }
}