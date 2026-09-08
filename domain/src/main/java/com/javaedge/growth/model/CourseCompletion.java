package com.javaedge.growth.model;

/**
 * 外部"课程中心"模型在本（会员成长）上下文中的<b>本地表示</b>。
 *
 * <p>这是防腐层（ACL）翻译后的产物：会员成长子域只认识这个简单结构，
 * 不关心课程中心内部的复杂 DTO。外部系统如何建模与本域完全解耦。
 */
public record CourseCompletion(String courseCode, boolean completed, int grantedPoints) {
}
