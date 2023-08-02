package com.javagpt.interviewspider.data.nowcoder;

import lombok.Data;

import java.util.List;

/**
 * JavaGPT
 */
@Data
public class RecommendInternCompany {

    private String address;
    private Long companyId;
    private String companyName;
    private String companyShortName;
    private Object creditCode;
    private Long followCount;
    private List<String> industryTagNameList;
    private String personScales;
    private String picUrl;
    private Long projectId;
    private String scaleTagName;
    private Long searchSource;
    private Long tagId;


}
