package com.javaedge.back.vo.course;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseCategoryVO {

    private Integer id;
    private String name;
    private Integer category;
}
