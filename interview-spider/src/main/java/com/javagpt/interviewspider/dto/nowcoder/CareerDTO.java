package com.javagpt.interviewspider.dto.nowcoder;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @Author JavaGPT
 * @Date 2023/7/25 16:41
 * @PackageName:com.javagpt.interviewspider.dto
 * @ClassName: CareerDTO
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
     * 父类别
     */
    private Integer parentId;


    /**
     * 是否用户职业
     */
    private Boolean isUserCareerJob;
}
