package com.javagpt.interviewspider.dto.nowcoder;

import lombok.Data;

import java.util.List;

/**
 * @Author JavaGPT
 * @Date 2023/8/1 15:07
 * @PackageName:com.javagpt.interviewspider.dto.nowcoder
 * @ClassName: NowCoderPage
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
