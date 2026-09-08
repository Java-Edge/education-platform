package com.javaedge.infra.growth;

import com.javaedge.common.constant.UserSignConstant;
import com.javaedge.growth.service.SignRewardPolicy;
import com.javaedge.growth.valueobject.SignReward;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 默认签到奖励策略：基于配置表的纯 Java 实现。
 *
 * <p>规则与现有 {@code UserSignConstant} 保持一致：
 * <ul>
 *   <li>基础分 = {@code SIGN_TYPE_NORMAL_INTEGRAL}（5）；</li>
 *   <li>连续奖励 = {@code SIGN_CONFIGURATION} 中按"连续天数精确命中"查得的奖励（5/7/15/32 天 → 10/30/80/150），未命中则为 0。</li>
 * </ul>
 * 标记为 {@code @Primary}，使应用服务按类型注入时默认取这一条确定性策略（不依赖规则引擎即可运行）。
 */
@Primary
@Component
public class DefaultSignRewardPolicy implements SignRewardPolicy {

    @Override
    public SignReward rewardFor(int continuousSignDays) {
        int base = UserSignConstant.SIGN_TYPE_NORMAL_INTEGRAL;
        int bonus = UserSignConstant.SIGN_CONFIGURATION.getOrDefault(continuousSignDays, 0);
        String description = bonus > 0 ? "连续签到" + continuousSignDays + "天奖励" : "普通签到";
        return SignReward.of(base, bonus, description);
    }
}
