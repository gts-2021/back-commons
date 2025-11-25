package com.gts.backcommons.dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReferenceResponse {

    private String label;
    private String description;
    private List<TranslationResponse> translations;
}
