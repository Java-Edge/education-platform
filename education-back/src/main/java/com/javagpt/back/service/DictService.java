package com.javagpt.back.service;

import com.javagpt.back.entity.Dictionary;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DictService extends IService<Dictionary> {

    List<Dictionary> selectList(String typeKey);
}
