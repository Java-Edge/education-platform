package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mkingg
 * @since 2023-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SignRecord implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 签到年份
     */
    private Integer year;

    /**
     * 签到月份
     */
    private Integer month;

    /**
     * 签到日期
     */
    private Integer day;


}
