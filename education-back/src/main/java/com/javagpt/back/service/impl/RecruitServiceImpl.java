package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.PageQueryParam;
import com.javagpt.back.dto.RecruitDTO;
import com.javagpt.back.entity.Recruit;
import com.javagpt.back.mapper.RecruitMapper;
import com.javagpt.back.service.RecruitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.vo.RecruitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zqy
 * @since 2023-07-23
 */
@Service
public class RecruitServiceImpl extends ServiceImpl<RecruitMapper, Recruit> implements RecruitService {


    @Autowired
    private RecruitMapper recruitMapper;

    @Override
    public Page<Recruit> selectPage(Integer current, Integer size) {
        Page<Recruit> page = new Page<>(current, size);
        QueryWrapper<Recruit> qw = new QueryWrapper<>();
        qw.orderByDesc("create_time");
        Page<Recruit> recruitPage = this.baseMapper.selectPage(page, qw);
        return recruitPage;
    }

    @Override
    public Page<RecruitVO> selectByCondition(PageQueryParam<RecruitDTO> pageQueryParam) {
        Page<Recruit> page = new Page<>();
        page.setCurrent(pageQueryParam.getPageNo());
        page.setSize(pageQueryParam.getPageSize());

        Page<RecruitVO> recruitVOPage = recruitMapper.queryPage(page, pageQueryParam.getParam());

        return recruitVOPage;
    }

    @Override
    public RecruitVO queryById(String id) {
        RecruitVO recruitVO = recruitMapper.queryById(id);
        return recruitVO;
    }
}
