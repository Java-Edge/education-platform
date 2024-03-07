package com.javagpt.infra.asm;

import java.lang.instrument.Instrumentation;

/**
 * @author JavaEdge
 * @date 2024/4/9
 */
public class MyCustomAgent {
    /**
     * jvm 参数形式启动，运行此方法
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("premain");
        customLogic(inst);
    }

    /**
     * 动态 attach 方式启动，运行此方法
     * @param agentArgs
     * @param inst
     */
    public static void agentmain(String agentArgs, Instrumentation inst){
        System.out.println("agentmain");
        customLogic(inst);
    }

    /**
     * 打印所有已加载的类名称
     * 修改字节码
     * @param inst
     */
    private static void customLogic(Instrumentation inst){
        inst.addTransformer(new MyTransformer(), true);
        Class[] classes = inst.getAllLoadedClasses();
        for(Class cls :classes){
            System.out.println(cls.getName());
        }
    }
}
