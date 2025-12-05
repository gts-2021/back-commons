package com.gts.backcommons.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ErrorDetails {

  @NonNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private String timestamp;

  @NonNull
  private String message;

  @NonNull
  private String path;

  @NonNull
  private String errorCode;

  private List<String> errors;
}
