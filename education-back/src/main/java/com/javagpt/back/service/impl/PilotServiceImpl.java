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

import java.sql.Array;
import java.util.*;

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


    static HashMap<String, Set<String>> hashMap = new HashMap<>();
    @Override
    public Map<String, List<Pilot>> getList() {
        List<Dictionary> pilotTypes = dictionaryService.selectList("pilot_type");
        Map<String , List<Pilot>> res = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        for (Dictionary d : pilotTypes) {
            map.put(d.getValue(), d.getLabel());
        }
        QueryWrapper<Pilot> qw = new QueryWrapper<>();
        List<Pilot> pilots = pilotMapper.selectList(qw);
        for (Pilot p : pilots) {
            p.setPilotTypeName(map.get(String.valueOf(p.getPilotType())));
            res.computeIfAbsent(p.getPilotTypeName(), key -> new ArrayList<Pilot>()).add(p);
        }
        return res;
    }
}
