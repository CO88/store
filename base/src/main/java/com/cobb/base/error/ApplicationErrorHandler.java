package com.cobb.base.error;

import com.cobb.base.error.enumerate.ErrorType;
import com.cobb.base.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApplicationErrorHandler {

  private final ExceptionConverter exceptionConverter;

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handle(Throwable throwable) {
    CustomException customException = exceptionConverter.convert(throwable);
    ErrorType errorType = customException.getType();
    ErrorResponse errorResult = new ErrorResponse(customException.getMessage());
    MDC.clear();
    return new ResponseEntity<>(errorResult, errorType.getHttpStatus());

  }

  public record ErrorResponse(String message) {

  }
}
