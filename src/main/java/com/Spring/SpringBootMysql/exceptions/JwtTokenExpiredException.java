package com.Spring.SpringBootMysql.exceptions;

public class JwtTokenExpiredException extends RuntimeException {

  public JwtTokenExpiredException(String message) {
    super(message);
  }
}
