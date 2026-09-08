package com.javaedge.growth.service;

import com.javaedge.growth.valueobject.SignReward;

/**
 * 签到奖励策略（领域服务 / Domain Service）。
 *
 * <p>当一段逻辑<b>不属于任何一个实体或值对象</b>、且可能涉及规则/外部系统协作时，
 * 把它放进领域服务。这里"连续天数 → 奖励"的规则被抽成接口，便于插拔实现：
 * <ul>
 *   <li>{@code DefaultSignRewardPolicy}：基于配置表的纯 Java 实现；</li>
 *   <li>{@code DroolsSignRewardPolicy}：用 Drools 规则引擎实现（演示规则外置）。</li>
 * </ul>
 * 聚合根只依赖这个接口，不关心具体规则存在哪里。
 */
public interface SignRewardPolicy {

    /**
     * 根据连续签到天数计算本次奖励。
     *
     * @param continuousSignDays 连续签到天数（含今天）
     * @return 奖励值对象（基础分 + 连续奖励分 + 描述）
     */
    SignReward rewardFor(int continuousSignDays);
}
