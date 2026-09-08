package com.javaedge.growth.repository;

import com.javaedge.growth.aggregate.MemberGrowth;
import com.javaedge.growth.aggregate.AwardOutcome;
import com.javaedge.growth.aggregate.SignOutcome;
import com.javaedge.growth.valueobject.MemberId;

import java.util.Optional;

/**
 * 会员成长仓储接口（定义在领域层，实现在基础设施层）。
 *
 * <p>仓储负责聚合根的<b>持久化生命周期</b>，对调用方屏蔽"怎么存"的细节：
 * 上层只说"给我某会员的成长聚合""保存这次变更"，至于底层是 MySQL、Redis 还是内存，
 * 由 infra 模块决定。注意仓储操作的是<b>整个聚合</b>，不是某张表——这正是聚合根与仓储一一对应的约定。
 */
public interface MemberGrowthRepository {

    /**
     * 按会员加载聚合根。会一并加载其本月签到记录，以保证连续天数计算所需的一致性边界。
     */
    Optional<MemberGrowth> findById(MemberId memberId);

    /**
     * 保存一次签到变更。实现需负责：
     * 聚合根积分/等级的 upsert、本次新增签到记录的 insert、以及积分流水（log）的写入。
     */
    void save(MemberGrowth growth, SignOutcome outcome);

    /**
     * 保存一次"课程完成奖励"变更：积分账户 upsert + 一条积分流水（logType 由调用方指定类型）。
     */
    void saveAward(MemberGrowth growth, AwardOutcome outcome, int logType);
}
