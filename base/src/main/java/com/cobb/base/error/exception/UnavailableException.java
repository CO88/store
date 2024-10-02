package com.cobb.base.error.exception;

import com.cobb.base.error.enumerate.ErrorType;
import lombok.Getter;

@Getter
public class UnavailableException extends CustomException {
  public UnavailableException() {
    super(ErrorType.UNAVAILABLE.toString(), ErrorType.UNAVAILABLE);
  }

  public UnavailableException(String message) {
    super(message, ErrorType.UNAVAILABLE);
  }

  public UnavailableException(String message, Throwable cause) {
    super(message, cause, ErrorType.UNAVAILABLE);
  }

  public UnavailableException(Throwable cause) {
    super(cause, ErrorType.UNAVAILABLE);
  }
}