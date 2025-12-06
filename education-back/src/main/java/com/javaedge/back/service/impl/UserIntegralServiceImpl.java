package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javaedge.back.entity.SignRecord;
import com.javaedge.back.entity.UserIntegral;
import com.javaedge.back.entity.UserIntegralLog;
import com.javaedge.back.mapper.UserIntegralMapper;
import com.javaedge.back.service.SignRecordService;
import com.javaedge.back.service.UserIntegralLogService;
import com.javaedge.back.service.UserIntegralService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaedge.common.constant.UserSignConstant;
import com.javaedge.common.enums.UserSignTypeEnums;
import com.javaedge.common.resp.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zqy
 * @since 2023-10-26
 */
@Service
@Slf4j
public class UserIntegralServiceImpl extends ServiceImpl<UserIntegralMapper, UserIntegral> implements UserIntegralService {

    @Autowired
    private UserIntegralLogService userIntegralLogService;

    @Autowired
    private SignRecordService signRecordService;

    @Override
    public ResultBody sign(Integer userId) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
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

        // 用户所得积分
        int count = 0;

        // 每次签到获得基础分数
        count += UserSignConstant.SIGN_TYPE_NORMAL_INTEGRAL;

        log.info("{}在{}:{}:{}签到", userId, year, month, day);

        List<UserIntegralLog> userIntegralLogs = new ArrayList<>();

        // 记录正常签到日志
        UserIntegralLog userIntegralLog = new UserIntegralLog();
        userIntegralLog.setIntegral(count);
        userIntegralLog.setUserId(userId);
        userIntegralLog.setIntegralType(UserSignTypeEnums.SIGN_NORMAL_INTEGRAL.getType());
        userIntegralLog.setCreateTime(LocalDateTime.now());

        userIntegralLogs.add(userIntegralLog);

        int continousSignDays = getContinousSignDays(userId);
        log.info("用户{}连续签到天数为{}", userId, continousSignDays);
        // 如果连续签到的天数等于当月的总天数的话，表明整月全部签到，用连续签到 32 天来标记
        if (continousSignDays == LocalDate.now().lengthOfMonth()) {
            continousSignDays = 32;
        }
        Map<Integer, Integer> signConfiguration = UserSignConstant.SIGN_CONFIGURATION;

        /**
         * 获取当前连续签到天数所对应的奖励，只有连续签到指定天数才会获得奖励
         * 如果对应的连续签到天数没有奖励，那么获取的 continousIntegral 为 null
         */
        Integer continousIntegral = signConfiguration.get(continousSignDays);
        if (continousIntegral != null) {
            // 如果是整月都签到，这里将连续签到天数设置为准确的该月天数
            if (continousSignDays == 32) {
                continousSignDays = LocalDate.now().lengthOfMonth();
            }
            // 记录连续签到获得奖励日志
            UserIntegralLog continousIntegralLog = new UserIntegralLog();
            continousIntegralLog.setIntegral(continousIntegral);
            continousIntegralLog.setUserId(userId);
            continousIntegralLog.setIntegralType(UserSignTypeEnums.SIGN_CONTINOUS_INTEGRAL.getType());
            continousIntegralLog.setCreateTime(LocalDateTime.now());
            userIntegralLogs.add(continousIntegralLog);
            // count 为用户本次签到所得积分
            count += continousIntegral;
        }

        boolean flag = updateUserIntegral(userId, count) && userIntegralLogService.saveBatch(userIntegralLogs);

        return flag ? ResultBody.success("签到成功") : ResultBody.error("签到失败");
    }

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



    public int getContinousSignDays(Integer userId) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        QueryWrapper<SignRecord> qw = new QueryWrapper<>();
        qw.eq("year", year);
        qw.eq("month", month);
        qw.ge("day", 1);
        qw.le("day", day);
        List<SignRecord> signRecords = signRecordService.list(qw);
        int continousSignDay = 0;
        if (signRecords == null || signRecords.size() == 0) return continousSignDay;

        for (int i = signRecords.size() - 1; i >= 0; i --) {
            SignRecord signRecord = signRecords.get(i);
            if (signRecord.getDay() == day) {
                continousSignDay ++;
            } else {
                break;
            }
        }

        return continousSignDay;
    }


}
