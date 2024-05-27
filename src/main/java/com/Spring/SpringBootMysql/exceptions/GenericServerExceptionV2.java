package com.Spring.SpringBootMysql.exceptions;

import com.Spring.SpringBootMysql.constants.ErrorConstants;

public class GenericServerExceptionV2 extends CommonAuthServerException {
  private final ErrorConstants.ErrorType errorType;

  public GenericServerExceptionV2(ErrorConstants.ErrorType errorType, String message) {
    super(message);

    this.errorType = errorType;
  }

  public ErrorConstants.ErrorType getErrorType() {
    return errorType;
  }
}
