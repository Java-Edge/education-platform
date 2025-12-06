package com.javaedge.back.vo.course;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialColumnVO {

    private Integer id;

    private String name;

    private String image;

    private String sourceUrl;

    private Integer parentId;

    private Integer pageView;
}