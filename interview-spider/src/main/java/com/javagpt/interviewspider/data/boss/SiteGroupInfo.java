package com.javagpt.interviewspider.data.boss;

import lombok.Data;

import java.util.List;

/**
 * @author bubaiwantong
 * @date 2023/7/30 20:44
 * @description this is a class file created by bubaiwantong in 2023/7/30 20:44
 */
@Data
public class SiteGroupInfo {


    private String firstChar;

    private List<CityData> cityList;

}
