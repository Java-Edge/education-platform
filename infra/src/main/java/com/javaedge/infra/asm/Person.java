package com.javaedge.infra.asm;

/**
 * @author JavaEdge
 * @date 2024/4/9
 */
public class Person {

    private String name;

    public Integer age;

    public String test(){
        System.out.println("执行测试方法");
        return "I'm ok";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
