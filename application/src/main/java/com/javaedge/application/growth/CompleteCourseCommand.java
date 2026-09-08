package com.javaedge.application.growth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 课程完成奖励命令：携带会员与课程编码，经防腐层查询后发放积分。
 */
public record CompleteCourseCommand(@NotNull Integer userId, @NotBlank String courseCode) {
}
