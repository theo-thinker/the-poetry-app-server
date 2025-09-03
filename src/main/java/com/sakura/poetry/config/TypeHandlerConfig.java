package com.sakura.poetry.config;

import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis类型处理器配置
 * 
 * <p>配置MyBatis的类型处理器，特别是枚举类型的处理。
 * 确保枚举类型在数据库存储和查询时的正确转换。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>枚举类型自动转换 - 枚举对象与数据库值的双向转换</li>
 *   <li>类型安全保证 - 确保枚举值的有效性</li>
 *   <li>统一处理规则 - 全局统一的枚举处理逻辑</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Configuration
public class TypeHandlerConfig {

    /**
     * 注册枚举类型处理器
     * 
     * <p>配置MyBatis的枚举类型处理器，使实现了BaseStatusEnum接口的枚举类
     * 能够正确地在Java对象和数据库值之间进行转换。</p>
     * 
     * @return 类型处理器注册表
     */
    @Bean
    public TypeHandlerRegistry typeHandlerRegistry() {
        TypeHandlerRegistry registry = new TypeHandlerRegistry();
        
        // 注册枚举类型处理器
        // MyBatis-Plus会自动处理实现了IEnum接口的枚举类型
        registry.setDefaultEnumTypeHandler(MybatisEnumTypeHandler.class);
        
        return registry;
    }
}