package com.Spring.SpringBootMysql.exceptions;

public class UserNotFoundExceptionV2 extends RuntimeException {

  public UserNotFoundExceptionV2(String message) {
    super(message);
  }
}
