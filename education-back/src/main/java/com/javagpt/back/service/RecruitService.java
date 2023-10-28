package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.back.dto.Recruit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.vo.RecruitEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface RecruitService extends IService<com.javagpt.back.entity.Recruit> {

    Page<com.javagpt.back.entity.Recruit> selectPage(Integer current, Integer size, HttpServletRequest request);


    /**
     * 分页条件查询
     */
    Page<RecruitEntity> selectByCondition(PageQueryParam<Recruit> pageQueryParam, HttpServletRequest request);


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    RecruitEntity queryById(String id);

    /**
     * 获取热门职位
     * @return
     */
    List<com.javagpt.back.entity.Recruit> getHotRecruits();


}
