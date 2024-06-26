package com.javagpt.back.service;

import com.javagpt.back.entity.Pilot;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface PilotService extends IService<Pilot> {

    Map<String, List<Pilot>> getList();

    void refresh();

    void pv(Integer itemId);
}