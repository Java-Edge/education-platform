package com.javagpt.interviewspider.data.boss;

import lombok.Data;

import java.util.List;

/**
 * JavaGPT
 */
@Data
public class PositionPage {

    private Object brandCard;
    private String filterString;
    private Boolean hasMore;
    private List<PositionData> jobList;
    private String lid;
    private Long resCount;
    private Long totalCount;


}
