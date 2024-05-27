package com.Spring.SpringBootMysql.exceptions;

public class ValidAuthTokenNotFoundException extends RuntimeException {

  public ValidAuthTokenNotFoundException(String message) {
    super(message);
  }
}
