package com.cobb.base.error.exception;

import com.cobb.base.error.enumerate.ErrorType;
import lombok.Getter;

@Getter
public class ForbiddenException extends CustomException {
  public ForbiddenException() {
    super(ErrorType.FORBIDDEN.toString(), ErrorType.FORBIDDEN);
  }

  public ForbiddenException(String message) {
    super(message, ErrorType.FORBIDDEN);
  }

  public ForbiddenException(String message, Throwable cause) {
    super(message, cause, ErrorType.FORBIDDEN);
  }

  public ForbiddenException(Throwable cause) {
    super(cause, ErrorType.FORBIDDEN);
  }
}