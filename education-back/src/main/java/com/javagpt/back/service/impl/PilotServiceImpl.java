package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.entity.Pilot;
import com.javagpt.back.mapper.PilotMapper;
import com.javagpt.back.service.DictionaryService;
import com.javagpt.back.service.PilotService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zqy
 * @since 2023-09-12
 */
@Service
public class PilotServiceImpl extends ServiceImpl<PilotMapper, Pilot> implements PilotService {

    @Resource
    private PilotMapper pilotMapper;

    @Resource
    private DictionaryService dictionaryService;

    @Override
    public List<Pilot> getList() {
        QueryWrapper<Pilot> qw = new QueryWrapper<>();
        List<Pilot> pilots = pilotMapper.selectList(qw);
        return pilots;
    }
}
