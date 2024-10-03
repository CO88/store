package com.cobb.base.error.exception;

import com.cobb.base.error.enumerate.ErrorType;
import com.cobb.base.error.exception.constant.ErrorMessage;
import lombok.Getter;

@Getter
public class BadRequestException extends CustomException {
  public BadRequestException() {
    super(ErrorMessage.BAD_REQUEST, ErrorType.BAD_REQUEST);
  }

  public BadRequestException(String message) {
    super(message, ErrorType.BAD_REQUEST);
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause, ErrorType.BAD_REQUEST);
  }

  public BadRequestException(Throwable cause) {
    super(cause, ErrorType.BAD_REQUEST);
  }
}