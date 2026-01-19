# Drools规则引擎快速入门

## 🚀 5分钟快速上手

### 步骤1：理解核心概念

**规则引擎三要素：**
1. **事实（Fact）** - 业务数据对象
2. **规则（Rule）** - 业务逻辑判断
3. **引擎（Engine）** - 执行规则的容器

**工作流程：**
```
输入事实对象 → 规则引擎匹配规则 → 执行规则动作 → 输出结果
```

### 步骤2：使用现有规则

#### 场景1：计算用户签到积分

```java
@Autowired
private DroolsRuleService droolsRuleService;

public void userSign(Integer userId) {
    // 1. 获取连续签到天数
    int days = getContinuousSignDays(userId);
    
    // 2. 构建事实对象
    UserSignIntegralFact fact = UserSignIntegralFact.buildSignFact(userId, days);
    
    // 3. 执行规则
    droolsRuleService.executeRulesWithGlobal(
        fact, 
        "logger", 
        LoggerFactory.getLogger("drools.rules")
    );
    
    // 4. 获取结果
    System.out.println("获得积分：" + fact.getTotalIntegral());
    System.out.println("奖励说明：" + fact.getBonusDescription());
}
```

#### 场景2：计算课程推荐分数

```java
@Autowired
private DroolsExampleService droolsExampleService;

public void recommendCourse(Integer courseId) {
    // 直接调用示例服务
    CourseRecommendFact result = droolsExampleService.calculateCourseRecommend(courseId);
    
    System.out.println("推荐分数：" + result.getRecommendScore());
    System.out.println("推荐理由：" + result.getRecommendReason());
    System.out.println("是否推荐：" + result.getIsRecommended());
}
```

### 步骤3：创建自定义规则

#### 示例：优惠券发放规则

**1. 创建事实对象**
```java
@Data
@Builder
public class CouponIssueFact {
    private Integer userId;
    private Integer userLevel;
    private Integer totalIntegral;
    private Boolean isNewUser;
    
    // 规则计算结果
    private Integer couponAmount;  // 优惠券金额
    private String couponType;     // 优惠券类型
    private Boolean isIssue;       // 是否发放
}
```

**2. 编写规则文件** `rules/coupon-issue-rules.drl`
```drl
package com.javaedge.rules.coupon

import com.javaedge.common.drools.model.CouponIssueFact

global org.slf4j.Logger logger

// 新用户注册优惠券
rule "新用户注册优惠券"
    salience 100
    when
        $fact : CouponIssueFact(isNewUser == true)
    then
        logger.info("触发新用户优惠券");
        $fact.setCouponAmount(50);
        $fact.setCouponType("新人专享券");
        $fact.setIsIssue(true);
        update($fact);
end

// 高等级用户专属优惠券
rule "高等级用户专属优惠券"
    salience 90
    when
        $fact : CouponIssueFact(userLevel >= 4, isNewUser == false)
    then
        logger.info("触发高等级用户优惠券");
        $fact.setCouponAmount(100);
        $fact.setCouponType("VIP专属券");
        $fact.setIsIssue(true);
        update($fact);
end
```

**3. 使用规则**
```java
@Autowired
private DroolsRuleService droolsRuleService;

public void issueCoupon(Integer userId) {
    // 构建事实
    CouponIssueFact fact = CouponIssueFact.builder()
        .userId(userId)
        .userLevel(getUserLevel(userId))
        .isNewUser(checkIfNewUser(userId))
        .build();
    
    // 执行规则
    droolsRuleService.executeRules(fact);
    
    // 处理结果
    if (fact.getIsIssue()) {
        saveCoupon(userId, fact.getCouponAmount(), fact.getCouponType());
    }
}
```

## 📝 规则编写速查表

### 基本语法

```drl
rule "规则名称"
    salience 100          // 优先级（可选）
    when
        条件部分
    then
        动作部分
end
```

### 常用条件表达式

```drl
// 1. 基本条件
$fact : MyFact(field == value)

// 2. 比较运算
$fact : MyFact(age >= 18, score > 60)

// 3. 逻辑运算
$fact : MyFact(age >= 18 && age <= 60)

// 4. 包含判断
$fact : MyFact(status in ("ACTIVE", "PENDING"))

// 5. 空值判断
$fact : MyFact(name != null)

// 6. 正则匹配
$fact : MyFact(email matches ".*@.*\\.com")

// 7. 集合操作
$fact : MyFact(tags contains "VIP")

// 8. 自定义函数
eval(customFunction($fact))
```

### 常用动作

```drl
then
    // 修改属性
    $fact.setField(value);
    
    // 更新事实（触发规则重新匹配）
    update($fact);
    
    // 插入新事实
    insert(new AnotherFact());
    
    // 删除事实
    delete($fact);
    
    // 日志输出
    logger.info("规则触发");
    
    // 调用Java方法
    someService.doSomething();
end
```

## 🎯 实战案例

### 案例1：动态定价规则

**业务需求：**
- 新课程首月8折
- VIP用户7折
- 购买3门以上5折
- 节假日额外9折

**实现：**

```java
// 事实对象
@Data
@Builder
public class CoursePricingFact {
    private Integer courseId;
    private Double originalPrice;
    private Boolean isNewCourse;
    private Boolean isVip;
    private Integer purchaseCount;
    private Boolean isHoliday;
    
    private Double finalPrice;
    private Double discount = 1.0;
    private String discountReason = "";
}
```

```drl
// 规则文件
rule "新课程首月折扣"
    salience 100
    when
        $fact : CoursePricingFact(isNewCourse == true)
    then
        $fact.setDiscount($fact.getDiscount() * 0.8);
        $fact.setDiscountReason($fact.getDiscountReason() + "新课8折;");
        update($fact);
end

rule "VIP用户折扣"
    salience 95
    when
        $fact : CoursePricingFact(isVip == true)
    then
        $fact.setDiscount($fact.getDiscount() * 0.7);
        $fact.setDiscountReason($fact.getDiscountReason() + "VIP7折;");
        update($fact);
end

rule "计算最终价格"
    salience 50
    when
        $fact : CoursePricingFact()
    then
        $fact.setFinalPrice($fact.getOriginalPrice() * $fact.getDiscount());
        update($fact);
end
```

### 案例2：风控规则

**业务需求：**
- 检测异常登录
- 检测频繁操作
- 检测可疑交易

```drl
rule "异常登录-异地登录"
    when
        $fact : LoginCheckFact(
            lastLoginCity != null,
            currentLoginCity != lastLoginCity,
            timeSinceLastLogin < 3600  // 1小时内
        )
    then
        $fact.setRiskLevel("HIGH");
        $fact.setRiskReason("异地登录风险");
        $fact.setNeedVerify(true);
        update($fact);
end

rule "频繁操作检测"
    when
        $fact : OperationCheckFact(operationCount > 100 within 1 hour)
    then
        $fact.setRiskLevel("MEDIUM");
        $fact.setAction("RATE_LIMIT");
        update($fact);
end
```

## 🐛 常见问题

### Q1: 规则没有执行？
**检查清单：**
- ✓ 规则文件是否在`resources/rules/`目录下
- ✓ 规则文件扩展名是否为`.drl`
- ✓ 事实对象是否正确插入到session
- ✓ 规则条件是否匹配

### Q2: 规则执行顺序错误？
**解决方案：**
使用`salience`属性控制优先级：
```drl
rule "高优先级规则"
    salience 100  // 先执行
    
rule "低优先级规则"
    salience 50   // 后执行
```

### Q3: 规则死循环？
**原因：**
规则中使用`update()`导致规则反复匹配

**解决方案：**
- 添加防护条件
- 使用`modify`替代`update`
- 合理设置规则优先级

### Q4: 性能问题？
**优化方案：**
- 减少规则数量
- 优化规则条件
- 使用索引字段
- 批量处理数据

## 🎓 进阶学习

### 1. 规则组管理
```drl
rule "规则A"
    agenda-group "group1"
    when
        ...
    then
        ...
end
```

```java
kieSession.getAgenda().getAgendaGroup("group1").setFocus();
kieSession.fireAllRules();
```

### 2. 规则流（RuleFlow）
使用BPMN2规范定义规则执行流程

### 3. 决策表（Decision Table）
使用Excel管理规则，适合业务人员维护

### 4. 复杂事件处理（CEP）
处理时间窗口内的事件流

## 📚 推荐阅读

1. **入门教程**
   - [Drools快速入门](https://docs.drools.org/latest/drools-docs/html_single/index.html#_gettingstarted)
   
2. **最佳实践**
   - 规则设计模式
   - 性能优化指南
   - 测试策略

3. **进阶主题**
   - Drools与Spring Boot集成
   - 规则热部署
   - 分布式规则引擎

## 🔗 相关链接

- [项目详细文档](./DROOLS_INTEGRATION.md)
- [Drools官网](https://www.drools.org/)
- [GitHub示例](https://github.com/kiegroup/drools)

---

**提示：** 遇到问题？查看日志输出，Drools会提供详细的错误信息！
