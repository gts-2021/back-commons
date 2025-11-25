package com.gts.backcommons.mapper;

import com.gts.backcommons.models.Reference;
import com.gts.backcommons.dtos.ReferenceResponse;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;


@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {TranslationMapper.class},  builder = @Builder(disableBuilder = true))
public interface ReferenceMapper {

    ReferenceResponse toResponse(Reference reference);

}
