package com.javagpt.interviewspider.dto.nowcoder;

import lombok.Data;

import java.util.List;

/**
 * @Author JavaGPT
 * @Date 2023/7/25 16:24
 * @PackageName:com.javagpt.interviewspider.dto
 * @ClassName: Result
 */
@Data
public class Result<T> {

    private List<T> result;

    private List<T> careerJobSelectors;

    private List<T> allJobs;


}
