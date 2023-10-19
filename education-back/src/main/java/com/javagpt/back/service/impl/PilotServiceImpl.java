package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.entity.Pilot;
import com.javagpt.back.mapper.PilotMapper;
import com.javagpt.back.service.DictionaryService;
import com.javagpt.back.service.PilotService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.javagpt.common.constant.Constants.cache_max_dict_refresh_counts;
import static com.javagpt.common.constant.Constants.cache_max_pilot_refresh_counts;

@Service
@Slf4j
public class PilotServiceImpl extends ServiceImpl<PilotMapper, Pilot> implements PilotService {

    @Resource
    private PilotMapper pilotMapper;

    @Resource
    private DictionaryService dictionaryService;

    @Resource
    private Cache<Integer, List<Pilot>> pilotRefreshCountsCache;

    @Resource
    private Cache<Integer, List<Dictionary>> dictRefreshCache;

    @Override
    public Map<String, List<Pilot>> getList() {
        List<Dictionary> pilotTypesCache = dictRefreshCache.get(cache_max_dict_refresh_counts, s -> {
            log.info("缓存过期，从 MySQL 查询");
            return dictionaryService.selectList("pilot_type");
        });

        Map<String , List<Pilot>> ret = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        for (Dictionary d : pilotTypesCache) {
            map.put(d.getValue(), d.getLabel());
        }

        List<Pilot> pilotsCache = pilotRefreshCountsCache.get(cache_max_pilot_refresh_counts, s -> {
            QueryWrapper<Pilot> qw = new QueryWrapper<>();
            return pilotMapper.selectList(qw);
        });
        for (Pilot pilot : pilotsCache) {
            pilot.setPilotTypeName(map.get(String.valueOf(pilot.getPilotType())));
            ret.computeIfAbsent(pilot.getPilotTypeName(), key -> new ArrayList<>()).add(pilot);
        }
        return ret;
    }
}
