package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author zhmsky
 * @since 2022-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UcenterMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String openid;
    private String mobile;

    private String password;

    private String nickname;
    private Integer sex;
    private Integer age;
    private String avatar;
    private String sign;
    private Boolean isDisabled;
    private Boolean isDeleted;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

}

