package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zqy
 * @since 2023-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pilot implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    /**
     * 创建时间
     */
/*    private LocalDateTime createTime;*/

    private Integer pilotType;

    @TableField(exist = false)
    private String pilotTypeName;

}
