package com.javaedge.application.context;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author JavaEdge
 * @date 2023/4/12
 */
@Component
public class AppEnv {

    @Value("${spring.profiles.active}")
    private String profile;

    private static String envName;

    private static final String env_dev = "dev";

    private static final String env_test = "test";

    @PostConstruct
    public void init() {
        envName = profile;
    }


    public static boolean isDev() {
        if (env_dev.equals(envName)) {
            return true;
        }
        return false;
    }

    public static boolean isTest() {
        if (env_test.equals(envName)) {
            return true;
        }
        return false;
    }


}
