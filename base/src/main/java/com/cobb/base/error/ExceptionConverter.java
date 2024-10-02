package com.cobb.base.error;

import com.cobb.base.error.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;

@Service
public class ExceptionConverter {
  public CustomException convert(Throwable throwable) {
    return switch (throwable) {
      case CustomException customException -> customException;
      case ErrorResponse errorResponse -> convertInternal(errorResponse);
      case null, default -> new InternalServerErrorException(throwable);
    };
  }

  private CustomException convertInternal(ErrorResponse errorResponse) {
    int httpStatusCode = errorResponse.getStatusCode().value();
    if (httpStatusCode == HttpStatus.BAD_REQUEST.value()) {
      return new BadRequestException();
    } else if (httpStatusCode == HttpStatus.UNAUTHORIZED.value()) {
      return new UnauthorizedException();
    } else if (httpStatusCode == HttpStatus.FORBIDDEN.value()) {
      return new ForbiddenException();
    } else if (httpStatusCode == HttpStatus.NOT_FOUND.value()) {
      return new NotFoundException();
    } else if (httpStatusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
      return new NotAllowedException();
    } else if (httpStatusCode == HttpStatus.TOO_MANY_REQUESTS.value()) {
      return new TooManyRequestException();
    } else if (httpStatusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
      return new UnavailableException();
    } else if (httpStatusCode == HttpStatus.GATEWAY_TIMEOUT.value()) {
      return new TimeOutException();
    } else {
      return new InternalServerErrorException();
    }
  }
}