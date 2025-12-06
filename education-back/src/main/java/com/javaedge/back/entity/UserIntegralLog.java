package com.javaedge.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2023-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserIntegralLog implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 积分类型：1.签到
     */
    private Integer integralType;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
