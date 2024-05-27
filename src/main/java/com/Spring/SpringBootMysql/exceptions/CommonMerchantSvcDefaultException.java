package com.Spring.SpringBootMysql.exceptions;

import com.Spring.SpringBootMysql.domains.common.ErrorDetail;

public class CommonMerchantSvcDefaultException extends RuntimeException {
  private ErrorDetail errorDetail;

  public CommonMerchantSvcDefaultException(String status, String title, String message) {
    ErrorDetail err = new ErrorDetail();
    err.setStatus(status);
    err.setTitle(title);
    err.setMessage(message);
    this.errorDetail = err;
  }

  public CommonMerchantSvcDefaultException(
      String status, String title, String message, String statusCode) {
    ErrorDetail err = new ErrorDetail();
    err.setStatus(status);
    err.setTitle(title);
    err.setMessage(message);
    err.setStatusCode(statusCode);
    this.errorDetail = err;
  }

  public ErrorDetail getErrorDetail() {
    return errorDetail;
  }
}
