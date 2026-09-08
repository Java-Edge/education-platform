package com.javaedge.growth.gateway;

import com.javaedge.growth.model.CourseCompletion;
import com.javaedge.growth.valueobject.MemberId;

/**
 * 防腐层（Anti-Corruption Layer）出口网关接口。
 *
 * <p>会员成长子域需要"课程是否完成"来决定是否发放积分，但它<b>不依赖</b>课程中心的具体实现，
 * 只依赖这个接口。外部系统的协议、模型、异常都被隔离在实现类（位于 infra 模块）中，
 * 防止外部概念"泄漏"污染本域的通用语言（Ubiquitous Language）。
 */
public interface CourseCompletionGateway {

    /**
     * 查询某会员是否完成了指定课程。
     *
     * @param memberId  会员标识
     * @param courseCode 课程编码
     * @return 本域认识的 {@link CourseCompletion} 本地模型
     */
    CourseCompletion fetchCompletion(MemberId memberId, String courseCode);
}
