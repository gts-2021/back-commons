/*
 * Copyright (c) 2024.
 */

package com.gts.backcommons.dtos;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TranslationResponse {

  private Long id;
  private String code;
  private String label;
  private String description;

}
