package com.gts.backcommons.exceptions;

import com.gts.backcommons.exceptions.constants.ExceptionConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                      WebRequest webRequest){

    final ErrorDetails errorDetails = ErrorDetails.builder()
            .timestamp( LocalDateTime.now())
            .message(exception.getMessage())
            .path(webRequest.getDescription(false))
            .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResourceAlreadyExistException.class)
  public ResponseEntity<ErrorDetails> handleExistingResourceException(ResourceAlreadyExistException exception,
                                                                      WebRequest webRequest){
    final ErrorDetails errorDetails = ErrorDetails.builder()
          .timestamp( LocalDateTime.now())
          .message(exception.getMessage())
          .path(webRequest.getDescription(false))
          .errorCode(ExceptionConstant.RESOURCE_ALREADY_EXISTS)
          .build();

    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  // This specific method handles specific form validation exceptions
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorDetails> handleValidationException(ValidationException exception,
                                                                WebRequest webRequest) {

    final ErrorDetails errorDetails = ErrorDetails.builder()
            .timestamp(LocalDateTime.now())
            .message("")
            .path(webRequest.getDescription(false))
            .errorCode(ExceptionConstant.VALIDATION_ERROR)
            .errors(exception.getErrorMessages())
            .build();

    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  // This specific method handles validation exceptions with validator validation
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException exception,
                                                                WebRequest webRequest) {
    final String validationMessage = Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage();

    final ErrorDetails errorDetails = ErrorDetails.builder()
            .timestamp( LocalDateTime.now())
            .message(validationMessage)
            .path(webRequest.getDescription(false))
            .errorCode(ExceptionConstant.VALIDATION_ERROR)
            .build();

    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  // This is a global exception handler
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                            WebRequest webRequest){

    exception.printStackTrace();

    final ErrorDetails errorDetails = ErrorDetails.builder()
            .timestamp( LocalDateTime.now())
            .message(exception.getMessage())
            .path(webRequest.getDescription(false))
            .errorCode(ExceptionConstant.INTERNAL_SERVER_ERROR)
            .build();

    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
