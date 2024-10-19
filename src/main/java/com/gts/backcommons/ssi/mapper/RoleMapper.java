package com.gts.backcommons.ssi.mapper;


import com.gts.backcommons.ssi.CommonRole;
import com.gts.backcommons.ssi.dtos.CommonRoleDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {ModuleMapper.class, FunctionalityMapper.class})
public interface RoleMapper {


    CommonRole fromDto(CommonRoleDTO dto);


    CommonRoleDTO toDto(CommonRole commonRole);

}
