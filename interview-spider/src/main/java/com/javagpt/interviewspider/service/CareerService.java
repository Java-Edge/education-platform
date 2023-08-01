package com.javagpt.interviewspider.service;

import com.javagpt.interviewspider.dto.nowcoder.CareerDTO;
import com.javagpt.interviewspider.entity.CareerEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author JavaGPT
* @description 针对表【career】的数据库操作Service
* @createDate 2023-07-25 15:49:38
*/
public interface CareerService extends IService<CareerEntity> {

    /**
     * 抓取所有职业
     *
     * @return
     */
    List<CareerDTO> grabAllCareer();


    /**
     * 获取职业
     *
     * @return
     */
    List<CareerDTO> grabLevel3Career();


}
