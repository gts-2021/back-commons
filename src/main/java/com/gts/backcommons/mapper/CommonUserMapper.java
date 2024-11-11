package com.gts.backcommons.mapper;


import com.gts.backcommons.models.CommonUser;
import com.gts.backcommons.ssi.dtos.CommonUserDTO;
import com.gts.backcommons.ssi.mapper.CommonRoleMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {CommonRoleMapper.class})
public interface CommonUserMapper {

    CommonUser fromDto(CommonUserDTO dto);

    CommonUserDTO toDto(CommonUser user);

    List<CommonUser> fromDto(List<CommonUserDTO> dto);

    List<CommonUserDTO> toDto(List<CommonUser> user);

}
