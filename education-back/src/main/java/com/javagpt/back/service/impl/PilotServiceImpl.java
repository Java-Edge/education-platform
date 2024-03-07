package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.entity.Pilot;
import com.javagpt.back.mapper.PilotMapper;
import com.javagpt.back.service.DictService;
import com.javagpt.back.service.PilotService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.javagpt.common.constant.Constants.cache_max_dict_local_cache;
import static com.javagpt.common.constant.Constants.cache_max_pilot_local_cache;

@Service
@Slf4j
public class PilotServiceImpl extends ServiceImpl<PilotMapper, Pilot> implements PilotService {

    @Resource
    private PilotMapper pilotMapper;

    @Resource
    private DictService dictService;

    @Resource
    private Cache<String, List<Pilot>> pilotCache;

    @Resource
    private Cache<String, List<Dictionary>> dictCache;

    @Override
    public Map<String, List<Pilot>> getList() {
        List<Dictionary> pilotTypesCache = dictCache.get(cache_max_dict_local_cache, s -> {
//            log.info("pilotTypesCache过期，从 MySQL 查询");
            return dictService.selectList("pilot_type");
        });

        Map<String , List<Pilot>> ret = new HashMap<>();
        Map<String, String> dictMap = new HashMap<>();
        for (Dictionary dictionary : pilotTypesCache) {
            dictMap.put(dictionary.getValue(), dictionary.getLabel());
        }

        List<Pilot> pilotsCache = pilotCache.get(cache_max_pilot_local_cache, s -> {
            QueryWrapper<Pilot> qw = new QueryWrapper<>();
            return pilotMapper.selectList(qw);
        });
        for (Pilot pilot : pilotsCache) {
            pilot.setPilotTypeName(dictMap.get(String.valueOf(pilot.getPilotType())));
            ret.computeIfAbsent(pilot.getPilotTypeName(), key -> new ArrayList<>()).add(pilot);
        }
        return ret;
    }

    @Override
    public void refresh() {
        QueryWrapper<Pilot> qw = new QueryWrapper<>();
        List<Pilot> pilots = pilotMapper.selectList(qw);
        pilotCache.put(cache_max_pilot_local_cache, pilots);
    }

    @Override
    public void pv( Integer itemId) {
        Pilot pilot = pilotMapper.selectById(itemId);
        pilot.setId(Long.valueOf(itemId));
        pilot.setPageView(pilot.getPageView() + 1);
        pilotMapper.updateById(pilot);
    }
}
