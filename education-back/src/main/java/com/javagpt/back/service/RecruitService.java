package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.PageQueryParam;
import com.javagpt.back.dto.RecruitDTO;
import com.javagpt.back.entity.Recruit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.vo.RecruitVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zqy
 * @since 2023-07-23
 */
public interface RecruitService extends IService<Recruit> {

    Page<Recruit> selectPage(Integer current, Integer size, HttpServletRequest request);


    /**
     * 分页条件查询
     *
     * @param pageQueryParam
     * @return
     */
    Page<RecruitVO> selectByCondition( PageQueryParam<RecruitDTO> pageQueryParam, HttpServletRequest request);


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    RecruitVO queryById(String id);

    /**
     * 获取热门职位
     * @return
     */
    List<Recruit> getHotRecruits();


}
