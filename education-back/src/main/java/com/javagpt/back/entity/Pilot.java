package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.javagpt.infra.mysql.po.common.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Pilot extends BasePO {

    /**
     * 导航名称
     */
    private String name;

    /**
     * 导航链接
     */
    private String link;

    /**
     * 导航图标
     */
    private String img;

    private Integer pilotType;

    private Integer pageView;

    @TableField(exist = false)
    private String pilotTypeName;
}
