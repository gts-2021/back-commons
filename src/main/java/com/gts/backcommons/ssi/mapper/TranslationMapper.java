package com.gts.backcommons.ssi.mapper;

import com.gts.backcommons.models.Translation;
import com.gts.backcommons.ssi.dtos.TranslationResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TranslationMapper {

  TranslationResponse mapToResponse(Translation translation);
}
