package com.Spring.SpringBootMysql.exceptions;

public class ValidTimeDifferenceException extends RuntimeException {
  public ValidTimeDifferenceException(String message) {
    super(message);
  }
}
