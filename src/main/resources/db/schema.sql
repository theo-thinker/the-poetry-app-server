-- ========================================
-- 企业级诗词APP后端服务数据库表结构脚本
-- 作者: Sakura Huang
-- 创建时间: 2025-09-03
-- 版本: 1.0.0
-- 描述: 包含所有业务模块的表结构定义
-- ========================================

USE `poetry_app_dev`;

-- ========================================
-- 1. 用户管理模块
-- ========================================

-- 用户基础信息表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`        VARCHAR(50)  NOT NULL COMMENT '用户名',
    `nickname`        VARCHAR(100) DEFAULT NULL COMMENT '昵称',
    `email`           VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `phone`           VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
    `password`        VARCHAR(255) NOT NULL COMMENT '密码(加密)',
    `avatar`          VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `gender`          TINYINT      DEFAULT 0 COMMENT '性别:0-未知,1-男,2-女',
    `birthday`        DATE         DEFAULT NULL COMMENT '生日',
    `signature`       VARCHAR(500) DEFAULT NULL COMMENT '个性签名',
    `status`          TINYINT      DEFAULT 1 COMMENT '状态:0-禁用,1-启用',
    `last_login_time` DATETIME     DEFAULT NULL COMMENT '最后登录时间',
    `last_login_ip`   VARCHAR(50)  DEFAULT NULL COMMENT '最后登录IP',
    `created_by`      BIGINT       DEFAULT NULL COMMENT '创建人ID',
    `created_time`    DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`      BIGINT       DEFAULT NULL COMMENT '更新人ID',
    `updated_time`    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`      TINYINT      DEFAULT 0 COMMENT '是否删除:0-否,1-是',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    UNIQUE KEY `uk_phone` (`phone`),
    KEY `idx_status` (`status`),
    KEY `idx_created_time` (`created_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户基础信息表';

-- 角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`    VARCHAR(50) NOT NULL COMMENT '角色名称',
    `role_code`    VARCHAR(50) NOT NULL COMMENT '角色编码',
    `description`  VARCHAR(255) DEFAULT NULL COMMENT '角色描述',
    `sort_order`   INT          DEFAULT 0 COMMENT '排序',
    `status`       TINYINT      DEFAULT 1 COMMENT '状态:0-禁用,1-启用',
    `created_by`   BIGINT       DEFAULT NULL COMMENT '创建人ID',
    `created_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   BIGINT       DEFAULT NULL COMMENT '更新人ID',
    `updated_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`   TINYINT      DEFAULT 0 COMMENT '是否删除:0-否,1-是',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_code` (`role_code`),
    KEY `idx_status` (`status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='角色表';

-- 用户角色关联表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`           BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`      BIGINT NOT NULL COMMENT '用户ID',
    `role_id`      BIGINT NOT NULL COMMENT '角色ID',
    `created_by`   BIGINT   DEFAULT NULL COMMENT '创建人ID',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_role_id` (`role_id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户角色关联表';

-- ========================================
-- 2. 诗词内容模块
-- ========================================

-- 诗词分类表
DROP TABLE IF EXISTS `poetry_category`;
CREATE TABLE `poetry_category`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `category_name` VARCHAR(100) NOT NULL COMMENT '分类名称',
    `category_code` VARCHAR(50)  NOT NULL COMMENT '分类编码',
    `parent_id`     BIGINT       DEFAULT 0 COMMENT '父分类ID,0为顶级分类',
    `level`         TINYINT      DEFAULT 1 COMMENT '分类层级',
    `sort_order`    INT          DEFAULT 0 COMMENT '排序',
    `description`   VARCHAR(500) DEFAULT NULL COMMENT '分类描述',
    `cover_image`   VARCHAR(500) DEFAULT NULL COMMENT '分类封面图',
    `status`        TINYINT      DEFAULT 1 COMMENT '状态:0-禁用,1-启用',
    `created_by`    BIGINT       DEFAULT NULL COMMENT '创建人ID',
    `created_time`  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`    BIGINT       DEFAULT NULL COMMENT '更新人ID',
    `updated_time`  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`    TINYINT      DEFAULT 0 COMMENT '是否删除:0-否,1-是',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_category_code` (`category_code`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_status` (`status`),
    KEY `idx_sort_order` (`sort_order`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='诗词分类表';

-- 朝代表
DROP TABLE IF EXISTS `dynasty`;
CREATE TABLE `dynasty`
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT COMMENT '朝代ID',
    `dynasty_name` VARCHAR(50) NOT NULL COMMENT '朝代名称',
    `dynasty_code` VARCHAR(20) NOT NULL COMMENT '朝代编码',
    `start_year`   INT      DEFAULT NULL COMMENT '开始年份',
    `end_year`     INT      DEFAULT NULL COMMENT '结束年份',
    `description`  TEXT     DEFAULT NULL COMMENT '朝代描述',
    `sort_order`   INT      DEFAULT 0 COMMENT '排序',
    `status`       TINYINT  DEFAULT 1 COMMENT '状态:0-禁用,1-启用',
    `created_by`   BIGINT   DEFAULT NULL COMMENT '创建人ID',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   BIGINT   DEFAULT NULL COMMENT '更新人ID',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`   TINYINT  DEFAULT 0 COMMENT '是否删除:0-否,1-是',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_dynasty_code` (`dynasty_code`),
    KEY `idx_status` (`status`),
    KEY `idx_sort_order` (`sort_order`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='朝代表';

-- 作者表
DROP TABLE IF EXISTS `poet`;
CREATE TABLE `poet`
(
    `id`                   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '诗人ID',
    `poet_name`            VARCHAR(100) NOT NULL COMMENT '诗人姓名',
    `poet_alias`           VARCHAR(200) DEFAULT NULL COMMENT '诗人别名/字号',
    `dynasty_id`           BIGINT       DEFAULT NULL COMMENT '朝代ID',
    `birth_year`           INT          DEFAULT NULL COMMENT '出生年份',
    `death_year`           INT          DEFAULT NULL COMMENT '逝世年份',
    `birthplace`           VARCHAR(200) DEFAULT NULL COMMENT '出生地',
    `biography`            TEXT         DEFAULT NULL COMMENT '生平简介',
    `achievements`         TEXT         DEFAULT NULL COMMENT '主要成就',
    `representative_works` TEXT         DEFAULT NULL COMMENT '代表作品',
    `avatar`               VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `view_count`           BIGINT       DEFAULT 0 COMMENT '查看次数',
    `like_count`           BIGINT       DEFAULT 0 COMMENT '点赞次数',
    `status`               TINYINT      DEFAULT 1 COMMENT '状态:0-禁用,1-启用',
    `created_by`           BIGINT       DEFAULT NULL COMMENT '创建人ID',
    `created_time`         DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`           BIGINT       DEFAULT NULL COMMENT '更新人ID',
    `updated_time`         DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`           TINYINT      DEFAULT 0 COMMENT '是否删除:0-否,1-是',
    PRIMARY KEY (`id`),
    KEY `idx_dynasty_id` (`dynasty_id`),
    KEY `idx_poet_name` (`poet_name`),
    KEY `idx_status` (`status`),
    KEY `idx_view_count` (`view_count`),
    KEY `idx_like_count` (`like_count`),
    FOREIGN KEY (`dynasty_id`) REFERENCES `dynasty` (`id`) ON DELETE SET NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='诗人表';

-- 诗词主表
DROP TABLE IF EXISTS `poetry`;
CREATE TABLE `poetry`
(
    `id`               BIGINT       NOT NULL AUTO_INCREMENT COMMENT '诗词ID',
    `title`            VARCHAR(200) NOT NULL COMMENT '诗词标题',
    `subtitle`         VARCHAR(200) DEFAULT NULL COMMENT '副标题',
    `poet_id`          BIGINT       NOT NULL COMMENT '作者ID',
    `dynasty_id`       BIGINT       DEFAULT NULL COMMENT '朝代ID',
    `category_id`      BIGINT       DEFAULT NULL COMMENT '分类ID',
    `content`          TEXT         NOT NULL COMMENT '诗词内容',
    `content_format`   TINYINT      DEFAULT 1 COMMENT '内容格式:1-原文,2-现代文',
    `translation`      TEXT         DEFAULT NULL COMMENT '译文',
    `annotation`       TEXT         DEFAULT NULL COMMENT '注释',
    `appreciation`     TEXT         DEFAULT NULL COMMENT '赏析',
    `background`       TEXT         DEFAULT NULL COMMENT '创作背景',
    `tags`             VARCHAR(500) DEFAULT NULL COMMENT '标签(逗号分隔)',
    `difficulty_level` TINYINT      DEFAULT 1 COMMENT '难度等级:1-简单,2-中等,3-困难',
    `word_count`       INT          DEFAULT 0 COMMENT '字数',
    `verse_count`      INT          DEFAULT 0 COMMENT '句数',
    `rhythm`           VARCHAR(100) DEFAULT NULL COMMENT '韵律',
    `rhyme_scheme`     VARCHAR(100) DEFAULT NULL COMMENT '押韵方式',
    `view_count`       BIGINT       DEFAULT 0 COMMENT '浏览次数',
    `like_count`       BIGINT       DEFAULT 0 COMMENT '点赞次数',
    `collect_count`    BIGINT       DEFAULT 0 COMMENT '收藏次数',
    `share_count`      BIGINT       DEFAULT 0 COMMENT '分享次数',
    `comment_count`    BIGINT       DEFAULT 0 COMMENT '评论次数',
    `is_featured`      TINYINT      DEFAULT 0 COMMENT '是否精选:0-否,1-是',
    `is_hot`           TINYINT      DEFAULT 0 COMMENT '是否热门:0-否,1-是',
    `source`           VARCHAR(200) DEFAULT NULL COMMENT '来源',
    `copyright_info`   VARCHAR(500) DEFAULT NULL COMMENT '版权信息',
    `status`           TINYINT      DEFAULT 1 COMMENT '状态:0-草稿,1-已发布,2-下线',
    `publish_time`     DATETIME     DEFAULT NULL COMMENT '发布时间',
    `created_by`       BIGINT       DEFAULT NULL COMMENT '创建人ID',
    `created_time`     DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`       BIGINT       DEFAULT NULL COMMENT '更新人ID',
    `updated_time`     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`       TINYINT      DEFAULT 0 COMMENT '是否删除:0-否,1-是',
    PRIMARY KEY (`id`),
    KEY `idx_poet_id` (`poet_id`),
    KEY `idx_dynasty_id` (`dynasty_id`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_title` (`title`),
    KEY `idx_status` (`status`),
    KEY `idx_view_count` (`view_count`),
    KEY `idx_like_count` (`like_count`),
    KEY `idx_is_featured` (`is_featured`),
    KEY `idx_is_hot` (`is_hot`),
    KEY `idx_publish_time` (`publish_time`),
    FULLTEXT KEY `ft_content` (`content`, `translation`, `annotation`),
    FOREIGN KEY (`poet_id`) REFERENCES `poet` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`dynasty_id`) REFERENCES `dynasty` (`id`) ON DELETE SET NULL,
    FOREIGN KEY (`category_id`) REFERENCES `poetry_category` (`id`) ON DELETE SET NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='诗词主表';

-- ========================================
-- 3. 用户交互模块
-- ========================================

-- 收藏夹表
DROP TABLE IF EXISTS `collection_folder`;
CREATE TABLE `collection_folder`
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '收藏夹ID',
    `user_id`      BIGINT       NOT NULL COMMENT '用户ID',
    `folder_name`  VARCHAR(100) NOT NULL COMMENT '收藏夹名称',
    `description`  VARCHAR(500) DEFAULT NULL COMMENT '收藏夹描述',
    `cover_image`  VARCHAR(500) DEFAULT NULL COMMENT '封面图片',
    `is_public`    TINYINT      DEFAULT 0 COMMENT '是否公开:0-私有,1-公开',
    `poetry_count` INT          DEFAULT 0 COMMENT '诗词数量',
    `sort_order`   INT          DEFAULT 0 COMMENT '排序',
    `created_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`   TINYINT      DEFAULT 0 COMMENT '是否删除:0-否,1-是',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_is_public` (`is_public`),
    KEY `idx_sort_order` (`sort_order`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='收藏夹表';

-- 用户收藏表
DROP TABLE IF EXISTS `user_collection`;
CREATE TABLE `user_collection`
(
    `id`                   BIGINT NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    `user_id`              BIGINT NOT NULL COMMENT '用户ID',
    `poetry_id`            BIGINT NOT NULL COMMENT '诗词ID',
    `collection_folder_id` BIGINT   DEFAULT NULL COMMENT '收藏夹ID',
    `notes`                TEXT     DEFAULT NULL COMMENT '收藏备注',
    `created_time`         DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_poetry` (`user_id`, `poetry_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_poetry_id` (`poetry_id`),
    KEY `idx_collection_folder_id` (`collection_folder_id`),
    KEY `idx_created_time` (`created_time`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`poetry_id`) REFERENCES `poetry` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`collection_folder_id`) REFERENCES `collection_folder` (`id`) ON DELETE SET NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户收藏表';

-- 用户点赞表
DROP TABLE IF EXISTS `user_like`;
CREATE TABLE `user_like`
(
    `id`           BIGINT  NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
    `user_id`      BIGINT  NOT NULL COMMENT '用户ID',
    `target_type`  TINYINT NOT NULL COMMENT '点赞目标类型:1-诗词,2-评论,3-诗人',
    `target_id`    BIGINT  NOT NULL COMMENT '目标ID',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_target` (`user_id`, `target_type`, `target_id`),
    KEY `idx_target` (`target_type`, `target_id`),
    KEY `idx_created_time` (`created_time`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户点赞表';

-- 用户评论表
DROP TABLE IF EXISTS `user_comment`;
CREATE TABLE `user_comment`
(
    `id`           BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `user_id`      BIGINT NOT NULL COMMENT '用户ID',
    `poetry_id`    BIGINT NOT NULL COMMENT '诗词ID',
    `parent_id`    BIGINT       DEFAULT 0 COMMENT '父评论ID,0为顶级评论',
    `content`      TEXT   NOT NULL COMMENT '评论内容',
    `like_count`   INT          DEFAULT 0 COMMENT '点赞数',
    `reply_count`  INT          DEFAULT 0 COMMENT '回复数',
    `status`       TINYINT      DEFAULT 1 COMMENT '状态:0-待审核,1-已通过,2-已拒绝',
    `ip_address`   VARCHAR(50)  DEFAULT NULL COMMENT 'IP地址',
    `user_agent`   VARCHAR(500) DEFAULT NULL COMMENT '用户代理',
    `created_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`   TINYINT      DEFAULT 0 COMMENT '是否删除:0-否,1-是',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_poetry_id` (`poetry_id`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_status` (`status`),
    KEY `idx_created_time` (`created_time`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`poetry_id`) REFERENCES `poetry` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户评论表';

-- ========================================
-- 4. 系统管理模块
-- ========================================

-- 系统配置表
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    `config_key`   VARCHAR(100) NOT NULL COMMENT '配置键',
    `config_value` TEXT         DEFAULT NULL COMMENT '配置值',
    `config_name`  VARCHAR(200) NOT NULL COMMENT '配置名称',
    `config_type`  VARCHAR(50)  DEFAULT 'string' COMMENT '配置类型:string,number,boolean,json',
    `config_group` VARCHAR(50)  DEFAULT 'default' COMMENT '配置分组',
    `description`  VARCHAR(500) DEFAULT NULL COMMENT '配置描述',
    `is_system`    TINYINT      DEFAULT 0 COMMENT '是否系统配置:0-否,1-是',
    `sort_order`   INT          DEFAULT 0 COMMENT '排序',
    `status`       TINYINT      DEFAULT 1 COMMENT '状态:0-禁用,1-启用',
    `created_by`   BIGINT       DEFAULT NULL COMMENT '创建人ID',
    `created_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   BIGINT       DEFAULT NULL COMMENT '更新人ID',
    `updated_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_config_key` (`config_key`),
    KEY `idx_config_group` (`config_group`),
    KEY `idx_status` (`status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统配置表';

-- 系统日志表
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `id`           BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `user_id`      BIGINT       DEFAULT NULL COMMENT '用户ID',
    `username`     VARCHAR(50)  DEFAULT NULL COMMENT '用户名',
    `operation`    VARCHAR(200) DEFAULT NULL COMMENT '操作内容',
    `method`       VARCHAR(200) DEFAULT NULL COMMENT '请求方法',
    `params`       TEXT         DEFAULT NULL COMMENT '请求参数',
    `time`         BIGINT       DEFAULT NULL COMMENT '执行时长(毫秒)',
    `ip`           VARCHAR(50)  DEFAULT NULL COMMENT 'IP地址',
    `location`     VARCHAR(200) DEFAULT NULL COMMENT '操作地点',
    `user_agent`   VARCHAR(500) DEFAULT NULL COMMENT '用户代理',
    `status`       TINYINT      DEFAULT 1 COMMENT '操作状态:0-失败,1-成功',
    `error_msg`    TEXT         DEFAULT NULL COMMENT '错误消息',
    `created_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_operation` (`operation`),
    KEY `idx_status` (`status`),
    KEY `idx_created_time` (`created_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统日志表';

-- ========================================
-- 5. 索引优化
-- ========================================

-- 为经常查询的字段添加复合索引
ALTER TABLE `poetry`
    ADD INDEX `idx_status_featured_hot` (`status`, `is_featured`, `is_hot`);
ALTER TABLE `poetry`
    ADD INDEX `idx_poet_status` (`poet_id`, `status`);
ALTER TABLE `poetry`
    ADD INDEX `idx_category_status` (`category_id`, `status`);
ALTER TABLE `poetry`
    ADD INDEX `idx_dynasty_status` (`dynasty_id`, `status`);

-- 为用户相关表添加复合索引
ALTER TABLE `user_collection`
    ADD INDEX `idx_user_folder` (`user_id`, `collection_folder_id`);
ALTER TABLE `user_like`
    ADD INDEX `idx_user_type_target` (`user_id`, `target_type`, `target_id`);
ALTER TABLE `user_comment`
    ADD INDEX `idx_poetry_status` (`poetry_id`, `status`);
