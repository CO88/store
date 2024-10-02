package com.cobb.base.error.exception;

import com.cobb.base.error.enumerate.ErrorType;
import lombok.Getter;

@Getter
public class NotFoundException extends CustomException {
  public NotFoundException() {
    super(ErrorType.NOT_FOUND.toString(), ErrorType.NOT_FOUND);
  }

  public NotFoundException(String message) {
    super(message, ErrorType.NOT_FOUND);
  }

  public NotFoundException(String message, Throwable cause) {
    super(message, cause, ErrorType.NOT_FOUND);
  }

  public NotFoundException(Throwable cause) {
    super(cause, ErrorType.NOT_FOUND);
  }
}