package com.javaedge.application.dict.param;

import com.javaedge.common.req.BasePageBean;
import lombok.Data;

import java.util.List;

/**
 * @author JavaEdge
 * @date 2024/3/5
 */
@Data
public class DictionaryTypeParam extends BasePageBean {

    private List<String> typeKeys;

}
