package com.javagpt.back.vo.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseVO implements Serializable {
    private Integer id;
    private String name;
    private String image;
    private String description;
    private String creator;
    private String updater;
    private String remark;
    private String sourceUrl;
    private BigDecimal price;
    private Integer step;
}