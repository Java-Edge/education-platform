#!/bin/bash

# ========================================
# Spring Boot 启动问题快速诊断脚本
# ========================================

echo "======================================"
echo "Spring Boot 启动问题诊断工具"
echo "======================================"
echo ""

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 配置变量
DB_HOST="192.168.5.16"
DB_PORT="3306"
DB_USER="root"
DB_PASS="123456"
DB_NAME="education"

echo "1. 检查数据库网络连接..."
if nc -zv $DB_HOST $DB_PORT 2>&1 | grep -q "succeeded"; then
    echo -e "${GREEN}✓${NC} 数据库端口 $DB_HOST:$DB_PORT 可以访问"
else
    echo -e "${RED}✗${NC} 数据库端口 $DB_HOST:$DB_PORT 无法访问"
    echo "   请检查："
    echo "   - 数据库服务是否运行"
    echo "   - 防火墙规则"
    echo "   - 网络连接"
fi
echo ""

echo "2. 检查本机 IP 地址..."
echo "   本机 IP 地址列表："
if command -v ifconfig &> /dev/null; then
    ifconfig | grep "inet " | grep -v 127.0.0.1 | awk '{print "   - " $2}'
elif command -v ip &> /dev/null; then
    ip addr | grep "inet " | grep -v 127.0.0.1 | awk '{print "   - " $2}' | cut -d'/' -f1
fi
echo "   注意：确保 MySQL 授权了以上 IP 地址"
echo ""

echo "3. 测试 MySQL 连接..."
if command -v mysql &> /dev/null; then
    if mysql -h $DB_HOST -P $DB_PORT -u $DB_USER -p$DB_PASS -e "SELECT 1;" 2>&1 | grep -q "ERROR"; then
        echo -e "${RED}✗${NC} MySQL 连接失败"
        echo "   可能原因："
        echo "   - 用户名或密码错误"
        echo "   - 用户未被授权从当前 IP 访问"
        echo "   - 数据库服务未运行"
    else
        echo -e "${GREEN}✓${NC} MySQL 连接成功"

        # 检查数据库是否存在
        if mysql -h $DB_HOST -P $DB_PORT -u $DB_USER -p$DB_PASS -e "USE $DB_NAME;" 2>&1 | grep -q "ERROR"; then
            echo -e "${YELLOW}⚠${NC} 数据库 '$DB_NAME' 不存在"
            echo "   建议执行：CREATE DATABASE $DB_NAME;"
        else
            echo -e "${GREEN}✓${NC} 数据库 '$DB_NAME' 存在"
        fi
    fi
else
    echo -e "${YELLOW}⚠${NC} 本机未安装 mysql 客户端，跳过连接测试"
    echo "   可以使用以下命令测试："
    echo "   mysql -h $DB_HOST -P $DB_PORT -u $DB_USER -p"
fi
echo ""

echo "4. 检查 Java 环境..."
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1)
    echo -e "${GREEN}✓${NC} Java 已安装: $JAVA_VERSION"
else
    echo -e "${RED}✗${NC} Java 未安装"
fi
echo ""

echo "5. 检查 Maven 环境..."
if command -v mvn &> /dev/null; then
    MVN_VERSION=$(mvn -version 2>&1 | head -n 1)
    echo -e "${GREEN}✓${NC} Maven 已安装: $MVN_VERSION"
else
    echo -e "${YELLOW}⚠${NC} Maven 未安装"
fi
echo ""

echo "6. 检查应用配置文件..."
CONFIG_FILE="education-back/src/main/resources/application.yml"
if [ -f "$CONFIG_FILE" ]; then
    echo -e "${GREEN}✓${NC} 配置文件存在: $CONFIG_FILE"

    # 检查数据库配置
    if grep -q "dialect.*MySQLDialect" "$CONFIG_FILE"; then
        echo -e "${GREEN}✓${NC} Hibernate 方言已配置"
    else
        echo -e "${YELLOW}⚠${NC} Hibernate 方言未配置或配置不正确"
    fi

    # 检查数据库 URL
    DB_URL=$(grep "url:" "$CONFIG_FILE" | grep jdbc | head -n 1)
    if [ -n "$DB_URL" ]; then
        echo "   数据库 URL: ${DB_URL}"
    fi
else
    echo -e "${RED}✗${NC} 配置文件不存在: $CONFIG_FILE"
fi
echo ""

echo "7. 检查最近的错误日志..."
ERROR_LOG=$(find LOG_PATH_IS_UNDEFINED -name "error.*.log" -type f -mtime -1 2>/dev/null | head -n 1)
if [ -n "$ERROR_LOG" ] && [ -f "$ERROR_LOG" ]; then
    echo "   最新错误日志: $ERROR_LOG"
    echo "   最后 5 行错误："
    tail -n 5 "$ERROR_LOG" | sed 's/^/   /'
else
    echo "   未找到最近的错误日志"
fi
echo ""

echo "======================================"
echo "诊断完成！"
echo "======================================"
echo ""
echo "修复建议："
echo "1. 如果数据库连接失败，执行 SQL 脚本修复权限："
echo "   mysql -h $DB_HOST -u root -p < fix_database_access.sql"
echo ""
echo "2. 如果配置有问题，application.yml 已更新，请重启应用"
echo ""
echo "3. 重启应用命令："
echo "   cd /Users/javaedge/soft/IDEAProjects/education-platform"
echo "   mvn clean spring-boot:run"
echo ""
echo "4. 查看完整排查文档："
echo "   cat TROUBLESHOOTING.md"
echo ""

