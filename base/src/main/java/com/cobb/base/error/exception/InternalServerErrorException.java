package com.cobb.base.error.exception;

import com.cobb.base.error.enumerate.ErrorType;
import com.cobb.base.error.exception.constant.ErrorMessage;
import lombok.Getter;

@Getter
public class InternalServerErrorException extends CustomException {
  public InternalServerErrorException() {
    super(ErrorMessage.SERVER_ERROR, ErrorType.INTERNAL_SERVER_ERROR);
  }

  public InternalServerErrorException(String message) {
    super(message, ErrorType.INTERNAL_SERVER_ERROR);
  }

  public InternalServerErrorException(String message, Throwable cause) {
    super(message, cause, ErrorType.INTERNAL_SERVER_ERROR);
  }

  public InternalServerErrorException(Throwable cause) {
    super(cause, ErrorType.INTERNAL_SERVER_ERROR);
  }
}