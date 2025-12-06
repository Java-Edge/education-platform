package com.javaedge.application.dict.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaedge.application.dict.dto.DictionaryTypeDTO;
import com.javaedge.application.dict.param.DictionaryTypeParam;
import com.javaedge.dict.entity.DictionaryTypeEntity;
import com.javaedge.dict.repository.DictTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DictTypeService {

    private final DictTypeRepository dictTypeRepository;

    public IPage<DictionaryTypeDTO> selectList(DictionaryTypeParam dictionaryTypeParam) {
        IPage<DictionaryTypeEntity> page = dictTypeRepository.page(dictionaryTypeParam);
        List<DictionaryTypeDTO> newRecords = new ArrayList<>();
        Page<DictionaryTypeDTO> newPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        newPage.setOrders(page.orders());
        newPage.setRecords(newRecords);
        return newPage;
    }
}