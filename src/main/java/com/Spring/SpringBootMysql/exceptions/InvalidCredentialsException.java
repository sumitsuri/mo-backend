package com.Spring.SpringBootMysql.exceptions;

public class InvalidCredentialsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InvalidCredentialsException(final String msg) {
    super(msg);
  }
}
