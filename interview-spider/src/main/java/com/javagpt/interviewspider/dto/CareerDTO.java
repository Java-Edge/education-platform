package com.javagpt.interviewspider.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By www.tom.com
 *
 * @Author 徐望成
 * @Date 2023/7/25 16:41
 * @PackageName:com.javagpt.interviewspider.dto
 * @ClassName: CareerDTO
 * @Description: TODO
 * @Copyright: 2019 www.tomonline-inc.com Inc. All rights reserved.
 * 注意：本内容仅限于TOM集团内部传阅，禁止外泄以及用于其他的商业目
 * @Version 1.0
 */
@Data
public class CareerDTO implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 职位名称
     */
    private String name;

    /**
     * 是否用户职业
     */
    private Boolean isUserCareerJob;
}
