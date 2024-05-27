package com.Spring.SpringBootMysql.exceptions;

public class UserNotAuthorizedException extends RuntimeException {

  public UserNotAuthorizedException(String message) {
    super(message);
  }
}
