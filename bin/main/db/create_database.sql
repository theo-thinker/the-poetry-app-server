-- ========================================
-- 企业级诗词APP后端服务数据库创建脚本
-- 作者: Sakura Huang
-- 创建时间: 2025-09-03
-- 版本: 1.0.0
-- 描述: 创建数据库和用户
-- ========================================

-- 创建开发环境数据库
CREATE DATABASE IF NOT EXISTS `poetry_app_dev` 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 创建生产环境数据库
CREATE DATABASE IF NOT EXISTS `poetry_app_prod` 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 创建测试环境数据库
CREATE DATABASE IF NOT EXISTS `poetry_app_test` 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 创建数据库用户（可选，用于生产环境）
-- CREATE USER 'poetry_user'@'%' IDENTIFIED BY 'your_secure_password';
-- GRANT ALL PRIVILEGES ON poetry_app_prod.* TO 'poetry_user'@'%';
-- GRANT ALL PRIVILEGES ON poetry_app_dev.* TO 'poetry_user'@'%';
-- GRANT ALL PRIVILEGES ON poetry_app_test.* TO 'poetry_user'@'%';
-- FLUSH PRIVILEGES;