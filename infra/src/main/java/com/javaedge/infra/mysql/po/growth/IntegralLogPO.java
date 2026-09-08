package com.javaedge.infra.mysql.po.growth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 积分流水持久化对象，对应表 {@code user_integral_log}。
 *
 * <p>一次签到可能产生两条流水：基础分（{@code integralType=1}）+ 连续奖励分（{@code integralType=2}），
 * 对应 {@code UserSignTypeEnums} 的两种语义。流水只追加、不更新，便于审计与回放。
 */
@Data
@TableName(value = "user_integral_log")
public class IntegralLogPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 积分类型：1.正常签到 2.连续签到奖励
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
