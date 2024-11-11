package com.gts.backcommons.ssi.mapper;


import com.gts.backcommons.ssi.CommonRole;
import com.gts.backcommons.ssi.dtos.CommonRoleDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {CommonModuleMapper.class, CommonFunctionalityMapper.class})
public interface CommonRoleMapper {


    CommonRole fromDto(CommonRoleDTO dto);


    CommonRoleDTO toDto(CommonRole commonRole);

}
