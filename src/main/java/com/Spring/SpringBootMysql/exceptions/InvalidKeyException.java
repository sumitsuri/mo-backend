package com.Spring.SpringBootMysql.exceptions;

public class InvalidKeyException extends Exception {

  private static final long serialVersionUID = 1L;

  public InvalidKeyException(final String msg) {
    super(msg);
  }
}
