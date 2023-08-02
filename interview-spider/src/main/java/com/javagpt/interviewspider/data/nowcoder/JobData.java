package com.javagpt.interviewspider.data.nowcoder;

import lombok.Data;

import java.util.List;

/**
 *
 * @Author JavaGPT
 * @Date 2023/8/1 13:36
 * @PackageName:com.javagpt.interviewspider.data.nowcoder
 * @ClassName: JobData
 * @Version 1.0
 */
@Data
public class JobData {


    /**
     * 当前大类
     */
    private JobInfo jobInfo;

    /**
     * 子类信息
     */
    private List<JobData> subJobs;

}
