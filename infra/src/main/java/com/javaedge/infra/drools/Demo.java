package com.javaedge.infra.drools;

import org.kie.api.KieServices;

/**
 * @author JavaEdge
 * @date 2024/3/16
 */
public class Demo {

    public static void main(String[] args) {
        System.getenv().forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
//        String property = System.getenv("user.dir");
//        System.out.println(property);
    }
}
