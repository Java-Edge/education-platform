package com.javagpt.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.dto.InnerRecommendQueryDTO;
import com.javagpt.back.entity.InnerRecommend;
import com.javagpt.back.vo.InnerRecommendVO;
import com.javagpt.common.req.PageQueryParam;

/**
* @author 26314
* @description 针对表【inner_recommend】的数据库操作Service
* @createDate 2023-10-22 23:10:25
*/
public interface InnerRecommendService extends IService<InnerRecommend> {

    /**
     * 条件查询
     *
     * @param pageQueryParam
     * @return
     */
    IPage<InnerRecommendVO> selectByCondition(PageQueryParam<InnerRecommendQueryDTO> pageQueryParam);

    /**
     * 获取文章
     *
     * @param id
     * @return
     */
    InnerRecommend articleOf(String id);

}
