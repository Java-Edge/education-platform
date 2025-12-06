# 🎯 立即执行：数据库权限修复指令

## 当前问题确认 ✅

通过诊断脚本确认：
- ✅ 数据库服务器可访问（192.168.5.16:3306）
- ✅ 应用配置已修复（Hibernate 方言已配置）
- ❌ **问题根因**：MySQL 未授权 `root@192.168.6.129` 访问

---

## 🚨 立即执行（3 步）

### **第 1 步：在 MySQL 服务器上执行授权**

#### 选项 A：使用 SQL 脚本（推荐）⭐
```bash
# 1. 将 fix_database_access.sql 复制到 MySQL 服务器
scp fix_database_access.sql user@192.168.5.16:/tmp/

# 2. 在 MySQL 服务器上执行
ssh user@192.168.5.16
mysql -u root -p < /tmp/fix_database_access.sql
```

#### 选项 B：直接执行 SQL 命令
```bash
# 登录 MySQL 服务器
ssh user@192.168.5.16

# 执行以下命令
mysql -u root -p
```

```sql
-- 在 MySQL 命令行中执行
CREATE USER IF NOT EXISTS 'root'@'192.168.6.129' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON education.* TO 'root'@'192.168.6.129';
FLUSH PRIVILEGES;

-- 验证授权
SHOW GRANTS FOR 'root'@'192.168.6.129';

-- 检查数据库
SHOW DATABASES LIKE 'education';

-- 如果数据库不存在，创建它
CREATE DATABASE IF NOT EXISTS education 
    DEFAULT CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- 退出
EXIT;
```

---

### **第 2 步：重启 Spring Boot 应用**

```bash
# 在本机执行
cd /Users/javaedge/soft/IDEAProjects/education-platform

# 清理并启动
mvn clean spring-boot:run
```

---

### **第 3 步：验证启动**

```bash
# 打开新终端，监控日志
tail -f LOG_PATH_IS_UNDEFINED/$(date +%Y-%m-%d)/info.0.log

# 如果有错误，查看错误日志
tail -f LOG_PATH_IS_UNDEFINED/$(date +%Y-%m-%d)/error.0.log
```

**成功标志**：
- 看到 `Started BackApplication in X.XXX seconds`
- 可以访问 http://localhost:8088

---

## 🔍 如果还有问题

### 问题 1：无法连接到 MySQL 服务器
```bash
# 检查 MySQL 服务状态
ssh user@192.168.5.16
sudo systemctl status mysql

# 检查 MySQL 绑定地址
cat /etc/mysql/mysql.conf.d/mysqld.cnf | grep bind-address
# 应该是 0.0.0.0 或注释掉，而不是 127.0.0.1

# 检查防火墙
sudo ufw status
sudo ufw allow 3306/tcp
```

### 问题 2：数据库不存在
```sql
-- 创建数据库
CREATE DATABASE education 
    DEFAULT CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;
```

### 问题 3：密码错误
```sql
-- 重置密码
ALTER USER 'root'@'192.168.6.129' IDENTIFIED BY '123456';
FLUSH PRIVILEGES;
```

---

## 📊 快速验证清单

- [ ] MySQL 授权已完成（`SHOW GRANTS` 验证）
- [ ] 数据库 `education` 已创建（`SHOW DATABASES` 验证）
- [ ] 应用配置正确（检查 `application.yml`）
- [ ] 应用成功启动（查看启动日志）
- [ ] 可以访问应用端点（http://localhost:8088）

---

## 💡 安全建议（生产环境）

```sql
-- 1. 创建专用数据库用户（推荐）
CREATE USER 'eduapp'@'192.168.6.129' IDENTIFIED BY 'StrongPassword123!';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, INDEX, ALTER 
ON education.* TO 'eduapp'@'192.168.6.129';
FLUSH PRIVILEGES;
```

然后修改 `application.yml`：
```yaml
spring:
  datasource:
    username: eduapp
    password: StrongPassword123!
```

---

## 📞 紧急求助

如果执行后仍有问题，提供以下信息：
1. MySQL 错误日志：`cat /var/log/mysql/error.log | tail -50`
2. 应用错误日志：最新的 `error.0.log` 内容
3. MySQL 用户列表：`SELECT host, user FROM mysql.user WHERE user='root';`
4. 网络连接测试：`telnet 192.168.5.16 3306`

---

**创建时间**：2025-12-05  
**预计修复时间**：5 分钟  
**优先级**：🔴 高

