//package com.javagpt.infra.test;
//
//import module java.base;      // exports java.util, which has a public Date class
//import module java.sql;       // exports java.sql, which has a public Date class
//import lombok.AllArgsConstructor;
//
//import java.sql.Date;         // resolve the ambiguity of the simple name Date
//
///**
// * @author JavaEdge
// * @date 2024/9/24
// */
//record Tuner(double pitchInHz) implements Effect {
//
//    // 这是一个实例主方法
//    void main() {
//        System.out.println("Hello, World!");
//    }
//
////    public static void main(String[] args) {
////        Date d = new Date(1); // Ok! Date is resolved to java.sql.Date
////    }
//
//
////    public static void main(String[] args) {
////        // int参数被扩展到double
////        var tuner = new Tuner(440);
////
////        // demo1：int参数上的记录模式匹配
////        if (tuner instanceof Tuner(int p)) {
////        }
////
////        // demo2：double参数上的记录模式匹配
////        if (tuner instanceof Tuner(double p)) {
////            int pitch = p; // 不能编译！需要转换为int
////        }
////
////        // demo3：double参数上的记录模式匹配，转换为int
////        if (tuner instanceof Tuner(double p)) {
////            int pitch = (int) p;
////        }
////    }
//
//    //    public static void main(String[] args) {
////        var singleEffect = new SingleEffect(...);
////
////        if (singleEffect instanceof SingleEffect(Delay d)) {
////            // ...
////        } else if (singleEffect instanceof SingleEffect(Reverb r)) {
////            // ...
////        } else {
////            // ...
////        }
////    }
//
//}
//
//record SingleEffect(Effect effect) {
//}
//
//
//@AllArgsConstructor
//class Orchestra {
//
//    private List<Instrument> instruments;
//}
//
//class Instrument {
//
//}
//
////public class StringQuartet extends Orchestra {
////
////    public StringQuartet(List<Instrument> instruments) {
////        super(validate(instruments));
////    }
////
////    private static List<Instrument> validate(List<Instrument> instruments) {
////        if (instruments.size() != 4) {
////            throw new IllegalArgumentException("不是四重奏！");
////        }
////        return instruments;
////    }
////}
//
//class StringQuartet extends Orchestra {
//    public StringQuartet(List<Instrument> instruments) {
//        super(instruments); // 可能不必要的工作！
//        if (instruments.size() != 4) {
//            throw new IllegalArgumentException("不是四重奏！");
//        }
//    }
//}
//
//
