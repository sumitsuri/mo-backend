package com.Spring.SpringBootMysql.exceptions;

public class CustomException extends RuntimeException {

  public CustomException(String message) {
    super(message);
  }
}
