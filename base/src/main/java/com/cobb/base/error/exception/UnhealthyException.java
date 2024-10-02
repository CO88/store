package com.cobb.base.error.exception;

import com.cobb.base.error.enumerate.ErrorType;
import lombok.Getter;

@Getter
public class UnhealthyException extends CustomException {
  public UnhealthyException() {
    super(ErrorType.UNHEALTHY.toString(), ErrorType.UNHEALTHY);
  }

  public UnhealthyException(String message) {
    super(message, ErrorType.UNHEALTHY);
  }

  public UnhealthyException(String message, Throwable cause) {
    super(message, cause, ErrorType.UNHEALTHY);
  }

  public UnhealthyException(Throwable cause) {
    super(cause, ErrorType.UNHEALTHY);
  }
}
