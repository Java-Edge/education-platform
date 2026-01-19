# Drools规则引擎集成方案

## 📋 项目概述

本项目已成功集成Drools规则引擎，用于优化和管理复杂的业务规则。Drools是一个业务规则管理系统(BRMS)，它将业务逻辑从代码中分离出来，使得业务规则更易于维护和修改。

## 🎯 规则引擎应用场景

### 1. 用户积分规则系统 ⭐⭐⭐⭐⭐
**场景说明：**
- 签到积分计算
- 连续签到奖励（3天、7天、15天、30天）
- 整月签到特别奖励
- 周末签到额外奖励

**优化效果：**
- ✅ 规则集中管理，易于调整积分策略
- ✅ 新增签到活动无需修改代码
- ✅ 支持动态配置不同时期的积分规则

### 2. 课程推荐规则 ⭐⭐⭐⭐
**场景说明：**
- 高人气课程推荐（PV >= 10000）
- 热门课程推荐（PV >= 5000）
- 新课程推荐（PV < 1000）
- 专栏课程额外加分
- 视频课程基础推荐

**优化效果：**
- ✅ 推荐算法可视化，业务人员可理解
- ✅ 灵活调整推荐权重和阈值
- ✅ 支持A/B测试不同推荐策略

### 3. 用户等级规则 ⭐⭐⭐⭐
**场景说明：**
- 根据积分划分用户等级（青铜、白银、黄金、铂金、钻石、至尊）
- 活跃用户额外加成
- 课程达人额外加成

**优化效果：**
- ✅ 等级体系易于扩展
- ✅ 权益配置灵活可调
- ✅ 支持多维度等级评定

## 🏗️ 架构设计

```
education-platform
├── common/
│   ├── src/main/java/com/javaedge/common/drools/
│   │   ├── DroolsConfig.java              # Drools配置类
│   │   ├── DroolsRuleService.java         # 规则执行服务
│   │   └── model/                          # 规则事实对象
│   │       ├── UserSignIntegralFact.java  # 签到积分事实
│   │       ├── CourseRecommendFact.java   # 课程推荐事实
│   │       └── UserLevelFact.java         # 用户等级事实
│   └── src/main/resources/rules/
│       ├── user-sign-integral-rules.drl   # 签到积分规则
│       ├── course-recommend-rules.drl     # 课程推荐规则
│       └── user-level-rules.drl           # 用户等级规则
└── education-back/
    └── src/main/java/com/javaedge/back/
        ├── service/
        │   ├── impl/
        │   │   ├── UserIntegralServiceImplWithDrools.java  # 使用规则引擎的积分服务
        │   │   └── DroolsExampleServiceImpl.java           # 规则引擎示例服务
        │   └── DroolsExampleService.java
        └── controller/
            └── DroolsExampleController.java                # 规则引擎示例接口
```

## 📦 核心组件

### 1. DroolsConfig.java
规则引擎配置类，负责初始化KieContainer和加载规则文件。

```java
@Configuration
public class DroolsConfig {
    @Bean
    public KieContainer kieContainer(KieFileSystem kieFileSystem) {
        // 加载并编译规则文件
        // 创建KieContainer
    }
}
```

### 2. DroolsRuleService.java
规则执行服务，提供统一的规则执行接口。

**主要方法：**
- `executeRules(Object fact)` - 执行单个事实对象的规则
- `executeRules(Collection<?> facts)` - 批量执行规则
- `executeRulesWithGlobal()` - 带全局变量执行规则
- `executeAndGetFact()` - 执行规则并返回更新后的事实对象

### 3. 事实对象（Fact）
事实对象是规则引擎的输入，包含业务数据和计算结果。

**主要事实对象：**
- `UserSignIntegralFact` - 用户签到积分事实
- `CourseRecommendFact` - 课程推荐事实
- `UserLevelFact` - 用户等级事实

## 🚀 使用方法

### 方式1：直接使用DroolsRuleService

```java
@Service
@RequiredArgsConstructor
public class YourService {
    private final DroolsRuleService droolsRuleService;
    
    public void businessMethod() {
        // 1. 构建事实对象
        UserSignIntegralFact fact = UserSignIntegralFact.buildSignFact(userId, days);
        
        // 2. 执行规则
        droolsRuleService.executeRules(fact);
        
        // 3. 获取计算结果
        int totalIntegral = fact.getTotalIntegral();
    }
}
```

### 方式2：使用示例服务

```java
@RestController
@RequiredArgsConstructor
public class YourController {
    private final DroolsExampleService droolsExampleService;
    
    @GetMapping("/course/recommend/{id}")
    public ResultBody recommend(@PathVariable Integer id) {
        CourseRecommendFact result = droolsExampleService.calculateCourseRecommend(id);
        return ResultBody.success(result);
    }
}
```

## 📝 规则文件编写指南

### 规则文件基本结构

```drl
package com.javaedge.rules.example

import com.javaedge.common.drools.model.YourFact

global org.slf4j.Logger logger

// 规则定义
rule "规则名称"
    salience 100  // 优先级，数字越大优先级越高
    when
        $fact : YourFact(condition)  // 条件
    then
        // 执行动作
        $fact.setSomeValue(value);
        update($fact);  // 更新事实对象
end
```

### 规则示例

```drl
// 高人气课程推荐规则
rule "高人气课程推荐"
    salience 100
    when
        $fact : CourseRecommendFact(pageView >= 10000)
    then
        logger.info("触发高人气课程推荐规则");
        $fact.setRecommendScore(50);
        $fact.setRecommendReason("高人气课程");
        $fact.setIsRecommended(true);
        update($fact);
end
```

## 🔧 API接口

### 1. 课程推荐接口

**计算单个课程推荐分数**
```http
GET /drools/course/recommend/{courseId}

响应示例：
{
  "code": 200,
  "data": {
    "courseId": 1,
    "courseName": "Java核心技术",
    "recommendScore": 80,
    "priority": 5,
    "recommendReason": "高人气课程;专栏课程;",
    "isRecommended": true
  }
}
```

**批量计算课程推荐**
```http
POST /drools/course/recommend/batch
Content-Type: application/json

[1, 2, 3, 4, 5]
```

**获取推荐课程列表**
```http
GET /drools/course/recommended?limit=10
```

### 2. 用户等级接口

**计算用户等级**
```http
GET /drools/user/level/{userId}

响应示例：
{
  "code": 200,
  "data": {
    "userId": 1,
    "totalIntegral": 1200,
    "userLevel": 3,
    "levelName": "黄金会员",
    "levelBenefits": "免费观看指定视频课程，专栏9折",
    "nextLevelIntegral": 1500
  }
}
```

## 🎨 规则优化建议

### 1. 规则优先级设计
- 100-110: 基础规则（必须执行）
- 90-99: 核心业务规则
- 80-89: 辅助规则
- 50-79: 默认规则

### 2. 规则命名规范
- 使用中文命名，清晰表达规则含义
- 格式：`业务对象-条件-动作`
- 示例：`用户签到-连续7天-奖励积分`

### 3. 规则拆分原则
- 一个规则只做一件事
- 复杂规则拆分成多个简单规则
- 使用salience控制执行顺序

## 🔍 规则调试

### 启用规则日志
```yaml
# application.yml
logging:
  level:
    drools.rules: DEBUG
    com.javaedge.common.drools: DEBUG
```

### 查看规则执行情况
规则执行时会在日志中输出：
```
触发规则：基础签到积分，用户ID：123
触发规则：连续签到7天奖励，用户ID：123
执行了 2 条规则，事实对象: UserSignIntegralFact
```

## 📊 性能优化

### 1. 规则文件组织
- 按业务模块拆分规则文件
- 避免单个文件规则过多
- 使用agenda-group分组管理

### 2. 事实对象设计
- 保持事实对象简洁
- 避免在事实对象中进行复杂计算
- 合理使用索引字段

### 3. 规则执行优化
- 使用stateless session处理无状态规则
- 批量处理时使用集合插入
- 及时dispose session释放资源

## 🎯 扩展规则示例

### 添加新的积分规则

**1. 在规则文件中添加新规则**
```drl
// 节假日签到双倍积分
rule "节假日签到双倍积分"
    salience 95
    when
        $fact : UserSignIntegralFact()
        eval(isHoliday())  // 自定义函数判断是否节假日
    then
        logger.info("触发节假日双倍积分规则");
        $fact.setBaseIntegral($fact.getBaseIntegral() * 2);
        $fact.setBonusDescription($fact.getBonusDescription() + "节假日双倍;");
        $fact.calculateTotal();
        update($fact);
end
```

**2. 重启应用即可生效**（规则文件在类路径下，修改后需重启）

### 动态规则加载（高级功能）
如需实现运行时动态修改规则，可以：
1. 将规则存储在数据库
2. 实现规则热加载机制
3. 提供规则管理界面

## ⚠️ 注意事项

1. **规则文件编码**：必须使用UTF-8编码
2. **规则编译**：规则文件修改后需要重启应用
3. **循环依赖**：避免规则之间的循环更新
4. **性能监控**：关注规则执行时间，复杂规则可能影响性能
5. **事务管理**：规则执行与数据库操作在同一事务中

## 📚 相关资源

- [Drools官方文档](https://docs.drools.org/)
- [Drools规则语言参考](https://docs.drools.org/latest/drools-docs/html_single/index.html#_droolslanguagereferencechapter)
- [业务规则管理系统最佳实践](https://www.drools.org/learn/documentation.html)

## 🤝 贡献指南

如需添加新的业务规则：
1. 在`common/drools/model/`下创建事实对象
2. 在`common/resources/rules/`下创建规则文件
3. 在业务服务中注入`DroolsRuleService`使用规则
4. 添加单元测试验证规则正确性

---

**作者：** JavaEdge  
**最后更新：** 2026-01-18
