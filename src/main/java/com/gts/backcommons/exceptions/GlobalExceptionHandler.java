package com.gts.backcommons.exceptions;

import com.gts.backcommons.exceptions.constants.ExceptionConstant;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
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
            .timestamp(LocalDateTime.now().toString())
            .message(exception.getMessage())
            .path(webRequest.getDescription(false))
            .errorCode(ExceptionConstant.RESOURCE_NOT_FOUND)
            .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResourceAlreadyExistException.class)
  public ResponseEntity<ErrorDetails> handleExistingResourceException(ResourceAlreadyExistException exception,
                                                                      WebRequest webRequest){
    final ErrorDetails errorDetails = ErrorDetails.builder()
          .timestamp(LocalDateTime.now().toString())
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
            .timestamp(LocalDateTime.now().toString())
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
            .timestamp(LocalDateTime.now().toString())
            .message(validationMessage)
            .path(webRequest.getDescription(false))
            .errorCode(ExceptionConstant.VALIDATION_ERROR)
            .build();

    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorDetails> handleBadCredentialsException(BadCredentialsException exception,
                                                                      WebRequest webRequest){
    final ErrorDetails errorDetails = ErrorDetails.builder()
            .timestamp(LocalDateTime.now().toString())
            .message(ExceptionConstant.BAD_CREDENTIALS)
            .errorCode(ExceptionConstant.BAD_REQUEST)
            .path(webRequest.getDescription(false))
            .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  /** JWT EXCEPTION */
  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<ErrorDetails> handleExpiredToken(ExpiredJwtException exception, HttpServletRequest request) {

    final ErrorDetails errorDetails = ErrorDetails.builder()
            .timestamp(LocalDateTime.now().toString())
            .message(exception.getMessage())
            .path(request.getRequestURI())
            .errorCode(ExceptionConstant.TOKEN_EXPIRED)
            .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(SignatureException.class)
  public ResponseEntity<?> handleInvalidSignature(SignatureException exception, HttpServletRequest request) {

    final ErrorDetails errorDetails = ErrorDetails.builder()
            .timestamp(LocalDateTime.now().toString())
            .message(exception.getMessage())
            .path(request.getRequestURI())
            .errorCode(ExceptionConstant.INVALID_SIGNATURE)
            .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(MalformedJwtException.class)
  public ResponseEntity<?> handleMalformedJwt(MalformedJwtException exception, HttpServletRequest request) {

    final ErrorDetails errorDetails = ErrorDetails.builder()
            .timestamp(LocalDateTime.now().toString())
            .message(exception.getMessage())
            .path(request.getRequestURI())
            .errorCode(ExceptionConstant.MALFORMED_TOKEN)
            .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<?> handleGenericJwt(JwtException exception, HttpServletRequest request) {

    final ErrorDetails errorDetails = ErrorDetails.builder()
            .timestamp(LocalDateTime.now().toString())
            .message(exception.getMessage())
            .path(request.getRequestURI())
            .errorCode(ExceptionConstant.INVALID_TOKEN)
            .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<?> handleForbidden(AccessDeniedException exception, HttpServletRequest request) {

    final ErrorDetails errorDetails = ErrorDetails.builder()
            .timestamp(LocalDateTime.now().toString())
            .message(exception.getMessage())
            .path(request.getRequestURI())
            .errorCode(ExceptionConstant.FORBIDDEN)
            .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
  }

  /** global exception */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                            WebRequest webRequest){

    exception.printStackTrace();

    final ErrorDetails errorDetails = ErrorDetails.builder()
            .timestamp(LocalDateTime.now().toString())
            .message(exception.getMessage())
            .path(webRequest.getDescription(false))
            .errorCode(ExceptionConstant.INTERNAL_SERVER_ERROR)
            .build();

    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
