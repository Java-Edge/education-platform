package com.javagpt.interviewspider.service;

import com.javagpt.interviewspider.dto.CareerDTO;
import com.javagpt.interviewspider.entity.CareerEntity;

import java.util.List;

/**
 * @author bubaiwantong
 * @date 2023/7/23 0:01
 * @description this is a class file created by bubaiwantong in 2023/7/23 0:01
 */
public interface SpiderService {

    void work();

    /**
     * 获取职业
     *
     * @return
     */
    List<CareerDTO> grabCareer();

}
