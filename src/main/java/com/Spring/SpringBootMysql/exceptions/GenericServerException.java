package com.Spring.SpringBootMysql.exceptions;

public class GenericServerException extends CommonMerchantSvcDefaultException {
  public GenericServerException(String status, String title, String message) {
    super(status, title, message);
  }
}
