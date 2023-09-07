package com.javagpt.interviewspider.controller;

import com.javagpt.interviewspider.dto.nowcoder.CareerDTO;
import com.javagpt.interviewspider.entity.CareerEntity;
import com.javagpt.interviewspider.entity.service.CareerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by JavaGPT in 2023/7/23 0:01
 */
@RestController
@RequestMapping("/career")
public class CareerController {


    @Autowired
    private CareerService careerService;

    @GetMapping("/grabAllCareer")
    public void grabAllCareer(){
        List<CareerDTO> list = careerService.grabAllCareer();

        List<CareerEntity> careerEntities = new ArrayList<>();
        for (CareerDTO careerDTO : list) {
            CareerEntity careerEntity = new CareerEntity();
            BeanUtils.copyProperties(careerDTO,careerEntity);
            careerEntities.add(careerEntity);
        }
        boolean b = careerService.saveOrUpdateBatch(careerEntities);
    }


    @GetMapping("/grabLevel3Career")
    public void grabLevel3Career(){
        careerService.grabLevel3Career();
    }



}
