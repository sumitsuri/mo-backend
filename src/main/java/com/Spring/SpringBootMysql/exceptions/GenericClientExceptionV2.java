package com.Spring.SpringBootMysql.exceptions;

import com.Spring.SpringBootMysql.constants.ErrorConstants;
import com.Spring.SpringBootMysql.domains.common.ErrorDetail;
import com.Spring.SpringBootMysql.utils.Utils;

public class GenericClientExceptionV2 extends CommonAuthServerException {
  private final ErrorConstants.ErrorType errorType;
  private GenericClientException genericClientException;

  public GenericClientExceptionV2(ErrorConstants.ErrorType errorType, String message) {
    super(message);

    this.errorType = errorType;
  }

  // Adding this constructor to have GenericClientException compatibility
  public GenericClientExceptionV2(GenericClientException genericClientException, String message) {
    super(message);

    this.genericClientException = genericClientException;
    this.errorType = ErrorConstants.ErrorType.GENERIC_CLIENT_EXCEPTION_V1;
  }

  public ErrorDetail getErrorDetail() {
    // For genericClientExceptionV1 compatibility
    if (genericClientException != null) {
      return genericClientException.getErrorDetail();
    }

    var errorDetail = Utils.getErrorDetailFromErrorType(errorType);
    errorDetail.setMessage(this.getMessage());

    return errorDetail;
  }
}
