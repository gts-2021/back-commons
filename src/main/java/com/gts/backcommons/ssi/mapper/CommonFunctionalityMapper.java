package com.gts.backcommons.ssi.mapper;



import com.gts.backcommons.ssi.CommonFunctionality;
import com.gts.backcommons.ssi.dtos.CommonFunctionalityDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommonFunctionalityMapper {

    CommonFunctionality fromDto(CommonFunctionalityDTO dto);

    CommonFunctionalityDTO toDto(CommonFunctionality module);
}
