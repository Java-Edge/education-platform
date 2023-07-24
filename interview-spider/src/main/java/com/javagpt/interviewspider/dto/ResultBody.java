package com.javagpt.interviewspider.dto;

import com.javagpt.interviewspider.data.InterviewData;
import lombok.Data;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by JavaGPT in 2023/7/23 0:01
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
    private Page<InterviewData> data;


}
