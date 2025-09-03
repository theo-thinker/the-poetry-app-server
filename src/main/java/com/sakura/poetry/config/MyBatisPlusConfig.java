package com.sakura.poetry.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus配置类
 * 
 * <p>配置MyBatis-Plus的核心功能，包括分页插件、乐观锁插件、防全表更新删除插件等。
 * 该配置类提供了企业级应用所需的各种数据库操作增强功能。</p>
 * 
 * <p>主要配置功能：</p>
 * <ul>
 *   <li>分页插件 - 提供高性能的物理分页功能</li>
 *   <li>乐观锁插件 - 防止并发更新冲突</li>
 *   <li>防全表更新删除插件 - 防止误操作导致的数据丢失</li>
 *   <li>自动填充处理器 - 自动填充创建时间、更新时间等字段</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Configuration
public class MyBatisPlusConfig {

    /**
     * MyBatis-Plus插件配置
     * 
     * <p>配置MyBatis-Plus的各种插件，提供增强的数据库操作功能。</p>
     * 
     * @return MyBatis-Plus拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 分页插件 - 支持MySQL数据库的物理分页
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        // 设置请求的页面大于最大页后操作，true调回到首页，false继续请求，默认false
        paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认500条，-1不受限制
        paginationInterceptor.setMaxLimit(1000L);
        interceptor.addInnerInterceptor(paginationInterceptor);
        
        // 乐观锁插件 - 防止并发更新冲突
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        
        // 防全表更新与删除插件 - 防止误操作
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        
        return interceptor;
    }

    /**
     * 自动填充处理器
     * 
     * <p>自动填充实体类的创建时间、更新时间、创建人、更新人等字段。
     * 在执行插入和更新操作时自动处理这些公共字段。</p>
     */
    @Component
    public static class AutoFillMetaObjectHandler implements MetaObjectHandler {

        /**
         * 插入时自动填充
         * 
         * <p>在执行insert操作时，自动填充创建时间、更新时间、创建人、更新人字段。</p>
         * 
         * @param metaObject 元对象
         */
        @Override
        public void insertFill(MetaObject metaObject) {
            LocalDateTime now = LocalDateTime.now();
            
            // 自动填充创建时间
            this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, now);
            // 自动填充更新时间
            this.strictInsertFill(metaObject, "updatedTime", LocalDateTime.class, now);
            
            // TODO: 从当前登录用户上下文获取用户ID
            // 目前先使用默认值，后续集成Spring Security后再完善
            Long currentUserId = getCurrentUserId();
            if (currentUserId != null) {
                // 自动填充创建人
                this.strictInsertFill(metaObject, "createdBy", Long.class, currentUserId);
                // 自动填充更新人
                this.strictInsertFill(metaObject, "updatedBy", Long.class, currentUserId);
            }
        }

        /**
         * 更新时自动填充
         * 
         * <p>在执行update操作时，自动填充更新时间和更新人字段。</p>
         * 
         * @param metaObject 元对象
         */
        @Override
        public void updateFill(MetaObject metaObject) {
            // 自动填充更新时间
            this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime.class, LocalDateTime.now());
            
            // TODO: 从当前登录用户上下文获取用户ID
            Long currentUserId = getCurrentUserId();
            if (currentUserId != null) {
                // 自动填充更新人
                this.strictUpdateFill(metaObject, "updatedBy", Long.class, currentUserId);
            }
        }

        /**
         * 获取当前登录用户ID
         * 
         * <p>从Spring Security上下文中获取当前登录用户的ID。
         * 在集成Spring Security后完善此方法的实现。</p>
         * 
         * @return 当前用户ID，如果未登录则返回null
         */
        private Long getCurrentUserId() {
            // TODO: 集成Spring Security后实现
            // SecurityContext context = SecurityContextHolder.getContext();
            // Authentication authentication = context.getAuthentication();
            // if (authentication != null && authentication.isAuthenticated()) {
            //     Object principal = authentication.getPrincipal();
            //     if (principal instanceof UserDetails) {
            //         // 从UserDetails中获取用户ID
            //         return ((CustomUserDetails) principal).getUserId();
            //     }
            // }
            return null;
        }
    }
}