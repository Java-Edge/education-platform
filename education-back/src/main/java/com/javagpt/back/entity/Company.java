package com.javagpt.back.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 公司主体
 * </p>
 *
 * @author zqy
 * @since 2023-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编号
     */
      private String id;

    /**
     * 公司图片
     */
    private String picUrl;

    /**
     * 名称
     */
    private String companyName;

    private String scaleTagName;

    /**
     * 公司规模
     */
    private String personScales;

    /**
     * 公司简称
     */
    private String companyShortName;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 公司性质
     */
    private Integer companyNatureId;


}
