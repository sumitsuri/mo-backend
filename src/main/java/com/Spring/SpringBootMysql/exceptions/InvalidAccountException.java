package com.Spring.SpringBootMysql.exceptions;

public class InvalidAccountException extends Exception {

  private static final long serialVersionUID = 1L;

  public InvalidAccountException(final String msg) {
    super(msg);
  }
}
