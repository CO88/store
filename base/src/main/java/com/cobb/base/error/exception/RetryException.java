package com.cobb.base.error.exception;

import com.cobb.base.error.enumerate.ErrorType;
import com.cobb.base.error.exception.constant.ErrorMessage;
import lombok.Getter;

@Getter
public class RetryException extends CustomException {
  public RetryException() {
    super(ErrorMessage.RETRY, ErrorType.CONFLICT);
  }

  public RetryException(String message) {
    super(message, ErrorType.CONFLICT);
  }
}
