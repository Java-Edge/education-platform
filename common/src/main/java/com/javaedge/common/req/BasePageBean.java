package com.javaedge.common.req;

import com.javaedge.common.annotation.IgnoreQuery;
import lombok.Data;

/**
 * @author JavaEdge
 * @date 2023/3/7
 */
@Data
public class BasePageBean extends BaseBean {

    @IgnoreQuery
    private int page = 1;

    @IgnoreQuery
    private int size = 20;

    /**
     * 默认id倒序
     */
    @IgnoreQuery
    private String order = "id,desc";
}
