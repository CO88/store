package com.cobb.base.error.exception;

import com.cobb.base.error.enumerate.ErrorType;
import lombok.Getter;

@Getter
public class UnauthorizedException extends CustomException {
  public UnauthorizedException() {
    super(ErrorType.UNAUTHORIZED.toString(), ErrorType.UNAUTHORIZED);
  }

  public UnauthorizedException(String message) {
    super(message, ErrorType.UNAUTHORIZED);
  }

  public UnauthorizedException(String message, Throwable cause) {
    super(message, cause, ErrorType.UNAUTHORIZED);
  }

  public UnauthorizedException(Throwable cause) {
    super(cause, ErrorType.UNAUTHORIZED);
  }
}