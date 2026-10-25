package com.javaedge.back.graph;

import com.javaedge.back.entity.CoursePO;
import com.javaedge.back.entity.RoadMap;
import com.javaedge.back.entity.RoadMapDetail;
import com.javaedge.back.mapper.CourseMapper;
import com.javaedge.back.mapper.RoadMapDetailMapper;
import com.javaedge.back.mapper.RoadMapMapper;
import com.javaedge.infra.graph.support.GraphWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 学习路径投影器（启动期一次性全量导入，演示"MySQL 事实源 → Neo4j 派生图"）。
 *
 * <p>职责：从 MySQL 三张关系表（source_course / roadmap / roadmap_detail）读出全量，
 * 调用 {@link GraphWriter} 以 MERGE 幂等投影进 Neo4j。所有写入走业务键 MERGE，
 * 可重复执行、可断点重跑——这是后期替换为 Debezium CDC 管道的物理前提。
 *
 * <p>@Order(2)：必须在 {@code GraphSchemaInitializer}(@Order(1)) 创建完唯一约束之后运行，
 * 否则 MERGE 无约束兜底、可能产生重复节点。Neo4j 不可用时整体 try/catch 降级，
 * 不影响主站 MySQL 读链路。
 */
@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class LearningPathProjector implements ApplicationRunner {

    private final RoadMapMapper roadMapMapper;
    private final RoadMapDetailMapper roadMapDetailMapper;
    private final CourseMapper courseMapper;
    private final GraphWriter graphWriter;

    @Override
    public void run(ApplicationArguments args) {
        try {
            int courses = projectCourses();
            int roadMaps = projectRoadMaps();
            int details = projectDetails();
            log.info("Neo4j 学习路径投影完成：Course={}, RoadMap={}, RoadMapDetail={}", courses, roadMaps, details);
        } catch (Exception e) {
            log.warn("Neo4j 投影失败（图库不可用不影响主站读链路，待 CDC 补齐）：{}", e.getMessage());
        }
    }

    private int projectCourses() {
        List<CoursePO> courses = courseMapper.selectList(null);
        graphWriter.upsertCourses(courses.stream()
                .map(c -> new GraphWriter.CourseUpsert(c.getId(), c.getName(), c.getType(), c.getSourceUrl(), c.getParentId()))
                .toList());
        return courses.size();
    }

    private int projectRoadMaps() {
        List<RoadMap> roadMaps = roadMapMapper.selectList(null);
        graphWriter.upsertRoadMaps(roadMaps.stream()
                .map(r -> new GraphWriter.RoadMapUpsert(r.getId(), r.getTitle(), r.getStep()))
                .toList());
        return roadMaps.size();
    }

    private int projectDetails() {
        List<RoadMapDetail> details = roadMapDetailMapper.selectList(null);
        graphWriter.upsertDetails(details.stream()
                .map(d -> new GraphWriter.DetailUpsert(
                        d.getId(), d.getTitle(), d.getMapOrder(), d.getType(),
                        d.getRoadMapId(), d.getCourseId()))
                .toList());
        return details.size();
    }
}
