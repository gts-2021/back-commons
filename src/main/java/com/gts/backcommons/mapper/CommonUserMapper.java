package com.gts.backcommons.mapper;


import com.gts.backcommons.models.CommonUser;
import com.gts.backcommons.ssi.dtos.CommonUserDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommonUserMapper {

    CommonUser fromDto(CommonUserDTO dto);

    CommonUserDTO toDto(CommonUser user);

}
