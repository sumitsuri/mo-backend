package com.Spring.SpringBootMysql.exceptions;

public class UserNotAuthorisedV2 extends RuntimeException {
  public UserNotAuthorisedV2(String message) {
    super(message);
  }
}
