package com.javaedge.infra.mysql.po.growth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会员积分账户持久化对象，对应表 {@code user_integral}。
 *
 * <p>该 PO 刻意<b>不复用 {@code BasePO}</b>：聚合根中积分/等级由领域模型推导，
 * 仓储只负责维护 {@code integral} 等必要列，避免被 {@code deleteFlag/createTime/updateTime}
 * 等审计列耦合。等级（level）不落库——它是累计积分的纯函数，重建时由 {@code MemberLevel.fromPoints} 推导。
 */
@Data
@TableName(value = "user_integral")
public class MemberAccountPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 累计积分
     */
    private Integer integral;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
