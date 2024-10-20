package com.gts.backcommons.exceptions;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ErrorDetails {

  @NonNull
  private LocalDateTime timestamp;

  @NonNull
  private String message;

  @NonNull
  private String path;

  @NonNull
  private String errorCode;

  private List<String> errors;
}
