package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.javagpt.back.dto.InnerRecommendQueryDTO;
import com.javagpt.back.entity.InnerRecommend;
import com.javagpt.back.service.InnerRecommendService;
import com.javagpt.back.vo.InnerRecommendVO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.common.resp.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/innerRecommend")
public class InnerRecommendController {

    @Autowired
    private InnerRecommendService innerRecommendService;


    @PostMapping("/selectByCondition")
    public ResultBody selectByCondition(@RequestBody PageQueryParam<InnerRecommendQueryDTO> pageQueryParam){
        IPage<InnerRecommendVO> articleVOIPage = innerRecommendService.selectByCondition(pageQueryParam);
        return ResultBody.success(articleVOIPage);
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable String id) {
        InnerRecommend article = innerRecommendService.articleOf(id);
        return ResultBody.success(article);
    }


}
