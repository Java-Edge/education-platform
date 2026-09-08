package com.javaedge.growth.factory;

import com.javaedge.growth.aggregate.MemberGrowth;
import com.javaedge.growth.entity.SignRecord;
import com.javaedge.growth.valueobject.MemberId;
import com.javaedge.growth.valueobject.MemberLevel;
import com.javaedge.growth.valueobject.Points;

import java.util.List;

/**
 * 会员成长工厂（Factory）。
 *
 * <p>当聚合根的创建过程比 {@code new} 复杂（需要从多个持久化对象组装、需要校验、
 * 或需要决定初始状态）时，用工厂封装"重建"逻辑，避免调用方了解内部构造细节。
 * 此处用于在仓储从 PO 重建聚合根时，把"积分/等级/签到记录"拼装成合法的聚合根。
 */
public final class MemberGrowthFactory {

    private MemberGrowthFactory() {
    }

    public static MemberGrowth reconstitute(MemberId memberId, int totalPoints, int level, List<SignRecord> signRecords) {
        return new MemberGrowth(memberId, Points.of(totalPoints), MemberLevel.of(level), signRecords);
    }
}
