package com.Spring.SpringBootMysql.exceptions;

public class UserAccountLockedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public UserAccountLockedException(final String msg) {
    super(msg);
  }
}
