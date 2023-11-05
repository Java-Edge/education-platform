package com.javagpt.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 千祎来了
 * @date 2023/10/28 22:43
 */
public interface UserSignConstant {

    /**
     * 连续签到积分奖励配置
     */
    Map<Integer, Integer> SIGN_CONFIGURATION = new HashMap<>() {{
        put(5, 10);
        put(7, 30);
        put(15, 80);
        put(32, 150);
    }};

    /**
     * 用户普通签到所获取积分
     */
    Integer SIGN_TYPE_NORMAL_INTEGRAL = 5;
}
