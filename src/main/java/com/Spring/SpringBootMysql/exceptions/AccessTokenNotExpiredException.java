package com.Spring.SpringBootMysql.exceptions;

public class AccessTokenNotExpiredException extends RuntimeException {

  public AccessTokenNotExpiredException(String message) {
    super(message);
  }
}
