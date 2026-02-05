# 公司列表本地缓存实现说明

## 概述

为公司列表查询接口添加本地缓存机制，使用 Spring Cache + Caffeine 实现高性能的本地内存缓存。

## 实现方案

### 1. 技术选型

- **Spring Cache**: Spring 框架提供的声明式缓存抽象
- **Caffeine**: 高性能的 Java 本地缓存库，基于 Java 8 的 ConcurrentHashMap 优化

### 2. 实现细节

#### 2.1 添加依赖

在 `education-back/pom.xml` 中添加了以下依赖：

```xml
<!-- Spring Cache 支持 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>

<!-- Caffeine 本地缓存 -->
<dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>caffeine</artifactId>
</dependency>
```

#### 2.2 启用缓存

在 `BackApplication.java` 中添加 `@EnableCaching` 注解：

```java
@SpringBootApplication
@EnableCaching
public class BackApplication {
    // ...
}
```

#### 2.3 缓存配置

创建 `CacheConfig.java` 配置类，配置 Caffeine 缓存管理器：

```java
@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(500)        // 初始容量（预估公司数量3000）
                .maximumSize(5000)           // 最大容量（预留增长空间）
                .expireAfterWrite(1, TimeUnit.HOURS)    // 写入后1小时过期
                .expireAfterAccess(30, TimeUnit.MINUTES) // 访问后30分钟过期
                .recordStats());             // 启用统计
        return cacheManager;
    }
}
```

#### 2.4 Service 层缓存

在 `CompanyServiceImpl.java` 中为 `getCompanyNames()` 方法添加 `@Cacheable` 注解：

```java
@Override
@Cacheable(value = "companyNames", key = "'allCompanies'")
public List<String> getCompanyNames() {
    // 查询数据库逻辑
}
```

#### 2.5 Controller 层优化

简化 `CompanyController.java`，移除本地 List 缓存，直接调用 Service 层方法：

```java
@GetMapping("/getList")
public ResultBody getList(){
    return ResultBody.success(companyService.getCompanyNames());
}
```

## 缓存策略

### 缓存特性

1. **自动失效**: 
   - 写入后 1 小时自动过期
   - 访问后 30 分钟无访问则过期

2. **容量控制**:
   - 初始容量: 500 个条目（预估公司数量约3000）
   - 最大容量: 5000 个条目（预留增长空间）
   - 超出容量使用 LRU 算法淘汰

3. **性能优化**:
   - 首次请求查询数据库，结果缓存到内存
   - 后续请求直接从内存获取，响应时间从毫秒级降至微秒级
   - 支持缓存统计，便于监控

### 缓存键设计

- **缓存名称**: `companyNames`
- **缓存键**: `allCompanies` (固定键，因为该方法没有参数)

## 优势

1. **性能提升**: 避免重复查询数据库，显著提高响应速度
2. **自动管理**: Spring Cache 自动处理缓存的读写和过期
3. **线程安全**: Caffeine 提供高并发下的线程安全保证
4. **内存可控**: 通过配置最大容量和过期策略，防止内存溢出
5. **代码简洁**: 使用注解方式，代码侵入性小

## 使用说明

### 清除缓存

如果需要手动清除缓存（例如公司数据更新时），可以在 Service 层添加：

```java
@CacheEvict(value = "companyNames", key = "'allCompanies'")
public void clearCompanyCache() {
    // 清除缓存
}
```

### 查看缓存统计

由于配置了 `recordStats()`，可以通过监控端点查看缓存命中率等统计信息。

## 注意事项

1. 本地缓存存储在应用内存中，应用重启后缓存会丢失
2. 如果是分布式部署，每个实例都有独立的缓存
3. 公司数据更新时，需要考虑缓存一致性问题
4. 建议根据实际业务需求调整过期时间和容量配置

## 测试建议

1. 首次访问 `/company/getList`，验证数据正确性
2. 连续多次访问，对比响应时间，验证缓存效果
3. 等待超过 30 分钟后再次访问，验证过期策略
4. 监控应用内存使用情况，确保缓存不会导致内存问题

---

**实施时间**: 2026-02-05  
**实施人**: zqy
