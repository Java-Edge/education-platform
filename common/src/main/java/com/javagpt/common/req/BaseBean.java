package com.javagpt.common.req;

import com.javagpt.common.annotation.IgnoreQuery;
import lombok.Data;

import java.io.Serializable;

/**
 * @author JavaEdge
 * @date 2023/3/7
 */
@Data
public class BaseBean implements Serializable {

    @IgnoreQuery
    private static final long serialVersionUID = 7272078611895639472L;

    private Long id;
}
