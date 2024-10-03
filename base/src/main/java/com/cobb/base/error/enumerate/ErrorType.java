package com.cobb.base.error.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
  BAD_REQUEST(HttpStatus.BAD_REQUEST),
  NOT_FOUND(HttpStatus.NOT_FOUND),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
  NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED),
  FORBIDDEN(HttpStatus.FORBIDDEN),
  TIME_OUT(HttpStatus.GATEWAY_TIMEOUT),
  UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE),
  UNHEALTHY(HttpStatus.INTERNAL_SERVER_ERROR),
  TOO_MANY_REQUEST(HttpStatus.TOO_MANY_REQUESTS),
  CONFLICT(HttpStatus.CONFLICT);

  private final HttpStatus httpStatus;
}
