package com.javaedge.common.drools;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Drools规则引擎服务类
 * 提供统一的规则执行接口
 * 
 * @author JavaEdge
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DroolsRuleService {

    private final KieContainer kieContainer;

    /**
     * 执行规则 - 单个事实对象
     * 
     * @param fact 事实对象
     * @return 执行的规则数量
     */
    public int executeRules(Object fact) {
        KieSession kieSession = kieContainer.newKieSession();
        try {
            kieSession.insert(fact);
            int firedRules = kieSession.fireAllRules();
            log.info("执行了 {} 条规则，事实对象: {}", firedRules, fact.getClass().getSimpleName());
            return firedRules;
        } finally {
            kieSession.dispose();
        }
    }

    /**
     * 执行规则 - 多个事实对象
     * 
     * @param facts 事实对象集合
     * @return 执行的规则数量
     */
    public int executeRules(Collection<?> facts) {
        KieSession kieSession = kieContainer.newKieSession();
        try {
            for (Object fact : facts) {
                kieSession.insert(fact);
            }
            int firedRules = kieSession.fireAllRules();
            log.info("执行了 {} 条规则，事实对象数量: {}", firedRules, facts.size());
            return firedRules;
        } finally {
            kieSession.dispose();
        }
    }

    /**
     * 执行规则 - 带全局变量
     * 
     * @param fact 事实对象
     * @param globalName 全局变量名
     * @param globalValue 全局变量值
     * @return 执行的规则数量
     */
    public int executeRulesWithGlobal(Object fact, String globalName, Object globalValue) {
        KieSession kieSession = kieContainer.newKieSession();
        try {
            kieSession.setGlobal(globalName, globalValue);
            kieSession.insert(fact);
            int firedRules = kieSession.fireAllRules();
            log.info("执行了 {} 条规则，事实对象: {}, 全局变量: {}={}", 
                firedRules, fact.getClass().getSimpleName(), globalName, globalValue);
            return firedRules;
        } finally {
            kieSession.dispose();
        }
    }

    /**
     * 执行规则并返回更新后的事实对象
     * 
     * @param fact 事实对象
     * @param <T> 事实对象类型
     * @return 更新后的事实对象
     */
    public <T> T executeAndGetFact(T fact) {
        executeRules(fact);
        return fact;
    }
}
