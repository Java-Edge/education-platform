package com.javaedge.common.drools;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * Drools规则引擎配置类
 * 负责初始化KieContainer和KieSession
 * 
 * @author JavaEdge
 */
@Configuration
public class DroolsConfig {

    private static final String RULES_PATH = "rules/";

    /**
     * 创建KieFileSystem并加载规则文件
     */
    @Bean
    public KieFileSystem kieFileSystem() throws IOException {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        
        // 加载所有.drl规则文件
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "**/*.drl");
        
        for (Resource resource : resources) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(
                RULES_PATH + resource.getFilename(), "UTF-8"));
        }
        
        return kieFileSystem;
    }

    /**
     * 创建KieContainer
     */
    @Bean
    public KieContainer kieContainer(KieFileSystem kieFileSystem) {
        KieServices kieServices = KieServices.Factory.get();
        
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        
        // 检查编译结果
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new IllegalStateException("规则文件编译失败: " + results.getMessages());
        }
        
        KieModule kieModule = kieBuilder.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

    /**
     * 创建KieSession Bean
     * 每次使用时创建新的session
     */
    @Bean
    public KieSession kieSession(KieContainer kieContainer) {
        return kieContainer.newKieSession();
    }
}