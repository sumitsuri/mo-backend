package com.Spring.SpringBootMysql.exceptions;

public class JwtTokenInvalidException extends RuntimeException {

  public JwtTokenInvalidException(String message) {
    super(message);
  }
}
