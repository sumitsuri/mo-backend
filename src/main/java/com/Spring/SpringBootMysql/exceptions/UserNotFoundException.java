package com.Spring.SpringBootMysql.exceptions;

public class UserNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public UserNotFoundException(final String msg) {
    super(msg);
  }
}
