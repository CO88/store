package com.cobb.base.error.exception;

import com.cobb.base.error.enumerate.ErrorType;
import lombok.Getter;

@Getter
public class TimeOutException extends CustomException {
  public TimeOutException() {
    super(ErrorType.TIME_OUT.toString(), ErrorType.TIME_OUT);
  }

  public TimeOutException(String message) {
    super(message, ErrorType.TIME_OUT);
  }

  public TimeOutException(String message, Throwable cause) {
    super(message, cause, ErrorType.TIME_OUT);
  }

  public TimeOutException(Throwable cause) {
    super(cause, ErrorType.TIME_OUT);
  }
}