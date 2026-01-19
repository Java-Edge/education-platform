package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaedge.back.entity.SignRecord;
import com.javaedge.back.entity.UserIntegral;
import com.javaedge.back.entity.UserIntegralLog;
import com.javaedge.back.mapper.UserIntegralMapper;
import com.javaedge.back.service.SignRecordService;
import com.javaedge.back.service.UserIntegralLogService;
import com.javaedge.back.service.UserIntegralService;
import com.javaedge.common.drools.DroolsRuleService;
import com.javaedge.common.drools.model.UserSignIntegralFact;
import com.javaedge.common.enums.UserSignTypeEnums;
import com.javaedge.common.resp.ResultBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户积分服务实现类（使用Drools规则引擎重构）
 * 
 * @author JavaEdge
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserIntegralServiceImplWithDrools extends ServiceImpl<UserIntegralMapper, UserIntegral> implements UserIntegralService {

    private final UserIntegralLogService userIntegralLogService;
    private final SignRecordService signRecordService;
    private final DroolsRuleService droolsRuleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBody sign(Integer userId) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        
        // 检查今天是否已签到
        QueryWrapper<SignRecord> qw = new QueryWrapper<>();
        qw.eq("year", year);
        qw.eq("month", month);
        qw.eq("day", day);
        qw.eq("user_id", userId);
        if (signRecordService.list(qw).size() > 0) {
            return ResultBody.success("用户已经签到");
        }

        // 记录今天签到
        SignRecord signRecord = new SignRecord();
        signRecord.setYear(year);
        signRecord.setMonth(month);
        signRecord.setDay(day);
        signRecord.setUserId(userId);
        signRecordService.save(signRecord);

        // 获取连续签到天数
        int continuousSignDays = getContinuousSignDays(userId);
        log.info("用户{}连续签到天数为{}", userId, continuousSignDays);

        // 使用Drools规则引擎计算积分
        UserSignIntegralFact signFact = UserSignIntegralFact.buildSignFact(userId, continuousSignDays);
        
        // 执行规则并传入logger作为全局变量
        droolsRuleService.executeRulesWithGlobal(signFact, "logger", LoggerFactory.getLogger("drools.rules"));

        // 保存积分日志
        List<UserIntegralLog> userIntegralLogs = new ArrayList<>();
        
        // 基础积分日志
        if (signFact.getBaseIntegral() > 0) {
            UserIntegralLog baseLog = new UserIntegralLog();
            baseLog.setIntegral(signFact.getBaseIntegral());
            baseLog.setUserId(userId);
            baseLog.setIntegralType(UserSignTypeEnums.SIGN_NORMAL_INTEGRAL.getType());
            baseLog.setCreateTime(LocalDateTime.now());
            userIntegralLogs.add(baseLog);
        }

        // 奖励积分日志
        if (signFact.getBonusIntegral() > 0) {
            UserIntegralLog bonusLog = new UserIntegralLog();
            bonusLog.setIntegral(signFact.getBonusIntegral());
            bonusLog.setUserId(userId);
            bonusLog.setIntegralType(UserSignTypeEnums.SIGN_CONTINOUS_INTEGRAL.getType());
            bonusLog.setCreateTime(LocalDateTime.now());
            userIntegralLogs.add(bonusLog);
        }

        // 更新用户总积分
        boolean updateSuccess = updateUserIntegral(userId, signFact.getTotalIntegral());
        boolean logSuccess = userIntegralLogService.saveBatch(userIntegralLogs);

        if (updateSuccess && logSuccess) {
            String message = String.format("签到成功！获得%d积分（基础%d + 奖励%d）%s", 
                signFact.getTotalIntegral(), 
                signFact.getBaseIntegral(), 
                signFact.getBonusIntegral(),
                signFact.getBonusDescription().isEmpty() ? "" : "，" + signFact.getBonusDescription());
            return ResultBody.success(message);
        } else {
            return ResultBody.error("签到失败");
        }
    }

    /**
     * 更新用户积分
     */
    private boolean updateUserIntegral(Integer userId, int count) {
        QueryWrapper<UserIntegral> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        UserIntegral userIntegral = this.getBaseMapper().selectOne(qw);
        
        // 如果之前没有记录用户积分，这里记录一下
        if (userIntegral == null) {
            userIntegral = new UserIntegral();
            userIntegral.setUserId(userId);
            userIntegral.setIntegral(0);
            userIntegral.setCreateTime(LocalDateTime.now());
            this.getBaseMapper().insert(userIntegral);
        }
        
        userIntegral.setIntegral(userIntegral.getIntegral() + count);
        int i = this.getBaseMapper().updateById(userIntegral);
        return i > 0;
    }

    /**
     * 获取连续签到天数
     */
    private int getContinuousSignDays(Integer userId) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        
        QueryWrapper<SignRecord> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        qw.eq("year", year);
        qw.eq("month", month);
        qw.ge("day", 1);
        qw.le("day", day);
        qw.orderByDesc("day");
        
        List<SignRecord> signRecords = signRecordService.list(qw);
        if (signRecords == null || signRecords.isEmpty()) {
            return 0;
        }

        int continuousSignDays = 0;
        for (int i = 0; i < signRecords.size(); i++) {
            SignRecord signRecord = signRecords.get(i);
            // 检查是否连续签到
            if (signRecord.getDay() == day - i) {
                continuousSignDays++;
            } else {
                break;
            }
        }

        return continuousSignDays;
    }
}
