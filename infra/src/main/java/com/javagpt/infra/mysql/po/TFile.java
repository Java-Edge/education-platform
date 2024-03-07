package com.javagpt.infra.mysql.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.javagpt.infra.mysql.po.common.BaseAuditPO;
import lombok.Data;

@TableName(value = "t_file_info")
@Data
public class TFile extends BaseAuditPO {
    /**
     * 主键id
     */
    @TableId(type = IdType.NONE)
    private Long id;

    /**
     * 企业id
     */
    private Long enterpriseId;

    /**
     * 存储类型 1、百度云
     */
    private Integer storageType;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件后缀,如pdf，word
     */
    private String suffix;

    /**
     * 文件存储路径
     */
    private String path;
}