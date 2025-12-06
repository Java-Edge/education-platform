-- ========================================
-- Spring Boot 数据库连接问题修复脚本
-- ========================================
-- 执行方式：在 MySQL 服务器上以 root 用户执行
-- mysql -u root -p < fix_database_access.sql
-- ========================================

-- 1. 查看当前 root 用户的授权情况
SELECT
    host,
    user,
    authentication_string,
    plugin
FROM mysql.user
WHERE user = 'root';

-- 2. 创建或更新 root 用户，允许从应用服务器访问
-- 注意：根据实际情况选择合适的 IP 地址

-- 方式 A：允许特定 IP 访问（推荐用于生产环境）
CREATE USER IF NOT EXISTS 'root'@'192.168.5.16' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON education.* TO 'root'@'192.168.5.16';

CREATE USER IF NOT EXISTS 'root'@'192.168.6.129' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON education.* TO 'root'@'192.168.6.129';

-- 方式 B：允许本地回环访问
CREATE USER IF NOT EXISTS 'root'@'127.0.0.1' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON education.* TO 'root'@'127.0.0.1';

-- 方式 C：允许所有 IP 访问（仅用于开发环境，生产环境不推荐）
-- CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED BY '123456';
-- GRANT ALL PRIVILEGES ON education.* TO 'root'@'%';

-- 3. 刷新权限
FLUSH PRIVILEGES;

-- 4. 验证授权结果
SHOW GRANTS FOR 'root'@'192.168.5.16';
SHOW GRANTS FOR 'root'@'192.168.6.129';

-- 5. 检查 education 数据库是否存在
SHOW DATABASES LIKE 'education';

-- 6. 如果数据库不存在，创建它
CREATE DATABASE IF NOT EXISTS education
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 7. 验证数据库
USE education;
SHOW TABLES;

-- ========================================
-- 额外的安全建议
-- ========================================

-- 建议：创建专用的应用用户（而不是使用 root）
-- CREATE USER IF NOT EXISTS 'eduapp'@'192.168.5.16' IDENTIFIED BY 'strong_password_here';
-- GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER ON education.* TO 'eduapp'@'192.168.5.16';
-- FLUSH PRIVILEGES;

-- ========================================
-- 完成提示
-- ========================================
SELECT 'Database access configuration completed!' AS Status;

