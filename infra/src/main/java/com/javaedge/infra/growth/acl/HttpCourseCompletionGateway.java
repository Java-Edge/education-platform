package com.javaedge.infra.growth.acl;

import com.javaedge.growth.gateway.CourseCompletionGateway;
import com.javaedge.growth.model.CourseCompletion;
import com.javaedge.growth.valueobject.MemberId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 课程完成情况的防腐层（ACL）出口网关实现。
 *
 * <p>这是"会员成长"子域访问外部"课程中心"的<b>唯一出口</b>。外部系统的协议（HTTP/JSON）、
 * 数据模型（{@link ExternalCompletionDto}）、异常都被隔离在本类内，
 * 本域只认识翻译后的 {@link CourseCompletion}。这正是防腐层"隔离外部概念、保护通用语言"的价值。
 *
 * <p>真实环境中 {@code COURSE_CENTER_URL} 指向课程中心服务；此处对调用失败做兜底（按"未完成"返回），
 * 保证成长子域在外部系统不可用时仍可独立运行（韧性设计）。
 */
@Slf4j
@Repository
public class HttpCourseCompletionGateway implements CourseCompletionGateway {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String COURSE_CENTER_URL =
            "http://course-center/api/courses/{code}/completion?memberId={memberId}";

    @Override
    public CourseCompletion fetchCompletion(MemberId memberId, String courseCode) {
        try {
            // 外部 DTO -> 本域模型：翻译在此完成，外部概念不泄漏到领域层
            ExternalCompletionDto dto = restTemplate.getForObject(
                    COURSE_CENTER_URL, ExternalCompletionDto.class, courseCode, memberId.value());
            if (dto == null) {
                return new CourseCompletion(courseCode, false, 0);
            }
            int granted = dto.grantedPoints() == null ? 0 : dto.grantedPoints();
            return new CourseCompletion(courseCode, Boolean.TRUE.equals(dto.completed()), granted);
        } catch (RestClientException e) {
            log.warn("课程中心暂不可用，按未完成处理 memberId={} courseCode={}", memberId.value(), courseCode, e);
            return new CourseCompletion(courseCode, false, 0);
        }
    }

    /**
     * 课程中心外部 DTO——被防腐层隔离的外部模型，本域不直接依赖它。
     */
    public record ExternalCompletionDto(Boolean completed, Integer grantedPoints) {
    }
}
