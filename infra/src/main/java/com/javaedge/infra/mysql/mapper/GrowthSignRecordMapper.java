package com.javaedge.infra.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaedge.infra.mysql.po.growth.SignRecordPO;

/**
 * 会员成长子域 - 签到记录 Mapper。
 *
 * <p>命名加 {@code Growth} 前缀以区别于旧 {@code com.javaedge.back.mapper.SignRecordMapper}，
 * 避免被 {@code @MapperScan("com.javaedge.infra.mysql.mapper")} 与 {@code @EnableMPP} 同时扫描时
 * 产生同名 bean（{@code signRecordMapper}）冲突。
 */
public interface GrowthSignRecordMapper extends BaseMapper<SignRecordPO> {
}
