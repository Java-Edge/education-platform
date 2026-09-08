package com.javaedge.infra.growth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javaedge.growth.aggregate.MemberGrowth;
import com.javaedge.growth.aggregate.AwardOutcome;
import com.javaedge.growth.aggregate.SignOutcome;
import com.javaedge.growth.entity.SignRecord;
import com.javaedge.growth.repository.MemberGrowthRepository;
import com.javaedge.growth.valueobject.MemberId;
import com.javaedge.growth.valueobject.SignDate;
import com.javaedge.infra.mysql.mapper.IntegralLogMapper;
import com.javaedge.infra.mysql.mapper.MemberAccountMapper;
import com.javaedge.infra.mysql.mapper.GrowthSignRecordMapper;
import com.javaedge.infra.mysql.po.growth.IntegralLogPO;
import com.javaedge.infra.mysql.po.growth.MemberAccountPO;
import com.javaedge.infra.mysql.po.growth.SignRecordPO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 会员成长仓储实现（基础设施层）。
 *
 * <p>实现定义在领域层的 {@link MemberGrowthRepository} 接口。它操作的是<b>整个聚合</b>，
 * 对上层屏蔽了"积分账户表 / 积分流水表 / 签到记录表"三张表的具体拼装。
 *
 * <p>本实现<b>不</b>标注 {@code @Transactional}：事务边界放在应用服务层（一次签到 = 一个事务），
 * 这里只做纯粹的持久化动作，职责单一、可直接单测。
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberGrowthRepositoryImpl implements MemberGrowthRepository {

    private final MemberAccountMapper accountMapper;
    private final IntegralLogMapper logMapper;
    private final GrowthSignRecordMapper signRecordMapper;

    @Override
    public Optional<MemberGrowth> findById(MemberId memberId) {
        QueryWrapper<MemberAccountPO> qw = new QueryWrapper<>();
        qw.eq("user_id", memberId.value());
        MemberAccountPO account = accountMapper.selectOne(qw);
        if (account == null) {
            return Optional.empty();
        }
        // 加载本月签到记录，保证连续天数计算所需的一致性边界
        SignDate today = SignDate.today();
        QueryWrapper<SignRecordPO> sqw = new QueryWrapper<>();
        sqw.eq("user_id", memberId.value());
        sqw.eq("year", today.year());
        sqw.eq("month", today.month());
        List<SignRecordPO> signPORecords = signRecordMapper.selectList(sqw);
        return Optional.of(MemberGrowthAssembler.toAggregate(account, signPORecords));
    }

    @Override
    public void save(MemberGrowth growth, SignOutcome outcome) {
        // 1) 积分账户 upsert（按 userId 存在则更新，否则插入）
        upsertAccount(growth);
        // 2) 积分流水（基础分 + 连续奖励分）
        List<IntegralLogPO> logs = MemberGrowthAssembler.toIntegralLogs(growth.memberId(), outcome.reward());
        for (IntegralLogPO log : logs) {
            logMapper.insert(log);
        }
        // 3) 本次新增、尚未持久化的签到记录
        List<SignRecord> pending = growth.drainPendingSignRecords();
        for (SignRecord record : pending) {
            signRecordMapper.insert(MemberGrowthAssembler.toSignRecordPO(record));
        }
        log.debug("会员 {} 签到已落库：连续 {} 天，奖励 {} 分",
                growth.memberId().value(), outcome.continuousDays(), outcome.reward().total().amount());
    }

    @Override
    public void saveAward(MemberGrowth growth, AwardOutcome outcome, int logType) {
        // 1) 积分账户 upsert
        upsertAccount(growth);
        // 2) 课程完成奖励流水（单条，类型由调用方指定）
        if (outcome.granted().amount() > 0) {
            IntegralLogPO log = new IntegralLogPO();
            log.setUserId(growth.memberId().value());
            log.setIntegralType(logType);
            log.setIntegral(outcome.granted().amount());
            log.setCreateTime(java.time.LocalDateTime.now());
            logMapper.insert(log);
        }
        log.debug("会员 {} 课程奖励已落库：发放 {} 分", growth.memberId().value(), outcome.granted().amount());
    }

    private void upsertAccount(MemberGrowth growth) {
        QueryWrapper<MemberAccountPO> qw = new QueryWrapper<>();
        qw.eq("user_id", growth.memberId().value());
        MemberAccountPO existing = accountMapper.selectOne(qw);
        if (existing == null) {
            accountMapper.insert(MemberGrowthAssembler.toAccountPO(growth));
        } else {
            existing.setIntegral(growth.totalPoints().amount());
            accountMapper.updateById(existing);
        }
    }
}
