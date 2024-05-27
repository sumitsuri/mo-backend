package com.Spring.SpringBootMysql.exceptions;

public class InvalidRequestExceptionV2 extends RuntimeException {
  public InvalidRequestExceptionV2(String message) {
    super(message);
  }
}
