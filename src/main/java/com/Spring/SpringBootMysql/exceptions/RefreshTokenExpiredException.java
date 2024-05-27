package com.Spring.SpringBootMysql.exceptions;

public class RefreshTokenExpiredException extends RuntimeException {

  public RefreshTokenExpiredException(String message) {
    super(message);
  }
}
