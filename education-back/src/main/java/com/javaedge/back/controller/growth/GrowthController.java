package com.javaedge.back.controller.growth;

import com.javaedge.application.growth.CompleteCourseCommand;
import com.javaedge.application.growth.MemberGrowthDTO;
import com.javaedge.application.growth.SignApplicationService;
import com.javaedge.application.growth.SignCommand;
import com.javaedge.application.growth.SignResultDTO;
import com.javaedge.common.resp.ResultBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员成长 REST 入口（接口层 / 用户接口）。
 *
 * <p>这一层是<b>薄</b>的：只做协议转换（路径参数 / 查询参数 -> 命令对象，领域产出 -> {@code ResultBody}），
 * 不含任何业务规则。真正的用例编排在 {@link SignApplicationService}，业务规则在领域层。
 */
@RestController
@RequestMapping("/growth")
@RequiredArgsConstructor
public class GrowthController {

    private final SignApplicationService signApplicationService;

    /**
     * 签到。
     */
    @PostMapping("/sign/{userId}")
    public ResultBody sign(@PathVariable Integer userId) {
        SignResultDTO dto = signApplicationService.sign(new SignCommand(userId));
        return ResultBody.success(dto);
    }

    /**
     * 查询会员成长快照（积分 / 等级 / 本月签到天数）。
     */
    @GetMapping("/{userId}")
    public ResultBody getGrowth(@PathVariable Integer userId) {
        MemberGrowthDTO dto = signApplicationService.getGrowth(userId);
        return ResultBody.success(dto);
    }

    /**
     * 课程完成奖励：经防腐层向课程中心查询完成情况并发放积分。
     */
    @PostMapping("/course")
    public ResultBody completeCourse(@RequestParam Integer userId, @RequestParam String courseCode) {
        MemberGrowthDTO dto = signApplicationService.completeCourse(new CompleteCourseCommand(userId, courseCode));
        return ResultBody.success(dto);
    }
}
