package com.Spring.SpringBootMysql.exceptions;

public class CommonAuthServerException extends RuntimeException {
  public CommonAuthServerException(String message) {
    super(message);
  }
}
