//package com.javaedge.infra.test;
//
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//import java.util.concurrent.StructuredTaskScope;
//import java.util.function.Supplier;
//
///**
// * @author JavaEdge
// * @date 2024/9/26
// */
//class Course {
//    private final CourseType type;
//    private final String name;
//
//    Course(CourseType type, String name) {
//        this.type = type;
//        this.name = name;
//    }
//}
//
//enum CourseType {
//    STARTER, MAIN, DESSERT
//}
//
//class Waiter {
//    private final String name;
//
//    Waiter(String name) {
//        this.name = name;
//    }
//
//    Course announceCourse(CourseType type) {
//        return new Course(type, "Course from " + name);
//    }
//}
//
//class MultiCourseMeal {
//
//    public MultiCourseMeal(Course course, Course course1, Course course2) {
//
//    }
//}
//
//interface Restaurant {
//    MultiCourseMeal announceMenu() throws ExecutionException, InterruptedException;
//
//    MultiCourseMeal announceMenu2() throws ExecutionException, InterruptedException;
//}
//
//public class MultiWaiterRestaurant implements Restaurant {
//    @Override
//    public MultiCourseMeal announceMenu() throws ExecutionException, InterruptedException {
//        Waiter grover = new Waiter("Grover");
//        Waiter zoe = new Waiter("Zoe");
//        Waiter rosita = new Waiter("Rosita");
//        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
//            Future<Course> starter = executor.submit(() -> grover.announceCourse(CourseType.STARTER));
//            Future<Course> main = executor.submit(() -> zoe.announceCourse(CourseType.MAIN));
//            Future<Course> dessert = executor.submit(() -> rosita.announceCourse(CourseType.DESSERT));
//            return new MultiCourseMeal(starter.get(), main.get(), dessert.get());
//        }
//    }
//
//    @Override
//    public MultiCourseMeal announceMenu2() throws ExecutionException, InterruptedException {
//        Waiter grover = new Waiter("Grover");
//        Waiter zoe = new Waiter("Zoe");
//        Waiter rosita = new Waiter("Rosita");
//        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
//            Supplier<Course> starter = scope.fork(() -> grover.announceCourse(CourseType.STARTER));
//            Supplier<Course> main = scope.fork(() -> zoe.announceCourse(CourseType.MAIN));
//            Supplier<Course> dessert = scope.fork(() -> rosita.announceCourse(CourseType.DESSERT));
//            scope.join(); // 1
//            scope.throwIfFailed(); // 2
//            return new MultiCourseMeal(starter.get(), main.get(), dessert.get()); // 3
//        }
//    }
//}