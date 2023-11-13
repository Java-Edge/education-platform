package com.javagpt.back.vo.course;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseVO {

    private Integer id;

    private String name;

    private String image;

    private String description;

    private String creator;

    private String updater;

    private String remark;

    private String sourceUrl;

    private Integer step;

    private Integer parentId;

    private Integer pageView;
}