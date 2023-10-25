package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.javagpt.back.converter.InnerConverter;
import com.javagpt.back.dto.InnerRecommendQueryDTO;
import com.javagpt.back.entity.InnerRecommend;
import com.javagpt.back.service.InnerRecommendService;
import com.javagpt.back.vo.InnerRecommendVO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.common.resp.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 内推页面入口
 */
@RestController
@RequestMapping("/innerRecommend")
public class InnerRecommendController {

    @Autowired
    private InnerRecommendService innerRecommendService;


    @PostMapping("/selectByCondition")
    public ResultBody selectByCondition(@RequestBody PageQueryParam<InnerRecommendQueryDTO> pageQueryParam){
        IPage<InnerRecommend> innerRecommendIPage = innerRecommendService.selectByCondition(pageQueryParam);
        List<InnerRecommendVO> interviewVOS = Lists.newArrayList();
        for (InnerRecommend innerRecommend: innerRecommendIPage.getRecords()) {
            interviewVOS.add(InnerConverter.INSTANCE.toVO(innerRecommend));
        }
        IPage<InnerRecommendVO> interviewVOIPage = new Page<>();
        interviewVOIPage.setRecords(interviewVOS);
        interviewVOIPage.setTotal(innerRecommendIPage.getTotal());
        return ResultBody.success(interviewVOIPage);
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable String id) {
        InnerRecommend article = innerRecommendService.articleOf(id);
        return ResultBody.success(article);
    }
}
