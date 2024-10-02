package com.cobb.base.error.exception;

import com.cobb.base.error.enumerate.ErrorType;
import lombok.Getter;

@Getter
public class TooManyRequestException extends CustomException {
  public TooManyRequestException() {
    super(ErrorType.TOO_MANY_REQUEST.toString(), ErrorType.TOO_MANY_REQUEST);
  }

  public TooManyRequestException(String message) {
    super(message, ErrorType.TOO_MANY_REQUEST);
  }

  public TooManyRequestException(String message, Throwable cause) {
    super(message, cause, ErrorType.TOO_MANY_REQUEST);
  }

  public TooManyRequestException(Throwable cause) {
    super(cause, ErrorType.TOO_MANY_REQUEST);
  }
}