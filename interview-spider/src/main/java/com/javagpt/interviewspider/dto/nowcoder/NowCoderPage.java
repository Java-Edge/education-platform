package com.javagpt.interviewspider.dto.nowcoder;

import lombok.Data;

import java.util.List;

/**
 * All rights Reserved, Designed By www.tom.com
 *
 * @Author 徐望成
 * @Date 2023/8/1 15:07
 * @PackageName:com.javagpt.interviewspider.dto.nowcoder
 * @ClassName: NowCoderPage
 * @Description: TODO
 * @Copyright: 2019 www.tomonline-inc.com Inc. All rights reserved.
 * 注意：本内容仅限于TOM集团内部传阅，禁止外泄以及用于其他的商业目
 * @Version 1.0
 */
@Data
public class NowCoderPage<T> {

    /**
     * 总数
     */
    private Integer totalCount;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 数据
     */
    private List<T> datas;

}
