package com.javaedge.infra.growth;

import com.javaedge.common.drools.DroolsRuleService;
import com.javaedge.common.drools.model.UserSignIntegralFact;
import com.javaedge.growth.service.SignRewardPolicy;
import com.javaedge.growth.valueobject.SignReward;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 基于 Drools 规则引擎的签到奖励策略（演示规则外置）。
 *
 * <p>与 {@code DefaultSignRewardPolicy} 实现同一领域接口 {@link SignRewardPolicy}，
 * 但把"连续天数 → 奖励"的规则下沉到 {@code rules/user-sign-integral-rules.drl}。
 * 业务方改奖励不需要动 Java 代码，只需调整规则文件——这是策略可插拔 + 规则外置的典型组合。
 *
 * <p>本 Bean 非 {@code @Primary}：作为备选实现存在，应用服务默认注入 {@code DefaultSignRewardPolicy}。
 * 如需切换到规则引擎，只需把本类标为 {@code @Primary} 即可，领域层零改动。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DroolsSignRewardPolicy implements SignRewardPolicy {

    private final DroolsRuleService droolsRuleService;

    @Override
    public SignReward rewardFor(int continuousSignDays) {
        // 复用公共事实类构造签到事实（userId 在此策略不参与计算，传 null）
        UserSignIntegralFact fact = UserSignIntegralFact.buildSignFact(null, continuousSignDays);
        // 与现有工程一致：以 logger 作为全局变量执行规则
        droolsRuleService.executeRulesWithGlobal(fact, "logger", LoggerFactory.getLogger("drools.rules"));
        return SignReward.of(fact.getBaseIntegral(), fact.getBonusIntegral(), fact.getBonusDescription());
    }
}
