package com.javagpt.infra.test.newplus;

/**
 * @author JavaEdge
 * @date 2024/7/14
 */
// 添加sealed修饰符，permits后面跟上只能被继承的子类名称
public sealed class Person permits Teacher, Worker, Student {

    public static void main(String[] args) {
        // 获取内存访问var句柄
//        var handle = MemoryHandles.varHandle(char.class,
//                ByteOrder.nativeOrder());
//// 申请200字节的堆外内存
//        try (MemorySegment segment = MemorySegment.allocateNative(200)) {
//            for (int i = 0; i < 25; i++) {
//                handle.set(segment, i << 2, (char) (i + 1 + 64));
//                System.out.println(handle.get(segment, i << 2));
//            }
//        }
    }
}

// 子类可被修饰为 final
final class Teacher extends Person {
}

// 子类可被修饰为 non-sealed，此时 Worker类就成了普通类，谁都可继承它
non-sealed class Worker extends Person {
}

// 任何类都可继承Worker
class AnyClass extends Worker {
}

// 子类可被修饰为 sealed,同上
sealed class Student extends Person permits MiddleSchoolStudent, GraduateStudent {
}

// 中学生
final class MiddleSchoolStudent extends Student {
}

// 研究生
final class GraduateStudent extends Student {
}
