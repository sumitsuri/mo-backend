package com.Spring.SpringBootMysql.exceptions;

public class GenericClientException extends CommonMerchantSvcDefaultException {
  public GenericClientException(String status, String title, String message) {
    super(status, title, message);
  }
}
