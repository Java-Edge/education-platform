package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.RecruitPO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.back.dto.RecruitQO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.vo.RecruitEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface RecruitService extends IService<RecruitPO> {

    Page<RecruitPO> selectPage(Integer current, Integer size, HttpServletRequest request);


    /**
     * 分页条件查询
     */
    Page<RecruitEntity> selectByCondition(PageQueryParam<RecruitQO> pageQueryParam, HttpServletRequest request);


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
    List<RecruitPO> getHotRecruits();


}
