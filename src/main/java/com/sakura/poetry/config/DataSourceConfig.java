package com.sakura.poetry.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 数据源配置类
 * 
 * <p>配置数据库连接池和数据源相关参数。
 * 使用阿里巴巴Druid连接池，提供高性能的数据库连接管理和监控功能。</p>
 * 
 * <p>Druid连接池特性：</p>
 * <ul>
 *   <li>高性能的数据库连接池</li>
 *   <li>完善的监控统计功能</li>
 *   <li>内置的SQL防火墙功能</li>
 *   <li>支持数据库密码加密</li>
 *   <li>提供Web监控界面</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    /**
     * 主数据源配置
     * 
     * <p>配置主数据库连接池，使用Druid数据源。
     * 从配置文件中读取spring.datasource.druid配置项。</p>
     * 
     * @return 配置好的数据源
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource dataSource() {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        
        // 配置初始化大小、最小、最大连接数
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(20);
        
        // 配置获取连接等待超时的时间
        dataSource.setMaxWait(60000);
        
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        
        // 配置一个连接在池中最小生存的时间，单位毫秒
        dataSource.setMinEvictableIdleTimeMillis(300000);
        
        // 用来检测连接是否有效的sql，要求是一个查询语句
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        
        // 打开PSCache，并且指定每个连接上PSCache的大小
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        
        return dataSource;
    }
}