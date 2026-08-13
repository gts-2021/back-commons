package com.gts.backcommons.ssi.mapper;

import com.gts.backcommons.ssi.CommonFunctionality;
import com.gts.backcommons.ssi.CommonModule;
import com.gts.backcommons.ssi.CommonRole;
import com.gts.backcommons.ssi.constants.RoleConstants;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommonModuleMapperTest {

    CommonModuleMapper mapper = Mappers.getMapper(CommonModuleMapper.class);

    @Test
    void toDto() {

         var funtionalities = List.of(
                 CommonFunctionality.builder()
                         .id(1L)
                         .code("DELETE_USER")
                         .build()
         );

        var module = CommonModule.builder()
                .functionalities((List<CommonFunctionality>) funtionalities)
                .build();

        var dto = mapper.toDto(module);

        assertNotNull(dto);

        var funtionalitiesDtos = dto.getFunctionalities();
        assertNotNull(funtionalitiesDtos);
        assertFalse(funtionalitiesDtos.isEmpty());

        var funDto1 = funtionalitiesDtos.getFirst();

        assertEquals("DELETE_USER", funDto1.getCode());
    }
}