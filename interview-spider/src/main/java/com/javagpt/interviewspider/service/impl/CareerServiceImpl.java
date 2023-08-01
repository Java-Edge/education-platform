package com.javagpt.interviewspider.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.interviewspider.dto.nowcoder.CareerDTO;
import com.javagpt.interviewspider.dto.nowcoder.CareerJobSelector;
import com.javagpt.interviewspider.dto.nowcoder.Result;
import com.javagpt.interviewspider.dto.nowcoder.ResultBody;
import com.javagpt.interviewspider.entity.CareerEntity;
import com.javagpt.interviewspider.service.CareerService;
import com.javagpt.interviewspider.mapper.CareerMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
* @author JavaGPT
* @description 针对表【career】的数据库操作Service实现
* @createDate 2023-07-25 15:49:38
*/
@Service
public class CareerServiceImpl extends ServiceImpl<CareerMapper, CareerEntity>
    implements CareerService{

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 获取职业
     */
    @Override
    public List<CareerDTO> grabLevel3Career() {
        try {
            String url = "https://gw-c.nowcoder.com/api/sparta/job-experience/experience/job/level3CareerJob?careerJobId=11200&_=1690270527713";
            ResponseEntity<ResultBody> response = restTemplate.getForEntity(url, ResultBody.class);
            ResultBody<Result> body = response.getBody();

            Result<CareerDTO> result = JSON.parseObject(JSON.toJSONString(body.getData()), Result.class);

            List<CareerDTO> list = JSON.parseArray(JSON.toJSONString(result.getResult()), CareerDTO.class);

            List<CareerEntity> careerEntities = new ArrayList<>();
            for (CareerDTO careerDTO : list) {
                CareerEntity careerEntity = new CareerEntity();
                BeanUtils.copyProperties(careerDTO, careerEntity);
                careerEntities.add(careerEntity);
            }

            boolean b = saveOrUpdateBatch(careerEntities);

            if (response.getStatusCodeValue() != 200) {
                log.error("response status code not 200");
            }
            return null;
        } catch (Exception e) {
            log.error("can not grab career", e);
        }
        return null;
    }

    @Override
    public List<CareerDTO> grabAllCareer() {
        try {
            String url = "https://gw-c.nowcoder.com/api/sparta/job-experience/experience/job/selector?_=1690278216728";
            ResponseEntity<ResultBody> response = restTemplate.getForEntity(url, ResultBody.class);
            ResultBody<Result> body = response.getBody();
            if (response.getStatusCodeValue() != 200) {
                log.error("response status code not 200");
            }
            Result<CareerJobSelector> result = JSON.parseObject(JSON.toJSONString(body.getData()), Result.class);

            List<CareerJobSelector> list = JSON.parseArray(JSON.toJSONString(result.getCareerJobSelectors()), CareerJobSelector.class);

            List<CareerDTO> careerDTOS = new ArrayList<>();
            for (CareerJobSelector careerJobSelector : list) {
                CareerDTO careerDTO = new CareerDTO();
                BeanUtils.copyProperties(careerJobSelector,careerDTO);
                List<CareerDTO> childCareerJobs = careerJobSelector.getChildCareerJobs();

                careerDTOS.add(careerDTO);

                if (childCareerJobs != null && childCareerJobs.size() >0 ){
                    for (CareerDTO childCareerJob : childCareerJobs) {
                        CareerDTO childCareerDTO = new CareerDTO();
                        BeanUtils.copyProperties(childCareerJob,childCareerDTO);
                        childCareerDTO.setParentId(careerJobSelector.getId());

                        careerDTOS.add(childCareerDTO);
                    }
                }
            }
            return careerDTOS;
        } catch (Exception e) {
            log.error("can not grab career", e);
        }
        return null;
    }



}




