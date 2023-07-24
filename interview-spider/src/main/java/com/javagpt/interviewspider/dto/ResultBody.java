package com.javagpt.interviewspider.dto;

import com.javagpt.interviewspider.entity.InterviewEntity;
import lombok.Data;

/**
 *
 * @Author bubaiwantong
 * @Date 2023/7/24 17:38
 * @PackageName:com.javagpt.interviewspider.dto
 * @ClassName: ResultBody
 * @Version 1.0
 */
@Data
public class ResultBody {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 状态码
     */
    private int code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据
     */
    private Page<InterviewEntity> Data;


}
