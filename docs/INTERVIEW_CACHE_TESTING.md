# 面经缓存功能测试指南

## 快速测试步骤

### 前置条件
确保应用已启动并运行在 `http://localhost:8080`

### 1. 测试分页查询缓存

#### 第一次请求（缓存未命中）
```bash
curl -X POST http://localhost:8080/interview/selectByCondition \
  -H "Content-Type: application/json" \
  -d '{
    "pageNo": 1,
    "pageSize": 10,
    "param": {
      "jobId": null
    }
  }' \
  -w "\n响应时间: %{time_total}s\n"
```

记录响应时间（预期：50-200ms）

#### 第二次相同请求（缓存命中）
```bash
curl -X POST http://localhost:8080/interview/selectByCondition \
  -H "Content-Type: application/json" \
  -d '{
    "pageNo": 1,
    "pageSize": 10,
    "param": {
      "jobId": null
    }
  }' \
  -w "\n响应时间: %{time_total}s\n"
```

记录响应时间（预期：<5ms，性能提升明显）

### 2. 测试详情查询缓存

#### 第一次请求（缓存未命中）
```bash
# 替换 {id} 为实际的面经ID
curl http://localhost:8080/interview/getById/{id} \
  -w "\n响应时间: %{time_total}s\n"
```

记录响应时间（预期：30-100ms）

#### 第二次相同请求（缓存命中）
```bash
curl http://localhost:8080/interview/getById/{id} \
  -w "\n响应时间: %{time_total}s\n"
```

记录响应时间（预期：<5ms，性能提升明显）

### 3. 测试不同查询条件

测试不同岗位筛选条件会产生不同的缓存：

```bash
# 岗位ID = 1
curl -X POST http://localhost:8080/interview/selectByCondition \
  -H "Content-Type: application/json" \
  -d '{"pageNo": 1, "pageSize": 10, "param": {"jobId": 1}}'

# 岗位ID = 2
curl -X POST http://localhost:8080/interview/selectByCondition \
  -H "Content-Type: application/json" \
  -d '{"pageNo": 1, "pageSize": 10, "param": {"jobId": 2}}'

# 再次请求岗位ID = 1（应该命中缓存）
curl -X POST http://localhost:8080/interview/selectByCondition \
  -H "Content-Type: application/json" \
  -d '{"pageNo": 1, "pageSize": 10, "param": {"jobId": 1}}'
```

### 4. 测试缓存过期

#### 测试分页查询缓存过期（5分钟）
```bash
# 1. 发起请求并记录时间
curl -X POST http://localhost:8080/interview/selectByCondition \
  -H "Content-Type: application/json" \
  -d '{"pageNo": 1, "pageSize": 10, "param": {"jobId": null}}'

# 2. 立即再次请求（应该命中缓存）
curl -X POST http://localhost:8080/interview/selectByCondition \
  -H "Content-Type: application/json" \
  -d '{"pageNo": 1, "pageSize": 10, "param": {"jobId": null}}'

# 3. 等待 5 分钟后再次请求（缓存已过期，应该重新查询数据库）
sleep 300
curl -X POST http://localhost:8080/interview/selectByCondition \
  -H "Content-Type: application/json" \
  -d '{"pageNo": 1, "pageSize": 10, "param": {"jobId": null}}'
```

## 性能压测

使用 Apache Bench 进行压力测试：

### 测试详情查询性能

```bash
# 第一轮：缓存预热
ab -n 100 -c 10 http://localhost:8080/interview/getById/1

# 第二轮：测试缓存效果
ab -n 1000 -c 50 http://localhost:8080/interview/getById/1
```

**预期结果**:
- QPS (每秒请求数): 显著提升
- 平均响应时间: < 5ms
- 99th 百分位响应时间: < 10ms

### 测试分页查询性能

由于分页查询是 POST 请求，可以使用 wrk 工具：

```bash
# 安装 wrk (macOS)
brew install wrk

# 创建测试脚本
cat > test.lua << 'EOF'
wrk.method = "POST"
wrk.body   = '{"pageNo":1,"pageSize":10,"param":{"jobId":null}}'
wrk.headers["Content-Type"] = "application/json"
EOF

# 运行压测
wrk -t4 -c100 -d30s --script=test.lua \
    http://localhost:8080/interview/selectByCondition
```

**预期结果**:
- 第一次请求后，后续请求应该主要命中缓存
- 平均延迟大幅降低
- 吞吐量显著提升

## 前端测试场景

### 场景1：列表页翻页
1. 打开面经列表页
2. 点击"下一页"（应该很快，新查询）
3. 点击"上一页"返回第一页（应该非常快，命中缓存）
4. 再次点击"下一页"（应该非常快，命中缓存）

**预期效果**: 翻页操作非常流畅，几乎无延迟

### 场景2：查看详情后返回
1. 在列表页点击某个面经查看详情（正常速度）
2. 返回列表页（应该非常快，命中缓存）
3. 再次点击同一个面经（应该非常快，命中缓存）

**预期效果**: 返回和重复查看操作响应迅速

### 场景3：岗位筛选
1. 选择某个岗位进行筛选（正常速度）
2. 切换到另一个岗位（正常速度）
3. 切换回第一个岗位（应该非常快，命中缓存）

**预期效果**: 重复筛选操作响应迅速

### 场景4：热门面经
1. 多个用户同时访问相同的热门面经
2. 第一个用户查询后会缓存结果
3. 后续用户访问同一面经时响应极快

**预期效果**: 热门内容访问速度极快，服务器压力小

## 监控验证

### 方法1：添加日志（开发环境）

在 `InterviewServiceImpl` 中添加日志：

```java
@Override
public IPage<InterviewEntity> selectByCondition(PageQueryParam<InterviewDTO> pageQueryParam) {
    // ... 构建缓存键的代码 ...
    
    IPage<InterviewEntity> cachedResult = interviewPageCache.getIfPresent(cacheKey);
    if (cachedResult != null) {
        log.info("分页查询缓存命中，cacheKey: {}", cacheKey);
        return cachedResult;
    }
    
    log.info("分页查询缓存未命中，查询数据库，cacheKey: {}", cacheKey);
    // ... 查询数据库的代码 ...
}
```

观察日志输出即可验证缓存是否生效。

### 方法2：使用 Caffeine 统计功能

修改 `CaffeineConfig.java`，启用统计：

```java
@Bean
public Cache<String, IPage<InterviewEntity>> interviewPageCache() {
    return Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .recordStats()  // 启用统计
            .build();
}
```

添加统计端点查看缓存效果：

```java
@RestController
@RequestMapping("/cache")
public class CacheStatsController {
    
    @Autowired
    private Cache<String, IPage<InterviewEntity>> interviewPageCache;
    
    @GetMapping("/stats")
    public Map<String, Object> getCacheStats() {
        CacheStats stats = interviewPageCache.stats();
        Map<String, Object> result = new HashMap<>();
        result.put("hitCount", stats.hitCount());
        result.put("missCount", stats.missCount());
        result.put("hitRate", String.format("%.2f%%", stats.hitRate() * 100));
        result.put("loadCount", stats.loadCount());
        result.put("evictionCount", stats.evictionCount());
        return result;
    }
}
```

访问 `http://localhost:8080/cache/stats` 查看缓存统计信息。

## 预期测试结果

✅ **分页查询缓存命中**: 响应时间从 50-200ms 降至 <5ms  
✅ **详情查询缓存命中**: 响应时间从 30-100ms 降至 <5ms  
✅ **缓存命中率**: 正常使用场景下应达到 60-80%  
✅ **系统负载**: 数据库查询次数显著降低  
✅ **用户体验**: 页面加载速度明显提升  

## 故障排查

### 问题1：缓存似乎没有生效
**检查**:
- 确认 `CaffeineConfig` 中的 Bean 配置正确
- 确认 `InterviewServiceImpl` 成功注入了缓存 Bean
- 查看应用日志是否有异常
- 使用相同的请求参数进行测试

### 问题2：缓存命中率很低
**可能原因**:
- 查询参数变化太频繁
- 缓存过期时间设置过短
- 用户访问模式分散，重复查询少

**解决方案**:
- 增加缓存容量 (maximumSize)
- 延长过期时间 (expireAfterWrite)
- 分析用户访问模式，优化缓存策略

### 问题3：内存占用过高
**可能原因**:
- 缓存容量设置过大
- 单个缓存对象太大

**解决方案**:
- 降低 maximumSize
- 考虑只缓存必要的字段
- 监控内存使用情况

## 总结

通过以上测试，你应该能够验证：
1. ✅ 缓存功能正常工作
2. ✅ 性能得到显著提升
3. ✅ 用户体验明显改善
4. ✅ 系统负载有效降低

如果测试结果符合预期，说明缓存集成成功！🎉

---

**测试文档版本**: v1.0  
**创建日期**: 2026-02-05  
