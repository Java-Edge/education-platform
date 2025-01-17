package com.javagpt.back.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SentinelConfig implements BlockExceptionHandler {
    
    @PostConstruct
    private void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        
        // 创建流控规则
        FlowRule rule = new FlowRule();
        // 设置受保护的资源
        rule.setResource("pilot_list");
        // 设置流控规则 QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置受保护的资源阈值
        rule.setCount(1);
        rules.add(rule);
        
        // 加载规则
        FlowRuleManager.loadRules(rules);
    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, String resourceName, BlockException e) throws Exception {
        response.setStatus(429);
        response.getWriter().write("访问过于频繁，请稍后再试");
    }
}