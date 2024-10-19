package com.gts.backcommons.ssi.mapper;

import com.gts.backcommons.ssi.CommonModule;
import com.gts.backcommons.ssi.dtos.CommonModuleDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ModuleMapper {

    CommonModule fromDto(CommonModuleDTO dto);

    CommonModuleDTO toDto(CommonModule commonModule);

    List<CommonModuleDTO> toDtos(List<CommonModule> commonModules);
}
