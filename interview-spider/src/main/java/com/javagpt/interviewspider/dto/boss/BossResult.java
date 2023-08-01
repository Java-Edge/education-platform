package com.javagpt.interviewspider.dto.boss;

import com.javagpt.interviewspider.data.boss.SiteGroupInfo;
import lombok.Data;

import java.util.List;

/**
 * @author bubaiwantong
 * @date 2023/7/29 23:31
 * @description this is a class file created by bubaiwantong in 2023/7/29 23:31
 */
@Data
public class BossResult<T> {

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 全国城市分类
     */
    private List<T> siteList;

    /**
     * 热门城市
     */
    private List<T> hotCitySites;

    /**
     * 根据拼音首字母分类城市
     */
    private List<SiteGroupInfo> siteGroup;

    /**
     *
     */
    private List<T> otherCitySites;

}
