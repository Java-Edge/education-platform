package com.javaedge.growth.exception;

/**
 * 当日已签到异常。
 *
 * <p>这是聚合根"同一自然日不可重复签到"不变量被违反时抛出的领域异常，
 * 属于可预期的业务校验失败，应用层应转换为友好提示而非 500。
 */
public class AlreadySignedTodayException extends MemberGrowthException {

    public AlreadySignedTodayException() {
        super("今日已签到，不可重复签到");
    }
}
