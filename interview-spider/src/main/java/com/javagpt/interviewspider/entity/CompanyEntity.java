package com.javagpt.interviewspider.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName company
 */
@TableName(value ="company")
@Data
public class CompanyEntity implements Serializable {
    /**
     * 公司编号
     */
    @TableId
    private Long id;

    /**
     * 公司图片
     */
    private String picUrl;

    /**
     * 名称
     */
    private String companyName;

    /**
     *
     */
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
     *
     */
    private String followCount;

    /**
     *
     */
    private Integer projectId;

    /**
     *
     */
    private Integer searchSource;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
