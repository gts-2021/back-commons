package com.gts.backcommons.mapper;

import com.gts.backcommons.models.Translation;
import com.gts.backcommons.dtos.TranslationResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TranslationMapper {

  TranslationResponse mapToResponse(Translation translation);
}
