package com.javaedge.back.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zqy
 * @since 2023-08-10
 */
@Data
public class CompanyNatureVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司性质id
     */
    private Integer id;

    /**
     * 公司性质名
     */
    private String name;

    /**
     * 公司性质简称
     */
    private String shortName;

}
