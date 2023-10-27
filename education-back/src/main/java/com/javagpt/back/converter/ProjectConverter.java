package com.javagpt.back.converter;

import com.javagpt.back.entity.ProjectEntity;
import com.javagpt.back.vo.ProjectVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author JavaEdge
 * @date 2023/10/27
 */
@Mapper
public interface ProjectConverter {

    ProjectConverter INSTANCE = Mappers.getMapper(ProjectConverter.class);

    ProjectVO toVO(ProjectEntity articleEntity);
}
