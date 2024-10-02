package com.cobb.base.error.exception;

import com.cobb.base.error.enumerate.ErrorType;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  private final ErrorType type;

  public CustomException(ErrorType type) {
    this.type = type;
  }

  public CustomException(String message, ErrorType type) {
    super(message);
    this.type = type;
  }

  public CustomException(String message, Throwable cause, ErrorType type) {
    super(message, cause);
    this.type = type;
  }

  public CustomException(Throwable cause, ErrorType type) {
    super(cause);
    this.type = type;
  }
}
