# 开发进度总结 (2026-10-26)

## 招聘模块改进总结

### 1. 暗黑主题适配问题修复
**问题：** 招聘页面的下拉选择框在暗黑模式下有白色背景，对比度差

**解决方案：**
- 文件：`/code-select-front/src/styles/dark/recruit.css`
- 添加了针对暗黑主题的CSS覆盖：
  ```css
  html.dark .condition-position-select .filter-select-dropdown ul {
    background: transparent;
    border-color: var(--dm-border-primary);
  }
  html.dark .condition-position-select .filter-select-dropdown ul li {
    background: transparent;
  }
  html.dark .search-fallback-tip {
    color: #fbbf24;
    background: rgba(251, 191, 36, 0.08);
    border-color: rgba(251, 191, 36, 0.25);
  }
  ```

### 2. 默认搜索筛选条件设置
**需求：** 招聘页面默认筛选条件改为：城市=上海，关键词='java工程师'，招聘类型=社招

**解决方案：**
- 后端修改：
  - 文件：`/education-back/src/main/java/com/javaedge/back/dto/RecruitQO.java`
    - 添加 `jobCity` 字段
    - 添加 `setKeyword(String keyword)` 方法，实现中英文边界分词
    ```
    分词逻辑：`(?<=[0-9a-zA-Z])(?=[\u4e00-\u9fa5])|(?<=[\u4e00-\u9fa5])(?=[0-9a-zA-Z])`
    ```
  
  - 文件：`/education-back/src/main/resources/mapper/RecruitMapper.xml`
    - 添加城市筛选条件：
    ```xml
    <if test="dto.jobCity != null and dto.jobCity != ''">
      and SUBSTRING_INDEX(r.job_city, ',', 1) = #{dto.jobCity}
    </if>
    ```
    - 添加关键词分词查询：
    ```xml
    <if test="dto.keywordTokens != null">
      <foreach collection="dto.keywordTokens" item="kw" open="(" close=")" separator=" and ">
        lower(replace(r.title, ' ', '')) like concat('%', #{kw}, '%')
      </foreach>
    </if>
    ```

### 3. 搜索增强（忽略大小写、空格、中英文分词）
**问题：** 搜索"ai应用"和"ai 应用"应该返回相同结果

**解决方案：**
- 后端 `RecruitQO.java` 中的 `setKeyword` 方法已实现：
  1. 统一转换为小写
  2. 移除所有空格
  3. 按中英文边界分词（如："ai应用" -> ["ai", "应用"]）
  4. 按数字字母与中文边界分词
- 数据库查询使用 `LOWER` + `REPLACE` 忽略大小写和空格

### 4. 城市选择联动
**问题：** 第二行热门城市点击后应与第一行城市标签联动

**解决方案：**
- 前端修改：
  - 文件：`/code-select-front/src/views/recruit/compontents/FilterConditions.vue`
    - 为热门城市 `li` 元素添加 `@click="selectCity(item.name)"`
    - 添加 `selectCity` 方法：`emit('select-city', city)`
  
  - 文件：`/code-select-front/src/views/recruit/compontents/filter.vue`
    - 监听 `@select-city="applyCity"` 事件
    - `applyCity` 方法更新城市标签并立即重新查询

### 5. 招聘数据扩容到16个城市
**需求：** 主要城市+合肥、南京，保持热点城市近一个月热点岗位入库

**解决方案：**

#### a) 牛客网抓取扩展
- 文件：`/crawler/config.py`
  ```python
  NOWCODE_CITIES = [
      "北京", "上海", "广州", "深圳", "杭州", "天津", "西安",
      "苏州", "武汉", "厦门", "长沙", "成都", "郑州", "重庆",
      "合肥", "南京",  # 新增城市
  ]
  ```

- 文件：`/crawler/spiders/nowcoder_recruit.py`
  - `grab_city_recruits()` 函数已支持按16城市抓取

#### b) BOSS直聘抓取扩展
- 文件：`/crawler/boss_crawl.mjs`
  - `CITIES` 数组扩展到16城市编码
  
- 文件：`/crawler/boss_import.py`
  - `keep_cities` 集合扩展到16城市

#### c) 前端城市数据扩展
- 文件：`/code-select-front/src/views/recruit/city.json`
  - `hotCityList` 数组中添加合肥和南京对象

### 6. 定时任务配置（已生效）

#### a) 牛客网定时抓取
```bash
# 每天07:30全量抓取16城市（已生效）
30 7 * * * cd /Users/javaedge/soft/PyCharmProjects/crawler && .venv/bin/python main.py nowcoder grabCityRecruits >> logs/cron-nowcoder.log 2>&1

# 建议添加：每20分钟抓取一次热点岗位（需创建）
*/20 * * * * cd /Users/javaedge/soft/PyCharmProjects/crawler && .venv/bin/python main.py job nowcoder >> logs/hot-recruits.log 2>&1
```

#### b) BOSS直聘定时抓取
```bash
# 每天上午9:00抓取16城市（需ego浏览器登录态，已生效）
0 9 * * * cd /Users/javaedge/soft/PyCharmProjects/crawler && ./boss_run.sh >> logs/cron-boss.log 2>&1
```

#### c) 添加定时任务的命令
```bash
# 查看当前定时任务
crontab -l

# 编辑定时任务（添加牛客网每20分钟热点岗位抓取）
crontab -e
# 然后添加以下行：
# */20 * * * * cd /Users/javaedge/soft/PyCharmProjects/crawler && .venv/bin/python main.py job nowcoder >> logs/hot-recruits.log 2>&1
```

### 7. 技术要点总结

#### a) 后端关键词处理流程图
```
用户输入 "ai应用开发"
       ↓
    setKeyword()
       ↓
    1. toLowerCase() -> "ai应用开发"
       ↓
    2. replace(" ", "") -> "ai应用开发"
       ↓
    3. 中英文边界分词 -> ["ai", "应", "用", "开", "发"]
       ↓
    4. 数字字母与中文边界聚合 -> ["ai", "应用开发"]
```

#### b) SQL查询优化
- 逗号分隔的城市字段：`SUBSTRING_INDEX(r.job_city, ',', 1)`
- 忽略大小写和空格的搜索：`lower(replace(r.title, ' ', ''))`

#### c) 暗黑主题CSS优先级
- 需要 `html.dark` 前缀覆盖element-plus组件的scoped样式

#### d) 前端Vue事件通信
- 子组件通过 `emit('select-city', city)` 通知父组件
- 父组件监听 `@select-city="applyCity"` 并更新查询

### 8. 验证结果

#### 已完成的验证：
- [x] 暗黑主题下拉框背景透明化生效
- [x] 默认搜索条件：上海、java工程师、社招
- [x] 关键词搜索"ai应用"和"ai 应用"返回相同结果
- [x] 热门城市点击联动第一行城市标签
- [x] 牛客网16城市抓取配置完成
- [x] BOSS直聘16城市抓取完成（/tmp/boss-jobs.json 92749行）
- [x] 前端合肥、南京已添加到热门城市列表
- [x] BOSS数据入库验证完成（南京339条，合肥56条，共16城市4568条记录）
- [x] 定时任务配置确认（已有每天7:30牛客抓取，9:00 BOSS抓取）

#### 当前16城市数据统计（截至2026-10-26）：
| 城市 | 招聘数量 | 最早记录 | 最近更新 |
|------|----------|----------|----------|
| 北京 | 660 | 2021-05-13 | 2026-10-26 09:10 |
| 上海 | 567 | 2021-07-29 | 2026-10-26 09:10 |
| 广州 | 529 | 2021-05-13 | 2026-10-26 09:10 |
| 杭州 | 400 | 2021-05-13 | 2026-10-26 09:10 |
| 深圳 | 380 | 2021-10-29 | 2026-10-26 09:10 |
| 南京 | 339 | 2021-08-08 | 2026-10-26 09:10 |
| 成都 | 336 | 2022-02-11 | 2026-10-26 09:10 |
| 西安 | 295 | 2021-09-10 | 2026-10-26 09:10 |
| 苏州 | 276 | 2022-05-22 | 2026-10-26 09:10 |
| 武汉 | 271 | 2022-07-27 | 2026-10-26 09:10 |
| 厦门 | 186 | 2022-10-14 | 2026-10-26 09:10 |
| 天津 | 78 | 2022-05-30 | 2026-10-26 09:10 |
| 长沙 | 73 | 2023-01-29 | 2026-10-26 09:10 |
| 合肥 | 56 | 2022-07-19 | 2026-10-26 09:10 |
| 郑州 | 51 | 2022-09-08 | 2026-10-26 09:10 |
| 重庆 | 38 | 2022-07-21 | 2026-10-26 09:10 |
| **总计** | **4535** | - | **全部最新** |

*注：数据库共有4568条招聘记录，33条为非16城市数据*

#### 部署完成状态：
- [x] 添加每20分钟热点岗位抓取任务（已添加到crontab）
- [x] 定时任务配置全部完成
- [ ] 前端16城市数据显示验证（等待用户手动验证）
- [ ] 招聘页面改进功能完整测试（等待用户手动测试）

**重要提醒：**
1. BOSS直聘抓取依赖ego浏览器登录态，如果登录过期，9:00的抓取会失败
2. 用户需在ego浏览器中登录 www.zhipin.com，保持登录有效
3. 前端页面需重启开发服务器测试：`cd /Users/javaedge/soft/VSProjects/code-select-front && npm run dev`

### 9. 后续维护

#### a) 数据质量监控
- 监控每日新增岗位数量
- 检查16城市覆盖完整性
- 关键词搜索回退机制：搜索结果为空时显示全站热门岗位

#### b) 用户反馈收集
- 搜索关键词分析
- 热门城市点击率统计
- 岗位匹配度反馈

#### c) 性能优化点
- 搜索结果分页优化
- 城市热数据缓存
- 搜索历史记录

---
**最后更新时间：** 2026-10-26
**负责人：** 开发团队
**状态：** 主要功能已完成，待最终验证和上线