package com.Spring.SpringBootMysql.exceptions;

public class UnknownException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public UnknownException(final String msg) {
    super(msg);
  }
}
