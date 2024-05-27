package com.Spring.SpringBootMysql.exceptions;

public class InvalidEmailException extends RuntimeException {
  public InvalidEmailException(String message) {
    super(message);
  }
}
