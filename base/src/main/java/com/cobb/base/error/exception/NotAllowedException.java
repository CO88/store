package com.cobb.base.error.exception;

import com.cobb.base.error.enumerate.ErrorType;
import lombok.Getter;

@Getter
public class NotAllowedException extends CustomException {
  public NotAllowedException() {
    super(ErrorType.NOT_ALLOWED.toString(), ErrorType.NOT_ALLOWED);
  }

  public NotAllowedException(String message) {
    super(message, ErrorType.NOT_ALLOWED);
  }

  public NotAllowedException(String message, Throwable cause) {
    super(message, cause, ErrorType.NOT_ALLOWED);
  }

  public NotAllowedException(Throwable cause) {
    super(cause, ErrorType.NOT_ALLOWED);
  }
}