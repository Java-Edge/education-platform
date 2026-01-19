# Drools规则引擎集成 - 运行验证指南

## ⚠️ 编译问题说明

经过检查，发现项目存在以下编译问题：

### 1. Java版本兼容性问题
- 项目使用Java 23
- common模块的类文件版本可能不匹配
- Lombok注解未正确生成getter/setter方法

### 2. 已创建的核心文件

✅ **配置和服务类：**
- `DroolsConfig.java` - 规则引擎配置
- `DroolsRuleService.java` - 规则执行服务

✅ **事实对象：**
- `UserSignIntegralFact.java` 
- `CourseRecommendFact.java`
- `UserLevelFact.java`

✅ **规则文件：**
- `user-sign-integral-rules.drl`
- `course-recommend-rules.drl`
- `user-level-rules.drl`

✅ **依赖配置：**
- 已在父POM添加Drools依赖
- 已在common模块添加Drools依赖

## 🔧 修复步骤

### 步骤1：清理并重新编译项目

```bash
# 进入项目根目录
cd /Users/javaedge/soft/IDEAProjects/education-platform

# 清理并安装
mvn clean install -DskipTests
```

### 步骤2：如果编译失败，尝试以下方案

**方案A：检查Lombok配置**
```bash
# 确保IDE已安装Lombok插件
# IDEA: File -> Settings -> Plugins -> 搜索Lombok

# 启用注解处理
# File -> Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors
# ✓ Enable annotation processing
```

**方案B：降级Drools版本（如果Java版本有兼容性问题）**

在`pom.xml`中修改：
```xml
<drools.version>8.44.0.Final</drools.version>
```

### 步骤3：验证规则文件加载

创建测试类验证规则引擎：

```java
package com.javaedge.back;

import com.javaedge.common.drools.DroolsRuleService;
import com.javaedge.common.drools.model.UserSignIntegralFact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DroolsTest {

    @Autowired
    private DroolsRuleService droolsRuleService;

    @Test
    public void testSignIntegralRules() {
        // 测试连续签到7天
        UserSignIntegralFact fact = UserSignIntegralFact.builder()
                .userId(1)
                .continuousSignDays(7)
                .currentMonth(1)
                .totalDaysInMonth(31)
                .isFullMonthSign(false)
                .baseIntegral(0)
                .bonusIntegral(0)
                .totalIntegral(0)
                .bonusDescription("")
                .build();

        int firedRules = droolsRuleService.executeRules(fact);

        System.out.println("执行规则数：" + firedRules);
        System.out.println("基础积分：" + fact.getBaseIntegral());
        System.out.println("奖励积分：" + fact.getBonusIntegral());
        System.out.println("总积分：" + fact.getTotalIntegral());
        System.out.println("奖励说明：" + fact.getBonusDescription());
    }
}
```

## 🎯 临时解决方案

如果遇到编译问题，可以先使用简化版本：

### 简化版DroolsConfig

```java
package com.javaedge.common.drools;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {

    @Bean
    public KieContainer kieContainer() {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        
        // 手动加载规则文件
        kieFileSystem.write("src/main/resources/rules/user-sign-integral-rules.drl",
                kieServices.getResources().newClassPathResource("rules/user-sign-integral-rules.drl"));
        
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        
        KieModule kieModule = kieBuilder.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }
}
```

## 📊 预期结果

如果规则引擎正常工作，测试应该输出：

```
执行规则数：3
基础积分：10
奖励积分：20
总积分：30
奖励说明：连续签到7天+20分;
```

## 🐛 常见问题排查

### 问题1：ClassNotFoundException for Drools classes
**解决方案：** 
```bash
mvn dependency:tree | grep drools
# 检查依赖是否正确加载
```

### 问题2：规则文件未找到
**解决方案：**
- 确认规则文件在`src/main/resources/rules/`目录
- 检查文件编码为UTF-8
- 确认文件扩展名为`.drl`

### 问题3：规则编译错误
**解决方案：**
- 查看启动日志中的详细错误信息
- 检查规则文件语法
- 确认package和import语句正确

## 📞 后续操作建议

1. **立即执行：** `mvn clean install -DskipTests`
2. **查看错误：** 如果编译失败，查看具体错误信息
3. **反馈问题：** 将错误日志反馈，我可以提供针对性的解决方案
4. **逐步验证：** 先验证基础配置，再测试规则执行

## 💡 实际应用建议

由于当前可能存在编译问题，建议采用以下策略：

### 短期方案（保守）
- 继续使用现有的`UserIntegralServiceImpl`
- 将规则逻辑作为参考文档
- 待环境稳定后再切换到规则引擎

### 长期方案（推荐）
- 解决编译问题
- 完整测试规则引擎
- 逐步迁移业务逻辑到规则文件

---

**状态：** ⚠️ 需要编译验证  
**优先级：** 解决Java版本兼容性和Lombok配置问题  
**下一步：** 执行`mvn clean install`并反馈结果
