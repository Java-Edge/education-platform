package com.javaedge.infra.mysql.po.growth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 签到记录持久化对象，对应表 {@code sign_record}。
 *
 * <p>业务键为（{@code userId + year + month + day}），与领域实体 {@code SignRecord} 一一对应。
 * 仓储重建聚合时只加载<b>当月</b>记录，以保证连续天数计算所需的一致性边界。
 */
@Data
@TableName(value = "sign_record")
public class SignRecordPO implements Serializable {

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
